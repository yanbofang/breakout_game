package breakout_game;

import java.util.ArrayList;
import javafx.scene.text.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.Reflection;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Breakout Game
 * 
 * The start of the game
 * 
 * @author Yanbo Fang
 * @author Robert C. Duvall
 */
public class Breakout_Game extends Application {
	public static final String TITLE = "Breakout Game";
	public static final String PADDLE_IMAGE = "paddle.gif";
	public static final String TABLE = "background.jpg";
	public static final String SLOW_SIGN = "slow.gif";
	public static final int WIDTH = 400;
	public static final int HEIGHT = 600;
	public static final Paint BACKGROUND = Color.WHITE;
	public static final int FRAMES_PER_SECOND = 60;
	public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
	public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
	public static final int KEY_INPUT_SPEED = 25;
	public static final double GROWTH_RATE = 1.1;
	public static final int INITIAL_SPEED = 200;

	// some things we need to remember during our game
	private Scene myScene;
	private ImageView myPaddle;
	private Bouncer myBouncer;
	private ImageView myTable;
	private ArrayList<Brick> myBricks;
	private ArrayList<Powerup> myPowerups;
	private ArrayList<Missile> myMissiles;
	private ImageView slowSign;
	private Bricks bricks;
	private Text livesLeft;
	private Text currentLevel;
	private Text pressToStart;
	private Text winningText;
	private Text losingText;
	private Timeline animation;
	private Powerup power;
	private int bouncerSpeed;

	/**
	 * Initialize what will be displayed and how it will be updated.
	 */
	@Override
	public void start(Stage s) {
		int currentLV = 1;
		bouncerSpeed = INITIAL_SPEED;
		Scene scene = setupGame(WIDTH, HEIGHT, BACKGROUND, s, currentLV, bouncerSpeed);
		s.setScene(scene);
		s.setTitle(TITLE);
		s.show();
		setAnimation(s, currentLV);
	}

	/**
	 * Set the animation
	 * 
	 * @param Stage
	 * @param currentLV
	 */
	public void setAnimation(Stage s, int currentLV) {
		KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY, s, currentLV));
		animation = new Timeline();
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.getKeyFrames().add(frame);
	}

	private ImageView createIV(ImageView iv, String imageFile, double width, double height, double x, double y) {
		Image image = new Image(getClass().getClassLoader().getResourceAsStream(imageFile));
		iv = new ImageView(image);
		iv.setFitWidth(width);
		iv.setFitHeight(height);
		iv.setX(x);
		iv.setY(y);
		return iv;
	}

	private Text createText(Text t, Reflection r, double x, double y, String message, int fontSize) {
		t = new Text();
		t.setX(x);
		t.setY(y);
		t.setFont(Font.font("Verdana", fontSize));
		t.setText(message);
		t.setFill(Color.MAGENTA);
		t.setEffect(r);
		return t;
	}

	/**
	 * Create the game's "scene": what shapes will be in the game and their
	 * starting properties
	 * 
	 * @param width
	 * @param height
	 * @param background
	 * @param stage
	 * @param currentLV
	 * @param bouncerSpeed
	 * @return
	 */
	public Scene setupGame(int width, int height, Paint background, Stage s, int currentLV, int bouncerSpeed) {
		Group root = new Group();
		myScene = new Scene(root, width, height, background);
		myTable = createIV(myTable, TABLE, WIDTH, HEIGHT, 0, 0);
		myPaddle = createIV(myPaddle, PADDLE_IMAGE, 0, 0, WIDTH / 2, HEIGHT - 12);
		myPaddle.setX(WIDTH / 2 - myPaddle.getBoundsInLocal().getWidth() / 2);
		Bouncer bouncer = new Bouncer(null, bouncerSpeed);
		myBouncer = bouncer.createBouncer(myPaddle, bouncerSpeed);
		myBouncer.changeSpeed(bouncerSpeed);
		bricks = new Bricks();
		myBricks = bricks.createBricks(WIDTH, HEIGHT, currentLV);
		power = new Powerup(null);
		Missile missile = new Missile(myPaddle);
		myMissiles = missile.createMissiles(myPaddle);
		myPowerups = power.createPowerups(myBricks);
		slowSign = createIV(slowSign, SLOW_SIGN, 60, 60, WIDTH / 2 - 30, HEIGHT / 2 + 30);
		Reflection r = new Reflection();
		r.setFraction(0.7f);
		livesLeft = createText(livesLeft, r, 13, 21, "Lives: " + myBouncer.lives(), 13);
		Levels level = new Levels(currentLV);
		currentLevel = createText(currentLevel, r, 280, 21, "Current Level:" + level.currentLevel(), 13);
		pressToStart = createText(pressToStart, r, WIDTH / 4, 3 * HEIGHT / 4, "Press Space to Start", 20);
		winningText = createText(winningText, r, WIDTH / 6, 3 * HEIGHT / 4,
				"Congratualations! You Won!" + "\n" + "Press esc to Exit", 20);
		losingText = createText(losingText, r, WIDTH / 6, 3 * HEIGHT / 4, "You Lost! Press esc to Exit!", 20);
		winningText.setVisible(false);
		losingText.setVisible(false);
		addToRoot(root);
		myScene.setOnKeyPressed(e -> handleKeyInput(e.getCode(), root, s, currentLV));
		return myScene;
	}

	/*
	 * add to group
	 */
	private void addToRoot(Group root) {
		root.getChildren().add(myTable);
		root.getChildren().add(livesLeft);
		root.getChildren().add(currentLevel);
		for (int i = 0; i < myBricks.size(); i++) {
			root.getChildren().add(myBricks.get(i).getBrickIV());
		}
		for (int i = 0; i < myPowerups.size(); i++) {
			root.getChildren().add(myPowerups.get(i).getPowerupIV());
		}
		for (int i = 0; i < myMissiles.size(); i++) {
			root.getChildren().add(myMissiles.get(i).getMissileIV());
		}
		root.getChildren().add(myPaddle);
		root.getChildren().add(myBouncer.getBouncerIV());
		root.getChildren().add(slowSign);
		root.getChildren().add(pressToStart);
		root.getChildren().add(winningText);
		root.getChildren().add(losingText);
	}

	/**
	 * Change properties of shapes to animate them
	 * 
	 * @param elapsedTime
	 * @param stage
	 * @param currentLV
	 */
	public void step(double elapsedTime, Stage s, int currentLV) {
		myBouncer = myBouncer.myBouncerPos(elapsedTime, myPaddle, animation, s, currentLV, myBricks, bouncerSpeed,
				winningText, losingText);
		myPowerups = power.myPowerPos(elapsedTime, myPowerups, myBricks, myPaddle, myBouncer, myMissiles);
		livesLeft.setText("Lives: " + myBouncer.lives());
		if (!myMissiles.isEmpty()) {
			for (Missile m : myMissiles) {
				if (m.checkMissile()) {
					m.missilePos(elapsedTime);
				}
			}
		}
		myBricks = bricks.checkBricks(myBouncer, myMissiles);
		slowSign.setRotate(slowSign.getRotate() + 1);
		if (slowSign.getBoundsInParent().intersects(myBouncer.getBouncerIV().getBoundsInParent())) {
			myBouncer.changeSpeed(80);
		} else {
			myBouncer.changeSpeed(200);
		}
	}

	/*
	 * handle keyboard input
	 */
	private void handleKeyInput(KeyCode code, Group root, Stage s, int currentLV) {
		if (code == KeyCode.SPACE) {
			animation.play();
			root.getChildren().remove(pressToStart);
		} else if (code == KeyCode.ESCAPE) {
			Platform.exit();
			System.exit(0);
		} else if (code == KeyCode.D) {
			for (Brick b : myBricks) {
				b.getBrickIV().setImage(null);
			}
			myBricks.clear();
		} else if (code == KeyCode.C) {
			Levels level = new Levels(currentLV);
			level.nextLevel(s, bouncerSpeed, winningText, animation);
			animation.stop();
		} else if (code == KeyCode.L) {
			myBouncer.addLife();
		} else if (code == KeyCode.P) {
			myBouncer.protectBouncer();
		} else if (code == KeyCode.RIGHT) {
			myPaddle.setX(myPaddle.getX() + KEY_INPUT_SPEED);
		} else if (code == KeyCode.LEFT) {
			myPaddle.setX(myPaddle.getX() - KEY_INPUT_SPEED);
		} else if (code == KeyCode.UP) {
			myPaddle.setY(myPaddle.getY() - KEY_INPUT_SPEED);
		} else if (code == KeyCode.DOWN) {
			myPaddle.setY(myPaddle.getY() + KEY_INPUT_SPEED);
		}
	}

	/**
	 * Start the program.
	 */
	public static void main(String[] args) {
		launch(args);
	}
}