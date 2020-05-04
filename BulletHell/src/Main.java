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

	private AnchorPane mainPane;
	private Pane gameField;
	private Character player;
	LinkedList<Bullet> bulletList;
	private Scene scene;
	private AnimationTimer gameLoop;
	@Override
	public void start(Stage stage){
		initialize(stage);
		createGameInputs();
		createGameLoop();
		gameLoop.start();
		
	}
	
	public void initialize(Stage stage) {
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
		
		gameField = new Pane();
		gameField.setPrefSize(400, 500);
		mainPane.getChildren().add(gameField);
		gameField.setStyle("-fx-background-color: rgb(230, 96, 221, 0.2);");
		AnchorPane.setTopAnchor(gameField, 30.0);
		AnchorPane.setLeftAnchor(gameField, 50.0);
		
		player = new Character(gameField);
		gameField.getChildren().add(player);
		
		bulletList = new LinkedList<Bullet>();
		bulletList.add(new Bullet(gameField, 1, 1));
		gameField.getChildren().addAll(bulletList);
	}
	
	public void createGameInputs() {
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
	
	public void createGameLoop() {
		gameLoop = new AnimationTimer() {
			
			@Override
			public void handle(long now) {
				player.update();
				for(Bullet bullet : bulletList) {
					bullet.update();
					if(bullet.getHitBoxBounds().intersects(player.getHitBoxBounds())) {
						player.loseLife();
					}
				}
			}
		};
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
