package breakout_game;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class Powerup {
	public static final String GREEN_POWER = "greenpower.gif";
	private ArrayList<Powerup> myPowerups;
	private ImageView powerup;
	private Brick brick;
	private String powerType;
	private Timer timer;
	private boolean ableToHit;

	
	public Powerup(Brick b){
		Image pu = new Image(getClass().getClassLoader().getResourceAsStream(GREEN_POWER));
    	powerup = new ImageView(pu);
    	brick = b;
    	powerType = "";
    	ableToHit = true;
	}
	
	public ImageView getPowerupIV(){
		return this.powerup;
	}
	
	public String setPower(){
		ArrayList<String> powerList = new ArrayList<String>();
		powerList.add("bigPaddle");
		powerList.add("smallPaddle");
		powerList.add("slowPuck");
		powerList.add("fastPuck");
		powerList.add("missile");
		
		
		Collections.shuffle(powerList);
		return powerList.get(0);
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
	    	pu.powerType = setPower();
			myBricks.get(i).addPower();
			myPowerups.add(pu);
		}
		return myPowerups;
	}
	
	
	public boolean checkHitPaddle(ImageView myPaddle){
		return this.ableToHit && this.powerup.getBoundsInParent().intersects(myPaddle.getBoundsInParent());
	}
	
	
	public void activatePower(String powerType, ImageView myPaddle, Bouncer myBouncer, ArrayList<Missile> myMissiles){
		if(powerType == "bigPaddle"){
			System.out.println(powerType);

			bigPaddle(myPaddle);
		}
		else if(powerType == "smallPaddle"){
			System.out.println(powerType);

			smallPaddle(myPaddle);
		}
		else if(powerType == "slowPuck"){
			System.out.println(powerType);

			slowPuck(myBouncer);
		}
		else if(powerType == "fastPuck"){
			System.out.println(powerType);

			fastPuck(myBouncer);
		}
		else if(powerType == "missile"){
			System.out.println(powerType);
			resetMissiles(myMissiles);
	        activateMissile(myPaddle, myMissiles);
		}
	}
	
	public void resetMissiles(ArrayList<Missile> myMissiles){
		for(Missile m: myMissiles){
			if(m.checkMissile()){
				m.resetMissile();
			}
		}
	}
	
	public void activateMissile(ImageView myPaddle, ArrayList<Missile> myMissiles){
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask(){
			int count = 0;
			public void run(){
				count++;
				if(count > 6){
					timer.cancel();
					timer.purge();
					return;
				}
				for(Missile m: myMissiles){
					if(!m.checkMissile()){
						m.useMissile();
						m.setInitialMissilePos(myPaddle);
						break;
					}
				}
			}
		}, 0, 2000);	
	}
	
	
	public void bigPaddle(ImageView myPaddle){
		myPaddle.setFitWidth(100);
	}
	
	public void smallPaddle(ImageView myPaddle){
		myPaddle.setFitWidth(40);
	}
	
	public void slowPuck(Bouncer myBouncer){
		myBouncer.changeSpeed(120);
	}
	
	public void fastPuck(Bouncer myBouncer){
		myBouncer.changeSpeed(360);
	}
	
	public ArrayList<Powerup> myPowerPos(double elapsedTime, ArrayList<Powerup> myPowerups, ArrayList<Brick> myBricks, ImageView myPaddle, Bouncer myBouncer, ArrayList<Missile> myMissiles){
		for(Powerup pu: myPowerups){
			if(!myBricks.contains(pu.brick)){
				pu.powerup.setVisible(true);
		    	pu.powerup.setY(pu.powerup.getY() + 80 * elapsedTime);
		    	if(pu.checkHitPaddle(myPaddle)){
		    		pu.ableToHit = false;
		    		pu.activatePower(pu.powerType, myPaddle, myBouncer, myMissiles);
		    	}
			}
		}
		return myPowerups;
	}
	
	
	
}
