import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

//http://junit.org/junit5/docs/current/user-guide/

class CalendarHelperTests {

    CalendarHelper cal = new CalendarHelper();

    @Test
    void testCalendarHelperCanGetDate() {
        assertEquals(2, 1 + 1);
    }

    @Test
    void testFirstAndLastDayOfEachMonthIn2017() {
        cal.setCalendarDate(1, 1); // January 1st
        assertEquals(1, cal.getDayMonthStartsOn());
        assertEquals(31, cal.getLastDayofMonth());

        cal.setCalendarDate(2, 1); // February 1st
        assertEquals(4, cal.getDayMonthStartsOn());
        assertEquals(28, cal.getLastDayofMonth());
    }
}