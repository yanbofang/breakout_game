package breakout_game;

import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Missile Class: Manage the properties of the Missile powerup
 * 
 * @author yanbofang
 *
 */
public class Missile {
	public static final String MISSILE_IMAGE = "missile.gif";
	public static final int MISSILE_SPEED = 90;
	private ImageView image;
	private int width = 22;
	private int height = 50;
	private double missileX;
	private double missileY;
	private ArrayList<Missile> myMissiles;
	private boolean used;

	/**
	 * Missile constructor
	 * 
	 * @param myPaddle
	 */
	public Missile(ImageView myPaddle) {
		Image m = new Image(getClass().getClassLoader().getResourceAsStream(MISSILE_IMAGE));
		image = new ImageView(m);
		this.setInitialMissilePos(myPaddle);
		image.setFitWidth(width);
		image.setFitHeight(height);
		image.setVisible(false);
		used = false;
	}

	/**
	 * Set the initial position of the missile
	 * 
	 * @param myPaddle
	 */
	public void setInitialMissilePos(ImageView myPaddle) {
		this.missileX = myPaddle.getX();
		this.missileY = myPaddle.getY();
		this.image.setX(this.missileX + width / 2);
		this.image.setY(this.missileY);
		image.setVisible(true);
	}

	/**
	 * Check if the missile has been used
	 * 
	 * @return
	 */
	public boolean checkMissile() {
		return this.used;
	}

	/**
	 * Reset the missile to unused
	 */
	public void resetMissile() {
		this.used = false;
	}

	/**
	 * Set the used field of missile to true
	 */
	public void useMissile() {
		this.used = true;
	}

	/**
	 * Get the ImageView of the missile
	 * 
	 * @return
	 */
	public ImageView getMissileIV() {
		return this.image;
	}

	/**
	 * Create an ArrayList of missiles
	 * 
	 * @param myPaddle
	 * @return
	 */
	public ArrayList<Missile> createMissiles(ImageView myPaddle) {
		myMissiles = new ArrayList<Missile>();
		for (int i = 0; i < 5; i++) {
			Missile m = new Missile(myPaddle);
			myMissiles.add(m);
		}
		return myMissiles;
	}

	/**
	 * Update the missile positions
	 * 
	 * @param elapsedTime
	 */
	public void missilePos(double elapsedTime) {
		this.image.setY(this.image.getY() - MISSILE_SPEED * elapsedTime);
	}

}
