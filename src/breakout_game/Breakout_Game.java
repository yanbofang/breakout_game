package breakout_game;
import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
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
    public static final String BALL_IMAGE = "red_puck.jpg";
    public static final String BRICK_1 = "brick1.gif";
    public static final String BRICK_2 = "brick2.gif";
    public static final String BRICK_3 = "brick3.gif";
    public static final int WIDTH = 400;
    public static final int HEIGHT = 600;
    public static final Paint BACKGROUND = Color.WHITE;
    public static final String TABLE = "background.jpg";
    public static final int FRAMES_PER_SECOND = 60;
    public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    public static final int KEY_INPUT_SPEED = 20;
    public static final double GROWTH_RATE = 1.1;
    public static final int BOUNCER_SPEED = 240;

    // some things we need to remember during our game
    private Scene myScene;
    private ImageView myPaddle;
    private ImageView myBouncer;
    private ImageView myTable;
    private ArrayList<ImageView> myBricks;
    private Rectangle myTopBlock;
    private Rectangle myBottomBlock;
    private double X_DIRECTION = 1;
    private double Y_DIRECTION = 1;
    private int lives = 5;

    
    /**
     * Initialize what will be displayed and how it will be updated.
     */
    @Override
    public void start (Stage s) {
        // attach scene to the stage and display it
        Scene scene = setupGame(WIDTH, HEIGHT, BACKGROUND);
        s.setScene(scene);
        s.setTitle(TITLE);
        s.show();
        // attach "game loop" to timeline to play it
        KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY),
                                      e -> step(SECOND_DELAY));
        Timeline animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
    }
    
    private ImageView createPaddle(){
    	Image image = new Image(getClass().getClassLoader().getResourceAsStream(PADDLE_IMAGE));
        myPaddle = new ImageView(image);
        myPaddle.setX(WIDTH / 2 - myPaddle.getBoundsInLocal().getWidth() / 2);
        myPaddle.setY(HEIGHT - myPaddle.getBoundsInLocal().getHeight());
        return myPaddle;
    }
    
    private ImageView createBouncer(){
    	Image image = new Image(getClass().getClassLoader().getResourceAsStream(BALL_IMAGE));
        myBouncer = new ImageView(image);
        myBouncer.setFitHeight(20);
        myBouncer.setFitWidth(20);
        myBouncer.setX(WIDTH / 2 - myBouncer.getBoundsInLocal().getWidth() / 2);
        myBouncer.setY(HEIGHT - myBouncer.getBoundsInLocal().getWidth() - myPaddle.getBoundsInLocal().getHeight());
        return myBouncer;
    }
    
    private ImageView createTable(){
        Image table = new Image(getClass().getClassLoader().getResourceAsStream(TABLE));
        myTable = new ImageView(table);
        myTable.setFitHeight(600);
        myTable.setFitWidth(400);
        return myTable;
    }
    
    private ArrayList<ImageView> createBricks(){
        Image brick1 = new Image(getClass().getClassLoader().getResourceAsStream(BRICK_1));
        Image brick2 = new Image(getClass().getClassLoader().getResourceAsStream(BRICK_2));
        Image brick3 = new Image(getClass().getClassLoader().getResourceAsStream(BRICK_3));
        myBricks = new ArrayList<ImageView>();
        for(int i = 0; i < 8; i++){
        	ImageView myBrick1 = new ImageView(brick1);
            myBrick1.setFitHeight(20);
            myBrick1.setFitWidth(20);
            myBrick1.setX(WIDTH / 4 - myBrick1.getBoundsInLocal().getWidth() / 1.5 + i * 30);
        	myBrick1.setY(HEIGHT/ 4 - myBrick1.getBoundsInLocal().getHeight() / 2);
        	myBricks.add(myBrick1);
        }
        for(int i = 0; i < 5; i++){
        	ImageView myBrick2 = new ImageView(brick2);
        	myBrick2.setFitHeight(20);
        	myBrick2.setFitWidth(20);
            myBrick2.setX(WIDTH / 6 - myBrick2.getBoundsInLocal().getWidth() / 2 + i * 30);
         	myBrick2.setY(HEIGHT/ 4 - myBrick2.getBoundsInLocal().getHeight() / 2 + i * 30);
         	myBricks.add(myBrick2);
        }
        for(int i = 0; i < 5; i++){
        	ImageView myBrick3 = new ImageView(brick3);
        	myBrick3.setFitHeight(20);
        	myBrick3.setFitWidth(20);
            myBrick3.setX(WIDTH - (WIDTH/ 6) - myBrick3.getBoundsInLocal().getWidth() / 2 - i * 30);
         	myBrick3.setY(HEIGHT/4 - myBrick3.getBoundsInLocal().getHeight() / 2 + i * 30);
         	myBricks.add(myBrick3);
        }
        return myBricks;
    }
    
    // Create the game's "scene": what shapes will be in the game and their starting properties
    private Scene setupGame (int width, int height, Paint background) {
        // create one top level collection to organize the things in the scene
        Group root = new Group();
        // create a place to see the shapes
        myScene = new Scene(root, width, height, background);
        // make some shapes and set their properties
        myPaddle = createPaddle();
        myBouncer = createBouncer(); 
        myTable = createTable();
        myBricks = createBricks();
        // x and y represent the top left corner, so center it
        myTopBlock = new Rectangle(width / 2 - 25, height / 2 - 100, 50, 50);
        myTopBlock.setFill(Color.RED);
        myBottomBlock = new Rectangle(width / 2 - 25, height / 2 + 50, 50, 50);
        myBottomBlock.setFill(Color.BISQUE);
        // order added to the group is the order in which they are drawn
        root.getChildren().add(myTable);
        for(int i = 0; i < myBricks.size(); i++){
        	root.getChildren().add(myBricks.get(i));
        }
        root.getChildren().add(myPaddle);
        root.getChildren().add(myBouncer);
        root.getChildren().add(myTopBlock);
        root.getChildren().add(myBottomBlock);        
        // respond to input
        myScene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
        myScene.setOnMouseClicked(e -> handleMouseInput(e.getX(), e.getY()));
        return myScene;
    }

    private void myBouncerPos(double elapsedTime) {
    	if (myBouncer.getBoundsInParent().getMaxX() >= WIDTH || myBouncer.getX() <= 0){
        	X_DIRECTION = X_DIRECTION * -1;
        }
    	if (myBouncer.getBoundsInParent().getMaxY() >= HEIGHT || myBouncer.getY() <= 0){
    		if((myBouncer.getBoundsInParent().contains(WIDTH/3 , HEIGHT, WIDTH/3, myBouncer.getBoundsInLocal().getHeight()/2))){
    			lives --;
    		}else{
            	Y_DIRECTION = Y_DIRECTION * -1;  			
    		}
        }
    	if (myBouncer.getBoundsInParent().intersects(myPaddle.getBoundsInParent())){
    		if(myBouncer.getX() + myBouncer.getBoundsInLocal().getWidth()/2 <= myPaddle.getX() + myPaddle.getBoundsInLocal().getWidth()/4){
    			X_DIRECTION = -1;
    			Y_DIRECTION = 1; 
    		}
    		else if(myBouncer.getX() + myBouncer.getBoundsInLocal().getWidth()/2 >= myPaddle.getX() + 3*myPaddle.getBoundsInLocal().getWidth()/4){
    			X_DIRECTION = 1;
    			Y_DIRECTION = 1; 
    		}
    		else if(myBouncer.getX() + myBouncer.getBoundsInLocal().getWidth()/2 <= myPaddle.getX() + myPaddle.getBoundsInLocal().getWidth()/2){
    			X_DIRECTION = -0.75;
    			Y_DIRECTION = 1; 
    		}
    		else if(myBouncer.getX() + myBouncer.getBoundsInLocal().getWidth()/2 >= myPaddle.getX() + myPaddle.getBoundsInLocal().getWidth()/2){
    			X_DIRECTION = 0.75;
    			Y_DIRECTION = 1; 
    		}
    		
    	}

    	myBouncer.setX(myBouncer.getX() + X_DIRECTION * BOUNCER_SPEED * elapsedTime);
        myBouncer.setY(myBouncer.getY() - Y_DIRECTION * BOUNCER_SPEED * elapsedTime);
    }
    
    
    
    private void checkBricks(){
    	for(ImageView brick: myBricks){
    		if(brick.getBoundsInParent().intersects(myBouncer.getBoundsInParent())){
    			brick.setImage(null);
    			myBricks.remove(brick);
    			X_DIRECTION = X_DIRECTION * -1;
    			Y_DIRECTION = Y_DIRECTION * -1;  
    		}
    	}
    }
    // Change properties of shapes to animate them 
    // Note, there are more sophisticated ways to animate shapes, but these simple ways work fine to start.
    private void step (double elapsedTime) {
        // update attributes
        myBouncerPos(elapsedTime);
        checkBricks();
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
        if (myBottomBlock.getBoundsInParent().intersects(myBouncer.getBoundsInParent())) {
            myBottomBlock.setFill(Color.BURLYWOOD);
        }
        else {
            myBottomBlock.setFill(Color.BISQUE);
        }
    }

    // What to do each time a key is pressed
    private void handleKeyInput (KeyCode code) {
        if (code == KeyCode.RIGHT) {
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