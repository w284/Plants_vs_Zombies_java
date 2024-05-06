package game.plants;

import game.Animation;
import game.AssetsLoader;
import game.Timer;


import java.awt.*;
import java.awt.geom.Rectangle2D;


/**
 * Class for pea shooter
 * This class draws a pea shooter on the frame
 */
public class PeaShooter extends Plant{

    private Animation idleAnimation1;//animation for the peashooter
    private Timer fireTimer = new Timer();//a timer for the fire time of peabullets


    public PeaShooter() {
        super(7);//call the parent class Plant and set the life score for the plant peaShooter to perform common update operations
        idleAnimation1 = new Animation(AssetsLoader.getGIF("images/plants/peaShooter_spit.gif"), 16);//set the gif as the animation
    }

    /**
     * draw a peaShooter
     * @param g the Graphics2D object for drawing
     * @param now the current time
     * @param dt the time difference between the current frame and the previous frame
     */
    @Override
    public void onRender(Graphics2D g, double now, double dt) {
        super.onRender(g, now, dt);
        idleAnimation1.draw(g, now, pos.x, pos.y + 20);
    }
    /**
     * Get a hit box for the peaShooter
     * @return a rectangle which is the hit box of the peaShooter
     */
    @Override
    public Rectangle2D.Double getHitbox() {
        return new Rectangle2D.Double(pos.x + 9, pos.y + 25 , 55, 60);
    }

    /**
     * Update the status of the peaShooter
     * @param now the current time
     * @param dt  the time difference between the current frame and the previous frame
     */
    @Override
    public void onUpdate(double now, double dt) {
        super.onUpdate(now, dt);//call the parent class in the abstract class Plant
        if (fireTimer.getElapsedTime() > 2.5) {
            PeaBullet blt = new PeaBullet((int)pos.x-10,(int)pos.y + 35);//create a new peaBullet object and set the parameters for its shooting spawner position
            getScene().addObject(blt);//add the bullet object to the main array where contains every object being rendered on the battle scene
            fireTimer.reset();//reset the fire timer so that the bullet is fired out every 1 second
        }

    }
}
