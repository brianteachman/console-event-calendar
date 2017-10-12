import com.sun.javaws.exceptions.InvalidArgumentException;
import java.util.Scanner;

// https://docs.oracle.com/javase/8/docs/api/java/util/Calendar.html

public class App {

    final static int CELL_WIDTH = 5;

    static int month;
    static int day;
    static CalendarHelper cal = new CalendarHelper();

    public static void main(String[] args) {

        asciiArt();

        String date = promptForDate();
//        System.out.println("Your calandar for "+date+", sir.");
//        cal.printFormattedToday();

        cal.getStartDate(month);

        drawMonth(month);

//        System.out.println("Month: ");
//        System.out.println("Day: ");
    }

    public static void asciiArt() {}

    public static String promptForDate() {
        System.out.print("What date would you like like to look at? (mm/dd): ");
        Scanner input = new Scanner(System.in); // user prompt
        String inputData = input.nextLine(); // in-line processing
//        setDate(inputData);
        try { // try to fail gracefully
            setDate(inputData);
        }
        catch (InvalidArgumentException e) {
            System.out.println(e.getRealMessage());
            promptForDate(); //TODO recursively reprompt? Why doesn't it work
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
    public static void setDate(String inputData) throws InvalidArgumentException {
        int delimiterIndex = inputData.indexOf('/');
        if ( !(delimiterIndex == 1 || delimiterIndex == 2)) { // accounts for '1' or '01' formatted case
            throw new IllegalArgumentException("Expected the format 'mm/dd', where mm is the month and dd is the day.");
        }
        // Is it more efficient to just pass the index in?
        month = cal.monthFromDate(inputData, delimiterIndex);
        day = cal.dayFromDate(inputData, delimiterIndex);
    }

    /*
     * Accepts an integer representing the month and displays
     * the month as a text formatted calendar
     *
     * @param int month
     * @return void
     * @exit Prints month to stream
     */
    static public void drawMonth(int month) {
        rowHeader(CELL_WIDTH);
        for (int i = 0; i < 5; i++) { // 5 weeks
            drawRow(i);
            if (i != 5) {
                rowHeader(CELL_WIDTH);
            }
        }
    }

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
     * @exit Prints one week (1-4) of the month to stream
     */
    public static void drawRow(int row) {
        for (int i = 0; i < cellHeight(); i++) { // cell height number of rows
            System.out.print("|"); // start row
            for (int j = 0; j < 7; j++) { // 7 cells for 7 days
                for (int k = 0; k < CELL_WIDTH; k++) { // cell content
                    if (k == 1 && i == 0) { // after one blank space on the first row

                        printCalendarDay();

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

    public static void printCalendarDay() {

        //TODO calendar day of the month goes here
        System.out.print('x');
    }
}
