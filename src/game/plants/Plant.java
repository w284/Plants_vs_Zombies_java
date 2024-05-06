package game.plants;

import game.GameObject;

import java.awt.geom.Rectangle2D;

/**
 * Abstract class for Plant
 */
public abstract class Plant extends GameObject {
    public double lifeScore = 5;// time for a plant to be killed in seconds

    public Plant(double lifeScore){
        this.lifeScore = lifeScore;
    }

    /**
     * Lose life when getting attacked
     * @param dt how long the plant has been eaten
     */
    public void minusLife(double dt){
        lifeScore -= dt;//the time the plant can be eaten is reducing when it is being eaten
    }

    /**
     * get the life score of a plant
     * @return the life score of a plant has been returned
     */
    public double getLifeScore(){
        return lifeScore;
    }

    /**
     * Remove the plant from the battle scene when it gets killed
     * @param now the current time
     * @param dt the time difference between the current frame and the previous frame
     */
    @Override
    public void onUpdate(double now, double dt) {
        if(this.getLifeScore()<=0){ //check if the plant still can be eaten for some while
            getScene().removeObject(this);//remove the object from the screen when it gets killed
        }
    }

    /**
     * @return the hit box for the plant has been returned
     */
    public abstract Rectangle2D.Double getHitbox();

}

