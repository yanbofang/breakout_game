package breakout_game;

//This entire file, along with Brick.java, Typical_Brick.java, Undestroyable_Brick.java is part of my masterpiece.
//Yanbo Fang


/**
 * Tough Brick class, extends Brick
 * @author yanbofang
 *
 */
public class Tough_Brick extends Brick {

	/**
	 * Constructor for Tough Brick
	 * @param width
	 * @param height
	 * @param type
	 * @param lives
	 * @param power
	 */
	public Tough_Brick(int width, int height, String type, int lives, boolean power) {
		super(width, height, type, lives, power);
	}
}
