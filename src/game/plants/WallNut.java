package game.plants;
import game.Animation;
import game.AssetsLoader;

import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * Class for wall nut
 * This class draws a wall nut on the frame and update different status of the wall nut once it is being eaten
 */
public class WallNut extends Plant{
    private Animation idleAnimation1;// animation for idle complete wall nut
    private Animation idleAnimation2;//animation for wall nut being eaten
    private Animation idleAnimation3;//animation for wall nut almost died

    public WallNut() {
        super(20);//time for the wall nut to be eaten up in seconds
        idleAnimation1 = new Animation(AssetsLoader.getGIF("images/plants/WallNut.gif"), 16);
        idleAnimation2 = new Animation(AssetsLoader.getGIF("images/plants/Wallnut_cracked1.gif"), 16);
        idleAnimation3 = new Animation(AssetsLoader.getGIF("images/plants/Wallnut_cracked2.gif"),16);
    }

    /**
     * draw a wall nut on the screen
     * @param g the Graphics2D object for drawing
     * @param now the current time
     * @param dt the time difference between the current frame and the previous frame
     */
    @Override
    public void onRender(Graphics2D g, double now, double dt) {
        if(this.getLifeScore() >10){
            idleAnimation1.draw(g, now, pos.x, pos.y + 20);//draw idleAnimation1(the normal WallNut) if the WallNut's life score is still greater than 10(which means it has not been eaten for more than 10 seconds)
        }else if(this.getLifeScore()<=10 &&this.getLifeScore()>5){
            idleAnimation2.draw(g, now, pos.x, pos.y + 20);//draw idleAnimation2(the first cracked WallNut) if the WallNut'life score is between 5 and 10(which means it has been eaten for more than 10 seconds but no more than 15 seconds)
        }else{
            idleAnimation3.draw(g, now, pos.x, pos.y + 20);//draw idleAnimation3(the final cracked WallNut) if the WallNut'life score is between 5 and 10(which means it has been eaten for more than 10 seconds but no more than 15 seconds)
        }
        //all plants will remove themselves once their life score is less or equals to 0(in the class Plant)
    }


    /**
     * get a hit box for the wall nut
     * @return the hit box for the wall nut has been returned
     */
    @Override
    public Rectangle2D.Double getHitbox() {
        return new Rectangle2D.Double(pos.x+10, pos.y + 35, 55, 60);
    }
}
