/*-----------------------------------------------------------------------------
* Author: Brian Teachman
* Date:   11/26/2017
*
* Static month and day text parsing class (formatted: MM/DD or M/DD)
*
* License: http://www.wtfpl.net/txt/copying/
*----------------------------------------------------------------------------*/
package eventcalendar;

import exceptions.InvalidDateInputException;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class DateParser {

    private static Delimiter delim;

    public DateParser() {
        delim = Delimiter.getInstance();
    }

    public static String tidyDateString(int month, int day) {
        DecimalFormat fmtr = new DecimalFormat("00"); // lazy load :)
        return String.format("%s/%s", fmtr.format(month), fmtr.format(day));
    }

    // Given a date as a String, extract an integer value for the day and return it.
    public static int dayFromDate(String formatedDate) throws InvalidDateInputException {
        String dd = formatedDate.substring(delim.getIndex(formatedDate, "/")+1);
        return validateAndReturnInt(dd, 31, "Days");
    }

    // Given a date as a String, extract an integer value for the month and return it.
    public static int monthFromDate(String formatedDate) throws InvalidDateInputException {
        String mm = formatedDate.substring(0, delim.getIndex(formatedDate, "/"));
        return validateAndReturnInt(mm, 12, "Months");
    }

    // Check date segments for valid, correctly formatted input
    private static int validateAndReturnInt(String dateSegment, int upperBound, String segmentName)
            throws InvalidDateInputException {
        dateSegment = removeLeadingZero(dateSegment);
        ArrayList<String> validSegments = getValidList(upperBound);
        if ( ! validSegments.contains(dateSegment)) { // check input is in valid month range
            throw new InvalidDateInputException(segmentName, upperBound, dateSegment);
        }
        return Integer.parseInt(dateSegment);
    }

    // Build a list of ascii integers representing months and days (for easy range checking)
    private static ArrayList<String> getValidList(int upperBound) {
        ArrayList<String> list = new ArrayList<String>();
        for (int i=1; i<=upperBound; i++) {
            list.add("".valueOf(i));
        }
        return list;
    }

    // If string segment starts with a zero, remove it
    private static String removeLeadingZero(String dateSegment) {
        if (dateSegment.startsWith("0")) {
            dateSegment = dateSegment.substring(1);
        }
        return dateSegment;
    }
}
