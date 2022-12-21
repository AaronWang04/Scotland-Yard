/**
 * Class:
 *      Node
 * Description:
 *      This class represents a node (station) on the map of the gameBoard
 * Areas of Concern
 *      None
 * Author(s):
 *      Aaron
 */

package model;

import java.util.*;

import controller.ScotlandYardController;

public class Node {

    private int[] mapCords;
    private int nodeNum;
    private HashMap<Integer,String> transitType;

    private HashMap<Integer,String> empty = new HashMap<>();

    public Node(int[] mapCords, int nodeNum, HashMap<Integer,String> transitType) {
        this.mapCords = mapCords;
        this.nodeNum = nodeNum;
        this.transitType = transitType;
    }

    // Overloaded constructor for empty nodes
    public Node() {
        this.mapCords = null;
        this.nodeNum = 0;
        this.transitType = empty;
    }

    public int[] getMapCords() {
        return this.mapCords;
    }

    public void setMapCords(int[] mapCords) {
        this.mapCords = mapCords;
    }

    public int getNodeNum() {
        return this.nodeNum;
    }

    public void setNodeNum(int nodeNum) {
        this.nodeNum = nodeNum;
    }

    public HashMap<Integer,String> getTransitType() {
        return this.transitType;
    }


    // Method to get all adjacent nodes and output in an node array
    public Node[] getAdjacent() {
        Node[] temp = new Node[transitType.size()];
        int index = 0;
        for(Integer key:getTransitType().keySet())
            temp[index++] = ScotlandYardController.nodeMap[key];
        return temp;
    }

    // Method to get all adjacent nodes and output in an integer array
    public int[] getAdjacentInt() {
        int[] temp = new int[transitType.size()];
        int index = 0;
        for(Integer key:transitType.keySet())
            temp[index++] = key;
        return temp;
    }

    public void setTransitType(HashMap<Integer,String> transType) {
        this.transitType = transType;
    }

    public void addTransitType(HashMap<Integer,String> transType) {
        this.transitType.putAll(transType);
    }

    @Override
    public String toString() {
        return "Node [mapCords=" + Arrays.toString(mapCords) + ", nodeNum=" + nodeNum
                + ", transitType=" + transitType + "]";
    }

}