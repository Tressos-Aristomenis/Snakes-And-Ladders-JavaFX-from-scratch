package POJO;

import javafx.scene.image.ImageView;

/**
 *
 * @author Aris
 */
public class Piece {
	private int position;
	private String color;
	private ImageView pieceImage;

	public Piece(int position, String color, ImageView pieceImage) {
		this.position = position;
		this.color = color;
		this.pieceImage = pieceImage;
	}
	
	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public ImageView getPieceImage() {
		return pieceImage;
	}

	public void setPieceImage(ImageView pieceImage) {
		this.pieceImage = pieceImage;
	}
}
