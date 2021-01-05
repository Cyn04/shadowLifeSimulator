/**
 * Represents a Direction.
 */
public class Direction {
    /**
     * Defines direction UP DOWN LEFT RIGHT by assigning them to integers.
     */
    public static final int UP = 1;
    public static final int DOWN = -1;
    public static final int LEFT = -2;
    public static final int RIGHT = 2;

    /**
     * Rotates direction 90 degrees clockwise.
     * @param n current direction.
     */
    public static int ninetyClockwise(int n) {
        if (n == UP){
            return RIGHT;
        }
        else if (n == DOWN){
            return LEFT;
        }
        else if (n == LEFT){
            return UP;
        }
        else{
            return DOWN;
        }
    }

    /**
     * Rotates direction 270 degrees clockwise.
     * @param n current direction.
     */
    public static int twoSeventyClockwise(int n) {
        if (n == LEFT) {
            return DOWN;
        } else if (n == RIGHT) {
            return UP;
        } else if (n == UP) {
            return LEFT;
        } else {
            return RIGHT;
        }
    }
}
