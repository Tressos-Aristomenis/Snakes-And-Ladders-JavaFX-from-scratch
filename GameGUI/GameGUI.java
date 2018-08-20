package GameGUI;

import GameBoard.GameBoard;
import ObjectControllers.PlayerController;
import POJO.ComputerPlayer;
import POJO.HumanPlayer;
import POJO.Piece;
import POJO.Player;
import enums.GameMode;
import enums.GameVariation;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 *
 * @author Aris
 */

public class GameGUI implements Initializable {
	@FXML
	private GridPane gameGrid;
	
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
	private final String BLUE_PIECE_IMAGE_PATH	  = "icons/blue_circle.png";
	private final String GREEN_PIECE_IMAGE_PATH   = "icons/green_circle.png";
	private final String ROLL_THE_DICE_IMAGE_PATH = "icons/Dice.png";
	private final String TRACKS_PATH = "D:/MyWork/Other/Programming/REFACTORED_SnakesAndLadders/Refactored_SnakesAndLadders/src/Yann Tiersen";
	private final double MEDIA_VOLUME = 0.4;
	
	private ImageView inGameBluePieceImage, inGameGreenPieceImage;
	private GameBoard gameBoard;
	private GameVariation gameVariation;
	private GameMode gameMode;
	private PlayerController playerController;
	private static MediaPlayer MY_MEDIA_PLAYER;
	private File[] TRACKLIST;
	private File TRACKS_DIRECTORY;
	private Media currentTrack = null;
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeStatus();
	}
	
	private void initializeStatus() {
		colorTiles();
		
		gameBoard = new GameBoard();
		playerController = new PlayerController(this, gameBoard);
		
		gameMode = GameMode.AGAINST_HUMAN;
		gameVariation = GameVariation.CLASSIC;
		
		inGameBluePieceImage = new ImageView(BLUE_PIECE_IMAGE_PATH);
		inGameGreenPieceImage = new ImageView(GREEN_PIECE_IMAGE_PATH);
		rollTheDiceBtn.setGraphic(new ImageView(ROLL_THE_DICE_IMAGE_PATH));
		
		aboutMediaPlayer();
		startNewGame();
		
		
		
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
	}
	
	private void startNewGame() {
		nullifyPlayerStats();
		removePiecesFromBoard();
		resetAllTiles();
		unlockPieceImages();
		numberRolledLabel.setText("Game starting...");
		diceIndicatorImage.setImage(new Image("icons/blankdice.png"));
		currentTurnLabel.setText("");
		lockDiceButton();
	}
	
	public void playAgain() {
		removePiecesFromBoard();
		resetAllTiles();
		unlockPieceImages();
		numberRolledLabel.setText("Game starting...");
		diceIndicatorImage.setImage(new Image("icons/blankdice.png"));
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
	
	public void aboutMediaPlayer() {
		TRACKS_DIRECTORY = new File(TRACKS_PATH);
        TRACKLIST = TRACKS_DIRECTORY.listFiles();
		
		int randomTrackNumber = new Random().nextInt((TRACKLIST.length -1 - 0) + 1) + 0;
		File trackDirectory = new File(TRACKS_DIRECTORY.getPath()+"/"+TRACKLIST[randomTrackNumber].getName());

		try {
			currentTrack = new Media(trackDirectory.toURI().toURL().toString());
		} catch(MalformedURLException me) {}
		
		MY_MEDIA_PLAYER = new MediaPlayer(currentTrack);
		MY_MEDIA_PLAYER.play();
		MY_MEDIA_PLAYER.setVolume(MEDIA_VOLUME);
		
		nowPlayingLabel.setText(TRACKLIST[randomTrackNumber].getName());
		
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
		int randomTrackNumber = new Random().nextInt((TRACKLIST.length -1 - 0) + 1) + 0;
		File trackDirectory = new File(TRACKS_DIRECTORY.getPath()+"/"+TRACKLIST[randomTrackNumber].getName());
		
		MY_MEDIA_PLAYER.stop();
		try {
			currentTrack = new Media(trackDirectory.toURI().toURL().toString());
		} catch(MalformedURLException me) {}

		MY_MEDIA_PLAYER = new MediaPlayer(currentTrack);
		MY_MEDIA_PLAYER.play();
		MY_MEDIA_PLAYER.setVolume(MEDIA_VOLUME);

		nowPlayingLabel.setText(TRACKLIST[randomTrackNumber].getName());
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
				diceIndicatorImage.setImage(new Image("icons/dice1.png"));
				break;
			case 2:
				diceIndicatorImage.setImage(new Image("icons/dice2.png"));
				break;
			case 3:
				diceIndicatorImage.setImage(new Image("icons/dice3.png"));
				break;
			case 4:
				diceIndicatorImage.setImage(new Image("icons/dice4.png"));
				break;
			case 5:
				diceIndicatorImage.setImage(new Image("icons/dice5.png"));
				break;
			case 6:
				diceIndicatorImage.setImage(new Image("icons/dice6.png"));
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
		boolean restartGame = gameBoard.playAgainOrExit();
		
		if (restartGame) {
			Player winner = gameBoard.getCurrentPlayer();
			updateScore(winner);
			playAgain();
		} else {
			System.exit(0);
		}
	}
	
	public GameBoard getGameBoard() {
		return this.gameBoard;
	}
	
	public GameVariation getGameVariation() {
		return this.gameVariation;
	}
}
