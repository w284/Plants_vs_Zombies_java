package game;

import game.scenes.Scene;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * Abstract class for all game objects
 */
public abstract class GameObject {

    public Point2D.Double pos = new Point2D.Double();
    public abstract Rectangle2D.Double getHitbox();//a rectangle2D for hitbox for every game object


    /**
     * this method is used to get the game object
     * @return the instance game
     */
    public Game getGame() {
        return Game.INSTANCE;
    }

    /**
     * this method is used to get the scene object in the game
     * @return the scene in the game
     */
    public Scene getScene() {
        return getGame().getScene();
    }

    /**
     * @param g Graphics2D to render on
     * @param now current time
     * @param dt duration time
     */
    public void onRender(Graphics2D g, double now, double dt){

    }

    /**
     * @param now current time
     * @param dt duration time
     */
    public void onUpdate(double now, double dt) {

    }
}
