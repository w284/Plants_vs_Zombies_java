package game;

import java.awt.*;

/**
 * Animation Class for animation object
 */
public class Animation {

    private final Image[] images;//Array of images for the animation
    private final int fps;//Frames per second of the animation
    public final int framesCount;//Total number of frames in the animation

    public Animation(Image[] images, int fps) {
        this.images = images;
        this.fps = fps;
        framesCount = images.length;
    }

    /**
     * Draws the current frame of the animation at the specified position
     * @param g the Graphics2D object used for drawing
     * @param now the current time
     * @param x the x-coordinate of the position to draw the animation
     * @param y the y-coordinate of the position to draw the animation
     */
    public void draw(Graphics2D g, double now, double x, double y) {
        int frameIndex = (int) ((now * fps) % images.length);//calculate the current frame index
        g.drawImage(images[frameIndex], (int) x, (int) y, null);//draw the current frame at the specified position
    }

    /**
     * get the duration of the animation in seconds
     * @return the duration of the animation in seconds has been returned
     */
    public double getDuration() {
        return (double) framesCount / fps;
    }
}
