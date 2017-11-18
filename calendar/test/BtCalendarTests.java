/* ----------------------------------------------------------------------------
    Brian Teachman
    CS 140: Whatcom Community College
    Created: 10/11/2017
    Updated: 11/17/2017

    BtCalendar Test Cases
 ----------------------------------------------------------------------------*/

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class BtCalendarTests {

    // TODO: 1.

    @Test
    void testConstructCalendarWithNoParams() {
        try {
            BtCalendar cm = new BtCalendar();
        }
        catch (Exception e) {
            assertEquals(true, "Should have worked.");
        }
    }

    @Test
    void testConstructCalendarWithParams() {
        try {
            BtCalendar cm = new BtCalendar(11, 15);
        }
        catch (Exception e) {
            assertEquals(true, "Should have worked.");
        }
    }

    @Test
    void testConstructedCalendarHasCorrectDate() {
        BtCalendar cm = new BtCalendar(11, 15);
        assertEquals(11, cm.getMonth());
        assertEquals(15, cm.getDay());
    }

    @Test
    void testNextMonthIncrementsOneMonth() {
        BtCalendar cm = new BtCalendar(1, 25);
        cm.nextMonth();
        assertEquals(2, cm.getMonth());
    }

    @Test
    void testPreviousMonthFromJanuarySetsDecemberOfPreviousYear() {
        BtCalendar cm = new BtCalendar(1, 25);
        cm.previousMonth();
        assertEquals(12, cm.getMonth());
        assertEquals(2016, cm.getYear());
    }

    @Test
    void testNextMonthFromDecemberSetsJanuaryOfNextYear() {
        BtCalendar cm = new BtCalendar(12, 25);
        cm.nextMonth();
        assertEquals(1, cm.getMonth());
        assertEquals(2018, cm.getYear());
    }

    @Test
    void testGetDayMonthStartsOn() {
        BtCalendar cm = new BtCalendar(11, 25);
        assertEquals(4, cm.getFirstWeekdayOfMonth());
    }

    @Test
    void testGetDayMonthStartsOnDoesntChangeCalendarDay() {
        BtCalendar cm = new BtCalendar(11, 25);
        cm.getFirstWeekdayOfMonth();
        assertEquals(25, cm.getDay());
    }

    @Test
    void testCanGetWeekdayOfBeginningOfMonth() {
        BtCalendar cm = new BtCalendar(11, 25);
        assertEquals(4, cm.getFirstWeekdayOfMonth());
    }

}
