package game.plants;

import game.Animation;
import game.AssetsLoader;
import game.GameObject;
import game.scenes.BattleScene;

import java.awt.*;
import java.awt.geom.Rectangle2D;


/**
 * Class for the sun produced by a sunflower
 * This class draws a sun on the frame, determines whether it is collected, if collected, moves the sun to the scoreboard
 */
public class Sun extends GameObject{
    private Animation sun;//animation for the sun
    private boolean collected = false;//the sun is not collected at first
    private Point targetPos;//the place that the sun needs to go when it is collected
    private int version;//version of produced by sunflower or the sky
    private double speed = 50;//speed of the sun falling from the top

    public Sun(int v){
        sun = new Animation(AssetsLoader.getGIF("images/Sun.gif"), 16);
        targetPos = new Point(10, 11);//place of the scoreboard
        version = v;
    }

    /**
     * draw a sun on the screen
     * @param g the Graphics2D object for drawing
     * @param now the current time
     * @param dt the time difference between the current frame and the previous frame
     */
    @Override
    public void onRender(Graphics2D g, double now, double dt) {
        super.onRender(g, now, dt);
        sun.draw(g, now, pos.x, pos.y);
    }

    /**
     * get the hit box of the sun
     * @return the hit box of the sun has been returned
     */
    @Override
    public Rectangle2D.Double getHitbox() {
        return new Rectangle2D.Double(pos.x + 10, pos.y+10, 55, 60);
    }

    /**
     * Display the sun above the sunflower and the further trajectory to the target point once it is being clicked
     * @param now the current time
     * @param dt the time difference between the current frame and the previous frame
     */
    @Override
    public void onUpdate(double now, double dt) {
        if(version == 1){//check if the sun is produced by the sky
            pos.y += speed * dt;//initial movement of the sun
        }
        //check if the sun is clicked
        if (getHitbox().contains(getScene().getMousePos()) && getScene().isMouseDown) {
            collected = true;
        }
        if(collected){
            double dx = pos.x - targetPos.x;//calculate the horizontal displacement from the sun to the target point
            double dy = pos.y - targetPos.y;//calculate the vertical displacement from the sun to the target point
            double distance = Math.sqrt(dx*dx + dy*dy);//calculate the displacement between the sun and the target point
            double speed = 800;//speed for the sun moving towards the target point
            double movement = speed * dt;//the distance the sun moves after each time different
            double ratio = movement / distance;// Calculate the ratio of movement to distance
            // Update the position of the sun based on the ratio
            pos.x -= (int)(dx * ratio);
            pos.y -= (int)(dy * ratio);
        }
        // Remove the sun and add sun scores if it reaches the target point
        if(pos.x <= 15  && pos.y <= 15){
            getScene().removeObject(this);
            BattleScene.sun += 25;//add sun scores when the sun is collected
        }
    }
}
