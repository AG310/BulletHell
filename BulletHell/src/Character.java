import java.net.URISyntaxException;

import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Character extends Pane{
	private static int SPEED = 4;
	
	private ImageView spriteImageView;
	private Image spriteStraightImage;
	private Image spriteLeftImage;
	private Image spriteRightImage;
	private int lives;
	private double x;
	private double y;
	private double xVelocity;
	private double yVelocity;
	private double gamePaneHeight;
	private double gamePaneWidth;
	private Pane gamePane;
	private Circle hitCircle;

	public Character(Pane gamePane) {
		this.gamePane = gamePane;
		lives = 3;
		xVelocity=0;
		yVelocity=0;
		gamePaneWidth = gamePane.getPrefWidth();
		gamePaneHeight = gamePane.getPrefHeight();
		try {
			spriteStraightImage = new Image(getClass().getResource("straightFlight.png").toURI().toString());
			spriteLeftImage = new Image(getClass().getResource("leftFlight.png").toURI().toString());
			spriteRightImage = new Image(getClass().getResource("rightFlight.png").toURI().toString());
			spriteImageView = new ImageView(spriteStraightImage);
			this.getChildren().add(spriteImageView);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		x = (gamePaneWidth - spriteImageView.getImage().getWidth()) / 2.0;
        y = gamePaneHeight * 0.8;
        
        hitCircle = new Circle(3, Color.CHARTREUSE);
        this.getChildren().add(hitCircle);
        refreshLocation();
	}
	
	public void update() {
		x+=xVelocity;
		y+=yVelocity;
		if(x<0) {
			x=0;
		}
		else if(x>gamePaneWidth-spriteImageView.getImage().getWidth()) {
			x=gamePaneWidth-spriteImageView.getImage().getWidth();
		}
		if(y<0) {
			y=0;
		}
		else if(y>gamePaneHeight-spriteImageView.getImage().getHeight()) {
			y=gamePaneHeight-spriteImageView.getImage().getHeight();
		}
		refreshLocation();
	}
	
	public void moveUp() {
		//TODO: Check bounds on game area
		yVelocity = -SPEED;
		setStraightImage();
	}
	
	public void moveDown() {
		//TODO: Check bounds on game area
		yVelocity = SPEED;
		setStraightImage();
	}
	
	public void moveLeft() {
		xVelocity = -SPEED;
		setLeftImage();
	}
	
	public void moveRight() {
		xVelocity = SPEED;
		setRightImage();
	}
	
	public void stopXMovement() {
		xVelocity=0;
		setStraightImage();
	}
	
	public void stopYMovement() {
		yVelocity=0;
	}
	
	public void setStraightImage() {
		spriteImageView.setImage(spriteStraightImage);
	}
	public void setLeftImage() {
		spriteImageView.setImage(spriteLeftImage);
	}
	public void setRightImage() {
		spriteImageView.setImage(spriteRightImage);
	}

	public void refreshLocation() {
		this.relocate(x, y);
        hitCircle.setCenterX(spriteImageView.getImage().getWidth()/2);
        hitCircle.setCenterY(spriteImageView.getImage().getHeight()/2);
	}
	
	public void loseLife() {
		lives--;
		x = (gamePaneWidth - spriteImageView.getImage().getWidth()) / 2.0;
        y = gamePaneHeight * 0.8;
        refreshLocation();
		
	}
	
	public boolean isDead() {
		return lives<=0;
	}
	
	public Bounds getHitBoxBounds() {
		return gamePane.sceneToLocal(this.localToScene(hitCircle.getBoundsInLocal()));
	}
}
