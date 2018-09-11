package POJO;

import enums.PlayerType;

/**
 *
 * @author Aris
 */
public class ComputerPlayer extends Player {
	public ComputerPlayer() {
		super();
	}
	
	public ComputerPlayer(int turn, Piece playerPiece) {
		super(turn, playerPiece);
	}

	@Override
	public PlayerType getPlayerType() {
		return PlayerType.COMPUTER;
	}
}
