package breakout_game;

//This entire file, along with Brick.java, Typical_Brick.java, Tough_Brick.java is part of my masterpiece.
//Yanbo Fang


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
