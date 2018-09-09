package POJO;

import enums.TileContainer;

/**
 *
 * @author Aris
 */
public class Tile {
	private int position;
	private final TileContainer tileContainer;

	public Tile(int position, TileContainer tileContainer) {
		this.position = position;
		this.tileContainer = tileContainer;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}
	
	public TileContainer getTileContainer() {
		return this.tileContainer;
	}
	
	public boolean containsLadderOrSnake() {
		return this.tileContainer != TileContainer.NONE;
	}
}