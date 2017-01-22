package breakout_game;

import java.util.ArrayList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Bricks extends Breakout_Game{
	
	private ArrayList<Brick> myBricks;
	Bouncer bouncer = new Bouncer(null);
	
	
    public ArrayList<Brick> createBricks(int WIDTH, int HEIGHT, int currentLV){
        myBricks = new ArrayList<Brick>();
        Brick b = new Brick(WIDTH, HEIGHT, "typical", false);
        b.create(myBricks, currentLV);
        return myBricks;
    }
    
  
    public ArrayList<Brick> checkBricks(Bouncer myBouncer){
    	for(Brick brick: myBricks){
    		if(brick.getBrickIV().getBoundsInParent().intersects(myBouncer.imageView.getBoundsInParent())){
    			if(myBouncer.imageView.getBoundsInParent().getMinX() >= brick.getBrickIV().getBoundsInParent().getMinX() 
    					&& myBouncer.imageView.getBoundsInParent().getMaxX() <= brick.getBrickIV().getBoundsInParent().getMaxX()){
    				myBouncer.changeXDirection();
    			}
    			brick.subtractLives();
    			System.out.println(myBouncer.imageView.getX());
    			if(brick.checkBrickLives() <= 0){
        			brick.getBrickIV().setImage(null);
        			myBricks.remove(brick);	
    			}
    			else {
    				myBouncer.changeYDirection();
    			}
    		}
    	}
    	return myBricks;
    }
}
