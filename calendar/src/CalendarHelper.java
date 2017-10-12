/*
    Brian Teachman
    CS 140: Whatcom Community College
    10/11/2017

    Java 8 Calendar Wrapper: console calendar helper class.
*/
import java.util.ArrayList;
import java.util.Calendar;

public class CalendarHelper {

    int[] monthDays = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    Calendar cal = Calendar.getInstance();

    public void getStartDate(int month) {
        // https://stackoverflow.com/a/2938209/503781

        // get today and clear time of day
        cal.set(Calendar.HOUR_OF_DAY, 0); // ! clear would not reset the hour of day !
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);

        // get start of the month
        cal.set(Calendar.DAY_OF_MONTH, 1);
        System.out.println("Start of the month:       " + cal.getTime());
        System.out.println("... in milliseconds:      " + cal.getTimeInMillis());

        // get start of the next month
        cal.add(Calendar.MONTH, 1);
        System.out.println("Start of the next month:  " + cal.getTime());
        System.out.println("... in milliseconds:      " + cal.getTimeInMillis());
    }

    public void printFormattedToday() {
        System.out.printf("%d/%s/%s", cal.get(Calendar.MONTH)+1, cal.get(Calendar.DAY_OF_MONTH), "2017");
    }

    /*
     * Given a date as a String, extract an integer value for the month and return it.
     *
     * @param String
     * @return int
     */
    public int monthFromDate(String formatedDate, int delimiterIndex) {
//        String mm = formatedDate.substring(0, formatedDate.indexOf('/'));
        String mm = formatedDate.substring(0, delimiterIndex);
        return validateAndReturnInt(mm, 12, "Months");
    }

    /*
     * Given a date as a String, extract an integer value for the day and return it.
     *
     * @param String
     * @return int
     */
    public int dayFromDate(String formatedDate, int delimiterIndex) {
        // Since I already have a call to indexOf stored in the parent scope, perhaps
        // its faster to just pass it index in, instead of call .indexOf again?
        String dd = formatedDate.substring(delimiterIndex+1);
        return validateAndReturnInt(dd, 31, "Days");
    }

    /*
     * Display the date information as a graphical representation of the calendar.
     *
     * @param int month
     * @param int day
     */
    public void displayDate(int month, int day) {

    }

    /**
     * Check date segments for valid, correctly formatted input
     *
     * @param dateSegment
     * @param upperBound
     * @param segmentName
     */
    public int validateAndReturnInt(String dateSegment, int upperBound, String segmentName) {
        dateSegment = removeLeadingZero(dateSegment);
        ArrayList<String> validSegments = getValidList(upperBound);
        if ( ! validSegments.contains(dateSegment)) { // check input is in valid month range
            throw new IllegalArgumentException(String.format("%s range from 1 to %d. %s given.",
                    segmentName, upperBound, dateSegment));
        }
        return Integer.parseInt(dateSegment);
    }

    /**
     * Build a list of ascii integers representing months and days (for easy range checking)
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
     * @param dateSegment
     * @return String
     */
    public static String removeLeadingZero(String dateSegment) {
        if (dateSegment.startsWith("0")) {
            dateSegment = dateSegment.substring(1);
        }
        return dateSegment;
    }
}
