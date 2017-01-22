package breakout_game;

import java.util.ArrayList;
import java.util.Collections;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Powerup {
	public static final String GREEN_POWER = "greenpower.gif";
	
	private ArrayList<Powerup> myPowerups;
	private ImageView powerup;
	private Brick brick;
	
	
	public Powerup(Brick b){
		Image pu = new Image(getClass().getClassLoader().getResourceAsStream(GREEN_POWER));
    	powerup = new ImageView(pu);
    	brick = b;
	}
	
	public ImageView getPowerupIV(){
		return this.powerup;
	}
	
	
	public ArrayList<Powerup> createPowerups(ArrayList<Brick> myBricks){
		Collections.shuffle(myBricks);
		myPowerups = new ArrayList<Powerup>();
		for(int i = 0; i < 15; i++){
	    	Powerup pu= new Powerup(myBricks.get(i));
	    	pu.powerup.setFitWidth(20);
	    	pu.powerup.setFitHeight(20);
	    	pu.powerup.setX(myBricks.get(i).getBrickIV().getX() + myBricks.get(i).BRICK_WIDTH/4);
	    	pu.powerup.setY(myBricks.get(i).getBrickIV().getY());
	    	pu.powerup.setVisible(false);
			myBricks.get(i).addPower();
			myPowerups.add(pu);
		}
		
		return myPowerups;
	}
	
	public ArrayList<Powerup> myPowerPos(double elapsedTime, ArrayList<Powerup> myPowerups, ArrayList<Brick> myBricks){
		for(Powerup pu: myPowerups){
			if(!myBricks.contains(pu.brick)){
				pu.powerup.setVisible(true);
		    	pu.powerup.setY(pu.powerup.getY() + 40 * elapsedTime);   	
			}
		}
		return myPowerups;
		
	}
	
	
	
}
