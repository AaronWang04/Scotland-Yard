/**
 * Class: TicketBoard
 * 
 * Description: A class that displays numbers of tickets left to users
 * 
 */

package view;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.Color;
import java.awt.Font;

public class TicketBoard extends JPanel {

	private int numTaxi;
	private int numBus;
	private int numTrain;

	private int numBlack;
	private int numDouble;
	
	// tickets images and labels
	private ImageIcon taxiIcon = new ImageIcon("images/taxi_ticket.png");
	private ImageIcon busIcon = new ImageIcon("images/bus_ticket.png");
	private ImageIcon trainIcon = new ImageIcon("images/train_ticket.png");

	private ImageIcon blackIcon = new ImageIcon("images/black_ticket.png");
	private ImageIcon doubleIcon = new ImageIcon("images/double_ticket.png");
	private Border selectedBorder = BorderFactory.createLineBorder(Color.CYAN, 5, true);

	private JLabel taxiLabel = new JLabel();
	private JLabel busLabel = new JLabel();
	private JLabel trainLabel = new JLabel();
	private JLabel taxiNumLabel = new JLabel();
	private JLabel busNumLabel = new JLabel();
	private JLabel trainNumLabel = new JLabel();

	private JButton blackButton = new JButton();
	private JButton doubleButton = new JButton();
	private JLabel blackNumLabel = new JLabel();
	private JLabel doubleNumLabel = new JLabel();

	private String displayMode;

	// Detective constructor (three numbers)
	public TicketBoard(int numTaxi, int numBus, int numTrain) {
		super();

		setBounds(0, 0, 840, 184);
		setLayout(null);
		setBackground(Color.GRAY);

		// add taxi ticket label
		taxiLabel.setIcon(new ImageIcon(taxiIcon.getImage().getScaledInstance(90, 60, java.awt.Image.SCALE_SMOOTH)));
		taxiLabel.setBounds(37, (this.getHeight() - 60)/2, 90, 60);
		add(taxiLabel);
		taxiNumLabel.setText(" : " + String.valueOf(numTaxi));
		taxiNumLabel.setBounds(37+90, (this.getHeight() - 60)/2, 90, 60);
		taxiNumLabel.setFont(new Font("Bahnschrift", Font.PLAIN,23));
		add(taxiNumLabel);

		// add bus ticket label
		busLabel.setIcon(new ImageIcon(busIcon.getImage().getScaledInstance(90, 60, java.awt.Image.SCALE_SMOOTH)));
		busLabel.setBounds(292, (this.getHeight() - 60)/2, 90, 60);
		add(busLabel);
		busNumLabel.setText(" : " + String.valueOf(numBus));
		busNumLabel.setBounds(292+90, (this.getHeight() - 60)/2, 90, 60);
		busNumLabel.setFont(new Font("Bahnschrift", Font.PLAIN,23));
		add(busNumLabel);

		// add train ticket label
		trainLabel.setIcon(new ImageIcon(trainIcon.getImage().getScaledInstance(90, 60, java.awt.Image.SCALE_SMOOTH)));
		trainLabel.setBounds(547, (this.getHeight() - 60)/2, 90, 60);
		add(trainLabel);
		trainNumLabel.setText(" : " + String.valueOf(numTrain));
		trainNumLabel.setBounds(547+90, (this.getHeight() - 60)/2, 90, 60);
		trainNumLabel.setFont(new Font("Bahnschrift", Font.PLAIN,23));
		add(trainNumLabel);

		// assign fields
		this.numTaxi = numTaxi;
		this.numBus = numBus;
		this.numTrain = numTrain;

		displayMode = "Detective";
	}

	// Mr X constructor (two numbers)
	public TicketBoard(int numBlack, int numDouble) {
		super();

		setBounds(0, 0, 840, 184);
		setLayout(null);
		setBackground(Color.GRAY);

		// add black ticket label
		blackButton.setIcon(new ImageIcon(blackIcon.getImage().getScaledInstance(90, 60, java.awt.Image.SCALE_SMOOTH)));
		blackButton.setBounds(190, (this.getHeight() - 60)/2, 90, 60);
		blackButton.setBorder(null);
		blackButton.setContentAreaFilled(false);
		blackButton.setFocusPainted(false);
		add(blackButton);
		blackNumLabel.setText(" : " + String.valueOf(numBlack));
		blackNumLabel.setBounds(190+90, (this.getHeight() - 60)/2, 90, 60);
		blackNumLabel.setFont(new Font("Bahnschrift", Font.PLAIN,23));
		add(blackNumLabel);

		// add double ticket label
		doubleButton.setIcon(new ImageIcon(doubleIcon.getImage().getScaledInstance(90, 60, java.awt.Image.SCALE_SMOOTH)));
		doubleButton.setBounds(470, (this.getHeight() - 60)/2, 90, 60);
		doubleButton.setBorder(null);
		doubleButton.setContentAreaFilled(false);
		doubleButton.setFocusPainted(false);
		add(doubleButton);
		doubleNumLabel.setText(" : " + String.valueOf(numDouble));
		doubleNumLabel.setBounds(470+90, (this.getHeight() - 60)/2, 90, 60);
		doubleNumLabel.setFont(new Font("Bahnschrift", Font.PLAIN,23));
		add(doubleNumLabel);

		// assign fields
		this.numBlack = numBlack;
		this.numDouble = numDouble;

		displayMode = "Mister X";
	}

	// Getters and Setters
	public int getNumTaxi() {
		return numTaxi;
	}

	public void setNumTaxi(int numTaxi) {
		this.numTaxi = numTaxi;
	}

	public int getNumBus() {
		return numBus;
	}

	public void setNumBus(int numBus) {
		this.numBus = numBus;
	}

	public int getNumTrain() {
		return numTrain;
	}

	public void setNumTrain(int numTrain) {
		this.numTrain = numTrain;
	}

	public int getNumBlack() {
		return numBlack;
	}

	public void setNumBlack(int numBlack) {
		this.numBlack = numBlack;
	}

	public int getNumDouble() {
		return numDouble;
	}

	public void setNumDouble(int numDouble) {
		this.numDouble = numDouble;
	}
	
	public JButton getBlackButton() {
		return blackButton;
	}

	public JButton getDoubleButton() {
		return doubleButton;
	}

	public void setAllNum(int numTaxi, int numBus, int numTrain) {

		this.numTaxi = numTaxi;
		this.numBus = numBus;
		this.numTrain = numTrain;

	}

	public void setAllNum(int numBlack, int numDouble) {

		this.numBlack = numBlack;
		this.numDouble = numDouble;

	}

	// Update the JPanel
	public void update() {

		if (displayMode.equals("Detective")) {
			taxiNumLabel.setText(" : " + String.valueOf(numTaxi));
			busNumLabel.setText(" : " + String.valueOf(numBus));
			trainNumLabel.setText(" : " + String.valueOf(numTrain));
		}
		else {
			blackNumLabel.setText(" : " + String.valueOf(numBlack));
			doubleNumLabel.setText(" : " + String.valueOf(numDouble));
		}

		invalidate();
		repaint();
		validate();

	}

	// selects black ticket by adding blue border around it
	public void selectBlack() {

		blackButton.setBorder(selectedBorder);

	}

	// selects double ticket by adding blue border around it
	public void selectDouble() {

		doubleButton.setBorder(selectedBorder);

	}

	@Override
	public String toString() {
		return "TicketBoard [numTaxi=" + numTaxi + ", numBus=" + numBus + ", numTrain=" + numTrain + "]";
	}
	
}