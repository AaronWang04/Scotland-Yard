/**
 * Class:
 *      TurnController
 * Description:
 *      This class manages the actions that occur during a turn or round change, including actual movement of 
 *      the game pieces
 * Areas of Concern:
 *      None
 * Author(s):
 *      Aaron, Ray
 */

package controller;

import model.*;

public class TurnController {

    // publically available array of all the player entities in the game, one Mister X, two detectives, two bobbies
    public static Player[] playerArray = new Player[5];
    
    // publically available Computer object for the duration of the game (in case the player wants to play against computer)
    public static Computer computer = new Computer();

    // publically available TicketBank object for the duration of the game
    public static TicketBank ticketBank = new TicketBank();

    // publically available information about things like the game mode, the round number, and who's turn it currently is
    public static String mode;
    public static int round = 1;
    public static boolean isMrXTurn = true; // True for Mister X's turn, False for detective/bobby turn, Mr X starts, initialized at true
    
    // has a double ticket been used?
    public static boolean doubleTicketUsedRound = false;

    // constructor
    // Author: Ray
    public TurnController(String givenMode) {

        // assign the mode
        mode = givenMode;

        // create the new players to fill the playerArray
        playerArray[0] = new Player("Mister X",StartCard.getRandomMrXPosition(), 0);
        playerArray[1] = new Player("Detective", StartCard.getRandomDetectivePosition(), 1, true);
        playerArray[2] = new Player("Detective", StartCard.getRandomDetectivePosition(), 2, true);
        playerArray[3] = new Player("Bobby", StartCard.getRandomDetectivePosition(), 3, true);
        playerArray[4] = new Player("Bobby", StartCard.getRandomDetectivePosition(), 4, true);
    	
    }

    // returns a value that will determine whether or not to continue to the next turn
    //      0: game continues
    //      1: the turn is incomplete
    //      2: detective win
    //      3: x win
    // Author: Ray
    public int nextTurn() {

        // if it is Mr X's turn and he hasn't moved, return incomplete turn state
        if (isMrXTurn && !playerArray[0].getHasMoved())
                return 1;
            
        // if it is Detectives' turn
        if (!isMrXTurn) {

            // ...and any one of their pieces haven't moved, return incomplete turn state
            if (!(
                playerArray[1].getHasMoved() && playerArray[2].getHasMoved() &&
                playerArray[3].getHasMoved() && playerArray[4].getHasMoved()
            ))
                return 1;

            // increment the round if all the Detectives have moved
            round++;
        }

        // check if there is a win, return the corresponding value if there is
        int winResult = checkWin();
        if (winResult == 1)
            return 2; // detective win
        if (winResult == 2)
            return 3; // x win

        // toggle isMrXTurn to the opposite boolean value
        isMrXTurn = !isMrXTurn;

        // reset all hasMoved variables back to false
        for(Player player : playerArray)
            player.setHasMoved(false);

        // the turn has passed all other possible results, so the game must continue
        return 0;

    }

    // check if either side has won yet
    //      0: No wins
    //      1: Detective wins
    //      2. MisterX wins
    // Author: Aaron
    public int checkWin(){

        int mrX = playerArray[0].getCurrentNode();

        // Check Detective win conditions
        // ===============================================
        
        // for each player in the playerArray, check if its current node matches mrX's current node
        for(int i = 1;i<playerArray.length ;i++)
            if(playerArray[i].getCurrentNode() == mrX)
                return 1;
        
        // if Mr X has no valid moves, detectives win
        if (playerArray[0].getPossibleDestinations().size() == 0)
            return 1;

        // Check MrX win conditions
        // ===============================================

        // if it is round 24, Mr X wins
        if (round == 24)
            return 2;
            // footnote: this condition interestingly enough will never be reached, as there are only enough tickets for each detective to move a maximum of 22 moves

        // if at any point one of the detectives or bobbies have no moves, then Mr X wins
        for(int i = 1; i<playerArray.length; i++)
            if(playerArray[i].getPossibleDestinations().size() == 0)
                return 2;
        
        // otherwise no one else has won, return 0
        return 0;
    }

    // move a selected player to a new node, expending a ticket of transportationMethod if necessary
    // Author: Aaron
    public static void move(Player player,int newNodeNum, String transportationMethod, boolean isDoubleTurn) {

        // if the player is a detective
        if (player.getPlayingAs().equals("Detective")){

            // use the correct ticket based on what transportationMethod is
            if(transportationMethod.equals("T"))
                ticketBank.useTaxiTicket();
            else if(transportationMethod.equals("B"))
                ticketBank.useBusTicket();
            else
                ticketBank.useSubwayTicket();
            
        }
        // otherwise, the player must be Mr X, if they used either a Ship or a Hiding move, use a black ticket
        else if (transportationMethod.equals("S") || transportationMethod.equals("X"))
                ticketBank.useBlackTicket();

        // change their location to be the new entered location
        player.setCurrentNode(newNodeNum);
        player.getPlayingPiece().setNodeNum(newNodeNum);
        
        // if a doubleTurn is not occurring, set the hasMoved variable to be true
        if (!isDoubleTurn)
            player.setHasMoved(true);
    }

}