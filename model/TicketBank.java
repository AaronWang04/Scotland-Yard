/**
 * Class:
 *      TicketBank
 * Description:
 *      This class represents the entire ticket bank that numbers the ticket amounts both sides need throughout the game 
 * Areas of Concern
 *      None
 * Author(s):
 *      Aaron
 */

package model;

// Object to store all of the tickets of a single game
public class TicketBank {

    // Detective tickets
    private int taxiTicket;
    private int busTicket;
    private int subwayTicket;
    // MrX Tickets
    private int blackTicket;
    private int doubleTicket;

    public TicketBank() {
        // Initialize to specified amount
        taxiTicket = 20;
        busTicket = 16;
        subwayTicket = 8;
        blackTicket = 5;
        doubleTicket = 2;

    }

    // Methods to use ticket
    public void useTaxiTicket(){
        taxiTicket--;
    }

    public void useBusTicket(){
        busTicket--;
    }

    public void useSubwayTicket(){
        subwayTicket--;
    }

    public void useBlackTicket() {
        blackTicket--;
    }

    public void useDoubleTicket() {
        doubleTicket--;
    }


    // Methods to get amount of tickets
    public int getTaxiTicket() {
        return this.taxiTicket;
    }

    public int getBusTicket() {
        return this.busTicket;
    }

    public int getSubwayTicket() {
        return this.subwayTicket;
    }

    public int getBlackTicket() {
        return blackTicket;
    }

    public int getDoubleTicket() {
        return doubleTicket;
    }

}
