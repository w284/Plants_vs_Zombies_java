package game;

import game.scenes.Scene;
import game.scenes.MenuScene;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.*;

/**
 * The Class Game for the game main loop
 */
public class Game extends JFrame implements MouseMotionListener, MouseListener {

    public static Game INSTANCE;//the instance object in the game
    public Point mousePos = new Point();//the mouse position

    private final BufferStrategy bufferStrategy;//for double buffering
    private final Canvas canvas;//the canvas where to render all the animation and images
    private boolean running = false;//a boolean for game's running
    public int width;//width of the canvas
    public int height;//height of the canvas

    private Scene scene = new MenuScene();//create a new menu scene for later adding it to the game

    public Game() {
        super();
        INSTANCE = this;
        // Configuring Window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("game.Game Window");
        setIgnoreRepaint(true); // Disable auto repaint so that we can handle rendering ourselves
        // Configure the canvas and add to the window
        canvas = new Canvas();
        canvas.setIgnoreRepaint(true);
        canvas.setSize(1020, 600);
        add(canvas);
        pack();

        // Double buffering
        canvas.createBufferStrategy(2);
        bufferStrategy = canvas.getBufferStrategy();

        width = canvas.getWidth();
        height = canvas.getHeight();

        canvas.addMouseMotionListener(this);
        canvas.addMouseListener(this);

        setResizable(false);//make the window fixed
    }

    /**
     * This method is used for setting the current scene in the game
     * @param scene the scene that will be set up in the game
     */
    public void setScene(Scene scene) {
        scene.onSetup();
        this.scene = scene;
    }

    /**This method is used for getting the scene that will show in the game
     * @return the current scene in the game is returned
     */
    public Scene getScene() {
        return scene;
    }

    /**
     * Gets called to render object to the screen
     * @param g graphics to render
     * @param dt delta time between current frame and previous frame in seconds
     * @param now time since window launched in seconds 
     */
    public void onDraw(Graphics2D g, double dt, double now) {
        scene.onRender(g, now, dt);
    }

    /**
     * Gets called to update
     * @param now current time in seconds
     * @param dt delta time between this update and previous update
     */
    public void onUpdate(double now, double dt) {
        scene.onUpdate(now, dt);
    }

    /**
     * Start the game loop
     */
    public final void mainLoop() {
        if (running) { // Check if this method was called twice
            throw new IllegalStateException("The game loop is already running!");
        }

        double launchTime = System.nanoTime() / 1e9d; // Get current time and convert to seconds
        double lastTickTime = 0;

        scene.onSetup();

        running = true;
        setVisible(true);

        while (running) { // This is "the hidden loop of Processing"
            // Calculate delta time between current frame and previous frame
            double now = System.nanoTime() / 1e9d  - launchTime; // Get time since launch in seconds
            double dt = now - lastTickTime;
            lastTickTime = now;

            // Update width and height values
            width = canvas.getWidth();
            height = canvas.getHeight();

            // Update game's data
            onUpdate(now, dt); // Always update no matter if the context is lost or not

            Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics(); // Get draw graphics
            // Render
            if (!bufferStrategy.contentsLost()){ // Only render when the context is not lost
                onDraw(g, dt, now); // Render to the buffer
                bufferStrategy.show(); // Swap buffers (display the rendered result)
            }

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * @param e
     */
    // An example of how you should start the game loop
    @Override
    public void mouseMoved(MouseEvent e) {
        mousePos.x = e.getX();
        mousePos.y = e.getY();
    }

    /**
     * @param e
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        mousePos.x = e.getX();
        mousePos.y = e.getY();
    }

    /**
     * @param e
     */
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    /**
     * @param e
     */
    @Override
    public void mousePressed(MouseEvent e) {
        scene.onMousePressed(e.getButton(), e.getX(), e.getY());
        scene.isMouseDown = true;
    }

    /**
     * @param e
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        scene.isMouseDown = false;
    }

    /**
     * @param e
     */
    @Override
    public void mouseEntered(MouseEvent e) {

    }

    /**
     * @param e
     */
    @Override
    public void mouseExited(MouseEvent e) {

    }

    public static void main(String[] args) {
        Game game = new Game(); // Create an instance
        game.mainLoop(); // Run game loop
    }
}