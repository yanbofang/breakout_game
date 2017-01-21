package breakout_game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Bouncer extends Breakout_Game{
    public static final String BALL_IMAGE = "red_puck.jpg";
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
    	X_DIRECTION = X_DIRECTION * -1;
    	setPos(SECOND_DELAY);
    }
    
    public void changeYDirection(){

    	Y_DIRECTION = Y_DIRECTION * -1;
    	setPos(SECOND_DELAY);
    }
    
    
    public Bouncer createBouncer(ImageView myPaddle){
    	Image image = new Image(getClass().getClassLoader().getResourceAsStream(BALL_IMAGE));
    	myBouncer = new Bouncer(image);
        myBouncer.imageView.setFitHeight(15);
        myBouncer.imageView.setFitWidth(15);
        myBouncer.imageView.setX(WIDTH / 2 - myBouncer.imageView.getBoundsInLocal().getWidth() / 2);
        myBouncer.imageView.setY(HEIGHT - myBouncer.imageView.getBoundsInLocal().getWidth() - myPaddle.getBoundsInLocal().getHeight());
        return myBouncer;
    }
    
    private void checkX(){
    	if (this.imageView.getBoundsInParent().getMaxX() >= WIDTH || this.imageView.getX() <= 0){
        	X_DIRECTION = X_DIRECTION * -1;
        }
    }
    
    private void checkY(int lives){
    	if (this.imageView.getBoundsInParent().getMaxY() >= HEIGHT){
        	//if (myBouncer.imageView.getBoundsInParent().getMaxY() >= HEIGHT && myBouncer.imageView.getBoundsInParent().contains(WIDTH/3 , HEIGHT, WIDTH/3, myBouncer.imageView.getBoundsInLocal().getHeight()/2)){

    			lives --;
    			
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
    
    
    
    public Bouncer myBouncerPos(double elapsedTime, ImageView myPaddle, int lives) {
    	checkX();
    	
    	checkY(lives);
    	
    	checkPaddle(myPaddle);
    	
    	setPos(elapsedTime);

        return this;
    }
    
    
    public void setPos(double elapsedTime){
    	this.imageView.setX(this.imageView.getX() + X_DIRECTION * BOUNCER_SPEED * elapsedTime);
    	this.imageView.setY(this.imageView.getY() - Y_DIRECTION * BOUNCER_SPEED * elapsedTime);
    }
    
    
    
}
