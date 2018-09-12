package GameGUI;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author Aris
 */
public class PlayAgainOrExitAlert implements Initializable {
	@FXML
    private Label winnerName;

    @FXML
    private Button yesBtn;

    @FXML
    private Button noBtn;
	
	public static boolean isRematch;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		yesBtn.setOnAction(ev -> {
			isRematch = true;
			Stage stage = (Stage) yesBtn.getScene().getWindow();
			stage.fireEvent(new WindowEvent(stage,WindowEvent.WINDOW_CLOSE_REQUEST));
			stage.close();
		});
		
		noBtn.setOnAction(ev -> {
			isRematch = false;
			Stage stage = (Stage) noBtn.getScene().getWindow();
			stage.fireEvent(new WindowEvent(stage,WindowEvent.WINDOW_CLOSE_REQUEST));
			stage.close();
		});
	}
	
	public boolean rematchOrNot() {
		return isRematch;
	}
	
	public void setWinnerName(String winner) {
		winnerName.setText(winner);
	}
	
	public Label getWinnerName() {
		return winnerName;
	}
}
