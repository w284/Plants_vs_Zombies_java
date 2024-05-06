package game.scenes;

import game.Animation;
import game.AssetsLoader;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * This class shows the player the hidden zombies on the road
 */
public class StartScene extends Scene{

    Image imgBackyard;//image of the background
    double progress = 0;//animation progress
    Animation idleAnimation;//animation of idle zombie
    Point2D.Double[] zombiePos = new Point2D.Double[8];//point array to store the coordinates of the 8 zombies

    /**
     * Randomly draw 8 zombies on the road
     */
    @Override
    public void onSetup() {
        super.onSetup();
        imgBackyard = AssetsLoader.getImage("images/backyard.png");//draw the background


        for (int i = 0; i < zombiePos.length; i++) {
            double y = getHeight() * (0.15 + 0.7 * (i / (double) zombiePos.length));//make zombies appears only in the middle 70% of the height
            double x = getWidth() + 100;//make zombies appear with 100 pixels wider

            x += Math.random() * 100 - 50;//add random x offset to the x
            y += Math.random() * 100 - 50;//add random y offset to the y

            zombiePos[i] = new Point2D.Double(x, y);//set location for the zombie
        }

        idleAnimation = new Animation(AssetsLoader.getGIF("images/zombie/idleZombie.gif"), 16);//get
    }

    /**
     * Moves the scene to the left and move back
     * @param g the Graphics2D object for drawing
     * @param now the current time
     * @param dt the time difference between the current frame and the previous frame
     */
    @Override
    public void onRender(Graphics2D g, double now, double dt) {
        progress += dt * 0.3;//progresses 30% per second
        if (progress > 1) {
            getGame().setScene(new BattleScene());//change the scene once the progress is 100% done
        }

        double offsetRatio = - Math.pow(2 * progress - 1, 8) + 1;//power function for the pace of background moving
        int xOffset = (int) (-350 * offsetRatio);//move background to the left 350 pixels

        g.drawImage(imgBackyard, xOffset, 0, null); // Background

        for (Point2D.Double pos : zombiePos) {
            idleAnimation.draw(g, now, pos.x - 50 + xOffset, pos.y - 50);//draw random idle images
        }

    }

    @Override
    public void onUpdate(double now, double dt){

    }
}
