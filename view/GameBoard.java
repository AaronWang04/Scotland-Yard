/**
 * Class:
 * 		GameBoard
 * Description:
 * 		This class represents the JPanel that holds the board where actual gameplay occurs, including elements 
 * 		such as the map, the playing pieces, and the travel log
 * Areas of Concern:
 * 		None
 * Author(s):
 * 		Ray
 */

package view;

import controller.*;
import model.*;

import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import java.util.*;

public class GameBoard extends JPanel {

	// game map label
	private ImageIcon bgMap = new ImageIcon("images/Scotland_Yard_Map.png");
	private JLabel mapLabel = new JLabel();

	// turn label
	private JButton endTurnButton = new JButton("End turn");
	private JLabel turnLabel = new JLabel();

	// round label
	private JLabel roundTextLabel = new JLabel("Round: ");
	private JLabel roundNumLabel = new JLabel();
	private JLabel roundBgLabel = new JLabel(new ImageIcon( new ImageIcon("images/sticky_note.png").getImage().getScaledInstance(200, 207, java.awt.Image.SCALE_SMOOTH) ));

	// travel log object
	private TravelLog travelLog = new TravelLog();

	// travel log panel
	private JLabel travelBGLabel = new JLabel();

	// ticket log panel
	private JPanel ticketLogPanel = new JPanel();
	// ticket logs of Mr X and Detectives
	private TicketBoard mrXTicketBoard = new TicketBoard(TurnController.ticketBank.getBlackTicket(), TurnController.ticketBank.getDoubleTicket());
	private TicketBoard detectiveTicketBoard = new TicketBoard(TurnController.ticketBank.getTaxiTicket(), TurnController.ticketBank.getBusTicket(), TurnController.ticketBank.getSubwayTicket());

	// crosshair image JLabel for detectives to see
	private JLabel crosshairLabel = new JLabel(new ImageIcon( new ImageIcon("images/crosshair.png").getImage().getScaledInstance(86, 90, java.awt.Image.SCALE_SMOOTH)));

	// alert box label - will be used to notify the user of any game-events
	private JLabel alertLabel = new JLabel();

	// represents the currently being shown possible moves
	private ArrayList<PlayingPiece> possibleMoves = new ArrayList<PlayingPiece>();
	private String currentlyShownMoves;
	
	// a margin of 20px between objects and the edge of the JFrame
	private final int margin = 20;

	// constructor
	public GameBoard() {

		// JPanel setup
		super();

		setSize(1920, 1080);
		setBackground(Color.GRAY);
		setLayout(null);
		
		// add map label
		mapLabel.setIcon(bgMap);
		mapLabel.setBounds((this.getWidth() - bgMap.getIconWidth() - margin), margin, bgMap.getIconWidth(), bgMap.getIconHeight());
		add(mapLabel);
		
		// add end turn button
		endTurnButton.setBounds(margin + 690, margin, 150, 40);
		add(endTurnButton);
		turnLabel.setBounds(margin, margin, 670, 40);
		turnLabel.setHorizontalAlignment(SwingConstants.CENTER);
		turnLabel.setFont(new Font("Bahnschrift", Font.PLAIN,23));
		add(turnLabel);
		
		// add labels for round information
		roundTextLabel.setBounds(1705, margin + mapLabel.getHeight() + 65, 280, 25);
		roundTextLabel.setFont(new Font("Bahnschrift", Font.PLAIN,23));
		add(roundTextLabel);
		roundNumLabel.setBounds(1735, margin + mapLabel.getHeight() + 95, 150, 55);
		roundNumLabel.setText("1");
		roundNumLabel.setFont(new Font("Bahnschrift", Font.PLAIN,50));
		add(roundNumLabel);
		roundBgLabel.setBounds(1640, margin + mapLabel.getHeight() + 15, 200, 207);
		add(roundBgLabel);

		// add the travelLog
		travelLog.setBounds(margin+21, 101, 791, 508);
		add(travelLog);

		// add the travelLog's background image
		travelBGLabel.setIcon(new ImageIcon("images/travelLog_Board.png"));
		travelBGLabel.setBounds(margin, 80, 833,550);
		add(travelBGLabel);

		// add the panel to contain the travelLog and background image
		ticketLogPanel.setBounds(margin, 630, 840, 184);
		ticketLogPanel.setLayout(null);
		add(ticketLogPanel);
		
		// add the alerting information label
		alertLabel.setBounds((this.getWidth() - bgMap.getIconWidth() - margin), bgMap.getIconHeight() + 2 * margin, 720, 90);
		alertLabel.setFont(new Font("Bahnschrift", Font.PLAIN,30));
		add(alertLabel);

		// set the preferred size of the Mr X display crosshair without adding it yet
		crosshairLabel.setSize(86, 86);

	}

	// GETTERS AND SETTERS

	public JLabel getMapLabel() {
		return mapLabel;
	}

	public void setMapLabel(JLabel mapLabel) {
		this.mapLabel = mapLabel;
	}

	public JButton getEndTurnButton() {
		return endTurnButton;
	}

	public void setEndTurnButton(JButton endTurnButton) {
		this.endTurnButton = endTurnButton;
	}

	public JLabel getTurnLabel() {
		return turnLabel;
	}
	
	public void setTurnLabel(JLabel turnLabel) {
		this.turnLabel = turnLabel;
	}
	
	public JLabel getRoundNumLabel() {
		return roundNumLabel;
	}
	
	public void setRoundNumLabel(JLabel roundNumLabel) {
		this.roundNumLabel = roundNumLabel;
	}
	
	public TicketBoard getMrXTicketBoard() {
		return mrXTicketBoard;
	}

	public void setMrXTicketBoard(TicketBoard mrXTicketBoard) {
		this.mrXTicketBoard = mrXTicketBoard;
	}

	public TicketBoard getDetectiveTicketBoard() {
		return detectiveTicketBoard;
	}

	public void setDetectiveTicketBoard(TicketBoard detectiveTicketBoard) {
		this.detectiveTicketBoard = detectiveTicketBoard;
	}

	public JLabel getAlertLabel() {
		return alertLabel;
	}

	public void setAlertLabel(JLabel alertLabel) {
		this.alertLabel = alertLabel;
	}
	
	public ArrayList<PlayingPiece> getPossibleMoves() {
		return possibleMoves;
	}

	public void setPossibleMoves(ArrayList<PlayingPiece> possibleMoves) {
		this.possibleMoves = possibleMoves;
	}

	public String getCurrentlyShownMoves() {
		return currentlyShownMoves;
	}

	public void setCurrentlyShownMoves(String currentlyShownMoves) {
		this.currentlyShownMoves = currentlyShownMoves;
	}
	
	public TravelLog getTravelLog() {
		return travelLog;
	}

	// UTILITY METHODS

	// selectively draws the playing pieces onto the gameBoard based on who's turn it is
	public void setPieces() {
		
		// generate the bounds for each playing piece
		for(Player player:TurnController.playerArray)
			player.getPlayingPiece().setBounds(player.getPlayingPiece().generateBounds());
	
		// if it is currently Mr X's turn, add his playing piece, add the other detectives' and bobbies' playing pieces regardless
		if (TurnController.isMrXTurn)
			mapLabel.add(TurnController.playerArray[0].getPlayingPiece());
		
		for (int i = 1; i < TurnController.playerArray.length; i++)
			mapLabel.add(TurnController.playerArray[i].getPlayingPiece());

	}

	// displays the possible moves of a playing piece using PlayingPiece objects
	public void displayPossibleMoves() {
		
		//  add the possible playing moves if there are any
		for (PlayingPiece piece: possibleMoves) {
			piece.setBounds(piece.generateBounds());
			mapLabel.add(piece);
		}
		
		update();
		
	}
	
	// hides the possible moves that are currently being displayed
	public void hidePossibleMoves() {
		
		//  remove the possible playing moves
		for (PlayingPiece piece: possibleMoves)
			mapLabel.remove(piece);
		
		update();
		
	}

	// utility method for quickly assigning an ActionListener to the possibleMoves pieces
	public void possibleMovesListener(ScotlandYardController listener) {

		for (PlayingPiece piece: possibleMoves)
			piece.addActionListener(listener);

	}
	
	// updates certain GUI elements that change with each turn (ie round label, currently shown players, etc)
	public void updateTurn() {

		// change look based on each new turn
		
		// clear the alertLabel if it isn't already empty
		alertLabel.setForeground(Color.BLACK);
		alertLabel.setText("");

		// remove the crosshair label in case the last turn was a revealing turn
		mapLabel.remove(crosshairLabel);

		// if it is Mr X's turn
		if (TurnController.isMrXTurn) {

			turnLabel.setText("Mr. X's Turn");

			// reset the borders of the two clickable tickets to "unselected"
			mrXTicketBoard.getBlackButton().setBorder(null);
			mrXTicketBoard.getDoubleButton().setBorder(null);

			// change the ticketBoard
			ticketLogPanel.remove(detectiveTicketBoard);
			ticketLogPanel.add(mrXTicketBoard);

			// if Mr X is about to be revealed with his next move, let him know through the alert label
			for (int round: TravelLog.surfaceRound)
				if (round == TurnController.round - 1) {
					alertLabel.setForeground(new Color(179, 14, 14));
					alertLabel.setText("Your next move will be revealed to the detectives!");
				}

		}
		// if it is the detectives' turn
		else {

			turnLabel.setText("Detective's Turn");

			// change the ticketBoard
			ticketLogPanel.remove(mrXTicketBoard);
			ticketLogPanel.add(detectiveTicketBoard);

			for (int round: TravelLog.surfaceRound) {
				// if the round is a revealing round, place the crosshair image at the location of Mr X
				if (round == TurnController.round - 1) {
					int revealedNode = Integer.valueOf(travelLog.getTravelLocationsArray()[round].getText());
					int[] mapImageCoords = ScotlandYardController.nodeMap[revealedNode].getMapCords();
					crosshairLabel.setLocation(mapImageCoords[0]- 43, mapImageCoords[1] - 43);
					//crosshairLabel.setLocation(mapImageCoords[0] + (this.getWidth() - bgMap.getIconWidth() - margin) - 43, mapImageCoords[1] - 22);
					mapLabel.add(crosshairLabel);
				}
				// if the round is right before a revealing round, give the detectives a heads up that they will see Mr X's position next turn
				else if (round - 1 == TurnController.round - 1) {
					
					alertLabel.setForeground(new Color(179, 14, 14));
					alertLabel.setText("You will see Mr X's location on the next turn!");

				}
			}

		}

		// update the round number
		getRoundNumLabel().setText(String.valueOf(TurnController.round));

		update();

	}

	// updates the travelLog of Mr X
	public void updateLog(String transportMethod, int position, boolean isSecondTurn) {

		travelLog.logNewEntry(transportMethod, position, isSecondTurn,true);
		
		update();

	}

	// redraws the gameBoard, especially important for when playing pieces change positions 
	public void update() {

		// redraw all the pieces
		for(Player player:TurnController.playerArray)
			mapLabel.remove(player.getPlayingPiece());

		setPieces();

		// redraw the travel panel 
		remove(travelBGLabel);
		add(travelBGLabel);
		
		// reassign numbers to the ticketLog for detectives, then update its JPanel holder
		detectiveTicketBoard.setAllNum(TurnController.ticketBank.getTaxiTicket(), TurnController.ticketBank.getBusTicket(), TurnController.ticketBank.getSubwayTicket());
		detectiveTicketBoard.update();
		
		// reassign numbers to the ticketLog for MrX, then update its JPanel holder
		mrXTicketBoard.setAllNum(TurnController.ticketBank.getBlackTicket(), TurnController.ticketBank.getDoubleTicket());
		mrXTicketBoard.update();

		// redraw the map last so that the pieces appear over it
		remove(mapLabel);
		add(mapLabel);
		
		invalidate();
		repaint();
		validate();

	}
	
}