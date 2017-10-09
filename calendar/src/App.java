import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

// https://docs.oracle.com/javase/8/docs/api/java/util/Calendar.html

public class App {

    int[] monthDays = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    static int month;
    static int day;
    static int weekday;

    public static void main(String[] args) {
        String date = promptForDate();
        System.out.println("Your calandar, sir.");

        // get todays date
//        getTodaysDate();

        printMonth(5);
    }

    public static void getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        month = cal.get(Calendar.MONTH); // indexed month (Jan == 0)
        day = cal.get(Calendar.DAY_OF_MONTH);
        weekday = cal.get(Calendar.DAY_OF_WEEK);

        System.out.print("It's the ");
        System.out.println(weekday + "th day of the week.");
        System.out.println(day);
        System.out.println( cal.getFirstDayOfWeek() );
    }

    public static String promptForDate() {
        System.out.print("What date would you like like to look at? (mm/dd): ");
        Scanner input = new Scanner(System.in);
        String inputData = input.nextLine(); // in-line processing
        setInputDate(inputData);
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
    public static void setInputDate(String inputData) {
        int delimiterIndex = inputData.indexOf('/');
        if ( !(delimiterIndex == 1 || delimiterIndex == 2)) { // accounts for '1' or '01' format
            throw new IllegalArgumentException("Expected the format 'mm/dd', where mm is the month and dd is the day.");
        }

        // if month starts with a zero, remove it
        String mm = inputData.substring(0, delimiterIndex);
        month = checkDateSegment(mm, 12, "Months"); //TODO set month

        String dd = inputData.substring(delimiterIndex+1);
        day = checkDateSegment(dd, 31, "Days"); //TODO set day
    }

    /**
     * Check date segments for valid, correctly formatted input
     *
     * @param segment
     * @param upperBound
     * @param segmentName
     */
    public static int checkDateSegment(String segment, int upperBound, String segmentName) {
        segment = removeLeadingZero(segment);
        ArrayList<String> validSegments = getValidList(upperBound);
        if ( ! validSegments.contains(segment)) { // check input is in valid month range
            throw new IllegalArgumentException(String.format("%s range from 1 to %d. %s given.",
                    segmentName, upperBound, segment));
        }
        return Integer.parseInt(segment);
    }

    /**
     * Build a list of ascii integers representing  (for easy range checking)
     *
     * @param upperBound int
     * @return ArrayList<String>
     */
    public static ArrayList<String> getValidList(int upperBound) {
        ArrayList<String> list = new ArrayList<String>();
        for (int i=1; i<=upperBound; i++) {
            list.add("".valueOf(i));
        }
        return list;
    }

    /**
     * If string segment starts with a zero, remove it
     *
     * @param dateString
     * @return String
     */
    public static String removeLeadingZero(String dateString) {
        if (dateString.startsWith("0")) {
            dateString = dateString.substring(1);
        }
        return dateString;
    }

    public static void rowHeader(int width) {
        for (int i=0; i<7; i++) {
            for (int j=0; j<width; j++) {
                System.out.print('=');
            }
        }
        System.out.print("=\n");
    }

    public static void printMonth(int width) {

        rowHeader(5);
        for (int i = 0; i < 5; i++) {
            System.out.print("|");
            for (int j = 0; j < 7; j++) {
                for (int k = 0; k < width; k++) {
                    if (k == 1) {

                        // calendar day of the month goes here
                        System.out.print('x');
                    }
                    else if (k == (width-1) ) {
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
                rowHeader(width);
            }
        }
    }

    public static int getDate() {
        return 1;
    }
}
