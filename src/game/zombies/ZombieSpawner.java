package game.zombies;

import game.GameObject;
import game.Timer;

import java.awt.geom.Rectangle2D;

/**
 * This class generates random two types of zombies and put it in random path after each random duration
 */
public class ZombieSpawner extends GameObject {
    private final double firstZombieTime = 30;//the first zombie appears time
    private double nextZombieTime = Math.random()*3+8;// the time difference between each zombie spawning time
    private Timer  timer =new Timer();//timer to record the time difference between each zombie spawning time
    private boolean firstZombieAppears = false;//check if first zombie has appeared

    /**
     * @return null
     */
    @Override
    public Rectangle2D.Double getHitbox() {
        return null;
    }


    /**
     * generate the random location for the zombie add it to the screen
     * @param now the current time
     * @param dt the time difference between the current frame and the previous frame
     */
    @Override
    public void onUpdate(double now, double dt) {
        //make the first zombie appear much slowly
        if(timer.getElapsedTime() > firstZombieTime && !firstZombieAppears){
            spawnZombie();
            firstZombieAppears = true;
            timer.reset();
        }
        //spawn the rest zombies
        if(timer.getElapsedTime() > nextZombieTime && firstZombieAppears){
            spawnZombie();
            timer.reset();
            nextZombieTime = Math.random()*3+8;
        }
    }

    /**
     * Randomly choose a type of zombie and randomly choose a path to add it on the screen
     */
    public void spawnZombie(){
        int n = (int)(Math.random()*2+1);//determine which zombie to choose
        int m = (int)(Math.random()*5);//determine which path to occur
        int y = (new int[]{139, 236, 337, 427, 534})[m];//y-coordinates for the zombie
        if(n == 1){
            getScene().addObject(new NormalZombie(), getScene().getWidth(), y);//add normal zombie to the frame
        }else{
            getScene().addObject(new FlagZombie(), getScene().getWidth(), y);//add flag zombie to the screen
        }
    }
}
