package game.plants;

import game.GameObject;

import java.awt.geom.Rectangle2D;

import game.Timer;

/**
 * Class for generating the random spot for the sun falling from the sky
 * This class generating sun in random location and random time
 */
public class SunSpawner extends GameObject {
    private double nextSunTime = Math.random()*6+10;;//random time difference between each sun being generated
    private Timer  timer =new Timer();//a timer to calculate the time difference
    private Timer firstSunTimer = new Timer();
    private boolean firstSunSpawned = false;

    /**
     * @return null
     */
    @Override
    public Rectangle2D.Double getHitbox() {
        return null;
    }

    /**
     * generate the random location for the sun falling from the sky and add the sun to the screen
     * @param now the current time
     * @param dt the time difference between the current frame and the previous frame
     */
    @Override
    public void onUpdate(double now, double dt) {
        //spawn the first sun more quickly
        if (firstSunTimer.getElapsedTime() > 2 && !firstSunSpawned) {
            firstSunSpawned = true;
            spawnOne();
        }
        //spawner the rest suns
        if (timer.getElapsedTime() > nextSunTime) {
           spawnOne();
            timer.reset();
            nextSunTime = Math.random()*6+10;//regenerate next sun spawn time
        }
    }

    /**
     * add one sun to the screen
     */
    public void spawnOne() {
        getScene().addObject(new Sun(1),  Math.random()*411+500, 0);
    }
}
