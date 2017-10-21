/*
    Brian Teachman
    CS 140: Whatcom Community College
    10/11/2017

    A console calendar - displays a given month using the CalendarHelper class
 */

import com.sun.javaws.exceptions.InvalidArgumentException;

import java.text.DecimalFormat;
import java.util.Scanner;

// https://docs.oracle.com/javase/8/docs/api/java/util/Calendar.html

public class Application {

    final static int CELL_WIDTH = 5;

    static int month;
    static int day;
    static CalendarHelper helper = new CalendarHelper();

    public static void main(String[] args) {

        asciiArt();

        String date = promptForDate();
        System.out.println("Your calandar for "+month+"/"+day+":\n");

        drawMonth(month);

        System.out.println("Month: " + month);
        System.out.println("Day:   " + day);
        System.out.println();
        System.out.println("Start of the month: " + helper.getDayMonthStartsOn());
        System.out.println("End of the month:   " + helper.getLastDayofMonth());
    }

    public static void asciiArt() {
        int rows = 10;
        int cols = 20;

        for (int i=0; i < rows; i++) {
//            for (int j=0; j < cols; j++) {
//                if (j % 5 == 0) print("/");
//                if (j == 19) print("\n");
//                if (i % 3 == 0 || j < 10) {
//                    print("/");
//                }
//            }
//            print("-");
        }
        print("\n\n");
    }

    /**
     *
     * @return String
     */
    public static String promptForDate() {
        System.out.print("What date would you like like to look at? (mm/dd): ");
        Scanner input = new Scanner(System.in); // user prompt
        String inputData = input.nextLine(); // in-line processing
        try {
            setDate(inputData);
        }
        catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            promptForDate(); // fail gracefully
        }
        return inputData;
    }

    /**
     * Validates and sets users input
     *
     * Check that month (mm) is between 1 and 12, day (dd) is between 1 and 31,
     * and that they are formatted correctly (mm/dd).
     *
     * @param inputData String
     * @throws IllegalArgumentException
     */
    public static void setDate(String inputData) throws IllegalArgumentException {
        int delimiterIndex = inputData.indexOf('/');
        if ( ! (delimiterIndex == 1 || delimiterIndex == 2)) { // accounts for '1' or '01' formatted case
            throw new IllegalArgumentException("Expected the format 'mm/dd', where mm is the month and dd is the day.");
        }
        // Is it more efficient to just pass the index in?
        month = helper.monthFromDate(inputData, delimiterIndex);
        day = helper.dayFromDate(inputData, delimiterIndex);
        helper.setCalendarDate(month, day);
    }

    /*
     * Accepts an integer representing the month and displays
     * the month as a text formatted calendar
     *
     * @param int month
     * @param void
     */
    static public void drawMonth(int month) {
        rowHeader(CELL_WIDTH);
        for (int i = 0; i < 5; i++) { // 5 weeks
            drawRow(i);
            rowHeader(CELL_WIDTH);
        }
    }

    /**
     * Print a row divider, some given width
     *
     * @param width
     * @return void
     */
    public static void rowHeader(int width) {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < width; j++) {
                System.out.print('=');
            }
        }
        System.out.print("=\n");
    }

    /*
     * Displays one week on the calendar (one row).
     *
     * @param int row  Integer representing which row to display
     * @return void
     */
    public static void drawRow(int row) {
        for (int rowHeight = 0; rowHeight < cellHeight(); rowHeight++) { // cell height number of rows
            System.out.print("|"); // start row
            for (int cellNumber = 0; cellNumber < 7; cellNumber++) { // 7 cells for 7 days
                for (int k = 0; k < CELL_WIDTH; k++) { // cell content
                    if (k == 1 && rowHeight == 0) { // after one blank space on the first row

                        printCalendarDay(row, cellNumber);
                        k++; // account for double digit format

                    } else if (k == (CELL_WIDTH - 1)) {
                        System.out.print("|"); // end of cell
                    } else {
                        System.out.print(' '); // pad cell
                    }
                }
            }
            System.out.print("\n"); // end of row
        }
    }

    /**
     * Let the row height be 1/2 * the row width, unless the cell's width
     * is less than or equal to 4, than it is 1.
     *
     * @return int
     */
    public static int cellHeight() {
        int rowHieght = CELL_WIDTH/2;
        if (rowHieght < 1 || CELL_WIDTH == 4) {
            rowHieght = 1;
            // when CELL_WIDTH < 4, right cell edge collapses
        }
        return rowHieght;
    }

    private static int dayCount = 0;

    /**
     *
     * @param rowNumber
     * @param cellNumber
     * return void
     */
    public static void printCalendarDay(int rowNumber, int cellNumber) {

        int startCell = helper.getDayMonthStartsOn(); //TODO: fix this, wrong output
        int end = helper.getLastDayofMonth();

        if ((rowNumber == 0 && cellNumber < (startCell - 1)) || dayCount >= end) {
            System.out.print("  ");
        }
        else if (dayCount < end) {
            if (dayCount < 9) { // pad w/space to match spacing for double digits
                System.out.print(' ');
            }
            System.out.print(++dayCount);
//            System.out.print(new DecimalFormat("00").format(++dayCount));
//            System.out.printf("%2d", ++dayCount);
        }
        else {
            System.out.print(" ");
        }
    }

    public static void print(String thing) {
        System.out.print(thing);
    }
}
