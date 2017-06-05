package concurrency.deadlock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Example borrowed
 * from http://docs.oracle.com/javase/tutorial/essential/concurrency/deadlock.html
 *
 * @author Created by tom on 04.06.2017.
 */
public class Deadlock {

    private static final Logger LOG = LoggerFactory.getLogger(Deadlock.class);

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

        Friend(String name) {
            this.name = name;
        }

        String getName() {
            return this.name;
        }

        synchronized void bow(Friend bower) {
            LOG.info(String.format("%s: %s  has bowed to me!%n", this.name,
                    bower.getName()));
            bower.bowBack(this);
        }

        synchronized void bowBack(Friend bower) {
            LOG.info(String.format("%s: %s  has bowed back to me!%n", this.name,
                    bower.getName()));
        }
    }
}
