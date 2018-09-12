package GameGUI;

import GameBoard.GameBoard;
import ObjectControllers.PlayerController;
import POJO.ComputerPlayer;
import POJO.HumanPlayer;
import POJO.Piece;
import POJO.Player;
import Res.ResourceLoader;
import enums.GameMode;
import enums.GameVariation;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

/**
 *
 * @author Aris
 */

public class GameGUI implements Initializable {
    @FXML
    private GridPane gameGrid;
	
	@FXML
	private Slider volumeSlider;
	
    @FXML
    private Label nowPlayingLabel;
	
    @FXML
    private Label currentTurnLabel;

    @FXML
    private Button rollTheDiceBtn;

    @FXML
    private Label numberRolledLabel;

    @FXML
    private ImageView diceIndicatorImage;
	
    @FXML
    private Button againstHumanBtn;

    @FXML
    private Button againstComputerBtn;

    @FXML
    private Label myScore;

    @FXML
    private Label enemyScore;

    @FXML
    private ImageView chooseBluePieceImage;

    @FXML
    private ImageView chooseGreenPieceImage;
	
    @FXML
    private Button classicVariation;

    @FXML
    private Button aristosVariation;
	
	public static final String SNAKE_OR_LADDER_HIT_CELL_STYLE = "-fx-background-color: #FFDF00";
	private final String DEFAULT_TILE_STYLE = "-fx-background-color: #228b22";
	private final Image BLUE_PIECE_IMAGE = ResourceLoader.getImage("blue_circle.png");
	private final Image GREEN_PIECE_IMAGE   = ResourceLoader.getImage("green_circle.png");
	private final Image ROLL_THE_DICE_IMAGE = ResourceLoader.getImage("Dice.png");
	private final double DEFAULT_STARTING_VOLUME = 0.0;
	
	private ImageView inGameBluePieceImage, inGameGreenPieceImage;
	private GameBoard gameBoard;
	private GameVariation gameVariation;
	private GameMode gameMode;
	private PlayerController playerController;
	private static MediaPlayer MY_MEDIA_PLAYER;
	private Media currentTrack = null;
	
	
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		try {
			initializeStatus();
		} catch (Exception e) {
			e.printStackTrace();
			reportError(e.toString());
		}
	}
		
	private void initializeStatus() {
		colorTiles();
		gameBoard = new GameBoard();
		playerController = new PlayerController(this, gameBoard);
		gameMode = GameMode.AGAINST_HUMAN;
		gameVariation = GameVariation.CLASSIC;
		
		inGameBluePieceImage = new ImageView(BLUE_PIECE_IMAGE);
		inGameGreenPieceImage = new ImageView(GREEN_PIECE_IMAGE);
		rollTheDiceBtn.setGraphic(new ImageView(ROLL_THE_DICE_IMAGE));
		
		againstHumanBtn.setOnAction(ev -> {
			gameMode = GameMode.AGAINST_HUMAN;
			startNewGame();
		});
		againstComputerBtn.setOnAction(ev -> {
			gameMode = GameMode.AGAINST_COMPUTER;
			startNewGame();
		});
		classicVariation.setOnAction(ev -> {
			gameVariation = GameVariation.CLASSIC;
			startNewGame();
		});
		aristosVariation.setOnAction(ev -> {
			gameVariation = GameVariation.ARISTOMENIS;
			startNewGame();
		});
		
		initializeMediaPlayer();
		startNewGame();
	}
	
	private void startNewGame() {
		nullifyPlayerStats();
		removePiecesFromBoard();
		resetAllTiles();
		unlockPieceImages();
		numberRolledLabel.setText("Game starting...");
		diceIndicatorImage.setImage(ResourceLoader.getImage("blankdice.png"));
		currentTurnLabel.setText("");
		lockDiceButton();
	}
	
	public void playAgain() {
		removePiecesFromBoard();
		resetAllTiles();
		unlockPieceImages();
		numberRolledLabel.setText("Game starting...");
		diceIndicatorImage.setImage(ResourceLoader.getImage("blankdice.png"));
		currentTurnLabel.setText("");
		lockDiceButton();
	}
	
	@FXML
	public void onDiceClick() {
		playerController.rollDiceAndMove();
	}
	
	@FXML
	public void choosePieceImage(MouseEvent event) {
		ImageView chosenImage = (ImageView) event.getSource();
		
		final Piece bluePiece = new Piece(GameBoard.PLAYER_STARTING_POSITION, "Blue", inGameBluePieceImage);
		final Piece greenPiece = new Piece(GameBoard.PLAYER_STARTING_POSITION, "Green", inGameGreenPieceImage);
		
		Player player1 = null;
		Player player2 = null;
		
		if (chosenImage.equals(chooseBluePieceImage)) {
			player1 = new HumanPlayer(1, bluePiece);
			
			if (gameMode == GameMode.AGAINST_HUMAN) {
				player2 = new HumanPlayer(0, greenPiece);
			} else if (gameMode == GameMode.AGAINST_COMPUTER) {
				player2 = new ComputerPlayer(0, greenPiece);
			}
		} else if (chosenImage.equals(chooseGreenPieceImage)) {
			player1 = new HumanPlayer(1, greenPiece);
			
			if (gameMode == GameMode.AGAINST_HUMAN) {
				player2 = new HumanPlayer(0, bluePiece);
			} else if (gameMode == GameMode.AGAINST_COMPUTER) {
				player2 = new ComputerPlayer(0, bluePiece);
			}
		}
		
		gameBoard.setPlayer1(player1);
		gameBoard.setPlayer2(player2);
		
		lockPieceImages();
		updateCurrentTurnLabel();
		unlockDiceButton();
	}
	
	
	public void initializeMediaPlayer() {
		int randomTrackNumber = new Random().nextInt((ResourceLoader.TRACKLIST.length -1 - 0) + 1) + 0;
		String track = ResourceLoader.getTrack(randomTrackNumber);
		currentTrack = new Media(track);
		
		MY_MEDIA_PLAYER = new MediaPlayer(currentTrack);
		MY_MEDIA_PLAYER.play();
<<<<<<< HEAD
<<<<<<< HEAD
		MY_MEDIA_PLAYER.setVolume(DEFAULT_STARTING_VOLUME);
=======
>>>>>>> 784998a3cc437a2c0687c8331a8bed24b8f99517
=======
>>>>>>> 784998a3cc437a2c0687c8331a8bed24b8f99517
		
		volumeSlider.setValue(MY_MEDIA_PLAYER.getVolume() * DEFAULT_STARTING_VOLUME);
		volumeSlider.valueProperty().addListener(new InvalidationListener() {
			@Override
			public void invalidated(Observable observable) {
				MY_MEDIA_PLAYER.setVolume(volumeSlider.getValue() / 100.0);
			}
		});
		
		nowPlayingLabel.setText(ResourceLoader.TRACKLIST[randomTrackNumber].getName());
		
		MY_MEDIA_PLAYER.setOnEndOfMedia(new Runnable() {
			@Override
			public void run() {
				playNextTrack();
			}
		});
	}
	
	@FXML
        public void resumeTrack() {
		MY_MEDIA_PLAYER.play();
	}
	
	@FXML
        public void pauseTrack() {
		MY_MEDIA_PLAYER.pause();
	}
	
	@FXML
	public void playNextTrack() {
		MY_MEDIA_PLAYER.stop();			// stop current media player.
		int randomTrackNumber = new Random().nextInt((ResourceLoader.TRACKLIST.length -1 - 0) + 1) + 0;
		String track = ResourceLoader.getTrack(randomTrackNumber);
		currentTrack = new Media(track);
		
		MY_MEDIA_PLAYER = new MediaPlayer(currentTrack);	// create new media player with another track.
		MY_MEDIA_PLAYER.play();

		nowPlayingLabel.setText(ResourceLoader.TRACKLIST[randomTrackNumber].getName());
	}
	
	public void updateScore(Player winner) {
		if (winner == gameBoard.getPlayer1()) {
			int newScore = Integer.parseInt(myScore.getText()) + 1;
			myScore.setText(String.valueOf(newScore));
		}
		else if (winner == gameBoard.getPlayer2()) {
			int newScore = Integer.parseInt(enemyScore.getText()) + 1;
			enemyScore.setText(String.valueOf(newScore));
		}
	}
	
	public void unlockDiceButton() {
		rollTheDiceBtn.setDisable(false);
	}
	
	public void lockDiceButton() {
		rollTheDiceBtn.setDisable(true);
	}
	
	private void unlockPieceImages() {
		chooseBluePieceImage.setDisable(false);
		chooseGreenPieceImage.setDisable(false);
	}
	
	private void lockPieceImages() {
		chooseBluePieceImage.setDisable(true);
		chooseGreenPieceImage.setDisable(true);
	}
	
	private void removePiecesFromBoard() {
		gameGrid.getChildren().remove(inGameBluePieceImage);
		gameGrid.getChildren().remove(inGameGreenPieceImage);
	}
	
	private void nullifyPlayerStats() {
		myScore.setText("0");
		enemyScore.setText("0");
	}
	
	public void movePieceImages(final int destinationRow, final int destinationColumn) {
		ImageView currentPieceImage = gameBoard.getCurrentPlayer().getPlayerPiece().getPieceImage();
		gameGrid.getChildren().remove(currentPieceImage);
		gameGrid.add(currentPieceImage, destinationColumn, destinationRow);
	}
	
	private void colorTiles() {
		for (Node node : gameGrid.getChildren()) {
			((Pane) node).setStyle(DEFAULT_TILE_STYLE);
		}
	}
	
	public void indicateDiceRoll(int diceRoll) {
		switch (diceRoll) {
			case 1:
				diceIndicatorImage.setImage(ResourceLoader.getImage("dice1.png"));
				break;
			case 2:
				diceIndicatorImage.setImage(ResourceLoader.getImage("dice2.png"));
				break;
			case 3:
				diceIndicatorImage.setImage(ResourceLoader.getImage("dice3.png"));
				break;
			case 4:
				diceIndicatorImage.setImage(ResourceLoader.getImage("dice4.png"));
				break;
			case 5:
				diceIndicatorImage.setImage(ResourceLoader.getImage("dice5.png"));
				break;
			case 6:
				diceIndicatorImage.setImage(ResourceLoader.getImage("dice6.png"));
				break;
		}
		
		numberRolledLabel.setText(gameBoard.getCurrentPlayer().getPlayerPiece().getColor() + " rolled: " + diceRoll);
	}
	
	public void updateCurrentTurnLabel() {
		currentTurnLabel.setText(gameBoard.getCurrentPlayer().getPlayerPiece().getColor().toUpperCase());
	}
	
	public void resetAllTiles() {
		for (Node child : gameGrid.getChildren()) {
			if (child instanceof Pane) {
				child.setStyle(DEFAULT_TILE_STYLE);
			}
		}
	}
	
	public void setCellStyle(Pane clicked, String style) {
		clicked.setStyle(style);
	}
	
	public Node getNodeByRowColumnIndex(final int row, final int column) {
		Node result = null;
		ObservableList<Node> childrens = gameGrid.getChildren();

		for (Node node : childrens) {
			if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column) {
				result = node;
				break;
			}
		}
		
		return result;
	}
	
	public void gameOver() {
		Stage gameOverStage = new Stage();
		Player winner = gameBoard.getCurrentPlayer();
		Parent root = null;
		PlayAgainOrExitAlert playAgainOrExit = null;
		
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PlayAgainOrExitPanel.fxml"));
			root = fxmlLoader.load();
			playAgainOrExit = fxmlLoader.<PlayAgainOrExitAlert>getController();
			playAgainOrExit.setWinnerName(winner.getPlayerPiece().getColor().toUpperCase());
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		gameOverStage.setTitle("Game over");
		gameOverStage.setScene(new Scene(root));
		gameOverStage.show();
		gameOverStage.setAlwaysOnTop(true);
		
		gameOverStage.setOnCloseRequest(ev -> {
			playAgainOrNot();
		});
	}
	
	private void playAgainOrNot() {
		Player winner = gameBoard.getCurrentPlayer();
		
		if (PlayAgainOrExitAlert.isRematch) {
			updateScore(winner);
			playAgain();
		} else {
			System.exit(0);
		}
	}
	
    public void reportError(String exception) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(exception);
        alert.showAndWait();
    }
	
	public GameBoard getGameBoard() {
		return this.gameBoard;
	}
	
	public GameVariation getGameVariation() {
		return this.gameVariation;
	}
}
