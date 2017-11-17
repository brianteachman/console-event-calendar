/* ----------------------------------------------------------------------------
    Brian Teachman
    CS 140: Whatcom Community College
    10/11/2017

    A text based calendar:

    Displays a given month in text using java.util.Calendar
 ----------------------------------------------------------------------------*/

import java.util.Scanner;

public class Application extends PrintCandy {

    private final static int CELL_WIDTH = 5;
    private static int dayCount = 0; // for tracking days in printCalendarDay

    // TODO: refactor these out of global space
    private static String lastDateIn;
    private static Integer delimiterIndex; // using Integer for nullability

    public static void displayDate(int month, int day) {
        println("\nMonth: " + month);
        println("Day:   " + day);
    }

    // Accepts an integer representing the month and displays
    // the month as a text formatted calendar
    public static void drawMonth(BtCalendar c) {
        println("\n"+c.getMonthName());
        rowHeader(CELL_WIDTH);
        for (int i = 0; i < 5; i++) { // 5 weeks
            drawRow(c, i);
            rowHeader(CELL_WIDTH);
        }
        displayDate(c.getMonth(), c.getDay());
        dayCount = 0;
    }

    // Print one week of the calendar (one row) to the stream
    public static void drawRow(BtCalendar c, int row) {
        for (int rowHeight = 0; rowHeight < cellHeight(); rowHeight++) { // cell height number of rows
            print("|"); // start row
            for (int cellNumber = 0; cellNumber < 7; cellNumber++) { // 7 cells for 7 days
                for (int k = 0; k < CELL_WIDTH; k++) { // cell content
                    if (k == 1 && rowHeight == 0) { // after one blank space on the first row

                        printCalendarDay(c, row, cellNumber);
                        if (c.isCurrentDay(dayCount)) {
                            print("*");
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

    // Print a row divider, some given width
    private static void rowHeader(int width) {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < width; j++) {
                print("=");
            }
        }
        print("=\n");
    }

    // Let the row height be 1/2 * the row width, unless the cell's width
    // is less than or equal to 4, than it is 1.
    private static int cellHeight() {
        int rowHieght = CELL_WIDTH/2;
        if (rowHieght < 1 || CELL_WIDTH == 4) {
            rowHieght = 1;
            // when CELL_WIDTH < 4, right cell edge collapses
        }
        return rowHieght;
    }

    // Print cell data to stream
    private static void printCalendarDay(BtCalendar c, int rowNumber, int cellNumber) {
        int startWeekDay = c.getFirstWeekdayOfMonth();
        int daysInMonth = c.getLastDayOfMonth();

        if ((rowNumber == 0 && cellNumber < (startWeekDay - 1))
                || dayCount >= daysInMonth) {
            print("  ");
        }
        else if (dayCount < daysInMonth) {
            if (dayCount < 9) { // pad w/space to match spacing for double digits
                print(" ");
            }
            print(++dayCount);
//            print("%2d", ++dayCount);
        }
        else {
            print(" ");
        }
    }

    private static void drawCurrentMonth(BtCalendar c) {
        if (c == null) {
            c = new BtCalendar();
        }
        drawMonth(c);
    }

    // The selected menu item. ("e", "t", "n", "p", or "q")
    private static char menu(Scanner input) {
        println("\nPlease type a command");
        println("    \"e\" to enter a date and display the corresponding");
        println("    \"t\" to get todays date and display today's calendar");
        println("    \"n\" to display the next month");
        println("    \"p\" to display the previous month");
        println("    \"q\" to quit the program");
        print("> ");
        String command = input.nextLine();
        if (command.length() > 0) {
            return command.charAt(0);
        }
        return 'x';
    }

    // Returns a date string in the format mm/dd
    private static String promptForDate(Scanner input) {
        print("What date would you like like to look at? (mm/dd): ");
        return input.nextLine(); // in-line processing
    }

    private static void setDateFromPrompt(BtCalendar c, Scanner input) {
        String formattedDate = promptForDate(input); // in-line processing
        try {
            int delimiterIndex = getDelimiterIndex(formattedDate);
            int month = Integer.valueOf(formattedDate.substring(0, delimiterIndex));
            int day = Integer.valueOf(formattedDate.substring(delimiterIndex+1));
            println(month + "/" + day);
            c.setDate(month, day, 2017);
        }
        catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            setDateFromPrompt(c, input); // fail gracefully (recursive reprompt)
        }
    }

    private static int getDelimiterIndex(String formattedDate) {
        if (delimiterIndex == null || !lastDateIn.equals(formattedDate)) { // only iterate through the string once
            setDelimiterIndex(formattedDate);
            lastDateIn = formattedDate;
        }
        return delimiterIndex;
    }

    private static void setDelimiterIndex(String formattedDate) {
        delimiterIndex = formattedDate.indexOf("/");
        if ( ! (delimiterIndex == 1 || delimiterIndex == 2)) { // accounts for '1' or '01' formatted case
            throw new IllegalArgumentException("Expected the format 'mm/dd', where mm is the month and dd is the day.");
        }
    }

    private static void stupidASCIIArt() {
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

    /*----------------------------------------------------------------------
    * Calendar client
    ----------------------------------------------------------------------*/
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in); // user prompt
        BtCalendar delta = new BtCalendar();
        BtCalendar current = null;

        boolean running = true;
        while (running) {
            switch (menu(input)) {
                case 'e':
                    setDateFromPrompt(delta, input);
                    // TODO: add ASCII art
                    drawMonth(delta);
                    drawCurrentMonth(current);
                    break;

                case 't':
                    // TODO: add ASCII art
                    drawCurrentMonth(current);
                    delta.setDateSetFlag(true);
                    break;

                case 'n':
                    if (delta.isDateSet()) {
                        delta.nextMonth();
                        // TODO: add ASCII art
                        drawMonth(delta);
                        drawCurrentMonth(current);
                    }
                    else {
                        println("\nYou need to have a calendar displayed first.");
                    }
                    break;

                case 'p':
                    if (delta.isDateSet()) {
                        delta.previousMonth();
                        // TODO: add ASCII art
                        drawMonth(delta);
                        drawCurrentMonth(current);
                    }
                    else {
                        println("\nYou need to have a calendar displayed first.");
                    }
                    break;

                case 'q':
                    running = false;
                    println("\nThat's one way to do it, good day.");
                    break;

                default:
                    println("Please enter a valid command.");
                    break;
            }
        }
    }
}
