package concurrency.motivation;

/**
 * Interview Question (for example by Google)
 * Taken from http://www.programcreek.com/2014/02/how-to-make-a-method-thread-safe-in-java/
 */
public class Example1_counter {
    private static int counter = 0;

    public static int getNextCount() {
        counter = counter + 1;
        return counter;
    }
}
