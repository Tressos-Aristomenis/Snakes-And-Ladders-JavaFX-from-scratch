package ObjectControllers;

import GameBoard.GameBoard;
import GameGUI.GameGUI;
import POJO.Player;
import enums.GameVariation;
import enums.PlayerType;
import javafx.animation.PauseTransition;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 *
 * @author Aris
 */
public class PlayerController {
	private GameGUI gameGUI;
	private GameBoard gameBoard;
	
	public PlayerController(GameGUI gameGUI, GameBoard gameBoard) {
		this.gameGUI = gameGUI;
		this.gameBoard = gameBoard;
	}
	
	public void rollDiceAndMove() {
		int diceRoll = gameBoard.rollDice();
		gameGUI.lockDiceButton();
		
		PauseTransition pause = new PauseTransition(Duration.seconds(GameBoard.DICE_ROLL_DELAY));
		
		pause.setOnFinished(event -> {
			gameGUI.indicateDiceRoll(diceRoll);
			gameGUI.resetAllTiles();
			gameGUI.unlockDiceButton();
			
			int newIndex = getPlayerIndexAfterRoll(diceRoll);
			move(newIndex);
			
			if (newIndex == GameBoard.WIN_POINT) {
				pause.stop();
				gameGUI.gameOver();
			} else {
				swapTurns();
				gameGUI.updateCurrentTurnLabel();
			}
			
			if (isComputerTurn()) {
				computerRollDiceAndMove();
			}
		});
		
		pause.play();
	}
	
	public void computerRollDiceAndMove() {
		int diceRoll = gameBoard.rollDice();
		gameGUI.lockDiceButton();
		
		PauseTransition pause = new PauseTransition(Duration.seconds(GameBoard.COMPUTER_MOVE_DELAY));
		
		pause.setOnFinished(event -> {
			gameGUI.indicateDiceRoll(diceRoll);
			gameGUI.resetAllTiles();
			gameGUI.unlockDiceButton();
			
			int newIndex = getPlayerIndexAfterRoll(diceRoll);
			move(newIndex);
			
			if (newIndex == GameBoard.WIN_POINT) {
				pause.stop();
				gameGUI.gameOver();
			} else {
				swapTurns();
				gameGUI.updateCurrentTurnLabel();
			}
		});
		
		pause.play();
	}
	
	public void move(int currentIndex) {
		int[] newCoordinates = gameBoard.getBoardCoordinates(GameBoard.NUMBER_OF_TILES - currentIndex);
		Pane clickedCell = (Pane)gameGUI.getNodeByRowColumnIndex(newCoordinates[0], newCoordinates[1]);
		
		gameBoard.getCurrentPlayer().getPlayerPiece().setPosition(currentIndex);
		gameGUI.movePieceImages(newCoordinates[0], newCoordinates[1]);
		
		if (currentIndex == GameBoard.WIN_POINT) {
			--currentIndex;							// silly way of avoiding the ArrayOutOfBoundsException.
		}
		
		if (gameBoard.getTile(currentIndex).containsLadderOrSnake()) {
			gameGUI.setCellStyle(clickedCell, GameGUI.SNAKE_OR_LADDER_HIT_CELL_STYLE);
			int updatedIndex = gameBoard.getUpdatedPosition(currentIndex);
			move(updatedIndex);
			return;
		}
	}
	
	public int getPlayerIndexAfterRoll(int diceRoll) {
		Player currentPlayer = gameBoard.getCurrentPlayer();
		
		int currentPosition = currentPlayer.getPlayerPiece().getPosition();
		int newIndex = -1;
		
		newIndex = currentPosition + diceRoll;
		
		if (newIndex > 100) {
			if (gameGUI.getGameVariation() == GameVariation.CLASSIC) {
				newIndex = 200 - newIndex;
			} else if (gameGUI.getGameVariation() == GameVariation.ARISTOMENIS) {
				newIndex = newIndex - 100;
			}
		}
		
		return newIndex;
	}
	
	public void swapTurns() {
		if (gameBoard.getCurrentPlayer() == gameBoard.getPlayer1()) {
			gameBoard.getPlayer1().setTurn(0);
			gameBoard.getPlayer2().setTurn(1);
		} else if (gameBoard.getCurrentPlayer() == gameBoard.getPlayer2()) {
			gameBoard.getPlayer1().setTurn(1);
			gameBoard.getPlayer2().setTurn(0);
		}
	}
	
	public boolean isComputerTurn() {
		return (gameBoard.getCurrentPlayer() == gameBoard.getPlayer2()) && (gameBoard.getPlayer2().getPlayerType() == PlayerType.COMPUTER);
	}
}