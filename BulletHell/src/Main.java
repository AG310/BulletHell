import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application{

	private AnchorPane mainPane;
	private Pane gameField;
	private Character player;
	
	@Override
	public void start(Stage stage){
		initialize(stage);
		
		
	}
	
	public void initialize(Stage stage) {
		mainPane = new AnchorPane();
		Scene scene = new Scene(mainPane,500, 600);
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
	
	public static void main(String[] args) {
		launch(args);
	}

}
