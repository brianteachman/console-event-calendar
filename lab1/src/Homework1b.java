/**
 * Created by BTEACHMAN on 9/25/2017.
 */
public class Homework1b {

    /* problem 1

    //////////////////////
    || Victory is mine! ||
    \\\\\\\\\\\\\\\\\\\\\\

    */

    /* problem 2

    //////////////////////
    || Victory is mine! ||
    \\\\\\\\\\\\\\\\\\\\\\
    || Victory is mine! ||
    \\\\\\\\\\\\\\\\\\\\\\
    || Victory is mine! ||
    \\\\\\\\\\\\\\\\\\\\\\

    */

    // problem 1
    public static void victory() {
        System.out.println("//////////////////////");
        victoryWords();
    }

    // problem 1
    public static void victoryWords() {
        System.out.println("|| Victory is mine! ||");
        System.out.println("\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\");
    }

    // problem 2
    public static void superVictory() {
        System.out.println("//////////////////////");
        victoryWords();
        victoryWords();
        victoryWords();
        victoryWords();
        victoryWords();
    }

    public static void main(String[] args){
//        victory();
        superVictory();
    }
}
