package game.scenes;

import game.AssetsLoader;
import game.Timer;

import javax.swing.*;
import java.awt.*;

/**
 * Class for winning scene
 * This class responsed for displaying the winning message for the player
 */
public class WinningScene extends Scene{
    private Image win;//Image for winning message
    Timer dialogueTimer = new Timer();//A timer to measure the time the winning message displays
    boolean dialogueOpened = false;//the dialogue will not be displayed at first

    public WinningScene(){
        win = AssetsLoader.getImage("images/winScene.png");
    }

    /**
     * Draw winning image on the screen and display message of whether go back to the menu page or not
     * @param g the Graphics2D object for drawing
     * @param now the current time
     * @param dt the time difference between the current frame and the previous frame
     */
    @Override
    public void onRender(Graphics2D g, double now, double dt) {
        super.onRender(g, now, dt);
        g.drawImage(win,
                getWidth()/2 - win.getWidth(null)/2,
                getHeight()/2 - win.getHeight(null)/2,
                null);// draw winning image on the screen

        if (!dialogueOpened && dialogueTimer.getElapsedTime() > 3) {
            dialogueOpened = true;
            showEndingSceneDialog();//if the dialogue is not opened and enough time has passed, open the dialogue for the ending
        }
    }

    /**
     * Asks the user whether to go back to the menu page
     */
    public void showEndingSceneDialog() {
        JOptionPane.showOptionDialog(null, "Go back to menu?", "Plants vs. Zombies", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
        getGame().setScene(new MenuScene());
    }
}
