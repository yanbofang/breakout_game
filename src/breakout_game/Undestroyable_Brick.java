package breakout_game;

/**
 * Undestroyable Brick class, extends Brick
 * @author yanbofang
 *
 */
public class Undestroyable_Brick extends Brick {

	/**
	 * Constructor for Undestroyable Brick
	 * @param width
	 * @param height
	 * @param type
	 * @param lives
	 * @param power
	 */
	public Undestroyable_Brick(int width, int height, String type, int lives, boolean power) {
		super(width, height, type, lives, power);
	}
}
