package breakout_game;
import java.util.ArrayList;

import javafx.scene.text.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
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
 * @author Robert C. Duvall
 * @author Yanbo Fang
 */
public class Breakout_Game extends Application {
    public static final String TITLE = "Breakout Game";
    public static final String PADDLE_IMAGE = "paddle.gif";
    public static final int WIDTH = 400;
    public static final int HEIGHT = 600;
    public static final Paint BACKGROUND = Color.WHITE;
    public static final String TABLE = "background.jpg";
    public static final int FRAMES_PER_SECOND = 60;
    public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    public static final int KEY_INPUT_SPEED = 25;
    public static final double GROWTH_RATE = 1.1;
    public static final int BOUNCER_SPEED = 240;

    // some things we need to remember during our game
    private Scene myScene;
    private ImageView myPaddle;
    private Bouncer myBouncer;
    private ImageView myTable;
    private ArrayList<ImageView> myBricks;
    private Rectangle myTopBlock;
    private Rectangle myBottomBlock;
    private Bricks bricks;
    private Text livesLeft;
    private Text currentLevel;
    private Timeline animation;

    /**
     * Initialize what will be displayed and how it will be updated.
     */
    @Override
    public void start (Stage s) {
        // attach scene to the stage and display it
    	int currentLV = 1;
        Scene scene = setupGame(WIDTH, HEIGHT, BACKGROUND, currentLV);
        s.setScene(scene);
        s.setTitle(TITLE);
        s.show();
        // attach "game loop" to timeline to play it
        KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY),
                                      e -> step(SECOND_DELAY, s, currentLV));
        animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        //animation.play();
    }
    
    private ImageView createPaddle(){
    	Image image = new Image(getClass().getClassLoader().getResourceAsStream(PADDLE_IMAGE));
        myPaddle = new ImageView(image);
        myPaddle.setX(WIDTH / 2 - myPaddle.getBoundsInLocal().getWidth() / 2);
        myPaddle.setY(HEIGHT - 12);
        return myPaddle;
    }
    
    
    private ImageView createTable(){
        Image table = new Image(getClass().getClassLoader().getResourceAsStream(TABLE));
        myTable = new ImageView(table);
        myTable.setFitHeight(600);
        myTable.setFitWidth(400);
        return myTable;
    }
    
    private Text createLivesLeft(Reflection r){
        livesLeft = new Text();
        livesLeft.setX(13);
        livesLeft.setY(21);  
        livesLeft.setText("Lives: " + myBouncer.lives()); 
        livesLeft.setFont(Font.font ("Verdana", 13));
        livesLeft.setFill(Color.MAGENTA);
        livesLeft.setEffect(r);
        return livesLeft;
    }
    
    private Text createCurrentLevel(Reflection r, Levels level){
        currentLevel = new Text();
        currentLevel.setX(280);
        currentLevel.setY(21);
        currentLevel.setFont(Font.font ("Verdana", 13));
        currentLevel.setText("Current Level: " + level.currentLevel());
        currentLevel.setFill(Color.MAGENTA);
        currentLevel.setEffect(r);
        return currentLevel;
    }
    // Create the game's "scene": what shapes will be in the game and their starting properties
    /**
     * 
     * @param width
     * @param height
     * @param background
     * @return
     */
    public Scene setupGame (int width, int height, Paint background, int currentLV) {
        // create one top level collection to organize the things in the scene
        Group root = new Group();
        // create a place to see the shapes
        myScene = new Scene(root, width, height, background);
        // make some shapes and set their properties
        myPaddle = createPaddle();
        bricks = new Bricks();
        Bouncer bouncer = new Bouncer(null);
        myBouncer = bouncer.createBouncer(myPaddle); 
       
        Powerup power = new Powerup();
        
        myTable = createTable();
        
        myBricks = bricks.createBricks(true, true, WIDTH, HEIGHT);
        // x and y represent the top left corner, so center it
        myTopBlock = new Rectangle(width / 2 - 25, height / 2 - 100, 50, 50);
        myTopBlock.setFill(Color.RED);
        myBottomBlock = new Rectangle(width / 2 - 25, height / 2 + 50, 50, 50);
        myBottomBlock.setFill(Color.BISQUE);
        
        
        Reflection r = new Reflection();
        r.setFraction(0.7f);
        livesLeft = createLivesLeft(r);
        
    	Levels level = new Levels(currentLV);
        currentLevel = createCurrentLevel(r, level);
        
        // order added to the group is the order in which they are drawn
        root.getChildren().add(myTable);
        root.getChildren().add(livesLeft);
        root.getChildren().add(currentLevel);
        for(int i = 0; i < myBricks.size(); i++){
        	root.getChildren().add(myBricks.get(i));
        }
        root.getChildren().add(myPaddle);
        root.getChildren().add(myBouncer.imageView);
        root.getChildren().add(myTopBlock);
        root.getChildren().add(myBottomBlock);        
        

        // respond to input
        myScene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
        myScene.setOnMouseClicked(e -> handleMouseInput(e.getX(), e.getY()));
        return myScene;
    }

    
    // Change properties of shapes to animate them 
    // Note, there are more sophisticated ways to animate shapes, but these simple ways work fine to start.
    private void step (double elapsedTime, Stage s, int currentLV) {
        // update attributes
        myBouncer = myBouncer.myBouncerPos(elapsedTime, myPaddle, animation, s, currentLV);
        livesLeft.setText("Lives: " + myBouncer.lives());
        myBricks = bricks.checkBricks(myBouncer);
        myTopBlock.setRotate(myBottomBlock.getRotate() - 1);
        myBottomBlock.setRotate(myBottomBlock.getRotate() + 1);

        // check for collisions
        // with shapes, can check precisely
        Shape intersect = Shape.intersect(myTopBlock, myBottomBlock);
        if (intersect.getBoundsInLocal().getWidth() != -1) {
            myTopBlock.setFill(Color.PLUM);
        }
        else {
            myTopBlock.setFill(Color.RED);
        }
        // with images can only check bounding box
        if (myBottomBlock.getBoundsInParent().intersects(myBouncer.imageView.getBoundsInParent())) {
            myBottomBlock.setFill(Color.BURLYWOOD);
        }
        else {
            myBottomBlock.setFill(Color.BISQUE);
        }
    }

    // What to do each time a key is pressed
    private void handleKeyInput (KeyCode code) {
    	if (code == KeyCode.SPACE){
    		animation.play();
    	}
    	else if (code == KeyCode.RIGHT) {
            myPaddle.setX(myPaddle.getX() + KEY_INPUT_SPEED);
        }
        else if (code == KeyCode.LEFT) {
            myPaddle.setX(myPaddle.getX() - KEY_INPUT_SPEED);
        }
        else if (code == KeyCode.UP) {
            myPaddle.setY(myPaddle.getY() - KEY_INPUT_SPEED);
        }
        else if (code == KeyCode.DOWN) {
            myPaddle.setY(myPaddle.getY() + KEY_INPUT_SPEED);
        }
    }

    // What to do each time a key is pressed
    private void handleMouseInput (double x, double y) {
        if (myBottomBlock.contains(x, y)) {
            myBottomBlock.setScaleX(myBottomBlock.getScaleX() * GROWTH_RATE);
            myBottomBlock.setScaleY(myBottomBlock.getScaleY() * GROWTH_RATE);
        }
    }

    /**
     * Start the program.
     */
    public static void main (String[] args) {
        launch(args);
    }
}