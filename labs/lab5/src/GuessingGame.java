/* ----------------------------------------------------------------------------
 * Lab 05 - Reverse Guessing Game
 *
 * Brian Teachmam
 * 11/1/2017
 * CS 140: Whatcom Communtiy College
 * --------------------------------------------------------------------------*/

import java.util.Random;
import java.util.Scanner;

public class GuessingGame {
    static Scanner in = new Scanner(System.in);
    static Random rgen = new Random();
    static boolean[] alreadyGuessed;
    static int upperBound;
    static int guessCount = 0;

    public static void main(String[] args) {

        System.out.print("Enter an upper bound for the guess range: ");
        initGameState(in.nextInt());
        in.nextLine(); // clear buffer

        System.out.println("Think of a number between 1 and "+upperBound+" then press ENTER to continue. ");
        in.nextLine();

        System.out.println("Now I'll guess your number.");
        char picked;

        boolean nextGuess = true;
        boolean gameOver = false;
        while (!gameOver) {

            if (nextGuess) {
                int theGuess = guessNumber();
                guessCount++;
                System.out.print("Is your number " + theGuess + "? (y/n): ");
            }
            picked = in.next().charAt(0);

            if (picked == 'y') {
                gameOver = true;
                nextGuess = false;
                System.out.println("I got it! After only "+guessCount+(guessCount == 1?" try!":" tries."));
            }
            else if (picked == 'n') {
                if (guessCount == upperBound) {
                    gameOver = true;
                    System.out.println("GAME OVER. OK now, cheating isn't cool!");
                }
                nextGuess = true;
            }
            else {
                System.out.print("Im sorry, come again? (y/n): ");
                nextGuess = false;
            }
        }
    }

    private static void initGameState(int size) {
        upperBound = size;
        alreadyGuessed = new boolean[upperBound];
        for(int i=0; i < upperBound; i++) {
            alreadyGuessed[i] = false;
        }
    }

    private static int guessNumber() {
        // rgen.nextInt() returns an integer between 0 (inclusively)
        // and the upperBound (exclusively)
        int theGuess = rgen.nextInt(upperBound);
        while (alreadyGuessed[theGuess]) {
            theGuess = rgen.nextInt(upperBound);
        }
        alreadyGuessed[theGuess] = true;
        return theGuess+1;
    }
}
