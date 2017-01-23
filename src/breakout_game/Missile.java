package breakout_game;

import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Missile {
	public static final String MISSILE_IMAGE = "missile.gif";
	private ImageView image;
	private int width = 22;
	private int height = 50;
	private double missileX;
	private double missileY;
	private ArrayList<Missile> myMissiles;
	private boolean used;
	
	public Missile(ImageView myPaddle){
		Image m = new Image(getClass().getClassLoader().getResourceAsStream(MISSILE_IMAGE));
		image = new ImageView(m);
		this.setInitialMissilePos(myPaddle);
    	image.setFitWidth(width);
    	image.setFitHeight(height);
    	image.setVisible(false);
		used = false;
	}
	
	public void setInitialMissilePos(ImageView myPaddle){
		this.missileX = myPaddle.getX();
		this.missileY = myPaddle.getY();
    	this.image.setX(this.missileX + width/2);
    	this.image.setY(this.missileY);
    	image.setVisible(true);
	}
	
	
	public boolean checkMissile(){
		return this.used;
	}
	
	public void resetMissile(){
		this.used = false;
	}
	
	public void useMissile(){
		this.used = true;
	}
	
	public ImageView getMissileIV(){
		return this.image;
	}
	
	public ArrayList<Missile> createMissiles(ImageView myPaddle){
		myMissiles = new ArrayList<Missile>();
		for(int i = 0; i < 5; i++){
			Missile m = new Missile(myPaddle);
			myMissiles.add(m);
		}
		return myMissiles;
	}
	
	public void missilePos(double elapsedTime){
		this.image.setY(this.image.getY() - 80 * elapsedTime);
	}
	
}
