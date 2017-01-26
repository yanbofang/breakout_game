package breakout_game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

//This entire file, along with Typical_Brick.java, Tough_Brick.java, Undestroyable_Brick.java is part of my masterpiece.
//Yanbo Fang

/**
 * Brick Class: Manage the properties of a brick
 * 
 * @author yanbofang
 *
 */
public class Brick {
	private int brickLives;
	private int WIDTH;
	private int HEIGHT;
	private boolean havePowerup;
	private String brickType;
	private ImageView brick;

	/**
	 * Constructor of the Brick class
	 * 
	 * @param width
	 * @param height
	 * @param brickType
	 * @param brickLives
	 * @param havePower
	 */
	public Brick(int width, int height, String type, int lives, boolean power) {
		WIDTH = width;
		HEIGHT = height;
		brickType = type;
		brickLives = lives;
		havePowerup = power;
		Image brickImage = new Image(getClass().getClassLoader().getResourceAsStream(brickType + "_brick.gif"));
		brick = new ImageView(brickImage);
		brick.setFitWidth(WIDTH);
		brick.setFitHeight(HEIGHT);
	}

	/**
	 * return the brick type of the brick
	 * 
	 * @return
	 */
	public String checkBrickType() {
		return this.brickType;
	}

	/**
	 * Subtract 1 brick life
	 */
	public void subtractLives() {
		this.brickLives--;
	}

	/**
	 * Return the lives the brick has left
	 * 
	 * @return
	 */
	public int checkBrickLives() {
		return this.brickLives;
	}

	/**
	 * Return the ImageView of the brick
	 * 
	 * @return
	 */
	public ImageView getBrickIV() {
		return this.brick;
	}

	/**
	 * Set the position of a brick
	 * 
	 * @param x
	 *            coordinate of the brick
	 * @param y
	 *            coordinate of the brick
	 */
	public void setBrickPos(double x, double y) {
		this.brick.setX(x);
		this.brick.setY(y);
	}

	/**
	 * Return a new, copied version of Brick, that does not reference to the
	 * same Brick
	 * 
	 */
	@Override
	public Brick clone() {
		return new Brick(this.WIDTH, this.HEIGHT, this.brickType, this.brickLives, this.havePowerup);
	}

	/**
	 * Check if the brick has a powerup
	 * 
	 * @return
	 */
	public boolean checkPower() {
		return this.havePowerup;
	}

	/**
	 * Set the boolean value to true, signifying there is a powerup behind the
	 * block
	 */
	public void addPower() {
		this.havePowerup = true;
	}

}
