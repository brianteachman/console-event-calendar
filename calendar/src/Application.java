/* ----------------------------------------------------------------------------
    Brian Teachman
    CS 140: Whatcom Community College
    Created: 10/11/2017
    Updated: 11/17/2017

    A text based console calendar, displays a given month in text using
    java.util.Calendar
 ----------------------------------------------------------------------------*/

import java.io.File;
import java.util.*;

public class Application {

    private final static int CELL_WIDTH = 5;
    private static int dayCount = 0; // for tracking days in printCalendarDay

    /*----------------------------------------------------------------------------
     * Application objects
     *--------------------------------------------------------------------------*/

    private static class Delimiter {
        private static String lastDateIn;
        private static Integer delimiterIndex;

        // Singleton pattern
        private static Delimiter instance = new Delimiter();
        private Delimiter() { /* locked */ }
        public static Delimiter getInstance() {
            return instance;
        }

        public static int getIndex(String formattedDate) {
            if (delimiterIndex == null || !lastDateIn.equals(formattedDate)) { // only iterate through the string once
                setIndex(formattedDate);
                lastDateIn = formattedDate;
            }
            return delimiterIndex;
        }

        public static void setIndex(String formattedDate) {
            delimiterIndex = formattedDate.indexOf("/");
            if ( ! (delimiterIndex == 1 || delimiterIndex == 2)) { // accounts for '1' or '01' formatted case
                throw new IllegalArgumentException("Expected the format 'mm/dd', where mm is the month and dd is the day.");
            }
        }
    }

    private static class BtCalendar {

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

    /*----------------------------------------------------------------------------
     * Calendar Layout
     *--------------------------------------------------------------------------*/

    public static void displayDate(int month, int day) {
        println("\nMonth: " + month);
        println("Day:   " + day);
    }

    // Accepts an integer representing the month and displays
    // the month as a text formatted calendar
    public static void drawMonth(BtCalendar c) {
        println("\n"+c.getMonthName());
        drawRowHeader("=", CELL_WIDTH);
        for (int i = 0; i < 5; i++) { // 5 weeks
            drawRow(c, i);
            drawRowHeader("=", CELL_WIDTH);
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

    private static void drawCurrentMonth(BtCalendar c) {
        if (c == null) {
            c = new BtCalendar();
        }
        drawMonth(c);
    }

    // Print a row divider, some given width
    private static void drawRowHeader(String glyph, int width) {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < width; j++) {
                print(glyph);
            }
        }
        println(glyph);
    }

    private static void drawHeader(String glyph, int width) {
        for (int i = 0; i < width; i++) {
            print(glyph);
        }
        println(glyph);
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

    /*----------------------------------------------------------------------------
     * Creative requirement
     *--------------------------------------------------------------------------*/

    private static void drawASCIIart() {
        StringBuilder s = new StringBuilder();
        Scanner in = null;
        try {
            in = new Scanner(new File("src/header_graphic.txt"));
            while(in.hasNextLine()) {
                s.append(in.nextLine()).append("\n");
            }
        }
        catch (Exception e) { /* keep quiet */ }
        println(s.toString());
    }

    private static void drawASCIIBanner() {
        String content = getDrQuote();
        println();
        drawHeader("-", content.length()+20);
        println(getDrQuote());
        drawHeader("-", content.length()+20);
    }

    private static String getDrQuote() {
        String[] quotes = {
            "\"Great men are forged in fire. It is the privilege of lesser men to light the flame.\" - The War Doctor",
            "\"As we learn about each other, so we learn about ourselves.\" - The First Doctor",
            "\"Logic merely enables one to be wrong with authority.\" - The Second Doctor",
            "\"I reversed the polarity of the neutron flow.\" - The Third Doctor",
            "\"Answers are easy. It's asking the right questions which is hard.\" - The Fourth Doctor",
            "\"Yes. Still, the future lies this way.\" - The Fifth Doctor",
            "\"What's the use of a good quotation if you can't change it?\" - The Sixth Doctor",
            "\"All is change, all is movement\" - The Seventh Doctor",
            "\"I love humans. Always seeing patterns in things that aren’t there.\" - The Eigth Doctor",
            "\"I love a happy medium!\" - The Ninth Doctor",
            "\"Allons-y!\" - The Tenth Doctor",
            "\"I have a thing. It's like a plan, but with more greatness.\" - The Eleventh Doctor",
            "\"Sometimes the only choices you have are bad ones, but you still have to choose.\" - The Twelfth Doctor"
        };
        return quotes[new Random().nextInt(quotes.length)];
    }

    /*----------------------------------------------------------------------------
     * UI
     *--------------------------------------------------------------------------*/

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
        Delimiter delim = Delimiter.getInstance();
        String formattedDate = promptForDate(input); // in-line processing
        try {
            int delimiterIndex = delim.getIndex(formattedDate);
//            int month = Integer.valueOf(formattedDate.substring(0, delimiterIndex));
            int month = monthFromDate(formattedDate, delimiterIndex);
//            int day = Integer.valueOf(formattedDate.substring(delimiterIndex+1));
            int day = dayFromDate(formattedDate, delimiterIndex);
            println(month + "/" + day);
            c.setDate(month, day, 2017);
        }
        catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            setDateFromPrompt(c, input); // fail gracefully (recursive reprompt)
        }
    }

    // Given a date as a String, extract an integer value for the day and return it.
    private static int dayFromDate(String formatedDate, int delimiter) throws IllegalArgumentException {
        String dd = formatedDate.substring(delimiter+1);
        return validateAndReturnInt(dd, 31, "Days");
    }

    // Given a date as a String, extract an integer value for the month and return it.
    private static int monthFromDate(String formatedDate, int delimiter) throws IllegalArgumentException {
        String mm = formatedDate.substring(0, delimiter);
        return validateAndReturnInt(mm, 12, "Months");
    }

    // Check date segments for valid, correctly formatted input
    private static int validateAndReturnInt(String dateSegment, int upperBound, String segmentName)
            throws IllegalArgumentException {
        dateSegment = removeLeadingZero(dateSegment);
        ArrayList<String> validSegments = getValidList(upperBound);
        if ( ! validSegments.contains(dateSegment)) { // check input is in valid month range
            throw new IllegalArgumentException(String.format("\n%s range from 1 to %d. %s given.\n",
                    segmentName, upperBound, dateSegment));
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

    /*----------------------------------------------------------------------
    * Calendar client
    ----------------------------------------------------------------------*/
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in); // user prompt
        BtCalendar delta = new BtCalendar();
        BtCalendar current = null;

        println("\nWelcome to my Doctor who themed calendar.");
        drawASCIIart();

        boolean running = true;
        while (running) {
            switch (menu(input)) {
                case 'e':
                    setDateFromPrompt(delta, input);
                    drawASCIIBanner();
                    drawMonth(delta);
                    drawCurrentMonth(current);
                    break;

                case 't':
                    drawASCIIBanner();
                    drawCurrentMonth(current);
                    delta.setDateSetFlag(true);
                    break;

                case 'n':
                    if (delta.isDateSet()) {
                        delta.nextMonth();
                        drawASCIIBanner();
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
                        drawASCIIBanner();
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

    /*----------------------------------------------------------------------------
     * Print Helpers
     *--------------------------------------------------------------------------*/

    private static void print(String thing) {
        System.out.print(thing);
    }

    private static void println(String thing) {
        System.out.println(thing);
    }

    private static void print(int num) {
        System.out.print(num);
    }

    private static void println(int num) {
        System.out.println(num);
    }

    private static void println() {
        System.out.println();
    }
}
