package game.plants;

import game.AssetsLoader;
import game.GameObject;

import java.awt.*;
import java.awt.geom.Rectangle2D;
/*
class for the effect after bullet hits zombies
 */
public class BulletHitEffect extends GameObject {
    private final Image hit;// Image for the hit effect
    private double timeElapsed = 0;//duration for the effect being displayed on the screen

    public BulletHitEffect(double x, double y){
        hit = AssetsLoader.getImage("images/plants/PeaBulletHit.gif");
        pos.x = x;
        pos.y = y;
    }

    /**
     * Draw the hit effect image on the screen
     * @param g the Graphics2D object for drawing
     * @param now the current time
     * @param dt the time difference between the current frame and the previous frame
     */
    @Override
    public void onRender(Graphics2D g, double now, double dt) {
        super.onRender(g, now, dt);
        g.drawImage(hit, (int)pos.x + 20, (int)pos.y, null);
    }

    /**
     * remove the hit effect after 1 second
     * @param now the current time
     * @param dt the time difference between the current frame and the previous frame
     */
    @Override
    public void onUpdate(double now, double dt) {
        super.onUpdate(now, dt);
        timeElapsed += dt;//calculate time difference after hitting
        if(timeElapsed >= 1){
            getScene().removeObject(this);//remove the explosion effect from the screen after 1 second
        }
    }

    /**
     * get hitbox of the hit effect
     * @return null
     */
    @Override
    public Rectangle2D.Double getHitbox() {
        return null;
    }
}
