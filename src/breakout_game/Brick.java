package breakout_game;

import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Brick {
	public static final int BRICK_WIDTH = 30;
	public static final int BRICK_HEIGHT = 20;
    public static final String BRICK_TYPICAL = "brick1.gif";
    private int brickLives;
    private int WIDTH;
    private int HEIGHT;
    private boolean powerup;
    private String brickType;
    private ImageView brick;
    
    /**
     * 
     * @param width
     * @param height
     * @param brickType
     * @param powerup
     */
    public Brick(int width, int height, String brickType, boolean power){
    	brickLives = 1;
    	WIDTH = width;
    	HEIGHT = height;
    	Image brickImage = new Image(getClass().getClassLoader().getResourceAsStream(BRICK_TYPICAL));
    	brick = new ImageView(brickImage);
    	powerup = power;
    }
    
    public ImageView getBrickIV(){
    	return this.brick;
    }
    
    public boolean checkPower(){
    	return this.powerup;
    }
    
    public void addPower(){
    	this.powerup = true;
    }
    
    
    public void create(ArrayList<Brick> myBricks, String brickType){
        createHorizontalBricks(myBricks, brickType);
        createInclinedBricks1(myBricks, brickType);
        createInclinedBricks2(myBricks, brickType);
    }
    
    
    public void createHorizontalBricks(ArrayList<Brick> myBricks, String brickType){
        for(int i = 0; i < 8; i++){
        	Brick tb = new Brick(WIDTH, HEIGHT, brickType, false);
    		tb.brick.setFitHeight(BRICK_HEIGHT);
    		tb.brick.setFitWidth(BRICK_WIDTH);
    		tb.brick.setX(WIDTH / 4 - tb.brick.getBoundsInLocal().getWidth() / 1.5 + i * 30);
    		tb.brick.setY(HEIGHT/ 4 - tb.brick.getBoundsInLocal().getHeight() / 2);
    		myBricks.add(tb);
    		}
    }
    
    public void createInclinedBricks1(ArrayList<Brick> myBricks, String brickType){
        for(int i = 0; i < 5; i++){
        	Brick tb = new Brick(WIDTH, HEIGHT, brickType, false);
        	tb.brick.setFitHeight(BRICK_HEIGHT);
        	tb.brick.setFitWidth(BRICK_WIDTH);
            tb.brick.setX(WIDTH / 6 - tb.brick.getBoundsInLocal().getWidth() / 2 + i * 30);
         	tb.brick.setY(HEIGHT/ 4 - tb.brick.getBoundsInLocal().getHeight() / 2 + i * 30);
         	myBricks.add(tb);
        }
    }
    
    public void createInclinedBricks2(ArrayList<Brick> myBricks, String brickType){
        for(int i = 0; i < 5; i++){
        	Brick tb = new Brick(WIDTH, HEIGHT, brickType, false);
        	tb.brick.setFitHeight(BRICK_HEIGHT);
        	tb.brick.setFitWidth(BRICK_WIDTH);
            tb.brick.setX(WIDTH - (WIDTH/ 6) - tb.brick.getBoundsInLocal().getWidth() / 2 - i * 30);
         	tb.brick.setY(HEIGHT/4 - tb.brick.getBoundsInLocal().getHeight() / 2 + i * 30);
         	myBricks.add(tb);
        }
        
    }
    
    
}
