/**
 * Class: 
 *      Computer
 * Description: 
 *      This class chooses moves to play as detective or Mr. X with varying levels of difficulty
 * Areas of Concern:
 *      - AI itself does not utilize black/double tickets to its advantage
 *      - MisterX AI 
 */

package controller;

import view.*;
import model.*;

import java.util.*;

public class Computer {

    // Randomize a move for any player
    public int randMove(Player player){

        ArrayList<Integer> possibleMoves = player.getPossibleDestinations();

        if(possibleMoves.isEmpty() == true)
            return 0;

        Collections.shuffle(possibleMoves);
        return possibleMoves.get(0);
    }

    // A simple algorithm that attempts to select a move for MrX such that no detective can land there the next turn
    public int medXmove(Player[] playerArray){

        ArrayList<Integer> adjacent = playerArray[0].getPossibleDestinations();

        boolean safeNode = false;

        // Double loop that runs until a "safe" node is found
        while(safeNode == false){
            if(adjacent.size() == 0)
                break;

            HashMap<Integer, String> temp = ScotlandYardController.nodeMap[adjacent.get(0)].getTransitType();

            safeNode = true;
            for(Integer key: temp.keySet()){
                if(playerArray[1].getCurrentNode() == key || playerArray[2].getCurrentNode() == key || playerArray[3].getCurrentNode() == key|| playerArray[4].getCurrentNode() == key)
                    safeNode = false;
            }

            if(safeNode == true)
                return adjacent.get(0);

            adjacent.remove(0);
        }

        // If no safe move found, return a random move
        return randMove(playerArray[0]);
    }

    // MrX AI that attempts to get as far away from detectives as possible
    public int HardMrX(Player[] playerArray){

        ArrayList<Integer> possibleDestination = playerArray[0].getPossibleDestinations();
        double distance = closestDetective(possibleDestination.get(0),playerArray);
        int destination = possibleDestination.get(0);

        // Find the possible destination with the largest distance to the closest detective
        for(int i = 1 ; i<possibleDestination.size() ; i++){
            if(closestDetective(possibleDestination.get(i),playerArray) > distance){
                distance = closestDetective(possibleDestination.get(i),playerArray);
                destination = possibleDestination.get(i);
            }
            // If the largest distance to closest detective is same, compare combined distance (Weighted)
            else if(closestDetective(possibleDestination.get(i),playerArray) == distance){
                double combinedDistance1 = combinedDetective(possibleDestination.get(i),playerArray);
                double combinedDistance2 = combinedDetective(destination,playerArray);

                if(combinedDistance1 > combinedDistance2)
                    destination = possibleDestination.get(i);
            }
        }
            
        return destination;

    }    

    // Advanced Detective Algorithm
    // Methodology: Find the move that minimizes distance to all MisterX's potential locations
    public int HardDetective(TravelLog travelLog,Player player,int round){

        // If round is before the reveal turn, return a random move
        if(round<3)
            return randMove(player);

        ArrayList<Integer> possibleMrX = possibleMrX(travelLog, round);

        // Compare distance of all possible destinations, and return the best one
        ArrayList<Integer> possibleNode = player.getPossibleDestinations();
        int tempDistance = 0;
        int bestNode = possibleNode.get(0);
        int combinedDistance = 0;
        int[][] Djik = graphPathing(possibleNode.get(0));

        for(Integer x:possibleMrX)
            combinedDistance+= Djik[0][x];

        for(int i = 1; i < possibleNode.size(); i++){
            Djik = graphPathing(possibleNode.get(i));
            tempDistance = 0;
            for(Integer x:possibleMrX)
                tempDistance+= Djik[0][x];
            if(tempDistance < combinedDistance){
                combinedDistance = tempDistance;
                bestNode = possibleNode.get(i);
            }
        }

        return bestNode;
    }

    // Advanced MisterX AI
    //Methodology: Attempts to maximize the amount of potential places MisterX could be at

    public int ExtremeMrX(Node[] nodeMap,TravelLog travelLog, Player[] playerArray, int round){

        int currentX = playerArray[0].getCurrentNode();

        // Buffer code, if there are no multiple moves that put u distance>1 away from detective
        if(TurnController.round<3)
            return medXmove(playerArray);

        ArrayList<Integer> destinations = new ArrayList<>();
        destinations = playerArray[0].getPossibleDestinations();
        ArrayList<Integer> toBeRemoved = new ArrayList<>();

        for(Integer x : destinations)
            if(closestDetective(playerArray[0].getCurrentNode(), playerArray) == 1)
                toBeRemoved.add(x);
        
        for(Integer x : toBeRemoved)
            destinations.remove(x);

        if(destinations.size() == 0)
            return randMove(playerArray[0]);
        else if(destinations.size() == 1)
            return destinations.get(0);
    
        // Algorithm (Search depth = 2)

        // bestNode denotes the current best node the algorithm has found
        int bestNode = destinations.get(0);
        TravelLog tempTravelLog = new TravelLog(travelLog);
        // Log new entry into tempTravelLog as if misterX has went to bestNode
        tempTravelLog.logNewEntry(nodeMap[currentX].getTransitType().get(bestNode), bestNode, false);
        // Depth 2
        tempTravelLog.logNewEntry(nodeMap[bestNode].getTransitType().get(nodeMap[bestNode].getAdjacentInt()[0]), nodeMap[bestNode].getAdjacentInt()[0], false);

        // for passing into possibleMrX method, denotes a +2 in round for depth 2
        int roundP2 = round+2;

        // Size of possibleMrX denotes how many potential locations misterX could be at
        int bestNodePossibleValue = possibleMrX(tempTravelLog, roundP2).size();

        // Loop through bestNode's all adjacent values, and assign the highest potential location to bestNodePossibleValue
        for(int i = 1; i<nodeMap[bestNode].getAdjacentInt().length; i++){
            tempTravelLog = new TravelLog(travelLog);
            tempTravelLog.logNewEntry(nodeMap[currentX].getTransitType().get(bestNode), bestNode, false);
            tempTravelLog.logNewEntry(nodeMap[bestNode].getTransitType().get(nodeMap[bestNode].getAdjacentInt()[i]), nodeMap[bestNode].getAdjacentInt()[i], false);

            if(possibleMrX(tempTravelLog, roundP2).size() > bestNodePossibleValue)
                bestNodePossibleValue = possibleMrX(tempTravelLog, roundP2).size();
        }

        TravelLog travelLog2 = new TravelLog();
        boolean doubleBreak = false;

        // First loop loops through all other destinations of misterX
        for(int i = 1;i<destinations.size();i++){
            int travelTo = destinations.get(i);
            doubleBreak = false;
            tempTravelLog = new TravelLog(travelLog);
            tempTravelLog.logNewEntry(nodeMap[currentX].getTransitType().get(travelTo), travelTo, false);

            // Out of those destinations, loop their adjacent neighbors for depth of 2
            for(int o = 0; o< (nodeMap[travelTo].getAdjacentInt().length) ; o++) {

                int travelTo2 = nodeMap[travelTo].getAdjacentInt()[o];
                travelLog2 = new TravelLog(tempTravelLog);
                travelLog2.logNewEntry(nodeMap[travelTo].getTransitType().get(travelTo2), travelTo2, false);

                if(possibleMrX(travelLog2, roundP2).size() > bestNodePossibleValue){
                    bestNode = travelTo;
                    bestNodePossibleValue = possibleMrX(travelLog2, roundP2).size();
                    doubleBreak = true;
                    break;
                }

            }
            if(doubleBreak == true)
                break;
        }


        return bestNode;

    }

    // Iterative BFS for all possible MrX Locations
    private ArrayList<Integer> possibleMrX(TravelLog travelLog, int round){

        int sinceReveal = (round-3)%5;
        int source = travelLog.getPositionArray()[round-sinceReveal-1];

        String[] ticketArray = new String[sinceReveal];

        for(int i = 0; i<ticketArray.length; i++)
            ticketArray[i] = travelLog.getTravelStringsArray()[round-sinceReveal+i-1];


        ArrayList<Integer> possibleLocation = new ArrayList<>();
        ArrayList<Node> nodeQueue1 = new ArrayList<>();
        ArrayList<Node> nodeQueue2 = new ArrayList<>();

        // Initialize by adding the starting location
        nodeQueue2.add(ScotlandYardController.nodeMap[source]);
        
        for(int i = 0; i < sinceReveal; i++){

            // Turn nodeQueue1 into a shadow copy of nodeQueue2, then empty nodeQueue2 
            nodeQueue1.clear();
            for(Node x:nodeQueue2)
                nodeQueue1.add(x);
            nodeQueue2.clear();
            
            // For the nodes in queue1
            for(Node x: nodeQueue1){

                HashMap<Integer,String> adjacentHashMap = x.getTransitType();

                ArrayList<Node> temp = new ArrayList<>();

                // For the node's neighbors
                for(int nodeNum : x.getAdjacentInt()){
                    // If black ticket used, add all adjacent nodes
                    if(ticketArray[i].equals("S"))
                    temp.add(ScotlandYardController.nodeMap[nodeNum]);
                    // If the neighbor's transit type matches travelLog, add
                    else if(adjacentHashMap.get(nodeNum).equals(ticketArray[i]))
                        temp.add(ScotlandYardController.nodeMap[nodeNum]);
                }
                // Add the potential locations into nodeQueue2. If the node already exists, do not add again
                for(Node y : temp)
                    if(nodeQueue2.contains(y) == false)
                        nodeQueue2.add(y);

            }

        }
        // Transport into an arraylist of integers to output
        for(Node x : nodeQueue2)
            possibleLocation.add(x.getNodeNum());

        return possibleLocation;

    }

    // Dijkstra's algorithm for finding shortest path from a starting node to all other nodes
    private static int[][] graphPathing(int starting){

        int[] distanceTo = new int[200];
        int[] previous = new int[200];

        ArrayList<Node> queue = new ArrayList<>();

        // Initialize all node distances to source to infinity
        for(int i = 0; i<distanceTo.length; i++){
            distanceTo[i] = Integer.MAX_VALUE;
            previous[i] = 0;
            queue.add(ScotlandYardController.nodeMap[i]);
        }

        // Set distance to starting node as 0
        distanceTo[starting] = 0;

        // The one represents the zeroth node which exists for easier indexing
        while(queue.size() != 1){
            
            ArrayList<Node> unvisited = new ArrayList<>();
            
            Node tempNode = queue.get(1);
            // Find tempNode (lowest distanceTo value from things in queue)

            // Find node with lowest distanceTo value
            for(int i = 1; i<queue.size(); i++){
                if(distanceTo[tempNode.getNodeNum()]>distanceTo[queue.get(i).getNodeNum()]) 
                    tempNode = queue.get(i);
            }

            // Create arrayList that containing unvisited adjacent nodes of tempNode
            for(Node adjacent: tempNode.getAdjacent())
                if(queue.contains(adjacent) && adjacent.getNodeNum()!= 0)
                    unvisited.add(adjacent);

            queue.remove(tempNode);

            for(Node unvisiteNode : unvisited){
                // Set distance to the source as +1 (Because it will take 1 extra step to get to a node's adjacent neighbors)
                int tempDistance = distanceTo[tempNode.getNodeNum()] + 1;
                // If the new distance is lower, update the arrays
                if(tempDistance < distanceTo[unvisiteNode.getNodeNum()]){
                    distanceTo[unvisiteNode.getNodeNum()] = tempDistance;
                    previous[unvisiteNode.getNodeNum()] = tempNode.getNodeNum();
                }
            }

        }

        // Return both arrays
        int[][] TwoReturnOneMethod = new int[][]{distanceTo,previous};
        return TwoReturnOneMethod;

    }

    // A method that finds the closest detectiveto MrX
    private double closestDetective(int source, Player[] playerArray){
        int[][] Dijkstra = graphPathing(source);
        double shortestDistance;
        shortestDistance = Dijkstra[0][playerArray[1].getCurrentNode()];
         
        for(int i = 2;i<playerArray.length;i++)
            if(Dijkstra[0][playerArray[i].getCurrentNode()] < shortestDistance)
                shortestDistance = Dijkstra[0][playerArray[i].getCurrentNode()];

        return shortestDistance;
    }

    // A weighted combined distance to all detectives
    private double combinedDetective(int source, Player[] playerArray){
        int[][] Dijkstra = graphPathing(source);
        double combinedDistance = 0;

        // Sqrt function allows for heavier weightings on closer detectives
        for(int i = 1;i<playerArray.length;i++)
            combinedDistance += Math.sqrt(Dijkstra[0][playerArray[i].getCurrentNode()])+1;
            
        return combinedDistance;
    }
    
}
