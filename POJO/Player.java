package POJO;

import enums.PlayerType;

/**
 *
 * @author Aris
 */
public abstract class Player {
	private int turn;
	private Piece playerPiece;
	
	public Player() {}
	
	public Player(int turn, Piece playerPiece) {
		this.turn = turn;
		this.playerPiece = playerPiece;
	}

	public int getTurn() {
		return turn;
	}

	public void setTurn(int turn) {
		this.turn = turn;
	}

	public Piece getPlayerPiece() {
		return playerPiece;
	}

	public void setPlayerPiece(Piece playerPiece) {
		this.playerPiece = playerPiece;
	}
	
	public abstract PlayerType getPlayerType();
	
	@Override
	public String toString() {
		return "[Turn: " + turn + ", Piece: " + playerPiece.getColor() + "]";
	}
}
