package game.plants;
import game.Animation;
import game.AssetsLoader;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import game.GameObject;
import game.zombies.Zombie;


/**
 * Class for potato mine
 * This class draws a potato mine on the frame and controls the explosion of the potato mine
 */
public class PotatoMine extends Plant{
    private Animation idleAnimation3;//animation when the potato mine is not exploding
    private Image unarmed;//Image for the potato mine under the ground
    private double totalTimeElapsed;//time difference between the potato mine is planted and grows up

    public PotatoMine() {
        super(5);//time for a potato mine to be eaten up in seconds
        idleAnimation3 = new Animation(AssetsLoader.getGIF("images/plants/PotatoMine.gif"), 16);
        unarmed = AssetsLoader.getImage("images/plants/PotatoMineUnder.png");
        totalTimeElapsed = 0.0;
    }

    /**
     * Draw a potato mine on the screen
     * @param g the Graphics2D object for drawing
     * @param now the current time
     * @param dt the time difference between the current frame and the previous frame
     */
    @Override
    public void onRender(Graphics2D g, double now, double dt) {
        super.onRender(g, now, dt);
        totalTimeElapsed += dt;//calculate how many times has been passed when the potato mine is planted

        if(totalTimeElapsed < 14.0){//check if the potato mine is mature
            g.drawImage(unarmed, (int)pos.x, (int)pos.y + 30, unarmed.getWidth(null),
                    unarmed.getHeight(null), null);
        } else { // Grown after 14.0 seconds
            idleAnimation3.draw(g, now, pos.x, pos.y + 30);
        }
    }

    /**
     * Get a hit box for the potato mine
     * @return a rectangle which is the hit box of the potato mine has been returned
     */
    @Override
    public Rectangle2D.Double getHitbox() {
        return new Rectangle2D.Double(pos.x + 12, pos.y + 45, 55, 40);
    }

    /**
     * Kill the zombie
     * @param now the current time
     * @param dt  the time difference between the current frame and the previous frame
     */
    @Override
    public void onUpdate(double now, double dt) {
        super.onUpdate(now, dt);
        if (totalTimeElapsed >= 14.0) { // Grown
            boolean explode = false;//the potato mine is initially at a stage not exploding
            for (GameObject gameObject : getScene().getGameObjects()) {
                if (gameObject instanceof Zombie) { //check if the object that touch the potato mine is a zombie
                    if (gameObject.getHitbox().intersects(getHitbox())) {
                        explode = true;// the potato mine will explode when a zombie steps on it
                        break;
                    }
                }
            }

            if (explode) {
                Rectangle2D.Double hitbox = getHitbox();//get the hit box of the potato mine
                //Adjust the hit box of the potato mine to better kill the zombies
                hitbox.x -= 4;//move the hit box to the left
                hitbox.y -= 4;//move the hit box upper
                //enlarge the hitbox
                hitbox.width += 8;
                hitbox.height += 8;
                for (GameObject gameObject : getScene().getGameObjects()) {
                    if (gameObject instanceof Zombie) {//check if the object that touch the potato mine is a zombie
                        if (gameObject.getHitbox().intersects(hitbox)) {
                            ((Zombie) gameObject).kill();//kill the zombie immediately
                        }
                    }
                }
                getScene().removeObject(this);//remove the potato mine when after explosion
                getScene().addObject(new PotatoMineExplosion(pos.x, pos.y));//add explosion effect
            }

        } else {//being eaten when it is not grown
            if (this.getLifeScore() <= 0) {
                getScene().removeObject(this);
            }
        }
    }
}
