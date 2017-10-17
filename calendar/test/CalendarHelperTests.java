import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//http://junit.org/junit5/docs/current/user-guide/

class CalendarHelperTests {

    CalendarHelper cal = new CalendarHelper();

    @Test
    void testValidateAndReturnIntBoundaries() {
        assertThrows(IllegalArgumentException.class, () -> {
            cal.validateAndReturnInt("13", 35, "days");
//            throw new IllegalArgumentException("oops");
        });
    }

    @Test
    void testFirstAndLastDayOfEachMonthIn2017() {
        cal.setCalendarDate(1, 1); // January 1st
        assertEquals(1, cal.getDayMonthStartsOn()); // Sunday = 1
        assertEquals(31, cal.getLastDayofMonth());

        cal.setCalendarDate(2, 1); // February 1st
        assertEquals(4, cal.getDayMonthStartsOn()); // Wednesday = 4
        assertEquals(28, cal.getLastDayofMonth());
    }

    @Test
    void testRemoveLeadingZero() {
        assertEquals("1", cal.removeLeadingZero("01"));
        assertEquals("9", cal.removeLeadingZero("09"));
        assertEquals("10", cal.removeLeadingZero("10"));
    }

    @Test
    void testGetValdListReturnsProperBoundedList() {
        // test months element values 1-12
        List expected = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12");
        ArrayList list = cal.getValidList(12);
        assertEquals(12, list.size());
        assertEquals(0, list.indexOf("1"));
        assertEquals(11, list.indexOf("".valueOf(list.size())));
        assertEquals(expected, list);
        // test days element values 1-31
        list = cal.getValidList(31);
        assertEquals(31, list.size());
        assertEquals(0, list.indexOf("1"));
        assertEquals(30, list.indexOf("".valueOf(list.size())));
        assertNotEquals(expected, list);
    }
}