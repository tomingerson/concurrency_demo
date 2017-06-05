package concurrency.deadlock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Example borrowed from
 * http://docs.oracle.com/javase/tutorial/essential/concurrency/deadlock.html
 *
 * @author Created by tom on 04.06.2017.
 */
public class DeadlockReentrant {

    private static final Logger LOG = LoggerFactory.getLogger(DeadlockReentrant.class);

    public static void main(String[] args) {
        final Friend alphonse = new Friend("Alphonse");
        final Friend gaston = new Friend("Gaston");

        final Runnable runnable1 = () -> alphonse.bow(gaston);
        final Runnable runnable2 = () -> gaston.bow(alphonse);

        new Thread(runnable1).start();
        new Thread(runnable2).start();
    }

    static class Friend {
        private final String name;

        private final ReentrantLock lock = new ReentrantLock();

        Friend(String name) {
            this.name = name;
        }

        String getName() {
            return this.name;
        }

        void bow(Friend bower) {
            // TODO utilize lock with tryLock and a timeout of 5 seconds; method is not
            // synchronized any more!
            LOG.debug(Thread.currentThread().getName() + ": Lock acquired.");

            LOG.info(String.format("%s: %s  has bowed to me!%n", this.name, bower.getName()));
            bower.bowBack(this);

            LOG.debug(Thread.currentThread().getName() + ": Lock released.");
        }

        void bowBack(Friend bower) {
            // TODO utilize lock with tryLock and a timeout of 5 seconds; method is not
            // synchronized any more!
            LOG.debug(Thread.currentThread().getName() + ": Lock acquired.");

            LOG.info(String.format("%s: %s  has bowed back to me!%n", this.name, bower.getName()));

            LOG.debug(Thread.currentThread().getName() + ": Lock released.");
        }
    }
}
