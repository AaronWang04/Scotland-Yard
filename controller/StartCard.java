/**
 * Class: StartCard
 * 
 * Description: Contains start card for detectives and MrX
 * 
 * Areas of Concern: None
 * 
 * Author(s): Aaron
 * 
 */

package controller;

import java.util.Random;

public class StartCard {

    public static int[] detectiveStartPositions = FileReader.readDetectiveStartCard();
    public static int[] misterXStartPositions = FileReader.readMisterXStartCard();

    public static int getRandomMrXPosition() {
        return getRandomPosition(detectiveStartPositions);
    }

    public static int getRandomDetectivePosition() {
        return getRandomPosition(misterXStartPositions);
    }

    // Method to get a random start card, and discard it once returned
    private static int getRandomPosition(int[] startingPositions) {

        int random = new Random().nextInt(startingPositions.length);
        while(startingPositions[random]<0)
            random = new Random().nextInt(startingPositions.length);

        int temp = startingPositions[random];
        startingPositions[random] = -1;

        return temp;

    }
}
