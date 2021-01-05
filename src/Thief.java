import java.util.ArrayList;

/**
 * Represents a Thief.
 */
public class Thief extends Animal{
    /**
     * Defines Thief type and its image.
     */
    public static final String TYPE = "Thief";
    public static final String THIEF_IMAGE =  "res/images/thief.png";
    private int tickCounter = -1;
    private static final int RESET_FREQUENCY = 5;
    private boolean consuming;

    /**
     * Creates a thief at given xy point with direction set to up, active set to true, carrying set to false.
     * @param x x coordinate of the thief.
     * @param y y coordinate of the thief.
     */
    public Thief(int x, int y) {
        super(THIEF_IMAGE, TYPE, x, y, Direction.UP, true, false);
        consuming = false;
    }

    /**
     * Creates a thief at given xy point with given direction, active set to true, carrying and consuming set to false.
     * @param x x coordinate of the thief.
     * @param y y coordinate of the thief.
     * @param direction direction of the thief.
     */
    public Thief(int x, int y, int direction) {
        super(THIEF_IMAGE, TYPE, x, y, direction, true, false);
        consuming = false;
    }


    /**
     * Moves the thief in its set direction if still active.
     */
    @Override
    public void update() {
        if (++tickCounter == RESET_FREQUENCY) {
            tickCounter = 0;
        }
        this.checkActive();
    }

    /**
     * Makes two new thieves when thief standing on a mitosis pool
     * and adds them to the createBuffer. Adds current thief to deleteBuffer.
     */
    private void split(ArrayList<Actor> createBuffer, ArrayList<Actor> deleteBuffer){
        if (getDirection() == Direction.RIGHT || getDirection() == Direction.LEFT) {
            Animal a = new Thief(getX(), getY(), Direction.UP);
            Animal b = new Thief(getX(), getY(), Direction.DOWN);
            createBuffer.add(a);
            createBuffer.add(b);
            a.move(0, -ShadowLife.TILE_SIZE);
            b.move(0, ShadowLife.TILE_SIZE);
        } else {
            Animal a = new Thief(getX(), getY(), Direction.LEFT);
            Animal b = new Thief(getX(), getY(), Direction.RIGHT);
            createBuffer.add(a);
            createBuffer.add(b);
            a.move(-ShadowLife.TILE_SIZE, 0);
            b.move(ShadowLife.TILE_SIZE, 0);
        }
        deleteBuffer.add(this);
    }


    /**
     * Takes fruit from fruit container, reduces fruit num by one.
     */
    private void getTreeFruit(Actor actor){
        if (((FruitContainer) actor).getFruitNum() > 0) {
            ((FruitContainer) actor).reduceOneFruit();
            this.setCarrying(true);
        }
    }


    /**
     * When thief is standing on a hoard, sets consuming, carrying and direction to appropriate states.
     */
    private void meetsHoard(Actor actor){
        if (consuming) {
            consuming = false;
            if (!this.isCarrying()) {
                if (((FruitContainer) actor).getFruitNum() > 0) {
                    this.setCarrying(true);
                    ((FruitContainer) actor).reduceOneFruit();
                }
                else {
                    setDirection(Direction.ninetyClockwise(getDirection()));
                }
            }
        }
        else if (this.isCarrying()) {
            this.setCarrying(false);
            ((FruitContainer) actor).addOneFruit();
            setDirection(Direction.ninetyClockwise(getDirection()));
        }
    }


    /**
     * When thief is standing on a stockpile, sets consuming, carrying and direction to appropriate states.
     */
    private void meetsStockpile(Actor actor){
        if (!this.isCarrying()) {
            if (((FruitContainer) actor).getFruitNum() > 0) {
                this.setCarrying(true);
                consuming = false;
                ((FruitContainer) actor).reduceOneFruit();
                setDirection(Direction.ninetyClockwise(getDirection()));
            }
        }
        else {
            setDirection(Direction.ninetyClockwise(getDirection()));
        }
    }


    /**
     * Method to check each thief in game and make appropriate updates based on
     * the xy point that the thief is standing on.
     * @param thiefActors that stores all the actors that a thief interacts with.
     * @param createBuffer Arraylist that stores the new thieves you want to add.
     * @param deleteBuffer Arraylist that stores the thieves you want to delete.
     */
    public void checkEachThief(ArrayList<Actor> thiefActors, ArrayList<Actor> createBuffer, ArrayList<Actor> deleteBuffer){
        for (Actor actor : thiefActors) {
            if (actor.getX() == getX() && actor.getY() == getY()) {
                if (actor.getActorType().equals("Fence")) {
                    this.stopOnFence();
                }
                if (actor.getActorType().equals("Pool")) {
                    this.split(createBuffer, deleteBuffer);
                }
                if (actor.getActorType().equals("Sign")) {
                    this.followSign(actor);
                }
                if (actor.getActorType().equals("Pad")) {
                    consuming = true;
                }
                if (actor.getActorType().equals("Gatherer")) {
                    setDirection(Direction.twoSeventyClockwise(getDirection()));
                }
                if (actor.getActorType().equals("Tree") && !this.isCarrying()) {
                    this.getTreeFruit(actor);
                }
                if (actor.getActorType().equals("GoldenTree") && !this.isCarrying()) {
                    this.setCarrying(true);
                }
                if (actor.getActorType().equals("Hoard")) {
                    this.meetsHoard(actor);
                }
                if (actor.getActorType().equals("Stockpile")) {
                    this.meetsStockpile(actor);
                }
            }
        }
    }
}
