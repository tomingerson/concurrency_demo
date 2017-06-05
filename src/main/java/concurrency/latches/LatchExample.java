package concurrency.latches;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * @author Created by tom on 04.06.2017.
 */
public class LatchExample {

    private static final Logger LOG = LoggerFactory.getLogger(LatchExample.class);

    public static void main(String... args) {
        final CountDownLatch latch = new CountDownLatch(2);

        final Random rand = new Random();

        final int[] part1 = new int[1000];
        Arrays.setAll(part1, (i)-> rand.nextInt());
        final IntCarrier result1 = new IntCarrier(0);
        final Thread firstPart = new Thread(new Summer(part1, latch, result1));

        final int[] part2 = new int[100000000];
        Arrays.setAll(part2, (i)-> rand.nextInt());
        final IntCarrier result2 = new IntCarrier(0);
        final Thread secondPart = new Thread(new Summer(part2, latch, result2));

        firstPart.start();
        secondPart.start();

        try {
            latch.await();
            LOG.info("results: " + result1.getValue() + result2.getValue());
        } catch (InterruptedException ex) {
            LOG.error("got interrupted", ex);
        }

    }

    /**
     * takes an array of ints and returns the sum of all values to the carrier.
     */
    private static class Summer implements Runnable {

        private static final Logger LOG = LoggerFactory.getLogger(Summer.class);

        private final int[] toSum;
        private final CountDownLatch latch;

        private IntCarrier result;

        Summer(final int[] toSum, final CountDownLatch latch, final IntCarrier result) {
            this.toSum = toSum;
            this.latch = latch;
            this.result = result;
        }

        @Override
        public void run() {
            LOG.info(Thread.currentThread().getName() + " firing up");
            Arrays.stream(toSum).reduce((a, b) -> a + b).ifPresent(result::setValue);
            LOG.info(Thread.currentThread().getName() + " has result " + result.getValue());
            latch.countDown();
        }
    }

    /**
     * just a bean holding an int value
     */
    private static class IntCarrier {
        private int value;

        IntCarrier(int value) {
            this.value = value;
        }

        int getValue() {
            return value;
        }

        void setValue(int value) {
            this.value = value;
        }
    }

}

