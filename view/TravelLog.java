/**
 * Class: TravelLog
 * 
 * Description: Class that displays MrX's travel history and acts as a data structure for it
 */

package view;

import controller.*;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.util.stream.IntStream;

public class TravelLog extends JPanel{

	public static final int[] surfaceRound = new int[]{2,7,12,17,22};

	// images
	private ImageIcon taxiImage = new ImageIcon("images/taxi_ticket.png");
	private ImageIcon busImage = new ImageIcon("images/bus_ticket.png");
	private ImageIcon trainImage = new ImageIcon("images/train_ticket.png");
	private ImageIcon blackImage = new ImageIcon("images/black_ticket.png");
	private ImageIcon doubleImage = new ImageIcon("images/double_ticket.png");

	// scaled images
	private ImageIcon taxiIcon = new ImageIcon(taxiImage.getImage().getScaledInstance(90, 60, Image.SCALE_SMOOTH));
	private ImageIcon busIcon = new ImageIcon(busImage.getImage().getScaledInstance(90, 60, Image.SCALE_SMOOTH));
	private ImageIcon trainIcon = new ImageIcon(trainImage.getImage().getScaledInstance(90, 60, Image.SCALE_SMOOTH));
	private ImageIcon blackIcon = new ImageIcon(blackImage.getImage().getScaledInstance(90, 60, Image.SCALE_SMOOTH));
	private ImageIcon doubleIcon = new ImageIcon(doubleImage.getImage().getScaledInstance(90, 60, Image.SCALE_SMOOTH));

	// last placed button
	private int lastPlacedEntryIndex = 0;

	private JPanel[] logArray = new JPanel[24];
	private JButton[] travelTicketsArray = new JButton[24];
	private int[] positionArray = new int[24];
	private JLabel[] travelLocationsArray = new JLabel[24];
	private String[] travelStringsArray = new String[24];
	// the two buttons that will be drawn on top, 
	//		0 = 2x card
	//		1 = second mode of transporation
	private JButton[][] doubleRounds = new JButton[24][2];

	// simple border to let the user know when a ticket is clickable
	private Border clickableBorder = BorderFactory.createDashedBorder(null, 5, 1, 1, false);

	// constructor
	public TravelLog() {

		super();

		// create the panels and buttons
		for(int i = 0; i<logArray.length;i++){
			logArray[i] = new JPanel();
			logArray[i].setLayout(null);
			logArray[i].setOpaque(false);
			add(logArray[i]);

			travelTicketsArray[i] = new JButton();
			travelTicketsArray[i].setBounds((127 - 90) / 2, (109 - 60) / 2, 90, 60);
			travelTicketsArray[i].setContentAreaFilled(false);
			travelTicketsArray[i].setBorderPainted(false);
			travelTicketsArray[i].setFocusPainted(false);
			travelTicketsArray[i].setForeground(Color.RED);
			
		}

		// set the two double rounds tickets
		for (int i = 0; i < doubleRounds.length; i++) {

			for (int j = 0; j < doubleRounds[i].length; j++) {

				doubleRounds[i][j] = new JButton();
				doubleRounds[i][j].setBounds(0, 0, 90, 60);
				doubleRounds[i][j].setContentAreaFilled(false);
				doubleRounds[i][j].setBorderPainted(false);
				doubleRounds[i][j].setFocusPainted(false);

				if (j == 1)
					doubleRounds[i][j].setIcon(doubleIcon);

			}

		}

		// setup the rest of the panel
		setOpaque(false);
		setLayout(new GridLayout(4,6,5,23));
		setVisible(true);

	}

	// constructor that duplicates a travelLog
	public TravelLog(TravelLog toBeDuplicated) {
		super();
		this.travelTicketsArray = toBeDuplicated.getTravelTicketsArray();
		this.travelStringsArray = toBeDuplicated.getTravelStringsArray();
		this.travelLocationsArray = toBeDuplicated.getTravelLocationsArray();
		this.positionArray = toBeDuplicated.getPositionArray();
		this.taxiIcon = toBeDuplicated.getTaxiIcon();
		this.busIcon = toBeDuplicated.getBusIcon();
		this.trainIcon = toBeDuplicated.getTrainIcon();
		this.blackIcon = toBeDuplicated.getBlackIcon();
		this.doubleRounds = toBeDuplicated.getDoubleRounds();

	}

	// Log a new entry of MrX's movement
	public void logNewEntry(String travel, int position, boolean isSecondMove, boolean isGUI) {

		if (!isSecondMove) {
			if (lastPlacedEntryIndex == TurnController.round) return;

			travelStringsArray[lastPlacedEntryIndex] = travel;
			positionArray[lastPlacedEntryIndex] = position;

			if(travel.equals("B"))
				travelTicketsArray[lastPlacedEntryIndex].setIcon(busIcon);
			else if(travel.equals("U"))
				travelTicketsArray[lastPlacedEntryIndex].setIcon(trainIcon);
			else if(travel.equals("S") || travel.equals("X"))
				travelTicketsArray[lastPlacedEntryIndex].setIcon(blackIcon);
			else
				travelTicketsArray[lastPlacedEntryIndex].setIcon(taxiIcon);

			logArray[lastPlacedEntryIndex].add(travelTicketsArray[lastPlacedEntryIndex]);

			if(IntStream.of(surfaceRound).anyMatch(x -> x == lastPlacedEntryIndex)){
				travelLocationsArray[lastPlacedEntryIndex] = new JLabel();
				travelLocationsArray[lastPlacedEntryIndex].setBounds((logArray[lastPlacedEntryIndex].getWidth() - 90) / 2, (logArray[lastPlacedEntryIndex].getHeight() - 60) / 2, 90, 60);
				travelLocationsArray[lastPlacedEntryIndex].setText(String.valueOf(position));
				travelLocationsArray[lastPlacedEntryIndex].setForeground(Color.BLACK);
				travelLocationsArray[lastPlacedEntryIndex].setFont(new Font("Bahnschrift", Font.PLAIN,36));
				travelLocationsArray[lastPlacedEntryIndex].setHorizontalAlignment(SwingConstants.CENTER);
				travelLocationsArray[lastPlacedEntryIndex].setVerticalAlignment(SwingConstants.CENTER);

				travelTicketsArray[lastPlacedEntryIndex].setBorderPainted(true);
				travelTicketsArray[lastPlacedEntryIndex].setBorder(clickableBorder);

				logArray[lastPlacedEntryIndex].add(travelLocationsArray[lastPlacedEntryIndex]);
			}
			lastPlacedEntryIndex++;
		}
		else {

			// set the double round at the last placed log entry to be a new double move button and...
			doubleRounds[lastPlacedEntryIndex - 1][0] = new JButton();
			doubleRounds[lastPlacedEntryIndex - 1][0].setIcon(doubleIcon);
			doubleRounds[lastPlacedEntryIndex - 1][0].setBounds((127 - 90) / 2 - 5, (109 - 60) / 2 + 5, 90, 60);
			doubleRounds[lastPlacedEntryIndex - 1][0].setContentAreaFilled(false);
			doubleRounds[lastPlacedEntryIndex - 1][0].setBorderPainted(false);
			doubleRounds[lastPlacedEntryIndex - 1][0].setFocusPainted(false);

			// ...a new travel method button
			doubleRounds[lastPlacedEntryIndex - 1][1] = new JButton();
			doubleRounds[lastPlacedEntryIndex - 1][1].setBounds((127 - 90) / 2 + 5, (109 - 60) / 2 - 5, 90, 60);
			doubleRounds[lastPlacedEntryIndex - 1][1].setContentAreaFilled(false);
			doubleRounds[lastPlacedEntryIndex - 1][1].setBorderPainted(false);
			doubleRounds[lastPlacedEntryIndex - 1][1].setFocusPainted(false);

			if(travel.equals("B"))
				doubleRounds[lastPlacedEntryIndex - 1][1].setIcon(busIcon);
			else if(travel.equals("U"))
				doubleRounds[lastPlacedEntryIndex - 1][1].setIcon(trainIcon);
			else if(travel.equals("S") || travel.equals("X"))
				doubleRounds[lastPlacedEntryIndex - 1][1].setIcon(blackIcon);
			else
				doubleRounds[lastPlacedEntryIndex - 1][1].setIcon(taxiIcon);

			// remove and rearrange all the cards one on top of another
			logArray[lastPlacedEntryIndex - 1].remove(travelTicketsArray[lastPlacedEntryIndex - 1]);
			if (travelLocationsArray[lastPlacedEntryIndex - 1] != null)
				logArray[lastPlacedEntryIndex - 1].remove(travelLocationsArray[lastPlacedEntryIndex - 1]);

			logArray[lastPlacedEntryIndex - 1].add(doubleRounds[lastPlacedEntryIndex - 1][0]);
			logArray[lastPlacedEntryIndex - 1].add(travelTicketsArray[lastPlacedEntryIndex - 1]);
			logArray[lastPlacedEntryIndex - 1].add(doubleRounds[lastPlacedEntryIndex - 1][1]);
			if (travelLocationsArray[lastPlacedEntryIndex - 1] != null)
				logArray[lastPlacedEntryIndex - 1].add(travelLocationsArray[lastPlacedEntryIndex - 1]);
			
		}

	}

	// Overloaded method: For when the travelLog is not used as a GUI, but rather a data structure
	public void logNewEntry(String travel, int position, boolean isSecondMove){
		if (!isSecondMove) {
			if (lastPlacedEntryIndex == TurnController.round) return;

			travelStringsArray[lastPlacedEntryIndex] = travel;
			positionArray[lastPlacedEntryIndex] = position;

			lastPlacedEntryIndex++;
		}
		else {

			// remove and rearrange all the cards one on top of another
			logArray[lastPlacedEntryIndex - 1].remove(travelTicketsArray[lastPlacedEntryIndex - 1]);
			if (travelLocationsArray[lastPlacedEntryIndex - 1] != null)
				logArray[lastPlacedEntryIndex - 1].remove(travelLocationsArray[lastPlacedEntryIndex - 1]);

			logArray[lastPlacedEntryIndex - 1].add(doubleRounds[lastPlacedEntryIndex - 1][0]);
			logArray[lastPlacedEntryIndex - 1].add(travelTicketsArray[lastPlacedEntryIndex - 1]);
			logArray[lastPlacedEntryIndex - 1].add(doubleRounds[lastPlacedEntryIndex - 1][1]);
			if (travelLocationsArray[lastPlacedEntryIndex - 1] != null)
				logArray[lastPlacedEntryIndex - 1].add(travelLocationsArray[lastPlacedEntryIndex - 1]);
			
		}
	}

	// Cycles through the visibility of the three stacked tickets
	public void cycleDoubleTurnCards(int index) {

		// was the double ticket used on a surface turn
		boolean isSurfaceTurn = false;

		// if it matches, assign the flag to be true
		for (int round: surfaceRound)
			if (round == index)
				isSurfaceTurn = true;

		// depending on which one was last invisible, make the next one right below it invisible as well
		if (doubleRounds[index][0].getIcon() != null)
			doubleRounds[index][0].setIcon(null);
		else if (travelTicketsArray[index].getIcon() != null)
			travelTicketsArray[index].setIcon(null);
		else if (isSurfaceTurn && doubleRounds[index][1].isVisible())
			doubleRounds[index][1].setVisible(false);
		else {

			// if we have reached the last layer, put all the cards back on top and visible again
			doubleRounds[index][0].setIcon(doubleIcon);

			switch(travelStringsArray[index]) {

				case "B":
					travelTicketsArray[index].setIcon(busIcon);
					break;
				case "U":
					travelTicketsArray[index].setIcon(trainIcon);
					break;
				case "S":
					travelTicketsArray[index].setIcon(blackIcon);
					break;
				case "X":
					travelTicketsArray[index].setIcon(blackIcon);
					break;
				default:
					travelTicketsArray[index].setIcon(taxiIcon);
					break;

			}

			doubleRounds[index][1].setVisible(true);

		}

	}

	// getters

	public JButton[] getTravelTicketsArray() {
		return travelTicketsArray;
	}
	
	public String[] getTravelStringsArray() {
		return travelStringsArray;
	}

	public JLabel[] getTravelLocationsArray() {
		return travelLocationsArray;
	}

	public int[] getPositionArray() {
		return positionArray;
	}
	
	public ImageIcon getTaxiIcon() {
		return taxiIcon;
	}

	public ImageIcon getBusIcon() {
		return busIcon;
	}

	public ImageIcon getTrainIcon() {
		return trainIcon;
	}

	public ImageIcon getBlackIcon() {
		return blackIcon;
	}

	public JButton[][] getDoubleRounds() {
		return doubleRounds;
	}

	
	public int getlastPlacedEntryIndex(){
		return lastPlacedEntryIndex;
	}
	
}