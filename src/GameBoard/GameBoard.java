package GameBoard;

import POJO.Player;
import POJO.Tile;
import enums.TileContainer;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

/**
 *
 * @author Aris
 */
public class GameBoard {
	public static final int ROWS = 10;
	public static final int COLS = 10;
	public static final int NUMBER_OF_TILES = ROWS * COLS;
	public static final int WIN_POINT = 100;
	public static final int DICE_ROLL_DELAY = 1;
	public static final int SECOND_MOVE_DELAY = 0;
	public static final int BEFORE_COMPUTER_TURN_DELAY = 2;
	public static final int PLAYER_STARTING_POSITION = 0;
	
	private Tile[] tiles;
	private Player player1, player2;
	private Map<Integer, Integer> SNAKES;
	private Map<Integer, Integer> LADDERS;
	
	
	public GameBoard() {
		initializeSNAKESandLADDERSPositions();
		initializeTiles();
	}
	
	private void initializeTiles() {
		tiles = new Tile[NUMBER_OF_TILES];
		
		for (int i = 0; i < tiles.length; i++) {
			if (SNAKES.containsKey(i)) {
				tiles[i] = new Tile(i, TileContainer.SNAKE);
			} else if (LADDERS.containsKey(i)) {
				tiles[i] = new Tile(i, TileContainer.LADDER);
			} else {
				tiles[i] = new Tile(i, TileContainer.NONE);
			}
		}
	}
	
	private void initializeSNAKESandLADDERSPositions() {
		SNAKES = new HashMap<>();
		LADDERS = new HashMap<>();
		
		SNAKES.put(19, 6);
		SNAKES.put(42, 17);
		SNAKES.put(49, 16);
		SNAKES.put(58, 45);
		SNAKES.put(61, 22);
		SNAKES.put(75, 47);
		SNAKES.put(88, 36);
		SNAKES.put(94, 70);
		SNAKES.put(97, 65);
		LADDERS.put(3, 37);
		LADDERS.put(7, 13);
		LADDERS.put(14, 32);
		LADDERS.put(27, 56);
		LADDERS.put(39, 44);
		LADDERS.put(41, 85);
		LADDERS.put(69, 87);
		LADDERS.put(79, 98);
		LADDERS.put(89, 91);
	}
	
	public int getUpdatedPosition(int currentIndex) {
		int updatedIndex = -1;
		
		if (SNAKES.containsKey(currentIndex)) {
		   updatedIndex = SNAKES.get(currentIndex);
		} else if (LADDERS.containsKey(currentIndex)) {
		   updatedIndex = LADDERS.get(currentIndex);
		}
		
		return updatedIndex;
	}
	
	public int[] getBoardCoordinates(int newIndex) {
		int row = newIndex / ROWS;	// ROWS - 1 - newIndex / ROWS if I don't send rows*cols - newIndex
		int column = newIndex % COLS;
		
		if (row % 2 == 1) {		// If I don't want to send ROW * COLS - newIndex, the condition will be row % 2 == 0
			column = COLS - 1 - column;
		}
		
		return new int[] {row, column};
	}
	
	public int rollDice() {
		final int MIN = 1;
		final int MAX = 6;
		
		return new Random().nextInt((MAX - MIN) + 1) + MIN;
	}
	
	public boolean playAgainOrExit() {
		Alert alert = new Alert(AlertType.NONE);
		alert.setTitle("Game Over!");
		alert.setContentText(getCurrentPlayer().getPlayerPiece().getColor().toUpperCase() + " WINS !!!\n\nDo you want to play again?");
		alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
		
		Optional<ButtonType> result = alert.showAndWait();
		
		return (result.get() == ButtonType.YES);
	}
	
	public Player getCurrentPlayer() {
		Player currentPlayer = null;
		
		if (player1.getTurn() == 1) {
			currentPlayer = player1;
		} else if (player2.getTurn() == 1) {
			currentPlayer = player2;
		}
		
		return currentPlayer;
	}
	
	public Tile getTile(int index) {
		return tiles[index];
	}
	
	public Player getPlayer1() {
		return player1;
	}

	public void setPlayer1(Player player1) {
		this.player1 = player1;
	}

	public Player getPlayer2() {
		return player2;
	}

	public void setPlayer2(Player player2) {
		this.player2 = player2;
	}
}