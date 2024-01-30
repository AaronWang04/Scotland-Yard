/**
 * Title: SCOTLAND YARD APPLICATION
 * Date:
 * 		May 8, 2022
 * Description:
 * 		This application recreates the strategy board game "Scotland Yard" by Ravensburger, the two player version. In this board game, 
 * 		there are two sides: Mister X, and the detectives. The goal of the Detectives is to figure out where Mr X is and either surround
 * 		him or capture him, while the goal of Mister X is to avoid capture. This application has options for playing with another person,
 * 		or playing against a computer.
 * Features:
 * 		- Player-vs-Player and Player-vs-Computer modes
 * 		- BFS-driven AI algorithms
 * 		- Click-to-move gameplay
 * Major skills:
 * 		- Objected Oriented Programming
 * 		- Dynamic Programming
 * 		- Graph Theory
 * 		- Greedy Algorithms
 * 		- GUI
 */


import controller.*;

public class ScotlandYardApplication {

	public static void main(String[] args) {

		new ScotlandYardController();
		
	}
	
}
