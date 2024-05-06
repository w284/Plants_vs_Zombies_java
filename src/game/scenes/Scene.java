package game.scenes;

import game.Game;
import game.GameObject;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

/**
 * Abstract Class Scene for different scenes in the game
 */
public class Scene {
    public static boolean drawDebugInfo = false;
    public boolean isMouseDown = false;

    private final ArrayList<GameObject> gameObjects = new ArrayList<>();// an arraylist to store all the game objects

    /**
     * This method is used to get the game objects arraylist
     * @return the arraylist of game objects
     */
    public ArrayList<GameObject> getGameObjects() {
        return new ArrayList<>(gameObjects);
    }

    /**
     * This method add object to the game objects arraylist and set location
     * @param object that to be added
     * @param x the x location of the game object in the scene
     * @param y the y location of the game object in the scene
     */
    public void addObject(GameObject object, double x, double y) {
        if (object == null)
            throw new RuntimeException("Attempted to add a nullptr game object to the scene");
        if (!gameObjects.contains(object)) {
            gameObjects.add(object);
            object.pos.setLocation(x, y);
        }
    }

    /**
     * This method add object to the game objects arraylist
     * @param object that to be added
     */
    public void addObject(GameObject object) {
        if (object == null)
            throw new RuntimeException("Attempted to add a nullptr game object to the scene");
        if (!gameObjects.contains(object))
            gameObjects.add(object);
    }

    /**
     * This method removes object to the game objects arraylist
     * @param object that to be removed
     */
    public void removeObject(GameObject object) {
        if (gameObjects.contains(object))
            gameObjects.remove(object);
    }

    /**
     * get the game instance
     * @return the game instance has been returned
     */
    public Game getGame() {
        return Game.INSTANCE;
    }

    /**
     * Get the width of the game
     * @return the width of the game has been returned
     */
    public int getWidth() {
        return getGame().width;
    }

    /**
     * @return the height of the scene
     */
    public int getHeight() {
        return getGame().height;
    }

    /**
     * @return the mouse position on the scene
     */
    public Point getMousePos() {
        return getGame().mousePos;
    }

    /**
     * This method is to be overridden to set up different scenes
     */
    public void onSetup() {

    }

    /**
     * @param g the Graphics2D object for drawing
     * @param now current time
     * @param dt duration time
     */
    public void onRender(Graphics2D g, double now, double dt) {
        ArrayList<GameObject> objects = getGameObjects();//copy all GameObjects array
        objects.sort((o1, o2) -> Double.compare(o1.pos.y, o2.pos.y));//sort the objects depends on y-coordinates
        for (GameObject gameObject : objects) {
            gameObject.onRender(g, now, dt);
            if (drawDebugInfo) {

                g.setColor(Color.RED);
                g.fillOval((int) (gameObject.pos.x-4), (int) (gameObject.pos.y-4), 8, 8);

                Rectangle2D.Double hitbox = gameObject.getHitbox();
                if (hitbox != null) {
                    g.setColor(Color.white);
                    g.setStroke(new BasicStroke(2));
                    g.drawRect((int) hitbox.x, (int) hitbox.y, (int) hitbox.width, (int) hitbox.height);
                }

                g.setColor(Color.white);
                g.setFont(g.getFont().deriveFont(14.0f));
                g.drawString(gameObject.getClass().toString(), (int) gameObject.pos.x, (int) gameObject.pos.y);
                g.drawString("Pos: " + (int) (gameObject.pos.x) + ", " + (int) gameObject.pos.y, (int) gameObject.pos.x, (int) gameObject.pos.y + 17);

            }
        }
    }

    /**
     * @param now current time
     * @param dt duration time
     */
    public void onUpdate(double now, double dt) {
        for (GameObject gameObject : new ArrayList<>(gameObjects)) {
            gameObject.onUpdate(now, dt);
        }
    }

    /**
     * @param button the left click of the mouse (1)
     * @param x the x position mouse clicks on
     * @param y the y position mouse clicks on
     */
    public void onMousePressed(int button, double x, double y) {
        System.out.println("x: " + x + "y: " + y );
    }

}
