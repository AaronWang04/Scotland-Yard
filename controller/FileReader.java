/**
 * Class: FIleRead
 * 
 * Description: Read the given txt files and return a data structure storing the data
 * 
 * Areas of Concern: None
 * 
 * Author(s): Aaron
 * 
 */

package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.HashMap;

import model.*;

public class FileReader {

	// Reads the detective starting positions
	public static int[] readDetectiveStartCard() {

		int[] detectiveStartCard = new int[16];

		try{
			File detectiveStartingPostion = new File("texts/Detective_Bobbie_Start_Positions.txt");
			Scanner FileScanner = new Scanner(detectiveStartingPostion);

			int index = 0;
			while (FileScanner.hasNextLine()) {

				String line = FileScanner.nextLine();
				detectiveStartCard[index] = Integer.valueOf(line);
				index++;

			}

			FileScanner.close();

		}
		catch(FileNotFoundException e){
			System.out.println("Detective_Bobbie_Start_Positions.txt could not be found");
		}

		return detectiveStartCard;

	}

	// Reads the MrX starting positions
	public static int[] readMisterXStartCard() {

		int[] misterXStartCard = new int[13];

		try{
			File mrXStartingPostion = new File("texts/MrX_Start_Positions.txt");
			Scanner FileScanner = new Scanner(mrXStartingPostion);

			int index = 0;

			while (FileScanner.hasNextLine()) {

				String line = FileScanner.nextLine();
				misterXStartCard[index] =Integer.valueOf(line);
				index++;

			}

			FileScanner.close();

		}
		catch(FileNotFoundException e){
			System.out.println("MrX_Start_Positions.txt could not be found");
		}

		return misterXStartCard;

	}

	// Reac the coordinates for the nodes
	public static int[][] readCoords() {

		int[][] coordinates = new int[199][2];

		try{
			File coordinateFile = new File("texts/Coordinates.txt");
			Scanner FileScanner = new Scanner(coordinateFile);

			for(int index = 0; index < coordinates.length; index++){
				FileScanner.nextInt();
				coordinates[index][0] = FileScanner.nextInt();
				coordinates[index][1] = FileScanner.nextInt();
			}

			FileScanner.close();

		}
		catch(FileNotFoundException e){
			System.out.println("Coordinates.txt could not be found");
		}

		return coordinates;
	
	}

	// Read the edges for the nodes
	public static Node[] readRoutes() {
		// Create 200 nodes (0 is not used, created for easier indexing)
		Node[] nodeArray = new Node[200];

		// Create empty node
		for(int i = 0; i < nodeArray.length;i++)
			nodeArray[i]= new Node();

		// Set Node Number
		for(int i = 0; i < nodeArray.length;i++)
			nodeArray[i].setNodeNum(i);;

		int[][] nodeCoord = readCoords();

		// Set the map coords
		for(int i = 0; i<nodeCoord.length;i++)
			nodeArray[i + 1].setMapCords(nodeCoord[i]);;

		try{
			File routeFile = new File("texts/Routes.txt");
			Scanner FileScanner = new Scanner(routeFile);

			while(FileScanner.hasNextLine()){

				int tempnum1 = FileScanner.nextInt();
				int tempnum2 = FileScanner.nextInt();
				String tempString = FileScanner.next();

				// Two hashmaps for both nodes connected to an edge
				HashMap<Integer,String> forward = new HashMap<Integer,String>();
				HashMap<Integer,String> backward = new HashMap<Integer,String>();
				
				forward.put(tempnum2,tempString);
				backward.put(tempnum1,tempString);

				nodeArray[tempnum1].addTransitType(forward);
				nodeArray[tempnum2].addTransitType(backward);
			}

			FileScanner.close();
		}
		catch(FileNotFoundException e) {
			System.out.println("Routes.txt could not be found");
		}

		return nodeArray;

	}

}