/*-----------------------------------------------------------------------------
* Author: Brian Teachman
* Date:   11/26/2017
*
* View model, formats calendar for text layout.
*----------------------------------------------------------------------------*/
package model;

public class ViewModel {

    private final static int CELL_WIDTH = 10;
    public final static String EOL = "\n";
    private static int dayCount = 0; // for tracking days in printCalendarDay

    /*----------------------------------------------------------------------------
     * Calendar Layout
     *--------------------------------------------------------------------------*/

    public static void displayDate(StringBuilder s, int month, int day) {
        s.append(EOL).append("Month: ").append(month)
         .append(EOL).append("Day: ").append(day).append(EOL);
    }

    public static void drawHeader(StringBuilder s, String glyph, int width) {
        for (int i = 0; i < width; i++) {
            s.append(glyph);
        }
        s.append(EOL);
    }

    // Accepts an integer representing the month and displays
    // the month as a text formatted calendar
    public static void drawMonth(CalendarModel c, StringBuilder s) {
        s.append("\n").append(c.getMonthName()).append("\n");
        drawRowHeader(s, "=", CELL_WIDTH);
        for (int i = 0; i < 5; i++) { // 5 weeks
            drawRow(c, s, i);
            drawRowHeader(s, "=", CELL_WIDTH);
        }
        displayDate(s, c.getMonth(), c.getDay());
        dayCount = 0;
    }

    public static void drawCurrentMonth(CalendarModel c, StringBuilder s) {
        if (c == null) {
            c = new CalendarModel();
        }
        drawMonth(c, s);
    }

    // Print one week of the calendar (one row) to the stream
    private static void drawRow(CalendarModel c, StringBuilder s, int row) {
        for (int rowHeight = 0; rowHeight < cellHeight(); rowHeight++) { // cell height number of rows
            s.append("|"); // start row
            for (int cellNumber = 0; cellNumber < 7; cellNumber++) { // 7 cells for 7 days
                for (int k = 0; k < CELL_WIDTH; k++) { // cell content
                    if (k == 1 && rowHeight == 0) { // after one blank space on the first row

                        printCalendarDay(c, s, row, cellNumber);
                        if (c.isCurrentDay(dayCount)) {
                            s.append("*");
                            k += 2; // account for double digit format
                        }
                        else {
                            k++; // account for double digit format
                        }
                    }
                    else if (k == (CELL_WIDTH - 1)) {
                        s.append("|"); // end of cell
                    } else {
                        s.append(" "); // pad cell
                    }
                }
            }
            s.append(EOL); // end of row
        }
    }

    // Print a row divider, some given width
    private static void drawRowHeader(StringBuilder s, String glyph, int width) {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < width; j++) {
                s.append(glyph);
            }
        }
        s.append(glyph).append(EOL);
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
    private static void printCalendarDay(CalendarModel c, StringBuilder s, int rowNumber, int cellNumber) {
        int startWeekDay = c.getFirstWeekdayOfMonth();
        int daysInMonth = c.getLastDayOfMonth();

        if ((rowNumber == 0 && cellNumber < (startWeekDay - 1))
                || dayCount >= daysInMonth) {
            s.append("  ");
        }
        else if (dayCount < daysInMonth) {
            if (dayCount < 9) { // pad w/space to match spacing for double digits
                s.append(" ");
            }
            s.append(++dayCount);
        }
        else {
            s.append(" ");
        }
    }

}
