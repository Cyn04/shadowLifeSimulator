/**
 * Represents a FruitContainer, an object that requires a fruit count that could
 * increase or decrease throughout simulation.
 * Types include: Tree, Stockpile, Hoard
 */

public class FruitContainer extends Actor{
    private int fruitNum;

    /**
     * Creates a FruitContainer with given image, type, location and initial fruit number.
     * @param filename the file name of the image used for the fruit container.
     * @param type the type of fruit container the object is.
     * @param y the y coordinate of the fruit container.
     * @param x the x coordinate of the fruit container.
     * @param fruitNum the initial fruit number.
     */
    public FruitContainer(String filename, String type, int x, int y, int fruitNum){
        super(filename, type, x, y);
        this.fruitNum = fruitNum;
    }


    /**
     * Gets the current fruit number of the FruitContainer.
     */
    public int getFruitNum() {
        return fruitNum;
    }


    /**
     * Reduces one fruit from the FruitContainer if it still has fruit.
     */
    public void reduceOneFruit(){
        if (fruitNum != 0) {
            fruitNum --;
        }
    }


    /**
     * Adds one fruit to the FruitContainer.
     */
    public void addOneFruit(){
        fruitNum++;
    }


    /**
     * Overrides the implemented abstract method from Actor class.
     */
    @Override
    public void update() {
    }

}
