/*
    Brian Teachman
    CS 140: Whatcom Community College
    10/11/2017

    A console calendar - displays a given month using the CalendarHelper class
 */

import java.util.Scanner;

// https://docs.oracle.com/javase/8/docs/api/java/util/Calendar.html

public class Application extends PrintCandy {

    final static int CELL_WIDTH = 5;

    private static CalendarHelper helper;
    private static Scanner input;
    private static boolean dateIsSet = false;

    public static void main(String[] args) {

        helper = new CalendarHelper();
        input = new Scanner(System.in); // user prompt

//        stupidASCIIArt();

        boolean running = true;
        while (running) {
            switch (menu()) {
                case 'e':
                    store(promptForDate());
                    drawMonth();
                    break;

                case 't':
                    store(helper.getMonth()+"/"+helper.getDay());
                    drawMonth();
                    break;

                case 'n':
                    if (dateIsSet) {
                        helper.nextMonth();
                        drawMonth();
                    }
                    else {
                        println("\nYou need to have a calendar displayed first.");
                    }
                    break;

                case 'p':
                    if (dateIsSet) {
                        helper.previousMonth();
                        drawMonth();
                    }
                    else {
                        println("\nYou need to have a calendar displayed first.");
                    }
                    break;

                case 'q':
                    running = false;
                    break;

                default:
                    println("Please enter a valid command.");
                    break;
            }
//            println("Start of the month: " + helper.getDayMonthStartsOn());
//            println("End of the month:   " + helper.getLastDayofMonth());
        }

    }

    /**
     * @return char  The selected menu item. ("e", "t", "n", "p", or "q")
     */
    public static char menu() {
        println("\nPlease type a command");
        println("    \"e\" to enter a date and display the corresponding");
        println("    \"t\" to get todays date and display today's calendar");
        println("    \"n\" to display the next month");
        println("    \"p\" to display the previous month");
        println("    \"q\" to quit the program");
        String command = input.nextLine();
        if (command.length() > 0) {
            return command.charAt(0);
        }
        return 'x';
    }

    /**
     * @return String  Returns a date string in the format mm/dd
     */
    public static String promptForDate() {
        print("What date would you like like to look at? (mm/dd): ");
        return input.nextLine(); // in-line processing
    }

    private static void store(String inputData) {
        try {
            setCalendarData(inputData);
        }
        catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            store(promptForDate()); // fail gracefully (recursive reprompt)
        }
        dateIsSet = true;
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
    public static void setCalendarData(String inputData) throws IllegalArgumentException {
        int delimiterIndex = helper.getDelimiterIndex(inputData);
        // TODO: move date formatting validation into it's mutator method?
        //       trying to keep view (the error message) data in the Application class
        //       and passing the error message through two method calls seems extreme
        if ( ! (delimiterIndex == 1 || delimiterIndex == 2)) { // accounts for '1' or '01' formatted case
            throw new IllegalArgumentException("Expected the format 'mm/dd', where mm is the month and dd is the day.");
        }
        helper.setCalendarDate(inputData);
    }

    private static int dayCount = 0; // for tracking days in printCalendarDay
    // TODO: refactor this global
    /*
     * Accepts an integer representing the month and displays
     * the month as a text formatted calendar
     *
     * @param int month
     * @param void
     */
    static public void drawMonth() {
        println("\n"+helper.getDateLong());
        rowHeader(CELL_WIDTH);
        for (int i = 0; i < 5; i++) { // 5 weeks
            drawRow(i);
            rowHeader(CELL_WIDTH);
        }
        displayDate(helper.getMonth(), helper.getDay());
        dayCount = 0;
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
                print("=");
            }
        }
        print("=\n");
    }

    /*
     * Displays one week on the calendar (one row).
     *
     * @param int row  Integer representing which row to display
     * @return void
     */
    public static void drawRow(int row) {
        for (int rowHeight = 0; rowHeight < cellHeight(); rowHeight++) { // cell height number of rows
            print("|"); // start row
            for (int cellNumber = 0; cellNumber < 7; cellNumber++) { // 7 cells for 7 days
                for (int k = 0; k < CELL_WIDTH; k++) { // cell content
                    if (k == 1 && rowHeight == 0) { // after one blank space on the first row

                        printCalendarDay(row, cellNumber);
                        if (helper.isCurrentDay(dayCount)) {
                            print("*"); //TODO: mark the current day
                            k += 2; // account for double digit format
                        }
                        else {
                            k++; // account for double digit format
                        }
                    }
                    else if (k == (CELL_WIDTH - 1)) {
                        print("|"); // end of cell
                    } else {
                        print(" "); // pad cell
                    }
                }
            }
            println(); // end of row
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

    /**
     *
     * @param rowNumber
     * @param cellNumber
     * return void
     */
    public static void printCalendarDay(int rowNumber, int cellNumber) {
        int startWeekDay = helper.getDayMonthStartsOn();
        int daysInMonth = helper.getLastDayofMonth();

        if ((rowNumber == 0 && cellNumber < (startWeekDay - 1)) || dayCount >= daysInMonth) {
            print("  ");
        }
        else if (dayCount < daysInMonth) {
            if (dayCount < 9) { // pad w/space to match spacing for double digits
                print(" ");
            }
            print(++dayCount);
//            print(new DecimalFormat("00").format(++dayCount));
//            print("%2d", ++dayCount);
        }
        else {
            print(" ");
        }
    }

    /**
     *
     * @param month
     * @param day
     * @return void
     */
    public static void displayDate(int month, int day) {
        println("\nMonth: " + month);
        println("Day:   " + day);
    }

    public static void stupidASCIIArt() {
        int rows = 10;
        int cols = CELL_WIDTH * 7;
        for (int i=0; i < rows; i++) {
            for (int j=0; j < cols; j++) {
                int rchar = (char) (Math.random()*95)+37;
                if (rchar % 2 == 0) {
                    print((char) rchar);
                }
                else if (rchar % 3 == 0) {
                    if (i % 3== 0) {
                        print(" >< ");
                    }
                    else {
                        print(" <> ");
                    }
                }
                else if (rchar % 13 == 0) {
                    print(" :) ");
                }
                else {
                    print(" ");
                }
            }
            print("\n");
        }
        print("\n\n");
    }

}
