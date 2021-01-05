/**
 * Represents a Direction.
 */

public class Sign extends Actor {
    /**
     * Defines Direction type.
     */
    public static final String TYPE = "Sign";
    private final int direction;


    /**
     * Creates a sign with the given xy integers, direction, and filename.
     * @param filename file name of the image used for the sign.
     * @param direction the direction of the sign.
     * @param y the y coordinate of the sign.
     * @param x the x coordinate of the sign.
     */
    public Sign(int x, int y, int direction, String filename) {
        super(filename, TYPE, x, y);
        this.direction = direction;
    }


    /**
     * Returns the current direction.
     */
    public int getDirection() {
        return direction;
    }


    /**
     * Overrides the implemented abstract method from Actor class.
     */
    @Override
    public void update() {}
}
