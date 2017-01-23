package breakout_game;

import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Brick {
	public static final int BRICK_WIDTH = 30;
	public static final int BRICK_HEIGHT = 20;
	private int brickLives;
	private int WIDTH;
	private int HEIGHT;
	private boolean powerup;
	private String brickType;
	private ImageView brick;

	/**
	 * Brick constructor
	 * 
	 * @param width
	 * @param height
	 * @param bt
	 * @param power
	 */
	public Brick(int width, int height, String bt, boolean power) {
		WIDTH = width;
		HEIGHT = height;
		brickType = bt;
		Image brickImage = new Image(getClass().getClassLoader().getResourceAsStream(brickType + "_brick.gif"));
		brick = new ImageView(brickImage);
		if (brickType == "typical") {
			brickLives = 1;
		} else if (brickType == "tough") {
			brickLives = 2;
		} else if (brickType == "undestroyable") {
			brickLives = 1000;
		}
		powerup = power;
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
	 * Check if the brick has a powerup
	 * 
	 * @return
	 */
	public boolean checkPower() {
		return this.powerup;
	}

	/**
	 * Add power
	 */
	public void addPower() {
		this.powerup = true;
	}

	/**
	 * Create the bricks
	 * 
	 * @param myBricks
	 * @param currentLV
	 */
	public void create(ArrayList<Brick> myBricks, int currentLV) {
		if (currentLV == 1) {
			createHorizontalBricks(myBricks, "typical", 8, 3, WIDTH / 4 - BRICK_WIDTH / 1.5,
					HEIGHT / 4 - BRICK_HEIGHT / 2);
			createBricks(myBricks, "tough", 5, WIDTH / 6 - BRICK_WIDTH / 2, HEIGHT / 4 - BRICK_HEIGHT / 2, 30, 30, 1);
			createBricks(myBricks, "tough", 5, 5 * WIDTH / 6 - BRICK_WIDTH / 2, HEIGHT / 4 - BRICK_HEIGHT / 2, 30, 30,
					-1);
			createBricks(myBricks, "undestroyable", 3, WIDTH / 3 - BRICK_WIDTH / 1.09, HEIGHT / 2 - BRICK_HEIGHT / 2,
					80, 0, 1);
		} else if (currentLV == 2) {
			createHorizontalBricks(myBricks, "typical", 12, 2, WIDTH / 10 - BRICK_WIDTH / 1.5,
					HEIGHT / 4 - BRICK_HEIGHT / 2);
			createBricks(myBricks, "typical", 8, WIDTH / 6 - BRICK_WIDTH / 2, HEIGHT / 4 - BRICK_HEIGHT / 2, 30, 30, 1);
			createBricks(myBricks, "tough", 8, 5 * WIDTH / 6 - BRICK_WIDTH / 2, HEIGHT / 4 - BRICK_HEIGHT / 2, 30, 30,
					-1);
			createBricks(myBricks, "undestroyable", 2, WIDTH / 2.32 - BRICK_WIDTH / 1.09, HEIGHT / 4 - BRICK_HEIGHT / 2,
					80, 0, 1);
		} else if (currentLV == 3) {
			createHorizontalBricks(myBricks, "typical", 12, 4, WIDTH / 10 - BRICK_WIDTH / 1.5,
					HEIGHT / 4 - BRICK_HEIGHT / 2);
			createBricks(myBricks, "tough", 10, WIDTH / 6 - BRICK_WIDTH / 2, HEIGHT / 4 - BRICK_HEIGHT / 2, 30, 30, 1);
			createBricks(myBricks, "tough", 10, 5 * WIDTH / 6 - BRICK_WIDTH / 2, HEIGHT / 4 - BRICK_HEIGHT / 2, 30, 30,
					-1);
			createBricks(myBricks, "undestroyable", 3, WIDTH / 3 - BRICK_WIDTH / 1.09, HEIGHT / 4 - BRICK_HEIGHT / 2,
					80, 0, 1);
		}
	}

	/**
	 * Create bricks and add to ArrayList
	 * 
	 * @param myBricks
	 * @param brickType
	 * @param length
	 * @param width
	 * @param height
	 * @param xGap
	 * @param yGap
	 * @param direction
	 */
	public void createBricks(ArrayList<Brick> myBricks, String brickType, int length, double width, double height,
			int xGap, int yGap, int direction) {
		for (int i = 0; i < length; i++) {
			Brick tb = new Brick(WIDTH, HEIGHT, brickType, false);
			tb.brick.setFitHeight(BRICK_HEIGHT);
			tb.brick.setFitWidth(BRICK_WIDTH);
			tb.brick.setX(width + direction * i * xGap);
			tb.brick.setY(height + i * yGap);
			myBricks.add(tb);
		}
	}

	/**
	 * Create length lines of horizontal bricks
	 * 
	 * @param myBricks
	 * @param brickType
	 * @param length
	 * @param rows
	 * @param width
	 * @param height
	 */
	public void createHorizontalBricks(ArrayList<Brick> myBricks, String brickType, int length, int rows, double width,
			double height) {
		for (int r = 0; r < rows; r++) {
			height = height - 25;
			createBricks(myBricks, brickType, length, width, height, 30, 0, 1);
		}
	}

}
