/**
 * Represents a StationaryObject, an object that does not require 'moving'
 * and need no other attribute other than its location, type, and image
 * to perform within the simulation.
 * Types include: Pool, Pad, Fence, GoldenTree
 */
public class StationaryObject extends Actor{

    /**
     * Passes the image, type and xy location to its parent class, Actor.
     * @param image the file name of the image used for the stationary object.
     * @param type the type of stationary object it is.
     * @param y the y coordinate of the stationary object.
     * @param x the x coordinate of the stationary object.
     */
    public StationaryObject(int x, int y, String type, String image) {
        super(image, type, x, y);
    }


    /**
     * Overrides the implemented abstract method from Actor class.
     */
    @Override
    public void update() {}
}
