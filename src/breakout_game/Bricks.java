package breakout_game;

import java.util.ArrayList;
import java.util.Iterator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Bricks class: Responsible for managing the ArrayList of bricks such as
 * creating the list etc.
 * 
 * @author yanbofang
 *
 */
public class Bricks extends Breakout_Game {
	public static final int BRICK_WIDTH = 30;
	public static final int BRICK_HEIGHT = 20;
	private ArrayList<Brick> myBricks;
	private int typicalLives = 1;
	private int toughLives = 2;
	private int undetroyableLives = 1000;
	

	/**
	 * Create an ArrayList of bricks
	 * 
	 * @param WIDTH
	 * @param HEIGHT
	 * @param currentLV
	 * @return
	 */
	public ArrayList<Brick> createBricks(int WIDTH, int HEIGHT, int currentLV) {
		myBricks = new ArrayList<Brick>();
		create(myBricks, currentLV);
		return myBricks;
	}

	
	/**
	 * Create the bricks
	 * 
	 * @param myBricks
	 * @param currentLV
	 */
	public void create(ArrayList<Brick> myBricks, int currentLV) {
		if (currentLV == 1) {
			createHorizontalBricks(myBricks, new Typical_Brick(BRICK_WIDTH, BRICK_HEIGHT, "typical", typicalLives, false), 8, 3, WIDTH / 4 - BRICK_WIDTH / 1.5,
					HEIGHT / 4 - BRICK_HEIGHT / 2);
			createBricks(myBricks, new Tough_Brick(BRICK_WIDTH, BRICK_HEIGHT, "tough", toughLives, false), 5, WIDTH / 6 - BRICK_WIDTH / 2, HEIGHT / 4 - BRICK_HEIGHT / 2, 30, 30, 1);
			createBricks(myBricks, new Tough_Brick(BRICK_WIDTH, BRICK_HEIGHT, "tough", toughLives, false), 5, 5 * WIDTH / 6 - BRICK_WIDTH / 2, HEIGHT / 4 - BRICK_HEIGHT / 2, 30, 30,
					-1);
			createBricks(myBricks, new Undestroyable_Brick(BRICK_WIDTH, BRICK_HEIGHT, "undestroyable", undetroyableLives, false), 3, WIDTH / 3 - BRICK_WIDTH / 1.09, HEIGHT / 2 - BRICK_HEIGHT / 2,
					80, 0, 1);
		} else if (currentLV == 2) {
			createHorizontalBricks(myBricks, new Typical_Brick(BRICK_WIDTH, BRICK_HEIGHT, "typical", typicalLives, false), 12, 2, WIDTH / 10 - BRICK_WIDTH / 1.5,
					HEIGHT / 4 - BRICK_HEIGHT / 2);
			createBricks(myBricks, new Typical_Brick(BRICK_WIDTH, BRICK_HEIGHT, "typical", typicalLives, false), 8, WIDTH / 6 - BRICK_WIDTH / 2, HEIGHT / 4 - BRICK_HEIGHT / 2, 30, 30, 1);
			createBricks(myBricks, new Tough_Brick(BRICK_WIDTH, BRICK_HEIGHT, "tough", toughLives, false), 8, 5 * WIDTH / 6 - BRICK_WIDTH / 2, HEIGHT / 4 - BRICK_HEIGHT / 2, 30, 30,
					-1);
			createBricks(myBricks, new Undestroyable_Brick(BRICK_WIDTH, BRICK_HEIGHT, "undestroyable", undetroyableLives, false), 2, WIDTH / 2.32 - BRICK_WIDTH / 1.09, HEIGHT / 4 - BRICK_HEIGHT / 2,
					80, 0, 1);
		} else if (currentLV == 3) {
			createHorizontalBricks(myBricks,new Typical_Brick(BRICK_WIDTH, BRICK_HEIGHT, "typical", typicalLives, false), 12, 4, WIDTH / 10 - BRICK_WIDTH / 1.5,
					HEIGHT / 4 - BRICK_HEIGHT / 2);
			createBricks(myBricks, new Tough_Brick(BRICK_WIDTH, BRICK_HEIGHT, "tough", toughLives, false), 10, WIDTH / 6 - BRICK_WIDTH / 2, HEIGHT / 4 - BRICK_HEIGHT / 2, 30, 30, 1);
			createBricks(myBricks, new Tough_Brick(BRICK_WIDTH, BRICK_HEIGHT, "tough", toughLives, false), 10, 5 * WIDTH / 6 - BRICK_WIDTH / 2, HEIGHT / 4 - BRICK_HEIGHT / 2, 30, 30,
					-1);
			createBricks(myBricks, new Undestroyable_Brick(BRICK_WIDTH, BRICK_HEIGHT, "undestroyable", undetroyableLives, false), 3, WIDTH / 3 - BRICK_WIDTH / 1.09, HEIGHT / 4 - BRICK_HEIGHT / 2,
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
	public void createBricks(ArrayList<Brick> myBricks, Brick original, int length, double width, double height,
			int xGap, int yGap, int direction) {
		for (int i = 0; i < length; i++) {
			Brick b = original.clone();
			b.setBrickPos(width + direction * i * xGap, height + i * yGap);
			myBricks.add(b);
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
	public void createHorizontalBricks(ArrayList<Brick> myBricks, Brick b, int length, int rows, double width,
			double height) {
		for (int r = 0; r < rows; r++) {
			height = height - 25;
			createBricks(myBricks, b, length, width, height, 30, 0, 1);
		}
	}
	
	
	/**
	 * Remove a brick
	 * 
	 * @param iter
	 * @param brick
	 */
	public void removeBrick(Iterator<Brick> iter, Brick brick) {
		brick.getBrickIV().setImage(null);
		iter.remove();
	}

	/**
	 * Check if brick has been hit
	 * 
	 * @param myBouncer
	 * @param myMissiles
	 * @return
	 */
	public ArrayList<Brick> checkBricks(Bouncer myBouncer, ArrayList<Missile> myMissiles) {
		Iterator<Brick> iter = myBricks.iterator();
		while (iter.hasNext()) {
			Brick brick = iter.next();
			if (brick.getBrickIV().getBoundsInParent().intersects(myBouncer.getBouncerIV().getBoundsInParent())) {
				if (myBouncer.getBouncerIV().getBoundsInParent().getMinX() >= brick.getBrickIV().getBoundsInParent()
						.getMinX()
						&& myBouncer.getBouncerIV().getBoundsInParent().getMaxX() <= brick.getBrickIV()
								.getBoundsInParent().getMaxX()) {
					myBouncer.changeXDirection();
				}
				brick.subtractLives();
				if (brick.checkBrickLives() <= 0) {
					removeBrick(iter, brick);
				} else {
					myBouncer.changeYDirection();
				}
			}
			for (Missile m : myMissiles) {
				if (brick.getBrickIV().getBoundsInParent().intersects(m.getMissileIV().getBoundsInParent())) {
					removeBrick(iter, brick);
				}
			}
		}
		return myBricks;
	}
}
