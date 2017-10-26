/*
    Brian Teachman
    CS 140: Whatcom Community College
    10/11/2017

    Java 8 Calendar Wrapper: console calendar helper class.
*/
import com.sun.javaws.exceptions.InvalidArgumentException;

import java.util.ArrayList;
import java.util.Calendar;

public class CalendarHelper {

    public int[] monthDays = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    private Calendar cal;

    private String formattedDate;
    private Integer delimiterIndex; // using Integer for nullability

    CalendarHelper() {
        this.cal = Calendar.getInstance();
    }

    public Calendar getCalendar() {
        return this.cal;
    }

    /**
     *
     * @param month
     * @param day
     * @return void
     */
    public void setCalendarDate(int month, int day) {

        this.cal.set(Calendar.DAY_OF_MONTH, day); // set day

        // set the time to the start of the day 00:00:00
        this.cal.set(Calendar.HOUR_OF_DAY, 0); // reset hour, clear doesn't reset the hour of day
        this.cal.clear(Calendar.MINUTE);       // reset minutes
        this.cal.clear(Calendar.SECOND);       // reset seconds
        this.cal.clear(Calendar.MILLISECOND);  // reset millis

        int thisMonth = this.cal.get(Calendar.MONTH); // set the month
        this.cal.add(Calendar.MONTH, (month-1) - thisMonth);
    }

    /**
     * Returns the day number the month starts on, where Sunday is 1 and Sat is 7
     *
     * @return int
     */
    public int getDayMonthStartsOn() {
        return this.cal.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * Returns the last calendar day of the month.
     *
     * The return value is in the range of 28 and 31
     *
     * @return int
     */
    public int getLastDayofMonth() {
        return this.cal.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /*
     * Display the date information as a graphical representation of the calendar.
     *
     * @param int month
     * @param int day
     * @return void
     */
    public void displayDate(int month, int day) {
        System.out.printf("%d/%s/%s",
                this.cal.get(Calendar.MONTH)+1,
                this.cal.get(Calendar.DAY_OF_MONTH), "2017");
    }

    public int getDelimiterIndex(String formattedDate) {
        if (delimiterIndex == null || !this.formattedDate.equals(formattedDate)) { // only iterate through the string once
            setDelimiterIndex(formattedDate);
            this.formattedDate = formattedDate;
        }
        return this.delimiterIndex;
    }

    public void setDelimiterIndex(String formattedDate) {
        delimiterIndex = formattedDate.indexOf("/");
        // TODO: considering moving date formatting validation here?
//        if ( ! (delimiterIndex == 1 || delimiterIndex == 2)) { // accounts for '1' or '01' formatted case
//            throw new IllegalArgumentException("Expected the format 'mm/dd', where mm is the month and dd is the day.");
//        }
    }

    /*
     * Given a date as a String, extract an integer value for the day and return it.
     *
     * @param String
     * @return int
     */
    public int dayFromDate(String formatedDate) throws IllegalArgumentException {
        String dd = formatedDate.substring(getDelimiterIndex(formatedDate)+1);
        return validateAndReturnInt(dd, 31, "Days");
    }

    /*
     * Given a date as a String, extract an integer value for the month and return it.
     *
     * @param String
     * @return int
     */
    public int monthFromDate(String formatedDate) throws IllegalArgumentException {
        String mm = formatedDate.substring(0, getDelimiterIndex(formatedDate));
        return validateAndReturnInt(mm, 12, "Months");
    }

    /**
     * Check date segments for valid, correctly formatted input
     *
     * @param dateSegment
     * @param upperBound
     * @param segmentName
     * @return int
     */
    public int validateAndReturnInt(String dateSegment, int upperBound, String segmentName)
            throws IllegalArgumentException {
        dateSegment = removeLeadingZero(dateSegment);
        ArrayList<String> validSegments = getValidList(upperBound);
        if ( ! validSegments.contains(dateSegment)) { // check input is in valid month range
            throw new IllegalArgumentException(String.format("\n%s range from 1 to %d. %s given.\n",
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
