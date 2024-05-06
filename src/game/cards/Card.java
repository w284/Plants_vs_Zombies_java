package game.cards;

import game.AssetsLoader;
import game.GameObject;
import game.plants.Plant;
import game.scenes.BattleScene;

import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 *
 */
/*Class for the cards of plants (seed packets)*/
public class Card extends GameObject {
    private int length=93;//an int to store the length of the card
    private int width=59;//an int to store the width of the card
    public static Card selected = null;//a Card object to store the object that has been selected

    public Image imgCard;//an image as the current status image of the card
    public Image imgActive;//an image as the active status of the card
    public Image imgInactive;//an image as the inactive status of the card
    public Image imgPlant;//an image as the card's plant's image
    public Class plantClass;//a class for the selected plant's object
    public int cost;//an int for the cost of the plant

    /**constructor for cards
     * @param cardPath the path for the image of card
     * @param plantPath the path for the image of the plant in the card
     * @param clazz the class for the plant object
     * @param cost the cost of the plant
     * @param x the x location for the card
     * @param y the y location for the card
     */
    public Card(String cardPath, String plantPath, Class clazz, int cost, double x, double y) {
        imgCard = imgActive = AssetsLoader.getImage(cardPath + ".png");//set the current card status image as the active image
        imgInactive = AssetsLoader.getImage(cardPath + "In.png");//store the inactive card image to imgInactive
        imgPlant = AssetsLoader.getImage(plantPath);//store the plant itself's image to imgPlant
        pos.x = x;//set the x location
        pos.y = y;//set the y location
        this.cost = cost;//set the cost
        this.plantClass = clazz;//set the plant object
    }
    /**
     Renders the card objects on the screen
     @param g the Graphics2D object to render on.
     @param now the current time in the game.
     @param dt the time elapsed since the last frame.
     */
    public void onRender(Graphics2D g, double now, double dt){
        super.onRender(g, now, dt);
        g.drawImage(imgCard,(int)pos.x,(int)pos.y,length,width,null);//draw the current status card on the screen with the location provided
        /*Check isActive and assign imgCard: if isActive() assign imgActive otherwise imgInactive*/
        imgCard = isActive() ? imgActive : imgInactive;
        if (selected == this) {
            //if this card is selected, draw the plant object's image in the middle
            g.drawImage(
                    imgPlant,
                    getScene().getMousePos().x - imgPlant.getWidth(null) / 2,
                    getScene().getMousePos().y - imgPlant.getHeight(null) / 2,
                    null);
        }

    }

    /**
     * @return boolean see if the plant's cost is affordable
     */
    public boolean isActive() {
        return BattleScene.sun >= cost;
    }

    /**the mouse action press method for selecting the card
     * @param button the left click
     * @param x the x position of mouse pressed
     * @param y the y position of mouse pressed
     */
    public void onMousePressed(int button, double x, double y) {
        if (!this.isActive())
            return;

        if (getHitbox().contains(x, y)) {
            if (selected == this)
                selected = null; //cancel selection
            else
                selected = this;
        } else if (selected == this) {
            //set up the coordinate system for planting the plants
            int left = 258;
            int right = 986;
            int top = 84;
            int bottom = 571;

            if (left < x && x < right && top < y && y < bottom) {
                //get the width and height of the field
                int fieldWidth = right - left;
                int fieldHeight = bottom - top;
                //divide the field into suitable amount of squares
                int widthGap = fieldWidth / 9;
                int heightGap = fieldHeight / 5;

                int plantX = (int) ((x - left) / widthGap);
                int plantY = (int) ((y - top) / heightGap);
                //Get the actual coordinates for the square to be planted by minus the edge's length
                double actualX = left + plantX * widthGap;
                double actualY = top + plantY * heightGap;

                try {

                    Plant instance = (Plant) plantClass.newInstance();//the previous parameter is class not object(instance)
                    instance.pos.setLocation(actualX, actualY);
                    for (GameObject object : getScene().getGameObjects()) {
                        if (object instanceof Plant && object.getHitbox().intersects(instance.getHitbox())) { // Is over a plant
                            return; // Cancel plant if there is already plant on the square
                        }
                    }
                    getScene().addObject(instance);//plant it in
                    selected = null;
                    BattleScene.sun-=cost;//sun amounts minus the price of the plant

                } catch (Exception ignored) {}
            }
        }
    }

    /**
     * @return the hit box for the card
     */
    @Override
    public Rectangle2D.Double getHitbox() {
        return  new Rectangle2D.Double((int)pos.x,(int)pos.y,length,width);
    }
}
