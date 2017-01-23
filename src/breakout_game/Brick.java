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
    public Brick(int width, int height, String bt, boolean power){
    	WIDTH = width;
    	HEIGHT = height;
    	brickType = bt;
    	Image brickImage = new Image(getClass().getClassLoader().getResourceAsStream(brickType + "_brick.gif"));
    	brick = new ImageView(brickImage);
    	if(brickType == "typical"){
        	brickLives = 1;
    	}else if(brickType == "tough"){
    		brickLives = 2;
    	}else if(brickType == "undestroyable"){
    		brickLives = 1000;
    	}
    	powerup = power;
    }
    
    public String checkBrickType(){
    	return this.brickType;
    }
    
    public void subtractLives(){
    	this.brickLives --; 
    }
    
    public int checkBrickLives(){
    	return this.brickLives;
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
    
    
    public void create(ArrayList<Brick> myBricks, int currentLV){
    	if(currentLV == 1){
            createHorizontalBricks(myBricks, "typical", 8, 3, WIDTH / 4, HEIGHT/ 4);
            createInclinedBricks1(myBricks, "tough", 5, WIDTH / 6, HEIGHT/ 4);
            createInclinedBricks2(myBricks, "tough", 5, 5*WIDTH/6, HEIGHT/ 4);
            createUndestroyableBricks(myBricks, 3, WIDTH/3, HEIGHT/2);
    	}else if(currentLV == 2){
            createHorizontalBricks(myBricks, "typical", 12, 2, WIDTH/10, HEIGHT/ 4);
            createInclinedBricks1(myBricks, "typical", 8, WIDTH/6, HEIGHT/ 4);
            createInclinedBricks2(myBricks, "tough", 8, 5*WIDTH/6, HEIGHT/ 4);
            createUndestroyableBricks(myBricks, 2, WIDTH/2.32, HEIGHT/4);
    	}else if(currentLV == 3){
            createHorizontalBricks(myBricks, "typical", 12, 4, WIDTH/10, HEIGHT/ 4);
            createInclinedBricks1(myBricks, "tough", 10, WIDTH/6, HEIGHT/ 4);
            createInclinedBricks2(myBricks, "tough", 10, 5*WIDTH/6, HEIGHT/ 4);
            createUndestroyableBricks(myBricks, 3, WIDTH/3, HEIGHT/4);
    	}
    }
    
    
    public void createUndestroyableBricks(ArrayList<Brick> myBricks, int length, double width, double height){
    	for(int i = 0; i < length; i++){
    		Brick tb = new Brick(WIDTH, HEIGHT, "undestroyable", false);
    		tb.brick.setFitHeight(BRICK_HEIGHT);
    		tb.brick.setFitWidth(BRICK_WIDTH);
    		tb.brick.setX(width - tb.brick.getBoundsInLocal().getWidth()/1.09 + i * 80);
    		tb.brick.setY(height - tb.brick.getBoundsInLocal().getHeight() / 2);
    		myBricks.add(tb);
    	}
    }
    
    
    public void createHorizontalBricks(ArrayList<Brick> myBricks, String brickType, int length, int rows, double width, double height){
    	for(int r = 0; r < rows; r++){
    		height = height - 25;
            for(int i = 0; i < length; i++){
            	Brick tb = new Brick(WIDTH, HEIGHT, brickType, false);
        		tb.brick.setFitHeight(BRICK_HEIGHT);
        		tb.brick.setFitWidth(BRICK_WIDTH);
        		tb.brick.setX(width - tb.brick.getBoundsInLocal().getWidth() / 1.5 + i * 30);
        		tb.brick.setY(height - tb.brick.getBoundsInLocal().getHeight() / 2);
        		myBricks.add(tb);
        		}
    	}
    }
    
    public void createInclinedBricks1(ArrayList<Brick> myBricks, String brickType, int length, double width, double height){
        for(int i = 0; i < length; i++){
        	Brick tb = new Brick(WIDTH, HEIGHT, brickType, false);
        	tb.brick.setFitHeight(BRICK_HEIGHT);
        	tb.brick.setFitWidth(BRICK_WIDTH);
            tb.brick.setX(width - tb.brick.getBoundsInLocal().getWidth() / 2 + i * 30);
         	tb.brick.setY(height - tb.brick.getBoundsInLocal().getHeight() / 2 + i * 30);
         	myBricks.add(tb);
        }
    }
    
    
    public void createInclinedBricks2(ArrayList<Brick> myBricks, String brickType, int length, double width, double height){
        for(int i = 0; i < length; i++){
        	Brick tb = new Brick(WIDTH, HEIGHT, brickType, false);
        	tb.brick.setFitHeight(BRICK_HEIGHT);
        	tb.brick.setFitWidth(BRICK_WIDTH);
            tb.brick.setX(width - tb.brick.getBoundsInLocal().getWidth() / 2 - i * 30);
         	tb.brick.setY(height - tb.brick.getBoundsInLocal().getHeight() / 2 + i * 30);
         	myBricks.add(tb);
        }
        
    }
    
    
}
