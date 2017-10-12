import java.util.Calendar;

/*
    Brian Teachman
    CS 140: Whatcom Community College
    10/2/2017

    Prints a text formatted calendar to the console.
*/
public class CalendarHelper {

    public void getTodaysDate() {
//        Calendar cal = Calendar.getInstance();
//        month = cal.get(Calendar.MONTH); // indexed month (Jan == 0)
//        day = cal.get(Calendar.DAY_OF_MONTH);
//        weekday = cal.get(Calendar.DAY_OF_WEEK);

        // https://stackoverflow.com/a/2938209/503781

        // get today and clear time of day
        Calendar cal = Calendar.getInstance();
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

    /*
    * Accepts an integer representing the month and displays
    * the month as a text formatted calendar
    *
    * @param int month
    * @return void
    * @exit Prints month to stream
    */
    public void drawMonth(int month) {
        // uses drawRow()
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

    /*
    * Display the date information as a graphical representation of the calendar.
    *
    * @param int month
    * @param int day
    */
    public void displayDate(int month, int day) {

    }

    /*
    * Given a date as a String, extract an integer value for the month and return it.
    *
    * the indexOf and substring methods may be helpful with this
    */
    public void monthFromDate(String date) {

    }

    /*
    * Given a date as a String, extract an integer value for the day and return it.
    */
    public void dayFromDate(String date) {
        // the indexOf and substring methods may be helpful with this
    }
}
