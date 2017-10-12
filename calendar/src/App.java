import com.sun.javaws.exceptions.InvalidArgumentException;
import java.util.Scanner;

// https://docs.oracle.com/javase/8/docs/api/java/util/Calendar.html

public class App {

    final static int CELL_WIDTH = 5;

    int[] monthDays = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    static int month;
    static int day;
    static int weekday;
    static CalendarHelper cal = new CalendarHelper();

    public static void main(String[] args) {
        String date = promptForDate();
        System.out.printf("Your calandar for %s, sir.\n", date);
        cal.printFormattedToday();

//        cal.getTodaysDate();

//        drawMonth(1);
    }

    public static String promptForDate() {
        System.out.print("What date would you like like to look at? (mm/dd): ");
        Scanner input = new Scanner(System.in); // user prompt
        String inputData = input.nextLine(); // in-line processing
//        setDate(inputData);
        try {
            setDate(inputData);
        }
        catch (InvalidArgumentException e) {
            System.out.println(e.getRealMessage());
            promptForDate(); // recursively reprompt?
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
//    public static void setDate(String inputData) {
        int delimiterIndex = inputData.indexOf('/');
        if ( !(delimiterIndex == 1 || delimiterIndex == 2)) { // accounts for '1' or '01' format
            throw new IllegalArgumentException("Expected the format 'mm/dd', where mm is the month and dd is the day.");
        }

        // Is it more efficient to just pass the index in?
        month = cal.monthFromDate(inputData, delimiterIndex);
        day = cal.dayFromDate(inputData, delimiterIndex);
    }

    /*
     * Displays one week on the calendar (one row).
     *
     * @param int row  Integer representing which row to display
     * @return void
     * @exit Prints one week (1-4) of the month to stream
     */
    public void drawRow(int row) {

    }

    public static void rowHeader(int width) {
        for (int i=0; i<7; i++) {
            for (int j=0; j<width; j++) {
                System.out.print('=');
            }
        }
        System.out.print("=\n");
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
        for (int i = 0; i < 5; i++) {
            System.out.print("|");
            for (int j = 0; j < 7; j++) {
                for (int k = 0; k < CELL_WIDTH; k++) {
                    if (k == 1) {

                        // calendar day of the month goes here
                        System.out.print('x');
                    }
                    else if (k == (CELL_WIDTH-1) ) {
                        // end of cell
                        System.out.print("|");
                    }
                    else {
                        // pad cell
                        System.out.print(' ');
                    }
                }
            }
            System.out.print("\n");
            if (i != 5) {
                rowHeader(CELL_WIDTH);
            }
        }
    }

    public static int getDate() {
        return 1;
    }
}
