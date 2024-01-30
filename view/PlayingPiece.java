/**
 * Class:
 * 		PlayingPiece
 * Description:
 * 		Represents a clickable playing piece of one of the players on gameboard
 */

package view;

import javax.swing.*;
import java.awt.Color;
import java.awt.Rectangle;

import controller.*;

public class PlayingPiece extends JButton {

	// fields
	private int nodeNum;
	private String pieceType;
	private boolean translucent;
	private Color selectedColor;

	// constants for piece colors 
	private static final Color BLACK_PIECE = new Color(33, 33, 33);
	private static final Color BLACK_LUSCENT = new Color(82, 80, 79);

	private static final Color RED_PIECE = new Color(255, 17, 0);
	private static final Color RED_LUSCENT = new Color(250, 127, 127);

	private static final Color BLUE_PIECE = new Color(0, 21, 255);
	private static final Color BLUE_LUSCENT = new Color(127, 143, 250);

	private static final Color GREEN_PIECE = new Color(0, 255, 51);
	private static final Color GREEN_LUSCENT = new Color(131, 250, 127);

	private static final Color ORANGE_PIECE = new Color(255, 157, 0);
	private static final Color ORANGE_LUSCENT = new Color(250, 201, 127);

	public PlayingPiece(int nodeNum, String pieceType, int color, boolean translucent) {

		// 0 = Black MR X
		// 1 = Red Detective1
		// 2 = Blue Detective2
		// 3 = Green Bobby1
		// 4 = Orange Bobby2

		super();

		// assign fields
		this.nodeNum = nodeNum;
		this.pieceType = pieceType;
		this.translucent = translucent;

		// assign color based on the entered color integer, and whether or not to make it "translucent" (used for displaying possible moves)
		switch (color) {
			case 0:
				if (translucent)
					selectedColor = BLACK_LUSCENT;
				else
					selectedColor = BLACK_PIECE;
				break;
			case 1:
				if (translucent)
					selectedColor = RED_LUSCENT;
				else
					selectedColor = RED_PIECE;
				break;
			case 2:
				if (translucent)
					selectedColor = BLUE_LUSCENT;
				else
					selectedColor = BLUE_PIECE;
				break;
			case 3:
				if (translucent)
					selectedColor = GREEN_LUSCENT;
				else
					selectedColor = GREEN_PIECE;
				break;
			case 4:
				if (translucent)
					selectedColor = ORANGE_LUSCENT;
				else
					selectedColor = ORANGE_PIECE;
				break;
			default:
				selectedColor = Color.WHITE;
				break;
		}

		// make the playing piece have a colored thick border but transparent center
		setBorder(BorderFactory.createLineBorder(selectedColor, 7));
		setOpaque(false);
		setContentAreaFilled(false);

	}

	// getters and setters

	public int getNodeNum() {
		return nodeNum;
	}

	public void setNodeNum(int nodeNum) {
		this.nodeNum = nodeNum;
	}

	public String getPieceType() {
		return pieceType;
	}

	public void setPieceType(String pieceType) {
		this.pieceType = pieceType;
	}

	public boolean isTranslucent() {
		return translucent;
	}
	
	public void setTranslucent(boolean translucent) {
		this.translucent = translucent;
	}
	
	// utility methods

	// create a Rectangle object that will be used as the bounds when setting the playing piece on gameBoard
	public Rectangle generateBounds() {

		return new Rectangle(
			(ScotlandYardController.nodeMap[nodeNum].getMapCords()[0] - 20), 
			(ScotlandYardController.nodeMap[nodeNum].getMapCords()[1] - 20),
			40, 40);
		
	}
	
	@Override
	public String toString() {
		return "PlayingPiece [nodeNum=" + nodeNum + ", pieceType=" + pieceType + "]";
	}

}