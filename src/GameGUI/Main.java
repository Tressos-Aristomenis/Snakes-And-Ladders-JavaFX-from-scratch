package GameGUI;

import Res.ResourceLoader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Aris
 */
public class Main extends Application {
	private final String FXML_LOCATION = "/GameGUI/SnakesAndLadders.fxml";
	
	@Override
	public void start(Stage stage) throws Exception {
		FXMLLoader myLoader = new FXMLLoader(getClass().getResource(FXML_LOCATION));
		Parent root = myLoader.load();
		
		Scene scene = new Scene(root);
		
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("SNAKES AND LADDERS");
		stage.getIcons().add(ResourceLoader.getImage("snake.png"));
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}