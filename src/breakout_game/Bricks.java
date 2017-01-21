package breakout_game;

import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Bricks extends Breakout_Game{
    public static final String BRICK_1 = "brick1.gif";
    public static final String BRICK_2 = "brick2.gif";
    public static final String BRICK_3 = "brick3.gif";
	private static final int BRICK_WIDTH = 30;
	private static final int BRICK_HEIGHT = 20;
	
	private ArrayList<ImageView> myBricks;
	Bouncer bouncer = new Bouncer(null);
	
    public ArrayList<ImageView> createBricks(){
        Image brick1 = new Image(getClass().getClassLoader().getResourceAsStream(BRICK_1));
        Image brick2 = new Image(getClass().getClassLoader().getResourceAsStream(BRICK_2));
        Image brick3 = new Image(getClass().getClassLoader().getResourceAsStream(BRICK_3));
        myBricks = new ArrayList<ImageView>();
        for(int i = 0; i < 8; i++){
        	ImageView myBrick1 = new ImageView(brick1);
            myBrick1.setFitHeight(BRICK_HEIGHT);
            myBrick1.setFitWidth(BRICK_WIDTH);
            myBrick1.setX(WIDTH / 4 - myBrick1.getBoundsInLocal().getWidth() / 1.5 + i * 30);
        	myBrick1.setY(HEIGHT/ 4 - myBrick1.getBoundsInLocal().getHeight() / 2);
        	myBricks.add(myBrick1);
        }
        for(int i = 0; i < 5; i++){
        	ImageView myBrick2 = new ImageView(brick2);
        	myBrick2.setFitHeight(BRICK_HEIGHT);
        	myBrick2.setFitWidth(BRICK_WIDTH);
            myBrick2.setX(WIDTH / 6 - myBrick2.getBoundsInLocal().getWidth() / 2 + i * 30);
         	myBrick2.setY(HEIGHT/ 4 - myBrick2.getBoundsInLocal().getHeight() / 2 + i * 30);
         	myBricks.add(myBrick2);
        }
        for(int i = 0; i < 5; i++){
        	ImageView myBrick3 = new ImageView(brick3);
        	myBrick3.setFitHeight(BRICK_HEIGHT);
        	myBrick3.setFitWidth(BRICK_WIDTH);
            myBrick3.setX(WIDTH - (WIDTH/ 6) - myBrick3.getBoundsInLocal().getWidth() / 2 - i * 30);
         	myBrick3.setY(HEIGHT/4 - myBrick3.getBoundsInLocal().getHeight() / 2 + i * 30);
         	myBricks.add(myBrick3);
        }
        return myBricks;
    }
    
  
    public ArrayList<ImageView> checkBricks(Bouncer myBouncer){
    	for(ImageView brick: myBricks){
    		if(brick.getBoundsInParent().intersects(myBouncer.imageView.getBoundsInParent())){
    			brick.setImage(null);
    			myBricks.remove(brick);	
    			
    			if(myBouncer.imageView.getBoundsInParent().getMinX() >= brick.getBoundsInParent().getMinX() 
    					&& myBouncer.imageView.getBoundsInParent().getMaxX() <= brick.getBoundsInParent().getMaxX()){
    				System.out.println("!!!!!!!!");
    				myBouncer.changeXDirection();
    			}
    			else {
    				myBouncer.changeYDirection();
    			}
    		}
    	}
    	return myBricks;
    }
}
