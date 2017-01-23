package breakout_game;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.effect.Reflection;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Levels class
 * 
 * Manage creating and switching levels of the game
 * 
 * @author yanbofang
 *
 */
public class Levels {
	public static final String TITLE = "Breakout Game";
	public static final int WIDTH = 400;
	public static final int HEIGHT = 600;
	public static final Paint BACKGROUND = Color.WHITE;
	public static final double GROWTH_RATE = 1.1;
	private int levelID;

	/**
	 * Levels constructor
	 * 
	 * @param level
	 */
	public Levels(int level) {
		levelID = level;
	}

	/**
	 * Return the current level
	 * 
	 * @return
	 */
	public int currentLevel() {
		return this.levelID;
	}

	/**
	 * Set up the next level
	 * 
	 * @param stage
	 * @param bouncerSpeed
	 * @param winningText
	 */
	public void nextLevel(Stage s, int bouncerSpeed, Text winningText, Timeline animation) {
		levelID++;
		bouncerSpeed = (int) (bouncerSpeed * GROWTH_RATE);
		if (levelID >= 4) {
			animation.stop();
			winningText.setVisible(true);
			return;
		}
		Breakout_Game nextBG = new Breakout_Game();
		Scene scene = nextBG.setupGame(WIDTH, HEIGHT, BACKGROUND, s, levelID, bouncerSpeed);
		s.setScene(scene);
		s.setTitle(TITLE);
		s.show();
		nextBG.setAnimation(s, levelID);
	}

}
