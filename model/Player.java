package model;

import view.*;
import controller.*;

import java.util.ArrayList;

public class Player {
    
    private String playingAs;
    private int currentNode;

    private PlayingPiece playingPiece;
    private int color;
    private boolean hasMoved;

    // constructor for mr X
    public Player(String playingAs, int currentNode, int color) {
        this.playingAs = playingAs;
        this.currentNode = currentNode;
        this.color = color;

        playingPiece = new PlayingPiece(currentNode, playingAs, color, false);
    }

    // constructor for bobbies and will also work for mr X if false is passed into limitedTickets
    public Player(String playingAs, int currentNode, int color, boolean limitedTickets) {
        this.playingAs = playingAs;
        this.currentNode = currentNode;
        this.color = color;

        playingPiece = new PlayingPiece(currentNode, playingAs, color, false);

    }

    // 
    // Author: Ray
    public ArrayList<Integer> getPossibleDestinations() {

        ArrayList<Integer> nodeArrayList = new ArrayList<Integer>();

        // for each adjacent location based on nodeMap, add a new PlayingPiece to the ArrayList
        for (Integer adjNode : ScotlandYardController.nodeMap[currentNode].getTransitType().keySet()) {
            nodeArrayList.add(adjNode);
        }

        ArrayList<Integer> toBeRemoved = new ArrayList<Integer>();

        // based on which character's moves is being displayed, add nodes to be removed to the toBeRemoved
        if (playingAs.equals("Mister X")) {
            
            // if playingAs is mr X, we need to remove any places where 
            //      there is currently an adjacent resting bobby/detective
            //      skip the ferries if there are no
        	for (Integer adjNode : nodeArrayList) {
        		
                String modeOfTrans = ScotlandYardController.nodeMap[currentNode].getTransitType().get(adjNode);

                // skip checking against misterX himself
                for(int i = 1; i<TurnController.playerArray.length;i++)
                    if(TurnController.playerArray[i].getCurrentNode() == adjNode)
                        toBeRemoved.add(adjNode);

                if (TurnController.ticketBank.getBlackTicket() == 0 && modeOfTrans.equals("S"))
                toBeRemoved.add(adjNode);
                    
        		
        	}
        	
        }
        else if (playingAs.equals("Detective")) {
        	
        	// if playingAs is detective, we need to remove any places where
            //      there is an adjacent resting bobby/detective
            //      there is no way to reach them by tickets
        	for (Integer adjNode : nodeArrayList) {
        		
        		String modeOfTrans = ScotlandYardController.nodeMap[currentNode].getTransitType().get(adjNode);

        		if (modeOfTrans.equals("T") && TurnController.ticketBank.getTaxiTicket() == 0) {
        			toBeRemoved.add(adjNode);
        			continue;
        		}
        		else if (modeOfTrans.equals("B") && TurnController.ticketBank.getBusTicket() == 0) {
        			toBeRemoved.add(adjNode);
        			continue;
        		}
        		else if (modeOfTrans.equals("U") && TurnController.ticketBank.getSubwayTicket() == 0) {
        			toBeRemoved.add(adjNode);
        			continue;
        		}
                else if (modeOfTrans.equals("S")) {
        			toBeRemoved.add(adjNode);
        			continue;
        		}

                // initiate from i = 1, to skip checking against misterX
                for(int i = 1; i<TurnController.playerArray.length;i++)
                    if(TurnController.playerArray[i].getCurrentNode() == adjNode)
                        toBeRemoved.add(adjNode); 
        	}
        	
        }
        else {

            // bobby
            //      check that it is not a ferry route
            //      avoid other bobbies and detectives            
            for (Integer adjNode : nodeArrayList) {
                
                if (ScotlandYardController.nodeMap[currentNode].getTransitType().get(adjNode).equals("S")) {
                    toBeRemoved.add(adjNode);
                    continue;
                }
                // initiate from i = 1, to skip checking against misterX
                for(int i = 1; i<TurnController.playerArray.length;i++)
                    if(TurnController.playerArray[i].getCurrentNode() == adjNode)
                        toBeRemoved.add(adjNode);
                
            }

        }

        // remove any nodes deemed invalid
        for (Integer invalidNode : toBeRemoved)
        	nodeArrayList.remove(invalidNode);

        return nodeArrayList;

    }
    
    // Author: Ray
    public ArrayList<PlayingPiece> displayPossibleDestinations() {

        ArrayList<PlayingPiece> destinationArrayList = new ArrayList<PlayingPiece>();

        // for each adjacent location based on nodeMap, add a new PlayingPiece to the ArrayList
        for (Integer availNode : getPossibleDestinations()) {
            destinationArrayList.add(new PlayingPiece(availNode, playingAs, color, true));
        }

        return destinationArrayList;

    }

    public String getPlayingAs() {
        return this.playingAs;
    }

    public void setPlayingAs(String playingAs) {
        this.playingAs = playingAs;
    }

    // Method to differentiate between different players
    public String getSpecificPlayer() {
        if(color == 0)
            return "Mister X";
        else if(color == 1)
            return "Detective 1";
        else if(color == 2)
            return "Detective 2";
        else if(color == 3)
            return "Bobby 1";
        else
            return "Bobby 2";
        
    }

    public int getCurrentNode() {
        return this.currentNode;
    }

    public void setCurrentNode(int currentNode) {
        this.currentNode = currentNode;
    }

    public PlayingPiece getPlayingPiece() {
        return this.playingPiece;
    }

    public void setPlayingPiece(PlayingPiece playingPiece) {
        this.playingPiece = playingPiece;
    }

    public boolean getHasMoved() {
        return this.hasMoved;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    @Override
    public String toString() {
        return "Player [color=" + color + ", currentNode=" + currentNode + ", hasMoved=" + hasMoved + ", playingAs="
                + playingAs + "]";
    }

}