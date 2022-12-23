/**
 * Class:
 *      HideScreen
 * Description:
 *      This class hides the gameBoard screen while the turn is being switched for player-vs-player
 */

package view;

import javax.swing.*;
import java.awt.Font;

public class HideScreen extends JPanel {
    
    // image and "No peeking" label
    private ImageIcon quietIcon = new ImageIcon("images/shush.png");
    private JLabel quietLabel = new JLabel();
    private JLabel boldText = new JLabel("NO PEEKING!");

    // button to indicate readiness
    private JButton readyButton = new JButton();

    // constructor
    // Author: Max
    public HideScreen() {

        // setup jpanel
        super();

        setSize(1920, 1080);
		setLayout(null);

        // add picture
        quietLabel.setIcon(new ImageIcon( quietIcon.getImage().getScaledInstance(300, 300, java.awt.Image.SCALE_SMOOTH)));
        quietLabel.setBounds((this.getWidth() - 300) / 2 - 15, 50, 300, 300);
        add(quietLabel);

        // add bold text jlabel
        boldText.setFont(new Font("Bahnschrift", Font.BOLD,30));
        boldText.setHorizontalAlignment(SwingConstants.CENTER);
        boldText.setBounds((this.getWidth() - 400)/2, 375, 400, 50);
        add(boldText);

        // add ready button
        readyButton.setBounds((this.getWidth() - 300) / 2, 500, 300, 50);
        readyButton.setText("Next Player: Ready");
        add(readyButton);

    }

    // getters and setters
    // Author: Ray

    public JButton getReadyButton() {
        return readyButton;
    }

    public void setReadyButton(JButton readyButton) {
        this.readyButton = readyButton;
    }

}