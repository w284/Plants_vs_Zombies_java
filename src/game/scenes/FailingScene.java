package game.scenes;

import game.AssetsLoader;
import game.Timer;

import javax.swing.*;
import java.awt.*;

/**
 * Class for the failing ending 
 * This class displays the failing ending and lets the user determine whether to fo back to menu page or not
 */
public class FailingScene extends Scene {

    Image imgTitle;//image for the failing message
    Image imgBackground;//image for the paused battle scene

    double progress = 0;//failing message animation progress

    Timer dialogueTimer = new Timer();//measure the time since the failing dialogue is drawn
    boolean dialogueOpened = false;//the message that asks if the user wish to go back to the menu page will not be displayed at first

    public FailingScene(Image imgBackground) {
        super();
        this.imgBackground = imgBackground;
    }

    /**
     * Called when failing scene is set
     */
    @Override
    public void onSetup() {
        super.onSetup();
        imgTitle = AssetsLoader.getImage("images/gameover.png");
        imgTitle = imgTitle.getScaledInstance(imgTitle.getWidth(null) * 2, imgTitle.getHeight(null) * 2, Image.SCALE_SMOOTH );
    }

    /**
     * Asks the user whether to go back to the menu page
     */
    public void showEndingSceneDialog() {
        JOptionPane.showOptionDialog(null, "Go back to menu?", "Plants vs. Zombies", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
        getGame().setScene(new MenuScene());
    }

    /**
     * Display failing message on the screen and pause the screen and asks if user wants to go back the the menu page
     * @param g for the Graphics2D to draw on
     * @param now for the curren time
     * @param dt for duration time
     */
    @Override
    public void onRender(Graphics2D g, double now, double dt) {
        super.onRender(g, now, dt);

        progress += dt;//animation progress for the failing dialogue
        if (progress > 1) progress = 1;//limit the progress value to 1
        double ratio = Math.pow(1 - progress, 3);//calculate the ratio based on progress

        double offset = ratio * -500;//calculate the offset for vertical positioning

        g.drawImage(imgBackground, 0, 0, null);//draw the copied background image
        g.drawImage(
                imgTitle,
                getWidth() / 2 - imgTitle.getWidth(null) / 2,
                (int) (getHeight() / 2 - imgTitle.getHeight(null) / 2 + offset),
                null
        );// draw the title image with the calculated offset

        if (!dialogueOpened && dialogueTimer.getElapsedTime() > 1.5) {
            dialogueOpened = true;
            showEndingSceneDialog();//if the dialogue is not opened and enough time has passed, open the dialogue for the ending
        }
    }
}
