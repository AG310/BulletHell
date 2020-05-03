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
		gameField.setStyle("-fx-background-color: lightblue;");
		AnchorPane.setTopAnchor(gameField, 30.0);
		AnchorPane.setLeftAnchor(gameField, 50.0);
		
		player = new Character(gameField);
		gameField.getChildren().add(player);
		player.refreshLocation();
	}
	
	public void createGameInputs() {
		scene.addEventHandler(KeyEvent.KEY_PRESSED, 
				ev->{
					KeyCode code = ev.getCode();
					if(code==KeyCode.UP) {
						player.moveUp();
					}
					if(code==KeyCode.DOWN) {
						player.moveDown();
					}
					if(code==KeyCode.LEFT) {
						player.moveLeft();
					}
					if(code==KeyCode.RIGHT) {
						player.moveRight();
					}
				}
		);
		
		scene.addEventHandler(KeyEvent.KEY_RELEASED, 
				ev->{
					KeyCode code = ev.getCode();
					if(code==KeyCode.UP) {
						player.stopYMovement();
					}
					if(code==KeyCode.DOWN) {
						player.stopYMovement();
					}
					if(code==KeyCode.LEFT) {
						player.stopXMovement();
					}
					if(code==KeyCode.RIGHT) {
						player.stopXMovement();
					}
				});
	}
	
	public void createGameLoop() {
		gameLoop = new AnimationTimer() {
			
			@Override
			public void handle(long now) {
				player.update();
			}
		};
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
