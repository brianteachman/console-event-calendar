import java.util.Scanner;
/*
Brian Teachman
CS 140: Whatcom Community College
9/29/2017

This class makes change in typical currency denomination for some given total amount.
*/
public class ChangeCalculator {
    /*
    This function is designed to use some elements of java we have not
    learned yet
    to take in input from the user and then return that information.
    It has no parameters and returns a double. A dollar amount such as
    34.65 should
    be entered after the prompt.
    */
    public static double getInput(){
        System.out.print("Please enter the amount of money you for which you would like to give change.: $");
        Scanner inScan = new Scanner(System.in);
        return inScan.nextDouble();
    }
    public static void main(String[] args){
        double amount = getInput();
        makeChange(amount); // returns correct denominations

        //Example calls
//        System.out.println("100s: "+ 5/* expression to calculate number of 10$ bills*/);
//        amount = 0;/* expression to calculate money left to give change for. */
    }

    public static void makeChange(double amount) {
        System.out.println("Your change is:");
        double change;
        change = printDenom(amount, 100);
        change = printDenom(change, 50);
        change = printDenom(change, 20);
        change = printDenom(change, 10);
        change = printDenom(change, 5);
        change = printDenom(change, 1);
        change = printDenom(change, 0.25);
        change = printDenom(change, 0.10);
        change = printDenom(change, 0.05); // TODO: still has rounding error
        printDenom(change, 0.01);
    }

    public static double printDenom(double payment, double denom) {
        int num = (int) (payment / denom); // Denomination quantity
        double change = payment % denom;   // Remainder after denomination multiple returned

        // Print change with correct denomination
        if (denom == 100 ) {
            System.out.println(num + " hundreds");
        }
        else if (denom == 50 ) {
            System.out.println(num + " fifties");
        }
        else if (denom == 20) {
            System.out.println(num + " twenties");
        }
        else if (denom == 10) {
            System.out.println(num + " tens");
        }
        else if (denom == 5) {
            System.out.println(num + " fives");
        }
        else if (denom == 1) {
            System.out.println(num + " ones");
        }
        else if (denom == 0.25) {
            System.out.println(num + " quarters");
        }
        else if (denom == 0.10) {
            System.out.println(num + " dimes");
        }
        else if (denom == 0.05) {
            System.out.println(num + " nickels");
        }
        else if (denom == 0.01) {
            //System.out.println(change);
            System.out.println(num+Math.round(100*change)+ " pennies");
        }

        return change;
    }
}
