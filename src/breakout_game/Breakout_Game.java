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

    // some things we need to remember during our game
    private Scene myScene;
    private ImageView myPaddle;
    private Bouncer myBouncer;
    private ImageView myTable;
    private ArrayList<Brick> myBricks;
    private ArrayList<Powerup> myPowerups;
    private ImageView slowSign;
    private Bricks bricks;
    private Text livesLeft;
    private Text currentLevel;
    private Timeline animation;
    private Powerup power;
    
    
 
    

    /**
     * Initialize what will be displayed and how it will be updated.
     */
    @Override
    public void start (Stage s) {
        // attach scene to the stage and display it
    	int currentLV = 1;
        Scene scene = setupGame(WIDTH, HEIGHT, BACKGROUND, s, currentLV);
        s.setScene(scene);
        s.setTitle(TITLE);
        s.show();
        // attach "game loop" to timeline to play it
        setAnimation(s, currentLV);
        //animation.play();
    }
    
    public void setAnimation(Stage s, int currentLV){
    	KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY),
                e -> step(SECOND_DELAY, s, currentLV));
    	animation = new Timeline();
    	animation.setCycleCount(Timeline.INDEFINITE);
    	animation.getKeyFrames().add(frame);
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
    
    private ImageView createSlowSign(){
        Image slow = new Image(getClass().getClassLoader().getResourceAsStream("slow.gif"));
        slowSign = new ImageView(slow);
        slowSign.setFitHeight(60);
        slowSign.setFitWidth(60);
        slowSign.setX(WIDTH / 2 - 30);
        slowSign.setY(HEIGHT/2 + 30);
        return slowSign;
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
    public Scene setupGame (int width, int height, Paint background, Stage s, int currentLV) {
        // create one top level collection to organize the things in the scene
        Group root = new Group();
        // create a place to see the shapes
        myScene = new Scene(root, width, height, background);
        myTable = createTable();
        myPaddle = createPaddle();
        Bouncer bouncer = new Bouncer(null);
        myBouncer = bouncer.createBouncer(myPaddle); 
        bricks = new Bricks();
        myBricks = bricks.createBricks(WIDTH, HEIGHT, currentLV);
        power = new Powerup(null);
        myPowerups = power.createPowerups(myBricks);

        
        slowSign = createSlowSign();
        
        
        Reflection r = new Reflection();
        r.setFraction(0.7f);
        livesLeft = createLivesLeft(r);
        
    	Levels level = new Levels(currentLV, animation);
        currentLevel = createCurrentLevel(r, level);
        
        // order added to the group is the order in which they are drawn
        root.getChildren().add(myTable);
        root.getChildren().add(livesLeft);
        root.getChildren().add(currentLevel);
        for(int i = 0; i < myBricks.size(); i++){
        	root.getChildren().add(myBricks.get(i).getBrickIV());
        }
        for(int i = 0; i < myPowerups.size(); i++){
        	root.getChildren().add(myPowerups.get(i).getPowerupIV());
        }
        root.getChildren().add(myPaddle);
        root.getChildren().add(myBouncer.imageView);
        root.getChildren().add(slowSign);        
        

        // respond to input
        myScene.setOnKeyPressed(e -> handleKeyInput(e.getCode(), s, currentLV));
        //myScene.setOnKeyPressed(e -> handleCheatKey(e.getCode(), s, currentLV));
        return myScene;
    }

    
    // Change properties of shapes to animate them 
    // Note, there are more sophisticated ways to animate shapes, but these simple ways work fine to start.
    public void step (double elapsedTime, Stage s, int currentLV) {
        // update attributes
        myBouncer = myBouncer.myBouncerPos(elapsedTime, myPaddle, animation, s, currentLV, myBricks);
        myPowerups = power.myPowerPos(elapsedTime, myPowerups, myBricks, myPaddle, myBouncer);
        livesLeft.setText("Lives: " + myBouncer.lives());
        myBricks = bricks.checkBricks(myBouncer);
        slowSign.setRotate(slowSign.getRotate() + 1);

        // check for collisions
        // with shapes, can check precisely

        // with images can only check bounding box
        if (slowSign.getBoundsInParent().intersects(myBouncer.imageView.getBoundsInParent())) {
        	myBouncer.changeSpeed(80);
        }
        else {
        	myBouncer.changeSpeed(200);
        }
    }

    // What to do each time a key is pressed
    private void handleKeyInput (KeyCode code, Stage s, int currentLV) {
    	if (code == KeyCode.SPACE){
    		animation.play();
    	}
    	else if (code == KeyCode.C){
            Levels level = new Levels(currentLV, animation);
            level.nextLevel(s);
            animation.stop();
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
    
    
    /**
     * Start the program.
     */
    public static void main (String[] args) {
        launch(args);
    }
}