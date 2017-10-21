import com.sun.javaws.exceptions.InvalidArgumentException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//http://junit.org/junit5/docs/current/user-guide/

class CalendarHelperTests {

    CalendarHelper cal = new CalendarHelper();

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

    @Test
    void testGetValdListFailsCorrectly() {
        // test months element values 1-12
        ArrayList list = cal.getValidList(12);
        assertFalse(list.contains("13"));
    }

    @Test
    void testValidateAndReturnIntThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            cal.validateAndReturnInt("13", 12, "months");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            cal.validateAndReturnInt("32", 31, "days");
        });
    }

    @Test
    void testDayFromDateThrowsException() throws InvalidArgumentException {
        assertEquals(31, cal.dayFromDate("12/31"));
        assertThrows(IllegalArgumentException.class, () -> {
            cal.dayFromDate("1/32");
        });
    }

    @Test
    void testMonthFromDateThrowsException() throws InvalidArgumentException {
        assertEquals(12, cal.monthFromDate("12/25"));
        assertThrows(IllegalArgumentException.class, () -> {
            cal.monthFromDate("13/25");
        });
    }

    @Test
    void testFirstAndLastDayOfEachMonthIn2017() {
        int[] monthStarts = {1, 4, 4, 7, 2, 5, 7, 3, 6, 1, 4, 6};
        for (int i=0; i < cal.monthDays.length; i++) {
            cal.setCalendarDate(i+1, 1);
            assertEquals(monthStarts[i], cal.getDayMonthStartsOn());
            assertEquals( cal.monthDays[i], cal.getLastDayofMonth());
        }
    }

    @Test
    void testRemoveLeadingZero() {
        assertEquals("1", cal.removeLeadingZero("01"));
        assertEquals("9", cal.removeLeadingZero("09"));
        assertEquals("10", cal.removeLeadingZero("10"));
    }
}