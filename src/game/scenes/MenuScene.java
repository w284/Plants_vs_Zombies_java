package game.scenes;

import game.AssetsLoader;

import java.awt.*;

/**
 * Class for the menu page
 * This class allows the user to enter the game
 */
public class MenuScene extends Scene {

    private Image imgBg;//Image for background
    private final Rectangle buttonRect = new Rectangle();//rectangle for button click to start

    /**
     *override onSetup method to set up the menu scene
     */
    @Override
    public void onSetup() {
        super.onSetup();
        imgBg = AssetsLoader.getImage("images/first_screen.png");
        buttonRect.setBounds(232, 528, 534, 63);
    }

    /**
     * @param g for the Graphics2D to draw on
     * @param now for the curren time
     * @param dt for duration time
     */
    @Override
    public void onRender(Graphics2D g, double now, double dt) {
        g.drawImage(imgBg, 0, 0,getWidth(),getHeight(), null);
    }

    /**
     * Check if user clicks the start button, if so, go to the start scene
     * @param button 1 represents left click of the mouses)
     * @param x the x position of the mouse click
     * @param y the y position of the mouse click
     */
    @Override
    public void onMousePressed(int button, double x, double y) {
        if (button == 1 && buttonRect.contains(x, y)) {//check if user uses left click and the position of the click is within the button
            getGame().setScene(new StartScene());//transfer to start scene
        }
    }
}
