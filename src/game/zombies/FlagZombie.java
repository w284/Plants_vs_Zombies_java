package game.zombies;

import game.Animation;
import game.AssetsLoader;

import java.awt.geom.Rectangle2D;

/**
 * Class for flag zombie
 * This class draws a flag zombie on the screen
 */
public class FlagZombie extends Zombie {

    public FlagZombie() {
        super(100);//call the parent class Zombie and set the health score for flag zombie
        //set the gifs for different statuses of the zombies to the series array
        animations[MOVING] = new Animation(AssetsLoader.getGIF("images/zombie/FlagZombie.gif"), 16);
        animations[MOVING_NOHEAD] = new Animation(AssetsLoader.getGIF("images/zombie/FlagZombieLostHead.gif"), 16);
        animations[ATTACKING] = new Animation(AssetsLoader.getGIF("images/zombie/FlagZombieAttack.gif"), 24);
        animations[ATTACKING_NOHEAD] = new Animation(AssetsLoader.getGIF("images/zombie/FlagZombieLostHeadAttack.gif"), 24);
        animations[DYING] = new Animation(AssetsLoader.getGIF("images/zombie/ZombieDie.gif"), 24);
        drawOffset.setLocation(-150, -110);//set the range for the offset for the gif moving
    }
    /**
     * Get a hit box for the normal zombie
     * @return a rectangle which is the hit box of the normal zombie
     */
    @Override
    public Rectangle2D.Double getHitbox() {
        return new Rectangle2D.Double(pos.x - 65, pos.y - 35, 55, 75);
    }

}
