/* ----------------------------------------------------------------------------
    Brian Teachman
    CS 140: Whatcom Community College
    Created: 10/11/2017
    Updated: 11/17/2017

    BtCalendar Test Cases
 ----------------------------------------------------------------------------*/

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class TextCalendarTests {

    @Test
    void testConstructCalendarWithNoParams() {
        try {
            TextCalendar.BtCalendar cm = new TextCalendar.BtCalendar();
        }
        catch (Exception e) {
            assertEquals(true, "Should have worked.");
        }
    }

    @Test
    void testConstructCalendarWithParams() {
        try {
            TextCalendar.BtCalendar cm = new TextCalendar.BtCalendar(11, 15);
        }
        catch (Exception e) {
            assertEquals(true, "Should have worked.");
        }
    }

    @Test
    void testConstructedCalendarHasCorrectDate() {
        TextCalendar.BtCalendar cm = new TextCalendar.BtCalendar(11, 15);
        assertEquals(11, cm.getMonth());
        assertEquals(15, cm.getDay());
    }

    @Test
    void testNextMonthIncrementsOneMonth() {
        TextCalendar.BtCalendar cm = new TextCalendar.BtCalendar(1, 25);
        cm.nextMonth();
        assertEquals(2, cm.getMonth());
    }

    @Test
    void testPreviousMonthFromJanuarySetsDecemberOfPreviousYear() {
        TextCalendar.BtCalendar cm = new TextCalendar.BtCalendar(1, 25);
        cm.previousMonth();
        assertEquals(12, cm.getMonth());
        assertEquals(2016, cm.getYear());
    }

    @Test
    void testNextMonthFromDecemberSetsJanuaryOfNextYear() {
        TextCalendar.BtCalendar cm = new TextCalendar.BtCalendar(12, 25);
        cm.nextMonth();
        assertEquals(1, cm.getMonth());
        assertEquals(2018, cm.getYear());
    }

    @Test
    void testGetDayMonthStartsOn() {
        TextCalendar.BtCalendar cm = new TextCalendar.BtCalendar(11, 25);
        assertEquals(4, cm.getFirstWeekdayOfMonth());
    }

    @Test
    void testGetDayMonthStartsOnDoesntChangeCalendarDay() {
        TextCalendar.BtCalendar cm = new TextCalendar.BtCalendar(11, 25);
        cm.getFirstWeekdayOfMonth();
        assertEquals(25, cm.getDay());
    }

    @Test
    void testCanGetWeekdayOfBeginningOfMonth() {
        TextCalendar.BtCalendar cm = new TextCalendar.BtCalendar(11, 25);
        assertEquals(4, cm.getFirstWeekdayOfMonth());
    }

}
