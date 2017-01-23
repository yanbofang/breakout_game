package breakout_game;

import java.util.ArrayList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Bricks extends Breakout_Game{
	
	private ArrayList<Brick> myBricks;
	
	
    public ArrayList<Brick> createBricks(int WIDTH, int HEIGHT, int currentLV){
        myBricks = new ArrayList<Brick>();
        Brick b = new Brick(WIDTH, HEIGHT, "typical", false);
        b.create(myBricks, currentLV);
        return myBricks;
    }
    
    public void removeBrick(ArrayList<Brick> myBricks, Brick brick){
		brick.getBrickIV().setImage(null);
		myBricks.remove(brick);	
    }
    
    public ArrayList<Brick> checkBricks(Bouncer myBouncer, ArrayList<Missile> myMissiles){
    	for(Brick brick: myBricks){
    		if(brick.getBrickIV().getBoundsInParent().intersects(myBouncer.getBouncerIV().getBoundsInParent())){
    			if(myBouncer.getBouncerIV().getBoundsInParent().getMinX() >= brick.getBrickIV().getBoundsInParent().getMinX() 
    					&& myBouncer.getBouncerIV().getBoundsInParent().getMaxX() <= brick.getBrickIV().getBoundsInParent().getMaxX()){
    				myBouncer.changeXDirection();
    			}
    			brick.subtractLives();
    			if(brick.checkBrickLives() <= 0){
        			removeBrick(myBricks, brick);
    			}
    			else {
    				myBouncer.changeYDirection();
    			}
    		}
    		for(Missile m: myMissiles){
        		if(brick.getBrickIV().getBoundsInParent().intersects(m.getMissileIV().getBoundsInParent())){
        			removeBrick(myBricks, brick);
        		}

    		    }    	
    		}
    	return myBricks;
    }
}
