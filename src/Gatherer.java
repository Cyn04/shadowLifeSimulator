import java.util.ArrayList;

/**
 * Represents a Gatherer.
 */
public class Gatherer extends Animal {
    /**
     * Defines Gatherer type and its image.
     */
    public static final String TYPE = "Gatherer";
    public static final String GATHERER_IMAGE =  "res/images/gatherer.png";
    private int tickCounter = -1;
    private static final int RESET_FREQUENCY = 5;


    /**
     * Creates a gatherer at given xy point with direction set to left, active set to true, carrying set to false.
     * @param x x coordinate of the gatherer.
     * @param y y coordinate of the gatherer.
     */
    public Gatherer(int x, int y) {
        super(GATHERER_IMAGE, TYPE, x, y, Direction.LEFT, true, false);
    }


    /**
     * Creates a gatherer at given xy point with given direction, active set to true, carrying set to false.
     * @param x x coordinate of the gatherer.
     * @param y y coordinate of the gatherer.
     * @param direction direction of the gatherer.
     */
    public Gatherer(int x, int y, int direction) {
        super(GATHERER_IMAGE, TYPE, x, y, direction, true, false);
    }


    /**
     * An update method to reset the tick counter and move the Gatherer in set direction if still active.
     */
    @Override
    public void update() {
        if (++tickCounter == RESET_FREQUENCY) {
            tickCounter = 0;
        }
        this.checkActive();
    }


    /**
     * Makes two new gatherers when gatherer standing on a mitosis pool
     * and adds them to the createBuffer.
     * Adds current gatherer to deleteBuffer.
     **/
    private void split(ArrayList<Actor> createBuffer, ArrayList<Actor> deleteBuffer){
        if (getDirection() == Direction.RIGHT || getDirection() == Direction.LEFT) {
            Animal a = new Gatherer(getX(), getY(), Direction.UP);
            Animal b = new Gatherer(getX(), getY(), Direction.DOWN);
            createBuffer.add(a);
            createBuffer.add(b);
            a.move(0, -ShadowLife.TILE_SIZE);
            b.move(0, ShadowLife.TILE_SIZE);
        } else {
            Animal a = new Gatherer(getX(), getY(), Direction.LEFT);
            Animal b = new Gatherer(getX(), getY(), Direction.RIGHT);
            createBuffer.add(a);
            createBuffer.add(b);
            a.move(-ShadowLife.TILE_SIZE, 0);
            b.move(ShadowLife.TILE_SIZE, 0);
        }
        deleteBuffer.add(this);
    }


    /**
     Takes fruit from fruit container, reduces fruit num by one.
     */
    private void getFruit(Actor actor){
        if (((FruitContainer) actor).getFruitNum() > 0) {
            ((FruitContainer) actor).reduceOneFruit();
            this.setCarrying(true);
            this.setDirection(this.getDirection() * -1);
        }
    }


    /**
     Puts fruit to fruit container, adds fruit num by one.
     */
    private void putFruit(Actor actor){
        if (this.isCarrying()) {
            this.setCarrying(false);
            ((FruitContainer) actor).addOneFruit();
        }
        this.setDirection(this.getDirection() * -1);
    }


    /**
     * Method to check each gatherer in game and make appropriate updates based on
     * the xy point that the gatherer is standing on.
     * @param gathererActors Arraylist that stores all the actors that a gatherer interacts with.
     * @param createBuffer Arraylist that stores the new gatherers you want to add.
     * @param deleteBuffer Arraylist that stores the gatherers you want to delete.
     */
    public void checkEachGatherer(ArrayList<Actor> gathererActors, ArrayList<Actor> createBuffer, ArrayList<Actor> deleteBuffer){
        for (Actor actor : gathererActors) {
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
                if (actor.getActorType().equals("Tree") && !this.isCarrying()) {
                    this.getFruit(actor);
                }
                if (actor.getActorType().equals("GoldenTree") && !this.isCarrying()) {
                    this.setCarrying(true);
                    this.setDirection(this.getDirection() * -1);
                }
                if (actor.getActorType().equals("Stockpile") || actor.getActorType().equals("Hoard")) {
                    this.putFruit(actor);
                }
            }
        }
    }
}
