package game.plants;
import game.Animation;
import game.AssetsLoader;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import game.Timer;


/**
 * Class for sunflower
 * This class draws a sunflower on the frame and randomly produces sun
 */
public class SunFlower extends Plant{
    private Animation idleAnimation2;
    private Timer timer = new Timer();


    public SunFlower() {
        super(5);//time for a sunflower to be eaten up in seconds
        idleAnimation2 = new Animation(AssetsLoader.getGIF("images/plants/SunFlower.gif"), 16);
    }

    /**
     * draw a sunflower on the screen
     * @param g the Graphics2D object for drawing
     * @param now the current time
     * @param dt the time difference between the current frame and the previous frame
     */
    @Override
    public void onRender(Graphics2D g, double now, double dt) {
        super.onRender(g, now, dt);
        idleAnimation2.draw(g, now, pos.x, pos.y + 20);
    }

    /**
     * get a hit box for a sunflower
     * @return the hit box of the sunflower has been returned
     */
    @Override
    public Rectangle2D.Double getHitbox() {
        return new Rectangle2D.Double(pos.x , pos.y + 20, 55, 60);
    }

    /**
     * produce suns
     * @param now the current time
     * @param dt  the time difference between the current frame and the previous frame
     */
    @Override
    public void onUpdate(double now, double dt) {
        super.onUpdate(now, dt);
        //vary the place every sun occurs
        double xOffset = Math.random()*40-20;
        double yOffset = Math.random()*30-80;
        //produces a sun after 10 seconds
        if(timer.getElapsedTime() >= 12){
            getScene().addObject(new Sun(2), pos.x + xOffset, pos.y + yOffset);
            timer.reset();
        }
    }
}
