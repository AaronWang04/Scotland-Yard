/**
 * Class:
 * 		HelpScreen
 * Description:
 * 		This class brings the user to a website explaining rules of Scotland Yard
 */

package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class HelpScreen extends JPanel {

	private JLabel ruleLabel = new JLabel("Rules of Scotland Yard");
	private String linkStr = "https://www.ultraboardgames.com/scotland-yard/game-rules.php";
	private JLabel linkLabel = new JLabel(linkStr);
	private JButton backToMenu = new JButton(new ImageIcon("images/back_to_menu.png"));

	public HelpScreen() throws MalformedURLException {
		super();

		setSize(1920, 1080);
		setLayout(null);

		ruleLabel.setBounds((this.getWidth() - 300) / 2, 75, 300, 100);
		ruleLabel.setHorizontalAlignment(SwingConstants.CENTER);

		linkLabel.setBounds((this.getWidth() - 300) / 2, 175, 300, 300);
		linkLabel.setHorizontalAlignment(SwingConstants.CENTER);
		linkLabel.setForeground(Color.BLUE.darker());
		linkLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		backToMenu.setBounds(30,850,350,140);
		backToMenu.setContentAreaFilled(false);
        backToMenu.setBorderPainted(false);
        backToMenu.setFocusPainted(false);
		add(backToMenu);

		linkLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				try {
					Desktop.getDesktop()
							.browse(new URI("https://www.ultraboardgames.com/scotland-yard/game-rules.php"));
				} catch (IOException | URISyntaxException e1) {
					e1.printStackTrace();
				}
				// the user clicked on the label
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				linkLabel.setText(linkStr);
				// the mouse has entered the label
			}

			@Override
			public void mouseExited(MouseEvent e) {
				linkLabel.setText(linkStr);
				// the mouse left the label
			}
		});

		// add both labels to the JPanel
		add(ruleLabel);
		add(linkLabel);

	}

	public JLabel getRuleLabel() {
		return ruleLabel;
	}

	public void setRuleLabel(JLabel ruleLabel) {
		this.ruleLabel = ruleLabel;
	}

	public JButton getBackToMenu(){
		return backToMenu;
	}

}