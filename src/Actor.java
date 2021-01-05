import bagel.Image;

/**
 * Represents an abstract Actor, an object that is rendered within the simulation.
 * Credit: Implemented sample solution from Project 1.
 */
public abstract class Actor {
    private int x;
    private int y;

    private final Image image;
    private final String type;

    /**
     * Creates an Actor, with given image, type, at the given xy point on screen.
     * @param filename the file name of the image used for the actor.
     * @param type the type of actor the object is.
     * @param y the y coordinate of the actor.
     * @param x the x coordinate of the actor.
     */
    public Actor(String filename, String type, int x, int y) {
        image = new Image(filename);
        this.type = type;
        this.x = x;
        this.y = y;
    }

    /**
     * Gets current x coordinate.
     */
    public int getX() {
        return x;
    }

    /**
     * Sets current x coordinate.
     * @param x the x coordinate to set for the actor.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Gets current y coordinate.
     */
    public int getY() {
        return y;
    }

    /**
     * Sets current y coordinate.
     * @param y the y coordinate to set for the actor.
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Gets current type
     */
    public String getActorType() {
        return type;
    }

    /**
     * A tick method that calls the update method for an Actor.
     */
    public final void tick() {
        update();
    }


    /**
     * Draws the actor on screen at point xy using its given image.
     */
    public void render() {
        image.drawFromTopLeft(x, y);
    }


    /**
     * Updates each actor.
     */
    public abstract void update();
}
