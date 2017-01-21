package breakout_game;

import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Bricks extends Breakout_Game{
    public static final String BRICK_1 = "brick1.gif";
    public static final String BRICK_2 = "brick2.gif";
    public static final String BRICK_3 = "brick3.gif";
	
	private ArrayList<ImageView> myBricks;
	Bouncer bouncer = new Bouncer(null);
	
	
    public ArrayList<ImageView> createBricks(boolean TYPICAL, boolean TOUGH, int WIDTH, int HEIGHT){
    	
    	
        Image brick2 = new Image(getClass().getClassLoader().getResourceAsStream(BRICK_2));
        Image brick3 = new Image(getClass().getClassLoader().getResourceAsStream(BRICK_3));
        myBricks = new ArrayList<ImageView>();
        if(TYPICAL){
        	TypicalBrick tb = new TypicalBrick(WIDTH, HEIGHT, "TYPICAL");
        	tb.create(myBricks, "TYPICAL");
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
