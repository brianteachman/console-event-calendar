/*-----------------------------------------------------------------------------
* Author: Brian Teachman
* Date:   11/26/2017
*
* View model, formats calendar for text layout.
*----------------------------------------------------------------------------*/
package eventcalendar;

import app.Service;
import app.ServiceManager;

public class View implements Service {

    public final static String EOL = "\n";
    private final int CELL_WIDTH = 10;

    private ServiceManager deps;

    String[][] events;

    private int dayCount; // for tracking days in printCalendarDay
    int eventsDayCount;
    private boolean isDonePrinting;

    public View(ServiceManager container) {
        this.deps = container;
        isDonePrinting = true;
    }

    /*----------------------------------------------------------------------------
     * Calendar View API
     *--------------------------------------------------------------------------*/

    public static void drawHeader(StringBuilder s, String glyph, int width) {
        for (int i = 0; i < width; i++) {
            s.append(glyph);
        }
        s.append(EOL);
    }

    public void drawCurrentMonth(CalendarModel c, String[][] e, StringBuilder s) {
        assert c == null;
        drawMonth(c, e, s);
    }

    // Accepts an integer representing the month and displays
    // the month as a text formatted calendar
    // EFFECTS: manages dayCount invariant
    public void drawMonth(CalendarModel c, String[][] e, StringBuilder s) {
        dayCount = 0;
        isDonePrinting = false;
        s.append("\n").append(c.getMonthName()).append("\n");
        drawRowHeader(s, "=", CELL_WIDTH);
        for (int row = 0; row < 6; row++) { // 6 weeks max
            if (isDonePrinting) continue;
            drawRow(c, e, s, row);
            drawRowHeader(s, "=", CELL_WIDTH);
        }
        displayDate(s, c.getMonth(), c.getDay());
    }

    public void displayDate(StringBuilder s, int month, int day) {
        s.append(EOL).append("Month: ").append(month)
                .append(EOL).append("Day: ").append(day).append(EOL);
    }

    /*----------------------------------------------------------------------------
     * Calendar Layout Helpers
     *--------------------------------------------------------------------------*/

    // Print one week of the calendar (one row) to the stream, including calendar days and events.
    // EFFECTS: manages isDonePrinting invariant
    private void drawRow(CalendarModel c, String[][] e, StringBuilder s, int row) {
        eventsDayCount = dayCount; // prime printDailyEvents() for each row

        for (int rowPos = 0; rowPos < cellHeight(); rowPos++) { // cell height number of rows

            s.append("|"); // start row
            for (int col = 0; col < 7; col++) { // 7 columns for 7 days

                for (int colPos = 0; colPos < CELL_WIDTH; colPos++) { // cell content

                    if (colPos == 1 && rowPos == 0) { // after one blank space on the first row

                        // increments dayCount once per cell if in correct position
                        printCalendarDay(c, s, row, col); // manages dayCount invariant
                        if (c.isCurrentDay(dayCount)) {
                            s.append("*");
                            colPos += 2; // account for double digit format + mark (*)
                        } else {
                            colPos++;   // account for double digit format
                        }
                    } else if (colPos == 1 && rowPos == 2) {

                        printDailyEvents(c, e, s, row, col);
                        colPos += 4;

                    } else if (colPos == (CELL_WIDTH - 1)) { // add cell wall
                        s.append("|");                       // end of cell
                    } else {                                 // add padding
                        s.append(" ");                       // pad cell
                    }

                    if (dayCount == c.getLastDayOfMonth()
                            && isCellComplete(c, rowPos, colPos) && !isDonePrinting) {
                        isDonePrinting = true;
//                        System.out.println("> Day "+dayCount + ": " + isDonePrinting
//                                            + "\n  x="+colPos+", y="+rowPos
//                                            + "\n  col="+col+", row="+row);
                    }
                }
            }
            s.append(EOL); // end of row
        }
    }

    private boolean isCellComplete(CalendarModel c, int rowPos, int colPos) {
        return (rowPos == cellHeight() - 1) && (colPos == CELL_WIDTH - 1);
    }

    // print calendar day to cell
    private void printCalendarDay(CalendarModel c, StringBuilder s, int row, int col) {
        if (row == 0 && col < c.getRowPositionOfMonthStart() - 1) {
            s.append("  ");
        } else if (dayCount < c.getLastDayOfMonth()) {
            s.append(++dayCount);
            if (dayCount < 10) { // pad w/space to match spacing for double digits
                s.append(" ");
            }
        } else {
            s.append("  ");
        }
//        System.out.println("> Day  "+dayCount + ": " + isDonePrinting + ", { col="+col+", row="+row + " }");
    }

    // print event data to cell
    private void printDailyEvents(CalendarModel c, String[][] e, StringBuilder s,
                                  int row, int col) {
        EventManager evm = (EventManager) deps.get("Events");

        if (cellIsInMonth(c, row, col, ++eventsDayCount)
            && evm.dayHasEvent(e, c.getMonth(), eventsDayCount)) {

            s.append("Event");

        } else {
            s.append("     ");
        }
//        System.out.println("  Evnt "+(++eventsDayCount) + ": " + isDonePrinting + ", { col="+col+", row="+row + " }");
    }

    public boolean cellIsInMonth(CalendarModel c, int row, int col, int day) {
        return (row == 0 && col >= c.getRowPositionOfMonthStart() - 1)
                || (row > 0 && day <= c.getLastDayOfMonth());
    }

    public boolean cellIsInMonth(CalendarModel c, int row, int col) {
        return (row == 0 && col >= (c.getRowPositionOfMonthStart() - 1))
                || ((row != 0 && row != 6 && c.getDay() <= c.getLastDayOfMonth()));
    }

    // Print a row divider, some given width
    private void drawRowHeader(StringBuilder s, String glyph, int width) {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < width; j++) {
                s.append(glyph);
            }
        }
        s.append(glyph).append(EOL);
    }

    // Let the row height be 1/2 * the row width, unless the cell's width
    // is less than or equal to 4, than it is 1.
    private int cellHeight() {
        int rowHieght = CELL_WIDTH/2;
        if (rowHieght < 1 || CELL_WIDTH == 4) {
            rowHieght = 1;
            // when CELL_WIDTH < 4, right cell edge collapses
        }
        return rowHieght;
    }

    /*----------------------------------------------------------------------------
     * Test Client
     *--------------------------------------------------------------------------*/

    public static void main(String[] args) {
        ServiceManager sm = new ServiceManager();
        View v = new View(sm);
//        CalendarManager c = new CalendarManager(sm);
//        sm.add("Calendar", c);
//        sm.add("Events", new EventManager());
//        sm.add("View", v);
//
//        StringBuilder s = new StringBuilder();
//        String[][] events = new String[12][31];
//
//        v.drawMonth(c.deltaMonth, events, s);
////        System.out.println(s);
//
////        s.delete(0, s.length()); // reset string
//        c.deltaMonth.nextMonth();
//        v.drawMonth(c.deltaMonth, events, s);
//        System.out.println(s);

        // simulate long month
        CalendarModel c = new CalendarModel();
//        c.setDate(12, 1, 2017);
        c.setDate(11, 1, 2017);
        int day = 0;
        for (int row = 0; row < 6; row++) {     // Six weeks max
            for (int col = 0; col < 7; col++) { // Seven days
                if ((row == 0 && col >= c.getRowPositionOfMonthStart()-1) || row > 0) {
//                    c.nextDay();
                    ++day;
                }
//                if (row > 3 && c.getDay() < 6) v.isDonePrinting = true;
                if (row > 3 && day < 6) v.isDonePrinting = true;
//                System.out.println(c.getDay() + ": " + v.cellIsInMonth(c, row, col));
                System.out.println(day + ": " + v.cellIsInMonth(c, row, col, day));
            }
        }
    }
}
