import java.util.Iterator;
import java.util.LinkedList;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application{
	/*
	 * Create Class Variables
	 */
	private AnchorPane mainPane;
	private Pane gameField;
	private Character player;
	LinkedList<Bullet> bulletList;
	private Scene scene;
	private AnimationTimer gameLoop;
	private BulletSpawner bulletSpawner;
	//private 
	
	@Override
	public void start(Stage stage){
		initialize(stage);
		createGameInputs();
		createGameLoop();
		gameLoop.start();
		
		//Create Thread to monitor if player is still alive to continue
		new Thread(new Runnable() {
			@Override
			public void run() {
				//TODO put label score keeping in loop
				while(!player.isDead()) {System.out.println();}
				//Stop game loop when player dies
				gameLoop.stop();
			}
		}).start();
	}
	
	/*
	 * Initialize variables and wire up panes
	 */
	public void initialize(Stage stage) {
		//Create AnchorPane for root and link to scene then stage
		mainPane = new AnchorPane();
		mainPane.setStyle("-fx-background-color: black;");
		scene = new Scene(mainPane,500, 600);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("Bullet Hell!");
		stage.show();
		
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				System.exit(0);}
		});
		
		//Create a Pane for our game
		gameField = new Pane();
		gameField.setPrefSize(400, 500);
		mainPane.getChildren().add(gameField);
		gameField.setStyle("-fx-background-color: rgb(230, 96, 221, 0.2);");
		AnchorPane.setTopAnchor(gameField, 30.0);
		AnchorPane.setLeftAnchor(gameField, 50.0);
		
		//Create the player object and add to game pane
		player = new Character(gameField);
		gameField.getChildren().add(player);
		
		//Create our list of bullets the player will have to dodge
		bulletList = new LinkedList<Bullet>();
		bulletList.add(new Bullet(gameField,50, 50, 1, 1));
		gameField.getChildren().addAll(bulletList);
		
		//Create a BulletSpawer to spawn bullets
		bulletSpawner = new BulletSpawner(gameField);
	}
	
	/*
	 * Create our inputs to move the Player 
	 */
	public void createGameInputs() {
		//Move player in direction based on what key pressed
		scene.addEventHandler(KeyEvent.KEY_PRESSED, 
				ev->{
					KeyCode code = ev.getCode();
					if(code==KeyCode.UP || code==KeyCode.W) {
						player.moveUp();
					}
					if(code==KeyCode.DOWN || code==KeyCode.S) {
						player.moveDown();
					}
					if(code==KeyCode.LEFT || code==KeyCode.A) {
						player.moveLeft();
					}
					if(code==KeyCode.RIGHT|| code==KeyCode.D) {
						player.moveRight();
					}
				}
		);
		
		//When keys are released stop its directional movement
		scene.addEventHandler(KeyEvent.KEY_RELEASED, 
				ev->{
					KeyCode code = ev.getCode();
					if(code==KeyCode.UP || code==KeyCode.W) {
						player.stopYMovement();
					}
					if(code==KeyCode.DOWN || code==KeyCode.S) {
						player.stopYMovement();
					}
					if(code==KeyCode.LEFT || code==KeyCode.A) {
						player.stopXMovement();
					}
					if(code==KeyCode.RIGHT || code==KeyCode.D) {
						player.stopXMovement();
					}
				});
	}
	
	//Remove all bullets from field if player hit or game over
	public void removeAllBullets() {
		gameField.getChildren().removeAll(bulletList);
		bulletList.clear();
	}
	
	/*
	 * Create Loop that will run game
	 */
	public void createGameLoop() {
		//Create new Animation Timer 
		gameLoop = new AnimationTimer() {
			//Create variables to keep track of time so we don't spawn to often
		    private long spawnTimer = 1000 * 1_000_000;
		    private long prevTime = 0;
			@Override
			public void handle(long now) {
				/*
				 * Update player and bullet sprites by calling their update methods
				 */
				player.update();
				Iterator<Bullet> bulletIterator = bulletList.iterator();
				
				//Iterate over bullet list
				while(bulletIterator.hasNext()) {
					Bullet bullet = bulletIterator.next();
					//If Bullet out of bounds remove it
					if(bullet.isOutOfBounds()) {
						gameField.getChildren().remove(bullet);
						bulletIterator.remove();
					}
					else {
						//Move Bullet
						bullet.update();
						/*
						 * If bullet hits play take away a life, add temporary invincibility, 
						 * and stop iterating over bullets
						 */
						if(bullet.getHitBoxBounds().intersects(player.getHitBoxBounds()) && !player.isInvincible()) {
							player.addInvincibility();
							player.loseLife();
							removeAllBullets();
							break;
						}	
					}
				}
				//If player invisible remove it and do not spawn bullets
				if(player.isInvincible()) {
					player.removeInvincibility();
				}
				//Spawn bullets if timer allows it
				else if (now-prevTime > spawnTimer) {
					LinkedList<Bullet> newBullets = bulletSpawner.spawnRandomBullets();
					bulletList.addAll(bulletSpawner.spawnRandomBullets());
					gameField.getChildren().addAll(newBullets);
					prevTime = now;
				}
				
			}
		};
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
