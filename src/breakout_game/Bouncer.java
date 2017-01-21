package breakout_game;

import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class Bouncer extends Breakout_Game{
    public static final String BALL_IMAGE = "red_puck.jpg";
    private Integer lives = 2;
    private double X_DIRECTION;
    private double Y_DIRECTION;  
    Bouncer myBouncer;
    ImageView imageView;
    
    public Bouncer(Image image){
    	X_DIRECTION = 1;
        Y_DIRECTION = 1;
        imageView = new ImageView(image);
    }
    
    public double getXDirection(){
    	return this.X_DIRECTION;
    }
    
    public double getYDirection(){
    	return this.Y_DIRECTION;
    }
    
    public void changeXDirection(){
    	this.X_DIRECTION = this.X_DIRECTION * -1;
    	this.setPos(SECOND_DELAY);
    }
    
    public void changeYDirection(){

    	this.Y_DIRECTION = this.Y_DIRECTION * -1;
    	this.setPos(SECOND_DELAY);
    }
    
    
    public Bouncer createBouncer(ImageView myPaddle){
    	Image image = new Image(getClass().getClassLoader().getResourceAsStream(BALL_IMAGE));
    	myBouncer = new Bouncer(image);
        myBouncer.imageView.setFitHeight(15);
        myBouncer.imageView.setFitWidth(15);
        myBouncer.resetBouncer();
        return myBouncer;
    }
    
    public void resetBouncer(){
        this.imageView.setX(WIDTH / 2 - this.imageView.getBoundsInLocal().getWidth() / 2);
        this.imageView.setY(HEIGHT - this.imageView.getBoundsInLocal().getWidth() - 12);
    }
    
    private void checkX(){
    	if (this.imageView.getBoundsInParent().getMaxX() >= WIDTH || this.imageView.getX() <= 0){
        	X_DIRECTION = X_DIRECTION * -1;
        }
    }
    
    private void checkY(Timeline animation, Stage s){
    	System.out.println((this.imageView.getBoundsInParent().getMinX() >= WIDTH/3 && this.imageView.getBoundsInParent().getMaxX() <= 2*WIDTH/3));
    	if (this.imageView.getBoundsInParent().getMinY() <= 0 && (this.imageView.getBoundsInParent().getMinX() >= WIDTH/3 && this.imageView.getBoundsInParent().getMaxX() <= 2*WIDTH/3)){
            System.out.println("@@@@@@@");
            Levels level = new Levels();
            level.nextLevel(s);
    		animation.stop();
    		
    	}
    	else if (this.imageView.getBoundsInParent().getMaxY() >= HEIGHT){
    			lives --;
    	        if(lives <= 0){
    				animation.stop();
    			}else{
    				this.resetBouncer();
        			animation.pause();
    			}
    	        
    			//System.out.println("hhhhhhhhhhhhhh"+myBouncer.imageView.getBoundsInParent().getMaxY());
    		}
    	else if (this.imageView.getY() <= 0){
            	Y_DIRECTION = Y_DIRECTION * -1;  			
    		}
     }
    
    private void checkPaddle(ImageView myPaddle){
    	if (this.imageView.getBoundsInParent().intersects(myPaddle.getBoundsInParent())){
    		if(this.imageView.getX() + this.imageView.getBoundsInLocal().getWidth()/2 <= myPaddle.getX() + myPaddle.getBoundsInLocal().getWidth()/6){
    			X_DIRECTION = -1.10;
    			Y_DIRECTION = 1; 
    		}
    		else if(this.imageView.getX() + this.imageView.getBoundsInLocal().getWidth()/2 >= myPaddle.getX() + 5*myPaddle.getBoundsInLocal().getWidth()/6){
    			X_DIRECTION = 1.10;
    			Y_DIRECTION = 1; 
    		}
    		else if(this.imageView.getX() + this.imageView.getBoundsInLocal().getWidth()/2 <= myPaddle.getX() + 2*myPaddle.getBoundsInLocal().getWidth()/6){
    			X_DIRECTION = -0.8;
    			Y_DIRECTION = 1; 
    		}
    		else if(this.imageView.getX() + this.imageView.getBoundsInLocal().getWidth()/2 >= myPaddle.getX() + 4*myPaddle.getBoundsInLocal().getWidth()/6){
    			X_DIRECTION = 0.8;
    			Y_DIRECTION = 1; 
    		}
    		else if(this.imageView.getX() + this.imageView.getBoundsInLocal().getWidth()/2 <= myPaddle.getX() + 3*myPaddle.getBoundsInLocal().getWidth()/6){
    			X_DIRECTION = -0.5;
    			Y_DIRECTION = 1; 
    		}
    		else if(this.imageView.getX() + this.imageView.getBoundsInLocal().getWidth()/2 >= myPaddle.getX() + 3*myPaddle.getBoundsInLocal().getWidth()/6){
    			X_DIRECTION = 0.5;
    			Y_DIRECTION = 1; 
    		}
    	}
    }
    
    public Integer lives(){
    	return this.lives;
    }
    
    
    public Bouncer myBouncerPos(double elapsedTime, ImageView myPaddle, Timeline animation, Stage s) {
    	checkX();
    	
    	checkY(animation, s);
    	
    	checkPaddle(myPaddle);
    	
    	setPos(elapsedTime);

        return this;
    }
    
    
    public void setPos(double elapsedTime){
    	this.imageView.setX(this.imageView.getX() + X_DIRECTION * BOUNCER_SPEED * elapsedTime);
    	this.imageView.setY(this.imageView.getY() - Y_DIRECTION * BOUNCER_SPEED * elapsedTime);
    }
    
    
    
}