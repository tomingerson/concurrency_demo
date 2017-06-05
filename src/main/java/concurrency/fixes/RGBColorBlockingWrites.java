package concurrency.fixes;

/**
 * @author Created by tom on 03.06.2017.
 */
public class RGBColorBlockingWrites {

    private int r;
    private int g;
    private int b;

    public RGBColorBlockingWrites(int r, int g, int b) {
        checkRGBVals(r, g, b);
        this.r = r;
        this.g = g;
        this.b = b;
    }

    private static void checkRGBVals(int r, int g, int b) {
        if (r < 0 || r > 255 || g < 0 || g > 255 ||
                b < 0 || b > 255) {
            throw new IllegalArgumentException();
        }
    }

    public void setColor(int r, int g, int b) {
        checkRGBVals(r, g, b);
        // TODO synchronize access to member variables
        this.r = r;
        this.g = g;
        this.b = b;
    }

    /**
     * returns color in an array of three ints: R, G, and B
     */
    public int[] getColor() {
        int[] retVal = new int[3];
        // TODO synchronize access to member variables
        retVal[0] = r;
        retVal[1] = g;
        retVal[2] = b;
        return retVal;
    }

    public void invert() {
        // TODO synchronize access to member variables
        r = 255 - r;
        g = 255 - g;
        b = 255 - b;
    }
}
