import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import app.ServiceManager;
import eventcalendar.CalendarModel;
import eventcalendar.EventManager;
import eventcalendar.View;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ViewTests {

    private View view;

    @BeforeEach
    void setUp() {
        ServiceManager sm = new ServiceManager();
        sm.add("ev", new EventManager());
        view = new View(sm);
    }

    @Test
    void testcellIsInMonth() {
        assertEquals(true, view.cellIsInMonth(new CalendarModel(), 0, 6, 2));
    }

    @Test
    void testcellIsNotInMonth() {
        assertEquals(false, view.cellIsInMonth(new CalendarModel(), 0, 4, 0));
    }

    @Test
    void testcellIsInNextMonth() {
        CalendarModel cal = new CalendarModel();
        assertEquals(6, cal.getDay());
        cal.nextMonth();
        assertTrue(view.cellIsInMonth(cal, 0, 4, 4));
    }

    @Test
    void testcellInNextMonthFails() {
        CalendarModel cal = new CalendarModel();
//        assertEquals(6, cal.getDay());
//        cal.nextMonth();
        assertFalse(view.cellIsInMonth(cal, 6, 1, 1));
    }
}
