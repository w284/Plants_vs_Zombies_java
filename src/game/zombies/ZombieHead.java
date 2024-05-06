package game.zombies;

import game.AssetsLoader;
import game.GameObject;
import game.scenes.WinningScene;

import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * Class for zombie's head
 * This class draws the animation of zombie's head falling onto the ground
 */
public class ZombieHead extends GameObject {

    Image image;//image to store the head
    double elapsedTime = 0;//the time elapsed from head losing
    double yOffset = -70;//the offset value for the head from its own pos.x(use this so that the head position can easily be modified(even with different speed and direction)
    double yVel = -32;//the velocity of dropping the head
    boolean bounced = false;//check if the head touched the ground

    /**
     * @param x the x position of the head
     * @param y the y position of the head
     */
    ZombieHead(double x, double y) {
        image = AssetsLoader.getImage("images/zombie/ZombieHead.gif");//set the image as the zombie head gif
        pos.setLocation(x, y);//set the head to the location
    }

    /**
     Renders the zombie head object on the screen
     @param g the Graphics2D object to render on.
     @param now the current time in the game.
     @param dt the time elapsed since the last frame.
     */
    @Override
    public void onRender(Graphics2D g, double now, double dt) {
        super.onRender(g, now, dt);
        elapsedTime += dt;
        if (elapsedTime > 1.25) {
            getScene().removeObject(this);//delete head object after the head is dropped by 1.25 seconds
        }
        yOffset += yVel * dt;//within 1.25 seconds the head's position will off set a distance as a result of the velocity and dt provided
        yVel += dt * 700;//acceleration of the movement
        if (yOffset >= 0) {
            yOffset = 0;//if the head go over the ground, set it to the ground
            if (!bounced) {
                bounced = true;//touch the ground(true)
                yVel = -140;//set velocity different direction
            }
        }

        g.drawImage(image, (int) pos.x, (int) (pos.y + yOffset), null);
    }

    /**
     * @return null
     */
    @Override
    public Rectangle2D.Double getHitbox() {
        return null;
    }

}
