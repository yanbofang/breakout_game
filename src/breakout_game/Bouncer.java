package breakout_game;

import java.util.ArrayList;

import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Bouncer Class: Manage the activities and properties of the bouncer (ball)
 * 
 * @author yanbofang
 *
 */
public class Bouncer extends Breakout_Game {
	public static final String BALL_IMAGE = "red_puck.jpg";
	private Integer lives = 3;
	private double X_DIRECTION;
	private double Y_DIRECTION;
	private int BOUNCER_SPEED;
	private boolean protectedBouncer;
	private Bouncer myBouncer;
	private ImageView imageView;

	/**
	 * Bouncer constructor
	 * 
	 * @param image
	 * @param speed
	 */
	public Bouncer(Image image, int speed) {
		X_DIRECTION = 1;
		Y_DIRECTION = 1;
		imageView = new ImageView(image);
		BOUNCER_SPEED = speed;
		protectedBouncer = false;
	}

	/**
	 * Return the ImageView of the bouncer
	 * 
	 * @return
	 */
	public ImageView getBouncerIV() {
		return this.imageView;
	}

	/**
	 * Change the bouncer to be protected, so that it won't fall under
	 */
	public void protectBouncer() {
		this.protectedBouncer = true;
	}

	/**
	 * Change the speed of the bouncer
	 * 
	 * @param speed
	 */
	public void changeSpeed(int speed) {
		this.BOUNCER_SPEED = speed;
	}

	/**
	 * Return the x direction of bouncer
	 * 
	 * @return
	 */
	public double getXDirection() {
		return this.X_DIRECTION;
	}

	/**
	 * Return the y direction of bouncer
	 * 
	 * @return
	 */
	public double getYDirection() {
		return this.Y_DIRECTION;
	}

	/**
	 * Change the x direction of bouncer
	 */
	public void changeXDirection() {
		this.X_DIRECTION = this.X_DIRECTION * -1;
		this.setPos(SECOND_DELAY);
	}

	/**
	 * Change the y direction of bouncer
	 */
	public void changeYDirection() {

		this.Y_DIRECTION = this.Y_DIRECTION * -1;
		this.setPos(SECOND_DELAY);
	}

	/**
	 * Return how many lives the user currently has
	 * 
	 * @return
	 */
	public Integer lives() {
		return this.lives;
	}

	/**
	 * Add an additional life
	 */
	public void addLife() {
		this.lives++;
	}

	/**
	 * Create a bouncer
	 * 
	 * @param myPaddle
	 * @param speed
	 * @return
	 */
	public Bouncer createBouncer(ImageView myPaddle, int speed) {
		Image image = new Image(getClass().getClassLoader().getResourceAsStream(BALL_IMAGE));
		myBouncer = new Bouncer(image, speed);
		myBouncer.imageView.setFitHeight(15);
		myBouncer.imageView.setFitWidth(15);
		myBouncer.resetBouncer();
		return myBouncer;
	}

	/**
	 * Check collision and update the position of the bouncer
	 * 
	 * @param elapsedTime
	 * @param myPaddle
	 * @param animation
	 * @param stage
	 * @param currentLV
	 * @param myBricks
	 * @param bouncerSpeed
	 * @param winningText
	 * @param losingText
	 * @return
	 */
	public Bouncer myBouncerPos(double elapsedTime, ImageView myPaddle, Timeline animation, Stage s, int currentLV,
			ArrayList<Brick> myBricks, int bouncerSpeed, Text winningText, Text losingText) {
		checkX();

		checkY(animation, s, currentLV, myBricks, bouncerSpeed, winningText, losingText, myPaddle);

		checkPaddle(myPaddle);

		setPos(elapsedTime);

		return this;
	}

	/**
	 * Set the position of bouncer
	 * 
	 * @param elapsedTime
	 */
	public void setPos(double elapsedTime) {
		this.imageView.setX(this.imageView.getX() + X_DIRECTION * this.BOUNCER_SPEED * elapsedTime);
		this.imageView.setY(this.imageView.getY() - Y_DIRECTION * this.BOUNCER_SPEED * elapsedTime);
	}

	/**
	 * Reset the bouncer to initial position
	 */
	public void resetBouncer() {
		this.imageView.setX(WIDTH / 2 - this.imageView.getBoundsInLocal().getWidth() / 2);
		this.imageView.setY(HEIGHT - this.imageView.getBoundsInLocal().getWidth() - 12);
	}

	/**
	 * Check if the bouncer hit the left or right of the screen, if so change
	 * direction
	 */
	private void checkX() {
		if (this.imageView.getBoundsInParent().getMaxX() >= WIDTH || this.imageView.getX() <= 0) {
			X_DIRECTION = X_DIRECTION * -1;
		}
	}

	private int countUndestroyable(ArrayList<Brick> myBricks) {
		int count = 0;
		for (Brick brick : myBricks) {
			if (brick.checkBrickType() == "undestroyable") {
				count++;
			}
		}
		return count;
	}

	private void resetPaddle(ImageView myPaddle) {
		myPaddle.setX(WIDTH / 2 - myPaddle.getBoundsInLocal().getWidth() / 2);
		myPaddle.setY(HEIGHT - 12);
	}

	private void checkY(Timeline animation, Stage s, int currentLV, ArrayList<Brick> myBricks, int bouncerSpeed,
			Text winningText, Text losingText, ImageView myPaddle) {
		if (myBricks.size() <= countUndestroyable(myBricks) && this.imageView.getBoundsInParent().getMinY() <= 0
				&& (this.imageView.getBoundsInParent().getMinX() >= WIDTH / 3
						&& this.imageView.getBoundsInParent().getMaxX() <= 2 * WIDTH / 3)) {
			animation.stop();
			Levels level = new Levels(currentLV);
			level.nextLevel(s, bouncerSpeed, winningText, animation);
		} else if (this.imageView.getBoundsInParent().getMaxY() >= HEIGHT) {
			if (!this.protectedBouncer) {
				lives--;
				if (lives <= 0) {
					losingText.setVisible(true);
					animation.stop();
				} else {
					this.resetBouncer();
					resetPaddle(myPaddle);
					animation.pause();
				}
			} else {
				Y_DIRECTION = Y_DIRECTION * -1;
			}
		} else if (this.imageView.getY() <= 0) {
			Y_DIRECTION = Y_DIRECTION * -1;
		}
	}

	/**
	 * Paddle configuration, determine the bouncing direction of the ball after
	 * hitting the paddle
	 * 
	 * @param myPaddle
	 */
	private void checkPaddle(ImageView myPaddle) {
		double xBouncer = this.imageView.getX() + this.imageView.getBoundsInLocal().getWidth() / 2;
		double xPaddle = myPaddle.getBoundsInLocal().getWidth() / 8;
		if (this.imageView.getBoundsInParent().intersects(myPaddle.getBoundsInParent())) {
			if (xBouncer <= myPaddle.getX() + xPaddle) {
				X_DIRECTION = -1.10;
				Y_DIRECTION = 1;
			} else if (xBouncer >= myPaddle.getX() + 7 * xPaddle) {
				X_DIRECTION = 1.10;
				Y_DIRECTION = 1;
			} else if (xBouncer <= myPaddle.getX() + 2 * xPaddle) {
				X_DIRECTION = -0.8;
				Y_DIRECTION = 1;
			} else if (xBouncer >= myPaddle.getX() + 6 * xPaddle) {
				X_DIRECTION = 0.8;
				Y_DIRECTION = 1;
			} else if (xBouncer <= myPaddle.getX() + 3 * xPaddle) {
				X_DIRECTION = -0.5;
				Y_DIRECTION = 1;
			} else if (xBouncer >= myPaddle.getX() + 5 * xPaddle) {
				X_DIRECTION = 0.5;
				Y_DIRECTION = 1;
			} else if (xBouncer <= myPaddle.getX() + 4 * xPaddle) {
				X_DIRECTION = -0.2;
				Y_DIRECTION = 1;
			} else if (xBouncer >= myPaddle.getX() + 4 * xPaddle) {
				X_DIRECTION = 0.2;
				Y_DIRECTION = 1;
			}
		}
	}
}
