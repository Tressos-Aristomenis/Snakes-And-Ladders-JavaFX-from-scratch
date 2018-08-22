package GameGUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Aris
 */
public class Main extends Application {
	private final String FXML_LOCATION = "SnakesAndLadders.fxml";
	
	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource(FXML_LOCATION));
		
		Scene scene = new Scene(root);
		
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("SNAKES AND LADDERS");
		stage.getIcons().add(new Image("icons/snake.png"));
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
<<<<<<< HEAD
}
=======
}

package GameGUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Aris
 */
public class Main extends Application {
	private final String FXML_LOCATION = "SnakesAndLadders.fxml";
	
	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource(FXML_LOCATION));
		
		Scene scene = new Scene(root);
		
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("SNAKES AND LADDERS");
		stage.getIcons().add(new Image("icons/snake.png"));
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
>>>>>>> 22f06dd8927291c79915ec5fe5abd8b0bcea84a6
