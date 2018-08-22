package POJO;

import enums.PlayerType;

/**
 *
 * @author Aris
 */
public class HumanPlayer extends Player {
	public HumanPlayer() {
		super();
	}
	
	public HumanPlayer(int turn, Piece playerPiece) {
		super(turn, playerPiece);
	}
	
	@Override
	public PlayerType getPlayerType() {
		return PlayerType.HUMAN;
	}
}
