import bagel.AbstractGame;
import bagel.Font;
import bagel.Image;
import bagel.Input;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

/**
 *  Project 2a - SWEN20003 - 10/2020
 *  Each gatherer, thief, and other element begins at a location specified in a worldFile and
 *  follows a set of rules to determine their behaviour. The simulation halts when
 *  maxTime has elapsed or all Gatherers and Thieves is inactive.
 *
 * @author Cynthia Joseph (938358)
 * Credit: Parts of Sample solution from Project 1 used in loadActors method and update method.
 */
public class ShadowLife extends AbstractGame {
    /**
     * Defines the initial fruit numbers for a tree, stockpile and hoard.
     * Defines the tile size in which Animals will move with.
     */
    public static final int INITIAL_TREE_FRUIT = 3;
    public static final int INITIAL_STOCKPILE_FRUIT = 0;
    public static final int INITIAL_HOARD_FRUIT = 0;
    public static final int TILE_SIZE = 64;

    private static long tickCount = 0;
    private static long TICK_TIME;
    private static long maxTicks;
    private long lastTick = 0;

    private final Font font = new Font("res/VeraMono.ttf", 24);
    private final Image background = new Image("res/images/background.png");

    private final ArrayList<Actor> actors = new ArrayList<>();
    private final ArrayList<Actor> actorsForGatherer = new ArrayList<>();
    private final ArrayList<Actor> actorsForThief = new ArrayList<>();
    private final ArrayList<Actor> createBuffer= new ArrayList<>();
    private final ArrayList<Actor> deleteBuffer= new ArrayList<>();

    /**
     * This is the main method. Checks if command line arguments are valid, makes a new game and runs it.
     */
    public static void main(String[] args) {
        checkValidCommandLine(args);

        TICK_TIME = Integer.parseInt(args[0]);
        maxTicks = Integer.parseInt(args[1]);
        String worldFile = args[2];

        new ShadowLife(worldFile).run();
    }


    /**
     * Calls the loadActors method and passes in the world file.
     * @param worldFile File name to generate game.
     */
    public ShadowLife(String worldFile) {
        loadActors(worldFile);
    }


    /**
     * Reads the input file while checking errors,
     * creates new actors, and adds them to actors,
     * actorsForGatherer and actorsForThief arraylists.
     * actorsForGatherer and actorsForThief only contain references to
     * the actors interacting with gatherer and thief.
     @param worldFile File name to generate game.
     */
    private void loadActors(String worldFile) {
        BufferedReader csvReader = null;
        try  {
            csvReader = new BufferedReader(new FileReader(worldFile));
        }
        catch (Exception e) {
            System.out.println("error: filename not found");
            System.exit(-1);
        }
        String row;
        int lineNum = 1;
        try  {
            while ((row = csvReader.readLine()) != null) {
                char lastChar = row.charAt(row.length() - 1);
                if (lastChar == ','){
                    throw new Exception("InvalidCommasException");
                }
                String[] parts = row.split(",");
                if (parts.length != 3) {
                    throw new Exception("InvalidCommasException");
                }
                String type = parts[0];
                int x = Integer.parseInt(parts[1]);
                int y = Integer.parseInt(parts[2]);
                switch (type) {
                    case "Tree":
                        actors.add(new FruitContainer("res/images/tree.png", "Tree", x, y,
                                INITIAL_TREE_FRUIT));
                        break;
                    case "Stockpile":
                        actors.add(new FruitContainer("res/images/cherries.png", "Stockpile", x, y,
                                INITIAL_STOCKPILE_FRUIT));
                        break;
                    case "Hoard":
                        actors.add(new FruitContainer("res/images/hoard.png", "Hoard", x, y,
                                INITIAL_HOARD_FRUIT));
                        break;
                    case "GoldenTree":
                        actors.add(new StationaryObject(x, y, "GoldenTree", "res/images/gold-tree.png"));
                        break;
                    case "Pad":
                        actors.add(new StationaryObject(x, y, "Pad", "res/images/pad.png"));
                        break;
                    case "Fence":
                        actors.add(new StationaryObject(x, y, "Fence", "res/images/fence.png"));
                        break;
                    case "Pool":
                        actors.add(new StationaryObject(x, y, "Pool", "res/images/pool.png"));
                        break;
                    case "SignUp":
                        actors.add(new Sign(x, y, Direction.UP, "res/images/up.png"));
                        break;
                    case "SignDown":
                        actors.add(new Sign(x, y, Direction.DOWN, "res/images/down.png"));
                        break;
                    case "SignLeft":
                        actors.add(new Sign(x, y, Direction.LEFT, "res/images/left.png"));
                        break;
                    case "SignRight":
                        actors.add(new Sign(x, y, Direction.RIGHT, "res/images/right.png"));
                        break;
                    case Gatherer.TYPE:
                        actors.add(new Gatherer(x, y));
                        break;
                    case Thief.TYPE:
                        actors.add(new Thief(x, y));
                        break;
                    default:
                        throw new Exception("InvalidTypeException");
                }
                lineNum++;
            }
            createSubArrays();
        }
        catch (Exception e) {
            e.printStackTrace();
            String out = String.format("error: in file \"%s\" at line %d", worldFile, lineNum);
            System.out.println(out);
            System.exit(-1);
        }
    }


    /**
     * For every tick, actors' state will change appropriately.
     * new actors (from pool interaction) will be added to createBuffer
     * and actors to be deleted will be added to deleteBuffer.
     * The arraylist of actors will then be updated after the loop.
     * All images will then be rendered.
     */
    @Override
    protected void update(Input input) {
        //Halts world if all active animals is inactive
        checkActive();

        //After every tick, update the actors
        if (System.currentTimeMillis() - lastTick > TICK_TIME) {
            tickCount++;

            if (tickCount>= maxTicks){
                System.out.println("Timed out");
                System.exit(-1);
            }
            lastTick = System.currentTimeMillis();
            for (Actor actor : actors) {
                if (actor != null) {
                    actor.tick();
                }
            }

            // Loops through actors array and updates states of each actor
            for (Actor actor : actors) {
                if (actor instanceof Gatherer){
                    ((Gatherer) actor).checkEachGatherer(actorsForGatherer, createBuffer, deleteBuffer);
                }
                else if (actor instanceof Thief){
                    ((Thief) actor).checkEachThief(actorsForThief, createBuffer, deleteBuffer);
                }
            }

            if (!createBuffer.isEmpty()){
                actors.addAll(createBuffer);
                actorsForThief.addAll(createBuffer);
                createBuffer.clear();
            }
            if (!deleteBuffer.isEmpty()) {
                actors.removeAll(deleteBuffer);
                actorsForThief.removeAll(deleteBuffer);
                deleteBuffer.clear();
            }
        }
        renderAllImages();
    }


    /**
     * Checks if given commandline arguments are valid.
     */
    private static void checkValidCommandLine(String[] args){
        try  {
            if (args.length != 3|| Integer.parseInt(args[0])<0 || Integer.parseInt(args[1])<0) {
                throw new Exception("InvalidInputException");
            }
        }
        catch (Exception e) {
            System.out.println("usage: ShadowLife <tick rate> <max ticks> <world file>");
            System.exit(-1);
        }
    }


    /**
     * Halts the simulation if all animals are not active.
     */
    private void checkActive(){
        int flag = 0;
        for (Actor actor : actors) {
            if (actor instanceof Animal){
                if (((Animal) actor).isActive()){
                    flag++;
                }
            }
        }
        if (flag == 0){
            printAndExit();
        }
    }


    /**
     * Prints the number of fruits in a stockpile or hoard in order.
     */
    private void printAndExit(){
        System.out.println(tickCount + " ticks");
        for (Actor a: actors){
            if (a.getActorType().equals("Stockpile")||a.getActorType().equals("Hoard")){
                System.out.println(((FruitContainer)a).getFruitNum());
            }
        }
        System.exit(0);
    }


    /**
     * Draws all images and fruit numbers
     */
    private void renderAllImages(){
        background.drawFromTopLeft(0, 0);
        for (Actor actor : actors) {
            if (actor != null) {
                actor.render();
            }
            assert actor != null;
            if (actor instanceof FruitContainer){
                font.drawString(Integer.toString(((FruitContainer)actor).getFruitNum()), actor.getX(), actor.getY());
            }
        }
    }


    /**
     * Array used to keep only the actors needed for Gatherer and Thief.
     */
    private void createSubArrays(){
        for (Actor actor : actors) {
            if (!(actor instanceof Animal)&&!(actor.getActorType().equals("Pad"))) {
                actorsForGatherer.add(actor);
            }
        }
        for (Actor actor : actors) {
            if (!(actor instanceof Thief)) {
                actorsForThief.add(actor);
            }
        }
    }
}
