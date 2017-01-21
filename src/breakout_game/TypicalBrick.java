package breakout_game;

import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TypicalBrick {
	public static final int BRICK_WIDTH = 30;
	public static final int BRICK_HEIGHT = 20;
    public static final String BRICK_TYPICAL = "brick1.gif";
    private int brickLives;
    private int WIDTH;
    private int HEIGHT;
    private String brickType;
    ImageView brick;
    
    public TypicalBrick(int width, int height, String brickType){
    	brickLives = 1;
    	WIDTH = width;
    	HEIGHT = height;
    	Image brickImage = new Image(getClass().getClassLoader().getResourceAsStream(BRICK_TYPICAL));
    	brick = new ImageView(brickImage);
    }
    
    
    public void create(ArrayList<ImageView> myBricks, String brickType){
        createHorizontalBricks(myBricks, brickType);
        createInclinedBricks1(myBricks, brickType);
        createInclinedBricks2(myBricks, brickType);

    }
    
    
    public void createHorizontalBricks(ArrayList<ImageView> myBricks, String brickType){
        for(int i = 0; i < 8; i++){
        	TypicalBrick tb = new TypicalBrick(WIDTH, HEIGHT, brickType);
    		tb.brick.setFitHeight(BRICK_HEIGHT);
    		tb.brick.setFitWidth(BRICK_WIDTH);
    		tb.brick.setX(WIDTH / 4 - tb.brick.getBoundsInLocal().getWidth() / 1.5 + i * 30);
    		tb.brick.setY(HEIGHT/ 4 - tb.brick.getBoundsInLocal().getHeight() / 2);
    		myBricks.add(tb.brick);
    		}
    }
    
    public void createInclinedBricks1(ArrayList<ImageView> myBricks, String brickType){
        for(int i = 0; i < 5; i++){
        	TypicalBrick tb = new TypicalBrick(WIDTH, HEIGHT, brickType);
        	tb.brick.setFitHeight(BRICK_HEIGHT);
        	tb.brick.setFitWidth(BRICK_WIDTH);
            tb.brick.setX(WIDTH / 6 - tb.brick.getBoundsInLocal().getWidth() / 2 + i * 30);
         	tb.brick.setY(HEIGHT/ 4 - tb.brick.getBoundsInLocal().getHeight() / 2 + i * 30);
         	myBricks.add(tb.brick);
        }
    }
    
    public void createInclinedBricks2(ArrayList<ImageView> myBricks, String brickType){
        for(int i = 0; i < 5; i++){
        	TypicalBrick tb = new TypicalBrick(WIDTH, HEIGHT, brickType);
        	tb.brick.setFitHeight(BRICK_HEIGHT);
        	tb.brick.setFitWidth(BRICK_WIDTH);
            tb.brick.setX(WIDTH - (WIDTH/ 6) - tb.brick.getBoundsInLocal().getWidth() / 2 - i * 30);
         	tb.brick.setY(HEIGHT/4 - tb.brick.getBoundsInLocal().getHeight() / 2 + i * 30);
         	myBricks.add(tb.brick);
        }
        
    }
    
    
}
