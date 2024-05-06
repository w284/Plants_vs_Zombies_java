package game.plants;

import game.AssetsLoader;
import game.GameObject;
import game.zombies.Zombie;

import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * Class for the lawn cleaner
 * This class draws a lawn cleaner on the frame and controls the movement of it
 */
public class LawnCleaner extends GameObject{
    private Image cleaner;//image of the lawn cleaner
    private double speed = 250;//speed for the lawn cleaner
    private boolean running = false;//the lawn cleaner is not moving at first

    public LawnCleaner(){
        cleaner = AssetsLoader.getImage("images/LawnCleaner.png");
    }

    /**
     * Draw a lawn cleaner
     * @param g the Graphics2D object for drawing
     * @param now the current time
     * @param dt the time difference between the current frame and the previous frame
     */
    @Override
    public void onRender(Graphics2D g, double now, double dt) {
        super.onRender(g, now, dt);
        g.drawImage(cleaner, (int)pos.x, (int)pos.y, cleaner.getWidth(null), cleaner.getHeight(null),null );
    }

    /**
     * Get a hit box for the lawn cleaner
     * @return a rectangle which is the hit box of the lawn cleaner has been returned
     */
    @Override
    public Rectangle2D.Double getHitbox() {
        return new Rectangle2D.Double(pos.x + 30, pos.y + 20, 37, 34);
    }

    /**
     * Make the lawn cleaner move and kill the zombies when the zombie hit the lawn clean
     * @param now the current time
     * @param dt the time difference between the current frame and the previous frame
     */
    @Override
    public void onUpdate(double now, double dt) {
        for (GameObject obj : getScene().getGameObjects()) {
            //check if the object that hit the lawn cleaner is from Zombie Class
            if(obj instanceof Zombie) {
                if(obj.getHitbox().intersects(this.getHitbox())){ //check if Zombie and the lawn cleaner touch each other
                    running = true;//the condition for the car to move is true
                    ((Zombie) obj).kill();//kill the zombie
                }
            }
        }
        if (running){
            pos.x += speed * dt;//move the lawn cleaner all the way to the right
        }
    }
}
