package breakout_game;

import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class Levels {
	 public static final String TITLE = "Breakout Game";
	    public static final String PADDLE_IMAGE = "paddle.gif";
	    public static final int WIDTH = 400;
	    public static final int HEIGHT = 600;
	    public static final Paint BACKGROUND = Color.WHITE;
	    public static final String TABLE = "background.jpg";
	    public static final int FRAMES_PER_SECOND = 60;
	    public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
	    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
	    public static final int KEY_INPUT_SPEED = 25;
	    public static final double GROWTH_RATE = 1.1;
	    public static final int BOUNCER_SPEED = 240;
	    private int levelID;
	
	public Levels(int level){
		levelID = level;
	}
	
	public int currentLevel(){
		return this.levelID;
	}
	
	public void nextLevel(Stage s){
		levelID ++;
		if(levelID == 2){
			secondLevel(s);
		}
		else if(levelID == 3){
			thirdLevel();
		}
	}
	
	public void secondLevel(Stage s){
		Breakout_Game secondBG = new Breakout_Game();
        Scene scene = secondBG.setupGame(WIDTH, HEIGHT, BACKGROUND, this.levelID);
        s.setScene(scene);
        s.setTitle(TITLE);
        s.show();
	}
	
	public void thirdLevel(){
		
	}
	
}
