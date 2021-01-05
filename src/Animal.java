/**
 Represents moving Animals: Gatherer, Thief, and their identical attributes and methods.
 Credit: Parts of Sample solution from Project 1 used in checkActive method.
 */
public abstract class Animal extends Actor {
    private int direction;
    private boolean active;
    private boolean carrying;


    /**
     * Creates an Animal with given image, type, xy point, direction, active state and carrying state.
     * @param filename the file name of the image used for the animal.
     * @param type the type of animal, thief or gatherer.
     * @param y the y coordinate of the animal
     * @param x the x coordinate of the animal.
     * @param direction direction of animal.
     * @param active state of whether active is true or false.
     * @param carrying state of whether carrying is true or false.
     */
    public Animal(String filename, String type, int x, int y, int direction, boolean active, boolean carrying){
        super(filename, type, x, y);
        this.direction = direction;
        this.active = active;
        this.carrying = carrying;
    }


    /**
     * Returns the Animal's current direction.
     */
    public int getDirection() {
        return direction;
    }


    /**
     * Sets the Animal's current direction.
     * @param direction the direction to set.
     */
    public void setDirection(int direction) {
        this.direction = direction;
    }


    /**
     * Returns the Animal's active state.
     */
    public boolean isActive() {
        return active;
    }


    /**
     * Returns the Animal's carrying state.
     */
    public boolean isCarrying() {
        return carrying;
    }


    /**
     * Sets the Animal's carrying state.
     * @param carrying the state of carrying to set.
     */
    public void setCarrying(boolean carrying) {
        this.carrying = carrying;
    }


    /**
     * Moves the animal if it is still active based on its current direction.
     **/
    public void checkActive(){
        if (active) {
            switch (direction) {
                case Direction.UP:
                    move(0, -ShadowLife.TILE_SIZE);
                    break;
                case Direction.DOWN:
                    move(0, ShadowLife.TILE_SIZE);
                    break;
                case Direction.LEFT:
                    move(-ShadowLife.TILE_SIZE, 0);
                    break;
                case Direction.RIGHT:
                    move(ShadowLife.TILE_SIZE, 0);
                    break;
            }
        }
    }


    /**
     * Makes current Animal inactive and moves it one step backwards, 64 pixels in its opposite direction.
     */
    public void stopOnFence(){
        active = false;
        if (direction == Direction.LEFT) {
            move(ShadowLife.TILE_SIZE, 0);
        }
        else if (direction == Direction.RIGHT) {
            move(-ShadowLife.TILE_SIZE, 0);
        }
        else if (direction == Direction.UP) {
            move(0, ShadowLife.TILE_SIZE);
        }
        else if (direction == Direction.DOWN) {
            move(0, -ShadowLife.TILE_SIZE);
        }
    }


    /**
     * Changes the direction of the actor to follow the sign's direction.
     * @param actor the actor that steps on the sign.
     */
    public void followSign(Actor actor){
        direction = (((Sign)actor).getDirection());
    }


    /**
     * Moves an animal 64 pixels in its direction.
     * @param deltaX integer to be added to current x position.
     * @param deltaY integer to be added to current y position.
     */
    public void move(int deltaX, int deltaY) {
        setX(getX() + deltaX);
        setY(getY() + deltaY);
    }


    /**
     * Overrides the implemented abstract method from Actor class.
     */
    @Override
    public void update() {
    }
}
