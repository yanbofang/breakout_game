package breakout_game;

import java.util.ArrayList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Bricks extends Breakout_Game{
    public static final String BRICK_1 = "brick1.gif";
    public static final String BRICK_2 = "brick2.gif";
    public static final String BRICK_3 = "brick3.gif";
	
	private ArrayList<Brick> myBricks;
	Bouncer bouncer = new Bouncer(null);
	
	
    public ArrayList<Brick> createBricks(boolean TYPICAL, boolean TOUGH, int WIDTH, int HEIGHT){
    	
        Image brick2 = new Image(getClass().getClassLoader().getResourceAsStream(BRICK_2));
        Image brick3 = new Image(getClass().getClassLoader().getResourceAsStream(BRICK_3));
        myBricks = new ArrayList<Brick>();
        if(TYPICAL){
        	Brick tb = new Brick(WIDTH, HEIGHT, "TYPICAL", false);
        	tb.create(myBricks, "TYPICAL");
        }
        return myBricks;
    }
    
  
    public ArrayList<Brick> checkBricks(Bouncer myBouncer){
    	for(Brick brick: myBricks){
    		if(brick.getBrickIV().getBoundsInParent().intersects(myBouncer.imageView.getBoundsInParent())){
    			brick.getBrickIV().setImage(null);
    			myBricks.remove(brick);	
    			if(myBouncer.imageView.getBoundsInParent().getMinX() >= brick.getBrickIV().getBoundsInParent().getMinX() 
    					&& myBouncer.imageView.getBoundsInParent().getMaxX() <= brick.getBrickIV().getBoundsInParent().getMaxX()){
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
