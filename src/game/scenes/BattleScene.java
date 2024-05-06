package game.scenes;

import game.AssetsLoader;
import game.Game;
import game.cards.Card;
import game.plants.*;
import game.zombies.ZombieSpawner;


import java.awt.*;
import java.util.ArrayList;

/**
 * Class for the battle scene
 * This class creates the battle for the plants and the zombies
 */
public class  BattleScene extends Scene {

    private Image imgYard;//image for the background
    private Image imgBoard;//image for the scoreboard

    public static int sun = 75;//initialize the score the user has

    public ArrayList<Card> cards = new ArrayList<>();//plant cards

    /**
     * Called when the battle scene is being set up
     */
    @Override
    public void onSetup() {
        super.onSetup();

        imgYard = AssetsLoader.getImage("images/backyard.png");//get background image
        imgBoard=AssetsLoader.getImage("images/sunScore.png");//get scoreboard image
        addObject(new SunSpawner());//randomly generate suns falling from the sky
        addObject(new ZombieSpawner());//randomly generate zombies

        //add lawn cleaners to the scene
        addObject(new LawnCleaner(),185,100);
        addObject(new LawnCleaner(),180,200);
        addObject(new LawnCleaner(),170,300);
        addObject(new LawnCleaner(),165,395);
        addObject(new LawnCleaner(),165,490);

        // Add cards to the scene
        addCard("images/SeedPackets/spSunFlower", "images/plants/SunFlower.gif", SunFlower.class, 50, 10, 80);
        addCard("images/SeedPackets/spPeaShooter", "images/plants/peaShooter_spit.gif", PeaShooter.class,100, 10,150);
        addCard("images/SeedPackets/spWallNut", "images/plants/WallNut.gif", WallNut.class,50, 10,210);
        addCard("images/SeedPackets/spPotatoMine", "images/plants/PotatoMine.gif", PotatoMine.class,25, 10,270);


    }

    /**
     * Add a card to the scene
     * @param cardImagePath the path to the card image
     * @param plantImagePath the path to the plant image
     * @param clazz the class of the plant
     * @param cost the cost of the plant
     * @param x the x-coordinate of the card
     * @param y the y-coordinate of the card
     */
    void addCard(String cardImagePath, String plantImagePath, Class clazz, int cost, double x, double y) {
        Card card = new Card(cardImagePath, plantImagePath, clazz, cost, x, y);
        addObject(card);
        cards.add(card);
    }


    /**
     * Called when the battle scene is being rendered
     * @param g the graphics context
     * @param now the current time
     * @param dt the time since the last frame
     */
    @Override
    public void onRender(Graphics2D g, double now, double dt) {
        g.drawImage(imgYard, 0, 0, null);//draw the background image
        g.drawImage(imgBoard,0,0,201,67,null);//draw the scored board

        //set the font of the score
        Font font = new Font("Arial", Font.BOLD, 18);
        g.setFont(font);

        g.drawString(Integer.toString(sun), 116,42);//draw the score on the scoreboard
        super.onRender(g, now, dt);//draw game object after drawing background stuff
    }

    /**
     * called when the battle scene is being updated
     * @param now the current time
     * @param dt the time since the last update
     */
    @Override
    public void onUpdate(double now, double dt) {
        super.onUpdate(now, dt);
    }

    /**
     * Called when a mouse button is pressed on the scene
     * @param button the button that was pressed
     * @param x the x-coordinate of the mouse position
     * @param y the y-coordinate of the mouse position
     */
    @Override
    public void onMousePressed(int button, double x, double y) {
        super.onMousePressed(button, x, y);
        for (Card card : cards) {//check if the object is card
            card.onMousePressed(button, x, y);
        }
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.setScene(new BattleScene());
        game.mainLoop();
    }
}
