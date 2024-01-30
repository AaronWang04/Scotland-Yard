/**
 * Class:
 * 		Surface
 * Description:
 * 		This class represents the window that is displaying one screen at a time, able to switch between them
 */

package view;

import javax.swing.*;
import java.net.MalformedURLException;

public class Surface extends JFrame {

	// the various screens that the JFrame can display
	private GameBoard gameBoard = new GameBoard();
	private MenuScreen menuScreen = new MenuScreen();
	private HideScreen hideScreen = new HideScreen();
	private WinScreen winScreen;
	private HelpScreen helpScreen;

	// reference string to tell class which screen it is on
	private String currentScreen = "menu";

	// constructor
	public Surface() {
		super();
		
		// set the JFrame size
		setSize(1920, 1080);

		// change the layout to be null
        setLayout(null);

		// change the window title
        setTitle("Group 2 - Scotland Yard");

		// change exiting window behaviour
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// set to be full screen to start
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		// initially make the menu screen shwon
		setContentPane(menuScreen);
        
		// attempt to initialize the help screen
		try {
			helpScreen = new HelpScreen();
		} catch (MalformedURLException e) {
			System.out.println("URL is malformed");
		}

		// make visible
        setVisible(true);

		
	}

	// getters and setters

	public GameBoard getGameBoard() {
		return gameBoard;
	}
	
	public void setGameBoard(GameBoard gameBoard) {
		this.gameBoard = gameBoard;
	}

	public MenuScreen getMenuScreen() {
		return menuScreen;
	}

	public void setMenuScreen(MenuScreen menuScreen) {
		this.menuScreen = menuScreen;
	}
	
	public HideScreen getHideScreen() {
		return hideScreen;
	}

	public void setHideScreen(HideScreen hideScreen) {
		this.hideScreen = hideScreen;
	}

	public HelpScreen getHelpScreen() {
		return helpScreen;
	}

	public void setHelpScreen(HelpScreen helpScreen) {
		this.helpScreen = helpScreen;
	}

	public WinScreen getWinScreen() {
		return winScreen;
	}

	public void setWinScreen(WinScreen winScreen) {
		this.winScreen = winScreen;
	}

	// utility methods

	// redraws the surface, important for changing screens
	public void update() {
		
		// update the currently shown screen
		if (currentScreen.equals("game"))
			gameBoard.update();
		else if (currentScreen.equals("menu"))
			menuScreen.update();

		
		invalidate();
		repaint();
		validate();
		
	}
	
	// changes the currently displayed screen
	public void changeScreens(String screen) {

		// internally save the new screen
		currentScreen = screen;

		// change the contentPane to be the newly selected screen
		switch (screen) {

			case "menu":
				setContentPane(menuScreen);
				break;
			case "game":
				setContentPane(gameBoard);
				break;
			case "hide":
				setContentPane(hideScreen);
				break;
			case "help":
				setContentPane(helpScreen);
				break;
			case "win":
				setContentPane(winScreen);
				break;
			default:
				System.out.println("Invalid screen type entered, screen remaining the same");
				break;

		}

		update();

	}
	
}