/* ----------------------------------------------------------------------------
 * Lab 05 - Reverse Guessing Game
 *
 * Brian Teachmam
 * 10/31/2017
 * CS 140: Whatcom Communtiy College
 * --------------------------------------------------------------------------*/

import java.util.Random;
import java.util.Scanner;

public class GuessingGame {
    static Scanner in = new Scanner(System.in);
    static Random rgen = new Random();
    static boolean[] alreadyGuessed;
    static int upperBound = 10;
    static int guessCount = 0;

    public static void main(String[] args) {
        alreadyGuessed = new boolean[upperBound];

        System.out.print("Pick a number between 1 and "+upperBound+". did you pick? (y/n): ");
        char picked = in.next().charAt(0);

        System.out.println("\nNow I'll guess your number.");

        boolean guessed = false;
        boolean firstGuess = true;
        while (!guessed) {
            if (!firstGuess) {
                System.out.println("Ok, I'll try again.");
            }
            int theGuess = guessNumber();
            System.out.print("\nIs your number " + theGuess + "? (y/n): ");
            if (in.next().charAt(0) == 'y'){
                guessed = true;
            }
        }
    }

    private static int guessNumber() {
        //returns an integer between 0 (inclusively) and upperBound (exclusively)
        int theGuess = rgen.nextInt(upperBound);
        if (alreadyGuessed[theGuess]) {
            guessNumber();
        }
        alreadyGuessed[theGuess] = true;
        return theGuess+1;
    }
}
