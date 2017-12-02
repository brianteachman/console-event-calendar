package model;/*-----------------------------------------------------------------------------
* Author: Brian Teachman
* Date:   11/26/2017
*
* Singleton delimiter class for parsing formatted date text.
*----------------------------------------------------------------------------*/

public class Delimiter {
    private static String lastDateIn;
    private static Integer delimiterIndex;

    // Singleton pattern
    private static Delimiter instance = new Delimiter();
    private Delimiter() { /* locked */ }
    public static Delimiter getInstance() {
        return instance;
    }

    public static int getIndex(String formattedDate, String delimiter) {
        if (delimiterIndex == null || !lastDateIn.equals(formattedDate)) { // only iterate through the string once
            setIndex(formattedDate, delimiter);
            lastDateIn = formattedDate;
        }
        return delimiterIndex;
    }

    public static void setIndex(String formattedDate, String delimiter) {
        delimiterIndex = formattedDate.indexOf(delimiter);
        if ( ! (delimiterIndex == 1 || delimiterIndex == 2)) { // accounts for '1' or '01' formatted case
            throw new IllegalArgumentException("Expected the format 'mm/dd', where mm is the month and dd is the day.");
        }
    }
}