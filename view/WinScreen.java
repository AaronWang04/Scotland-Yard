/**
 * Class:
 *      HideScreen
 * Description:
 *      This class hides the gameBoard screen while the turn is being switched for player-vs-player
 * Areas of Concern
 *      None
 * Author(s):
 *      Max, Ray
 */

package view;

import controller.*;
import model.*;

import javax.swing.*;
import java.awt.Color;
import java.awt.Font;

public class WinScreen extends JPanel {

    // background image
    private ImageIcon winBg = new ImageIcon("images/win_screen_bg.png");
    private JLabel winBgLabel = new JLabel();

    // map image
    private ImageIcon gameMap = new ImageIcon("images/Scotland_Yard_Map.png");
	private JLabel mapLabel = new JLabel();

    // text label for the winner
    private JLabel winnerLabel = new JLabel();

    // crosshair image JLabel representing Mr X's location
	private JLabel crosshairLabel = new JLabel(new ImageIcon( new ImageIcon("images/crosshair.png").getImage().getScaledInstance(86, 90, java.awt.Image.SCALE_SMOOTH)));

    // constructor
    public WinScreen(String winner) {
        super();

        // set size of jpanel
        // Author: Max
        setSize(1920, 1080);
        setLayout(null);
        
        // set up map image without adding it yet
        // Author: Max
        mapLabel.setIcon(gameMap);
        mapLabel.setBounds((this.getWidth() - gameMap.getIconWidth()) / 2, (this.getHeight() - gameMap.getIconHeight()) / 2, gameMap.getIconWidth(), gameMap.getIconHeight());

        // add all the last position of the players during the game end, replacing Mr X with a crosshair in case detective and Mr X overlap
        // Author: Ray
        for (Player player : TurnController.playerArray) {

            int[] mapImageCoords = ScotlandYardController.nodeMap[player.getCurrentNode()].getMapCords();

            if (player.getPlayingAs().equals("Mister X")) {    
                crosshairLabel.setBounds((mapImageCoords[0] - 43), (mapImageCoords[1] - 43), 86, 86);
                mapLabel.add(crosshairLabel);
                continue;
            }

            player.getPlayingPiece().setBounds((mapImageCoords[0] - 20), (mapImageCoords[1] - 20), 40, 40);
            mapLabel.add(player.getPlayingPiece());

        }

        // add the winner text label
        // Author: Max
        winnerLabel.setForeground(Color.WHITE);
        winnerLabel.setText(winner + " HAS WON THE GAME");
        winnerLabel.setFont(new Font("Bahnschrift", Font.PLAIN,45));
        winnerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        winnerLabel.setBounds(0, 32, 1920, 100);
        add(winnerLabel);

        // add in the map label (so nothing overlaps)
        add(mapLabel);

        // add in the win background label
        // Author: Max
        winBgLabel.setBounds(0, 0, 1920, 1080);
        winBgLabel.setIcon(new ImageIcon( winBg.getImage().getScaledInstance(1920, 1080, java.awt.Image.SCALE_SMOOTH)));
        add(winBgLabel);

    }

}
