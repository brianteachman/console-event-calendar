/* ----------------------------------------------------------------------------
    Brian Teachman
    CS 140: Whatcom Community College
    10/11/2017

    Java 8 Calendar Wrapper: console calendar helper class.
 ----------------------------------------------------------------------------*/

import java.util.Calendar;
import java.util.TimeZone;

public class BtCalendar {

    public int[] monthDays = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private String[] monthName = { "January", "February", "March", "April", "May", "June", "July",
            "August", "September", "October", "November", "December" };
    public boolean dateIsSet;
    private Calendar cal;

    /*----------------------------------------------------------------------------
     * Construction
     *--------------------------------------------------------------------------*/

    // This calendar instantiated with given date
    public BtCalendar(int month, int day) {
        this.cal = startNewCalendar(month, day);
        dateIsSet = true; // explicitly set date
    }

    // This calendar instantiated with todays date
    public BtCalendar() {
        this.cal = startNewCalendar();
        dateIsSet = false; // technically it is set, however, not explicitly
//        dateIsSet = true; // however, not explicitly
    }

    // Return Calendar object representing today if month and day are null
    public Calendar startNewCalendar(Integer month, Integer day) {
        Calendar c = startNewCalendar();
        setDate(c, month, day, 2017);
        return c;
    }

    public Calendar startNewCalendar() {
        return Calendar.getInstance(TimeZone.getTimeZone("America/Vancouver"));
    }

    /*----------------------------------------------------------------------------
     * Calendar Accessors
     *--------------------------------------------------------------------------*/

    public int getDay() {
        return this.cal.get(Calendar.DAY_OF_MONTH);
    }

    public int getMonth() {
        return this.cal.get(Calendar.MONTH) + 1;
    }

    public String getMonthName() {
        return this.monthName[this.cal.get(Calendar.MONTH)];
    }

    public int getYear() {
        return this.cal.get(Calendar.YEAR);
    }

    // Returns weekday of start of the month (1..7), Sunday through Saturday
    public int getFirstWeekdayOfMonth() {
        Calendar copy = (Calendar) this.cal.clone();
        copy.set(Calendar.DAY_OF_MONTH, 1);
        return copy.get(Calendar.DAY_OF_WEEK);
    }

    public int getLastDayOfMonth() {
        return this.cal.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public boolean isDateSet() {
        return dateIsSet;
    }

    public boolean isCurrentDay(int day) {
        return day == this.getDay();
    }

    /*----------------------------------------------------------------------------
     * Calendar Mutators
     *--------------------------------------------------------------------------*/

    // set the date of the invariant java.util.Calendar
    public void setDate(int month, int day, int year) {
        this.cal.set(year, month-1, day);
        dateIsSet = true;
    }

    // Set the date of a non-invariant Calendar.
    public void setDate(Calendar c, int month, int day, int year) {
        c.set(year, month-1, day);
        dateIsSet = true;
    }

    public void setDateSetFlag(boolean flag) {
        dateIsSet = flag;
    }

    public void nextMonth() {
        this.addMonthDelta(this.cal,1);
    }

    public void previousMonth() {
        this.addMonthDelta(this.cal,-1);
    }

    private void addMonthDelta(Calendar cal, int nextMonth) {
        int thisMonth = this.getMonth() + 1; // convert from index to month number
        cal.add(Calendar.MONTH, thisMonth - (thisMonth - nextMonth));
    }

    private void zeroOutTime(Calendar cal) {
        // set the time to the start of the day 00:00:00
        cal.set(Calendar.HOUR_OF_DAY, 0); // reset hour, clear doesn't reset the hour of day
        cal.clear(Calendar.MINUTE);       // reset minutes
        cal.clear(Calendar.SECOND);       // reset seconds
        cal.clear(Calendar.MILLISECOND);  // reset millis
    }
}
