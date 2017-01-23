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
	private ArrayList<Brick> myBricks;

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
		Brick b = new Brick(WIDTH, HEIGHT, "typical", false);
		b.create(myBricks, currentLV);
		return myBricks;
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
