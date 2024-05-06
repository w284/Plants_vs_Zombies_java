package game.zombies;

import game.Animation;
import game.Game;
import game.GameObject;
import game.plants.Plant;
import game.scenes.FailingScene;
import game.scenes.WinningScene;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;





/**
 * Abstract Class for the two types of zombie objects
 * Abstract class representing the base functionality of zombie objects.
 * Extends the GameObject class.
 */
public abstract class Zombie extends GameObject{

    public double speed = 15;// a double variable for the speed of the zombie
    public int killScore;//an int killScore as the health power for the zombie
    private boolean isEating = false;// Indicates if the zombie is currently eating (intersecting with other objects)
    public static int num = 0;

    /*the index for the array that stores the series animation for the zombie from 0 to 5*/
    public static final int IDLE = 0;
    public static final int MOVING = 1;
    public static final int MOVING_NOHEAD = 2;
    public static final int ATTACKING = 3;
    public static final int ATTACKING_NOHEAD = 4;
    public static final int DYING = 5;

    private double animationTime = Math.random() * 100;//The time for animation frames calculation(makes it random so that the zombie animation wouldn't be drawn all the same and covers each other)
    private int currentAnimationState = IDLE;//an int to store the index that refers to the current status for the zombie series array
    private double noHeadTime = 0;//the time of the animation of  zombie walks with no head
    private double dyingFallToGroundTime = 0;//the time of the animation of  zombie dies and falls to the ground
    boolean droppedHead = false;//a boolean to check if the zombie's head dropped

    protected final Animation[] animations = new Animation[6];//the final array to store series animation for the zombie
    public Point2D.Double drawOffset = new Point2D.Double();//point 2D array for the idle zombies' position

    public Zombie(int killScore){
        this.killScore = killScore;//set the killScore for a zombie object
        pos.x = Game.INSTANCE.getWidth();//Sets the initial x position of the zombie to the width of the game screen
    }

    /**
     Checks if the zombie is dying.
     @return true if the zombie is dying, false otherwise.
     */
    public boolean isDying() {
        return killScore <= 0;//if the killScore is less or equals to zero, the zombie dies(later this boolean is used to call series animation)
    }

    /**
     Renders the zombie object on the screen with different statuses.
     @param g the Graphics2D object to render on.
     @param now the current time in the game.
     @param dt the time elapsed since the last frame.
     */
    @Override
    public void onRender(Graphics2D g, double now, double dt) {
        super.onRender(g, now, dt);
        /* CHECK zombie STATUS and choose animation to draw--> moving or attacking*/
        currentAnimationState = isEating ? ATTACKING : MOVING; // If the zombie is eating then choose attacking animation otherwise moving
        /*CHECK zombie dying or not(run out of killScore or not, if So--->lost head animation*/
        if (isDying()) currentAnimationState  = currentAnimationState + 1; // Add one to switch to the corresponding "No Head" animation

        if (dyingFallToGroundTime > 0) {
            currentAnimationState = DYING;//after the zombie dies for more than 0 second, the animation status index changes to DYING
        }

        Animation animation = animations[currentAnimationState];//set the animation as the animation in the array referred by the index

        if (!isDying())
            animationTime += dt;
        //if the zombie lost its head and the lost head animation has not gone through enough time of its duration
        else if (noHeadTime < animation.getDuration()) {
            if (!droppedHead) {
                //drop the zombie's head
                getScene().addObject(new ZombieHead(pos.x - 70, pos.y));//make it pos.x-70 so that it draws in a proper position
                droppedHead = true;
            }
            noHeadTime += dt;//add time to the no head animation time

            //if the zombie dies and is falling to the ground and this action's animation has not gone through enough time of its duration
        } else if (dyingFallToGroundTime < animation.getDuration()) {
            dyingFallToGroundTime += dt;//add time to the no head animation displaying time
        }

        if (animation != null) {
            if (dyingFallToGroundTime > 0) {
                //when the animation for falling to ground is fully displayed
                if (dyingFallToGroundTime >= animation.getDuration()) {
                    dyingFallToGroundTime = animation.getDuration() - 0.001;  // Keep the animation at last frame
                    getScene().removeObject(this);//remove the zombie object from the object arraylist for the scene so that it will not be rendered
                    num++;
                }
                animation.draw(g, dyingFallToGroundTime, pos.x + drawOffset.x, pos.y + drawOffset.y);
            } else {
                animation.draw(g, animationTime + noHeadTime, pos.x + drawOffset.x, pos.y + drawOffset.y);
            }
        }
    }

    /**
     Checks if the zombie is currently eating.
     @return true if the zombie is eating, false otherwise.
     */
    public boolean isEating() {
        return isEating;
    }

    /**
     * Kill a zombie immediately
     */
    public void kill() {
        minusKill(10000);
    }

    /**
     Updates the state of the zombie.
     @param now the current time in the game.
     @param dt the time elapsed since the last update.
     */
    @Override
    public void onUpdate(double now, double dt) {
        boolean isEating = false;
        //Go through the objects array(where every object rendered is)
        for (GameObject gameObject : getScene().getGameObjects()) {
            if (gameObject instanceof Plant && gameObject.getHitbox().intersects(getHitbox())) {
                isEating = true;//set the status as isEating(set isEating true) if the zombie's hit box intersects with the plant's
                ((Plant) gameObject).minusLife(dt);//call the minusLife method for the plant so that the plant is damaged( the life damaging is by time)
            }
        }
        this.isEating = isEating;//set the status of the zombie
        if (!isEating && dyingFallToGroundTime < 0.1)
            pos.x -= speed * dt;//continue walking if not eating

       if(pos.x < 137){//Game loses when the zombies reach the house
           //get a BufferedImage of the time when the zombie enters the house
           BufferedImage imgBg = new BufferedImage(getGame().width, getGame().height, BufferedImage.TYPE_INT_ARGB);
           Graphics2D g = (Graphics2D) imgBg.getGraphics();
           getGame().onDraw(g, now, dt);

           getGame().setScene(new FailingScene(imgBg));//jump to failing scene
       }
       System.out.println(num);
       if(num >= 5){//game wins when more than 30 zombies have been killed
           getGame().setScene(new WinningScene());//go to winning scene
           num = 0;
       }
    }

    /**
     Abstract method to get the hit box of the zombie.
     @return the hit box of the zombie as a Rectangle2D.Double object.
     */
    public abstract Rectangle2D.Double getHitbox();




    /**
     Reduces the kill score of the zombie by a specified value.
     @param value the value to subtract from the kill score.
     */
    public void minusKill(int value){
        killScore -= value;
    }

}
