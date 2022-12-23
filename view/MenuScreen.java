/**
 * Class: MenuScreen
 * 
 * Description: This class creates the menu panel where user chooses which gamemode to play 
 */

package view;

import javax.swing.*;
import java.awt.Color;

public class MenuScreen extends JPanel {
    
    // logo
    private ImageIcon logoIcon = new ImageIcon("images/menu_logo.png");
	private JLabel logoLabel = new JLabel();

    // background
    private ImageIcon backgroundIcon = new ImageIcon("images/menu_background.png");
    private JLabel backgroundLabel = new JLabel();

    // playing as images
    private ImageIcon playAsX = new ImageIcon("images/play_as_X.png");
    private ImageIcon playAsDetective = new ImageIcon("images/play_as_detective.png");

    // menu
    private JButton playComputerButton = new JButton();
    private JButton playAsButton = new JButton();
    private JButton playHumanButton = new JButton();
    private JButton helpButton = new JButton("Help (WIP)");

    // constructor
    public MenuScreen() {
        super();

        setSize(1920, 1080);
		setBackground(Color.DARK_GRAY);
		setLayout(null);

        logoLabel.setIcon(logoIcon);
		logoLabel.setBounds((this.getWidth() - logoIcon.getIconWidth()) / 2, 100, logoIcon.getIconWidth(), logoIcon.getIconHeight());
		add(logoLabel);

        playComputerButton.setBounds((this.getWidth() - 400) / 2, 500, 400, 100);
        playComputerButton.setIcon(new ImageIcon("images/button_PVE.png"));
        playComputerButton.setContentAreaFilled(false);
        playComputerButton.setBorderPainted(false);
        playComputerButton.setFocusPainted(false);
        add(playComputerButton);

        playHumanButton.setBounds((this.getWidth() - 400) / 2, 625, 400, 100);
        playHumanButton.setIcon(new ImageIcon("images/button_PVP.png"));
        playHumanButton.setContentAreaFilled(false);
        playHumanButton.setBorderPainted(false);
        playHumanButton.setFocusPainted(false);
        add(playHumanButton);

        playAsButton.setBounds((this.getWidth() + playComputerButton.getIcon().getIconWidth())/2 + 10, 500, 100, 100);
        playAsButton.setIcon(new ImageIcon(playAsX.getImage().getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH)));
        playAsButton.setContentAreaFilled(false);
        playAsButton.setBorderPainted(false);
        playAsButton.setFocusPainted(false);
        add(playAsButton);

        helpButton.setBounds((this.getWidth() - 400) / 2, 740, 400, 100);
        helpButton.setIcon(new ImageIcon( new ImageIcon("images/button_Help.png").getImage().getScaledInstance(395, 110, java.awt.Image.SCALE_SMOOTH) ));
        helpButton.setContentAreaFilled(false);
        helpButton.setBorderPainted(false);
        helpButton.setFocusPainted(false);
        add(helpButton);

        backgroundLabel.setIcon(backgroundIcon);
        backgroundLabel.setBounds(0,0,1920,1080);
        add(backgroundLabel);

    }

    public JButton getPlayComputerButton() {
        return playComputerButton;
    }

    public void setPlayComputerButton(JButton playComputerButton) {
        this.playComputerButton = playComputerButton;
    }

    public JButton getPlayHumanButton() {
        return playHumanButton;
    }

    public void setPlayHumanButton(JButton playHumanButton) {
        this.playHumanButton = playHumanButton;
    }

    public JButton getHelpButton() {
        return helpButton;
    }

    public void setHelpButton(JButton helpButton) {
        this.helpButton = helpButton;
    }

    public JButton getPlayAsButton() {
        return playAsButton;
    }

    public void setPlayAsButton(JButton playAsButton) {
        this.playAsButton = playAsButton;
    }

    public void togglePlayAsButton(String togglePlayAs) {

        if (togglePlayAs.equals("as Mister X"))
            playAsButton.setIcon(new ImageIcon(playAsDetective.getImage().getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH)));
        else if (togglePlayAs.equals("as Detective"))
            playAsButton.setIcon(new ImageIcon(playAsX.getImage().getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH)));

    }

    public void update() {
        invalidate();
		repaint();
		validate();
    }

}