package breakout_game;

//This entire file, along with Brick.java, Tough_Brick.java, Undestroyable_Brick.java is part of my masterpiece.
//Yanbo Fang

/**
 * Typical Brick class, extends Brick
 * @author yanbofang
 *
 */
public class Typical_Brick extends Brick {

	/**
	 * Constructor for Typical Brick
	 * @param width
	 * @param height
	 * @param type
	 * @param lives
	 * @param power
	 */
	public Typical_Brick(int width, int height, String type, int lives, boolean power) {
		super(width, height, type, lives, power);
	}
}
