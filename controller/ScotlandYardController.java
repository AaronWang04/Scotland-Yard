/**
 * Class:
 * 		ScotlandYardController
 * Description:
 * 		This class brings the surface with all the screens and GUI components together with the TurnController 
 * 		that controls turns and player movement
 */

package controller;

import view.*;
import model.*;

import java.awt.Color;
import java.awt.event.*;

public class ScotlandYardController implements ActionListener {

	// create a new game surface (JFrame)
	private Surface surface = new Surface();

	// create a new controller for turns
	private TurnController turnController;

	// publically available array of nodes 
	public static final Node[] nodeMap = FileReader.readRoutes();
	
    // the value of the playAs (against computer)
	//		Either "as Mister X" or "as Detective"
    private String playAs = "as Mister X";

	// constructor
	public ScotlandYardController() {

		// add the menu button listeners to this object
		menuButtonListeners();

	}

	// add menu button listeners
	public void menuButtonListeners() {

		surface.getMenuScreen().getPlayComputerButton().addActionListener(this);
		surface.getMenuScreen().getPlayHumanButton().addActionListener(this);
		surface.getMenuScreen().getPlayAsButton().addActionListener(this);
		surface.getMenuScreen().getHelpButton().addActionListener(this);
		surface.getHelpScreen().getBackToMenu().addActionListener(this);

	}

	// add any other buttons to have their action listener set to this class
	public void setButtonListeners() {

		surface.getHideScreen().getReadyButton().addActionListener(this);
		surface.getGameBoard().getEndTurnButton().addActionListener(this);
		surface.getGameBoard().getMrXTicketBoard().getBlackButton().addActionListener(this);
		surface.getGameBoard().getMrXTicketBoard().getDoubleButton().addActionListener(this);

		// make each game piece clickable
		for (int i = 0; i < TurnController.playerArray.length; i++)
			TurnController.playerArray[i].getPlayingPiece().addActionListener(this); 

		// make each button that is on a surfacing round clickable
		for (int round : TravelLog.surfaceRound)
			surface.getGameBoard().getTravelLog().getTravelTicketsArray()[round].addActionListener(this);


	}
	
	// make a decision of what to do next with the game based on the returned status of the turn
	private void decideNextRound(int turnStatus) {
		
		//      0: game continues
		//      1: the turn is incomplete
		//      2: detective win
		//      3: x win

		if (turnStatus == 1) {
			surface.getGameBoard().getAlertLabel().setText("You have not moved all of your playing pieces!");
			surface.getGameBoard().getAlertLabel().setForeground(Color.BLACK);
		}
		else if (turnStatus == 2) {

			// change the screen to the win screen for Detectives
			surface.setWinScreen(new WinScreen("DETECTIVE"));
			surface.changeScreens("win");

		}
		else if (turnStatus == 3) {

			// change the screen to the win screen for Mr X
			surface.setWinScreen(new WinScreen("MISTER X"));
			surface.changeScreens("win");

		}
		else {
			
			// set the pieces of each player (Mr X, Detectives, Bobbies) on the board (essentially redrawing their positions)
			surface.getGameBoard().setPieces();

			// update the look of the game board 
			surface.getGameBoard().updateTurn();

			// set the doubleTicketUsedRound to be false
			TurnController.doubleTicketUsedRound = false;
			
			// if the game is PVP, change the screen to hide screen
			if (TurnController.mode.equals("PVP"))
				surface.changeScreens("hide");
			else {
				// otherwise tell the computer to make a move (if it is its turn)

				// if computer is Mr X
				if (TurnController.isMrXTurn && playAs.equals("as Detective")) {

					// select a move based on the hard difficulty function
					int compSelectedMove = TurnController.computer.ExtremeMrX(nodeMap,surface.getGameBoard().getTravelLog(),TurnController.playerArray,TurnController.round);

					String modeOfTransportation = nodeMap[TurnController.playerArray[0].getCurrentNode()].getTransitType().get(compSelectedMove);
					
					TurnController.move(TurnController.playerArray[0], compSelectedMove, modeOfTransportation, false);

					// update the travelLog
					surface.getGameBoard().updateLog(modeOfTransportation, compSelectedMove, false);

					// tell the turnController to attempt to move to the next turn
					int toPlayerTurn = turnController.nextTurn();

					// run this function again to decide what action to take for the next turn
					decideNextRound(toPlayerTurn);

				}
				// if computer is Detective
				else if (!TurnController.isMrXTurn && playAs.equals("as Mister X")) {

					// perform the move for each detective/bobby
					for (int i = 1; i < TurnController.playerArray.length; i++) {
						int compSelectedMove = TurnController.computer.HardDetective(surface.getGameBoard().getTravelLog(),TurnController.playerArray[i],TurnController.round);
						String modeOfTransportation = nodeMap[TurnController.playerArray[i].getCurrentNode()].getTransitType().get(compSelectedMove);

						TurnController.move(TurnController.playerArray[i], compSelectedMove, modeOfTransportation, false);
					}

					// tell the turnController to attempt to move to the next turn
					int toPlayerTurn = turnController.nextTurn();

					// run this function again to decide what action to take for the next turn
					decideNextRound(toPlayerTurn);

				}

			}
			
			// update JFrame
			surface.update();

		}

	}

	// listener function to detect the firing of events
	@Override
	public void actionPerformed(ActionEvent event) {

		if (event.getSource() == surface.getGameBoard().getEndTurnButton()) {

			// tell the turn controller to perform the next turn
			int turnStatus = turnController.nextTurn();

			// decide what action to take next based on gamemode and returned status by nextTurn()
			decideNextRound(turnStatus);

		}
		else if (event.getSource() == surface.getMenuScreen().getPlayHumanButton()) {

			// set the turnController to be a new mode of PVP and gameBoard to be a new gameBoard
			turnController = new TurnController("PVP");
			surface.setGameBoard(new GameBoard());

			// set up the button listeners
			setButtonListeners();

			// set the pieces of each player down on the gameBoard
			surface.getGameBoard().setPieces();
			
			// update the turn
			surface.getGameBoard().updateTurn();

			// update JFrame
			surface.update();
			
			// change the screen to the game screen
			surface.changeScreens("game");

		}
		else if (event.getSource() == surface.getMenuScreen().getPlayComputerButton()) {

			// set the turnController to be a new mode of PVP plus who the human will be playing as and gameBoard to be a new gameBoard
			turnController = new TurnController("PVE " + playAs);
			surface.setGameBoard(new GameBoard());

			// set up the button listeners
			setButtonListeners();

			// set the pieces of each player down on the gameBoard
			surface.getGameBoard().setPieces();
			
			// update the turn
			surface.getGameBoard().updateTurn();

			// update JFrame
			surface.update();
			
			// if the player is playing as detective (ie the computer is Mr X)
			if (playAs.equals("as Detective")) {

				// tell the computer to make a move first
				int compSelectedMove = TurnController.computer.ExtremeMrX(nodeMap,surface.getGameBoard().getTravelLog(),TurnController.playerArray,TurnController.round);

				String modeOfTransportation = nodeMap[TurnController.playerArray[0].getCurrentNode()].getTransitType().get(compSelectedMove);

				TurnController.move(TurnController.playerArray[0], compSelectedMove, modeOfTransportation, false);

				surface.getGameBoard().updateLog(modeOfTransportation, compSelectedMove, false);

				int turnStatus = turnController.nextTurn();

				decideNextRound(turnStatus);

			}

			surface.changeScreens("game");

		}
		else if (event.getSource() == surface.getMenuScreen().getHelpButton()) {

			// change the screen to the helpScreen
			surface.changeScreens("help");

		}
		else if (event.getSource() == surface.getHelpScreen().getBackToMenu()) {

			surface.changeScreens("menu");

		}
		else if (event.getSource() == surface.getMenuScreen().getPlayAsButton()) {

			// toggle the playAs button as well as the playAs variable itself
			surface.getMenuScreen().togglePlayAsButton(playAs);
			if (playAs.equals("as Mister X"))
				playAs = "as Detective";
			else if (playAs.equals("as Detective"))
				playAs = "as Mister X";

			// update JFrame
			surface.update();

		}
		else if (event.getSource() == surface.getHideScreen().getReadyButton()) {

			// update JFrame
			surface.update();
			
			// change the screen back to gameScreen
			surface.changeScreens("game");

		}
		else if (event.getSource() == surface.getGameBoard().getMrXTicketBoard().getBlackButton() && TurnController.ticketBank.getBlackTicket() > 0) {

			// select the black ticket by putting a border around it
			surface.getGameBoard().getMrXTicketBoard().selectBlack();

			// update JFrame
			surface.update();

		}
		else if (event.getSource() == surface.getGameBoard().getMrXTicketBoard().getDoubleButton() && TurnController.ticketBank.getDoubleTicket() > 0 && !TurnController.doubleTicketUsedRound) {

			// select the double ticket by putting a border around it
			surface.getGameBoard().getMrXTicketBoard().selectDouble();

			// a double ticket has been used, change the variable accordingly
			TurnController.doubleTicketUsedRound = true;

			// update JFrame
			surface.update();

		}
		else {

			// is the event from one of the playing pieces on the board?
			for(int i = 0; i < TurnController.playerArray.length; i++){

				if (
					// if the click occured on a player's piece and...
					event.getSource() == TurnController.playerArray[i].getPlayingPiece() && 
					// the player clicked has not already moved OR the double move ticket is in play and...
					(!TurnController.playerArray[i].getHasMoved() || surface.getGameBoard().getMrXTicketBoard().getDoubleButton().getBorder() != null) &&
					// it is the correct turn of the playing piece trying to be clicked on
					((TurnController.isMrXTurn && i == 0) || (!TurnController.isMrXTurn && i != 0))
				) {

					// display the possible moves of that playing piece
					surface.getGameBoard().setCurrentlyShownMoves(TurnController.playerArray[i].getSpecificPlayer());
					surface.getGameBoard().hidePossibleMoves();
					surface.getGameBoard().setPossibleMoves(TurnController.playerArray[i].displayPossibleDestinations());
					surface.getGameBoard().displayPossibleMoves();
					surface.getGameBoard().possibleMovesListener(this);

				}
					
			}

			// is the event from one of the possible moves being displayed currently?
			for (PlayingPiece move: surface.getGameBoard().getPossibleMoves()) {
				
				if (event.getSource() == move) {

					String modeOfTransportation = "";

					for(Player player:TurnController.playerArray) {
						// if the player who's possible moves are being displayed hasn't moved yet OR the double move ticket is in play
						if (surface.getGameBoard().getCurrentlyShownMoves().equals(player.getSpecificPlayer()) && (!player.getHasMoved() || surface.getGameBoard().getMrXTicketBoard().getDoubleButton().getBorder() != null)) {

							// get the modeOfTransporation that will be used
							modeOfTransportation = nodeMap[player.getCurrentNode()].getTransitType().get(move.getNodeNum());
							
							// if it is mr x's turn and the black ticket has been selected, set the modeOfTransportation to be X
							if (TurnController.isMrXTurn && surface.getGameBoard().getMrXTicketBoard().getBlackButton().getBorder() != null)
								modeOfTransportation = "X";

							// if it is mr x's turn and the double ticket has been selected (ie border is not null)
							if (TurnController.isMrXTurn && surface.getGameBoard().getMrXTicketBoard().getDoubleButton().getBorder() != null) {
								// if the player has already made their first move before selecting the double move ticket
								if (player.getHasMoved()) {
									// set their hasMoved to false, let them move again
									player.setHasMoved(false);
									TurnController.move(player, move.getNodeNum(), modeOfTransportation, false);
									surface.getGameBoard().updateLog(modeOfTransportation, move.getNodeNum(), true);
									surface.getGameBoard().getTravelLog().getDoubleRounds()[TurnController.round - 1][0].addActionListener(this);
								}
								// otherwise (they pressed the double move ticket before making a move)
								else {
									// perform the move, but indicate to the computer to not set hasMoved to true yet
									TurnController.move(player, move.getNodeNum(), modeOfTransportation, true);
									surface.getGameBoard().updateLog(modeOfTransportation, move.getNodeNum(), false);
								}

								// use up a ticket
								TurnController.ticketBank.useDoubleTicket();
								
								// reset both buttons to be "unselected"
								surface.getGameBoard().getMrXTicketBoard().getDoubleButton().setBorder(null);
								surface.getGameBoard().getMrXTicketBoard().getBlackButton().setBorder(null);
							}
							else {
								// otherwise, perform the move normally
								TurnController.move(player, move.getNodeNum(), modeOfTransportation, false);

								if (TurnController.isMrXTurn) {
									// add to the travelLog if it is Mr X's turn
									surface.getGameBoard().updateLog(modeOfTransportation, move.getNodeNum(), TurnController.doubleTicketUsedRound);
									if (TurnController.doubleTicketUsedRound)
										surface.getGameBoard().getTravelLog().getDoubleRounds()[TurnController.round - 1][0].addActionListener(this);
								}

							}

							
						}
					}
					
					// after selecting a move, hide the possible moves
					surface.getGameBoard().hidePossibleMoves();
					
					// update JFrame
					surface.update();

				}

			}
			
			// is the event from one of the clickable tickets on the TravelLog? (ie one of the Mr X surfacing turns)
			for (int i = 0; i < surface.getGameBoard().getTravelLog().getTravelTicketsArray().length; i++) {

				// since we only added action listeners to buttons on surfacing rounds, there is no need to perform a redundant check here
				if (event.getSource() == surface.getGameBoard().getTravelLog().getTravelTicketsArray()[i]) {
					// if the ticket is not hidden (it has an icon), make it hidden (make the icon null)
					if (surface.getGameBoard().getTravelLog().getTravelTicketsArray()[i].getIcon() != null)
						surface.getGameBoard().getTravelLog().getTravelTicketsArray()[i].setIcon(null);
					else {
						// otherwise, set the icon back to what it was meant to be based on the corresponding transporation method given by travelStringsArray
						switch(surface.getGameBoard().getTravelLog().getTravelStringsArray()[i]) {

							case "B":
								surface.getGameBoard().getTravelLog().getTravelTicketsArray()[i].setIcon(
									surface.getGameBoard().getTravelLog().getBusIcon()
								);
								break;
							case "U":
								surface.getGameBoard().getTravelLog().getTravelTicketsArray()[i].setIcon(
									surface.getGameBoard().getTravelLog().getTrainIcon()
								);
								break;
							case "S":
								surface.getGameBoard().getTravelLog().getTravelTicketsArray()[i].setIcon(
									surface.getGameBoard().getTravelLog().getBlackIcon()
								);
								break;
							case "X":
								surface.getGameBoard().getTravelLog().getTravelTicketsArray()[i].setIcon(
									surface.getGameBoard().getTravelLog().getBlackIcon()
								);
								break;
							default:
								surface.getGameBoard().getTravelLog().getTravelTicketsArray()[i].setIcon(
									surface.getGameBoard().getTravelLog().getTaxiIcon()
								);
								break;

						}

					}
				}

			}

			// is the event from one of the clickable double tickets on the TravelLog?
			for (int i = 0; i < surface.getGameBoard().getTravelLog().getDoubleRounds().length; i++) {

				if (event.getSource() == surface.getGameBoard().getTravelLog().getDoubleRounds()[i][0]) {

					// cycle through the tickets to show the detectives which two modes of transporation were used
					surface.getGameBoard().getTravelLog().cycleDoubleTurnCards(i);

					// update JFrame
					surface.update();

				}

			}

		}
	}
	
}