package game.plants;

import game.AssetsLoader;
import game.GameObject;

import java.awt.*;
import java.awt.geom.Rectangle2D;


/**
 * Class for the explosion effect of a potato mine
 * This class draws the explosion effect of the potato mine on the frame
 */
public class PotatoMineExplosion extends GameObject{
    private final Image explosion;//the image of the explosion effect
    private double timeElapsed = 0;//duration for the effect being displayed on the screen

    public PotatoMineExplosion(double x, double y){
        explosion = AssetsLoader.getImage("images/plants/PotatoMine_mashed.gif");
        pos.x = x;
        pos.y = y;
    }

    /**
     * Draw the explosion effect image on the screen
     * @param g the Graphics2D object for drawing
     * @param now the current time
     * @param dt the time difference between the current frame and the previous frame
     */
    @Override
    public void onRender(Graphics2D g, double now, double dt) {
        super.onRender(g, now, dt);
        g.drawImage(explosion, (int) pos.x, (int) pos.y, null);
    }

    /**
     * Get the hit box of the explosion effect
     * @return null
     */
    @Override
    public Rectangle2D.Double getHitbox() {
        return null;
    }

    /**
     * Delete the explosion effect
     * @param now the current time
     * @param dt the time difference between the current frame and the previous frame
     */
    @Override
    public void onUpdate(double now, double dt){
        timeElapsed += dt;//calculate time difference after explosion
        if(timeElapsed >= 1){
            getScene().removeObject(this);//remove the explosion effect from the screen after 1 second
        }
    }
}
