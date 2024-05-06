package game.plants;

import game.AssetsLoader;
import game.GameObject;
import game.zombies.Zombie;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;


/**
 * Class for pea bullet
 * This class continuously draws bullets for one pea shooter to hit the zombies
 */
public class PeaBullet extends GameObject {
    private Image imgBullet;//new image for the bullet image
    private double speed = 500;//a double for the speed of the bullet
    private boolean damaged = false;//a boolean to check if the bullet's hit box got the zombie's

    /**
     * @param x the x-axis position for the bullet
     * @param y the y-axis position for the bullet
     */
    public PeaBullet(double x, double y) {
        imgBullet = AssetsLoader.getImage("images/plants/bullet.png");//set the image bullet.png as the bullet image
        pos.x = x;//assign the x position
        pos.y = y;//assign the y position
    }

    /**
     * draw a peaBullet
     * @param g the Graphics2D object for drawing
     * @param now the current time
     * @param dt the time difference between the current frame and the previous frame
     */
    @Override
    public void onRender(Graphics2D g, double now, double dt) {
        g.drawImage(imgBullet, (int)pos.x, (int)pos.y, imgBullet.getWidth(null), imgBullet.getHeight(null),null);//draw the image to the Graphics 2D with the image's width and height
    }
    /**
     * Get a hit box for the peaBullet
     * @return a rectangle which is the hit box of the peaBullet
     */
    @Override
    public Rectangle2D.Double getHitbox() {
        return new Rectangle2D.Double(pos.x+8 , pos.y-10, 37, 34);
    }

    /**
     * Update the status of the peaBullet
     * @param now the current time
     * @param dt  the time difference between the current frame and the previous frame
     */
    @Override
    public void onUpdate(double now, double dt) {
        pos.x += speed * dt;//update the bullet's x position
        if (!damaged) {
            //get the gameObjects array from the Scene class
            ArrayList<GameObject> gameObjects = getScene().getGameObjects();
            //Go through the objects array(where every object rendered is)
            for (int i = 0; i < gameObjects.size(); i++) {
                GameObject obj = gameObjects.get(i);
                if (obj instanceof Zombie && !((Zombie) obj).isDying()) {
                    if (obj.getHitbox().intersects(this.getHitbox())) {
                        getScene().addObject(new BulletHitEffect(pos.x, pos.y));//add hit effect
                        getScene().removeObject(this);//remove the peaBullet from the object array if it hits the zombie object
                        ((Zombie) obj).minusKill(10);//call the minusKill method in zombie object's class and set the damage value as the parameter
                        damaged = true;//set the boolean damage as true so the loop ends
                        break;
                    }
                }
            }
        }
        if (pos.x > getScene().getWidth() + 100) {
            getScene().removeObject(this);//remove the peaBullet from the object array if it is out of the battle scene
        }
    }

}
