package game;

/**
 * Class for a timer that can be used to measure elapsed time
 */
public class Timer {
    long record;//The recorded time when the timer was last reset

    public Timer(){
        reset();
    }

    /**
     * Resets the recorded time to the current time
     */
    public void reset(){
        record = System.currentTimeMillis();
    }

    /**
     * get the elapsed time since the last reset in seconds
     * @return the elapsed time in seconds as a double value
     */
    public double getElapsedTime(){
        return (System.currentTimeMillis() - record)/1000.0;
    }
}
