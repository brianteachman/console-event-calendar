import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import exceptions.InvalidDateInputException;
import model.Events;
import org.junit.jupiter.api.*;

class EventsTests {

    private static String[][] events;

//    @BeforeEach
//    void setUp() {
//        events = new String[12][31];
//    }
//
//    @AfterEach
//    void tearDown() {
//        events = null;
//    }

    /*----------------------------------------------------------------------------------
    * setEvent
    *---------------------------------------------------------------------------------*/

    @Test
    void testSetEventSetsValidDate() {
        events = new String[13][32];
        try {
            Events.setEvent(events, "6/20 hello");
        }
        catch (InvalidDateInputException e) {
            fail("Events.setEvent should not not have thrown InvalidDateInputException.");
        }
        catch (ArrayIndexOutOfBoundsException e) {
            fail("Events.setEvent should not not have thrown ArrayIndexOutOfBoundsException.");
        }
        assertEquals("hello", events[6][20]);
    }

    @Test
    void testSetEventDayUpperBoundCollision() {
        events = new String[13][32];
        boolean exceptionThrown = false;
        try {
            Events.setEvent(events, "12/32 hello");
        }
        catch (InvalidDateInputException e) {
            exceptionThrown = true;
        }
        catch (ArrayIndexOutOfBoundsException e) {
            fail("Events.setEvent should not not have thrown ArrayIndexOutOfBoundsException.");
        }
        assertTrue(exceptionThrown);
    }

    @Test
    void testSetEventDayLowerBoundCollision() {
        events = new String[13][32];
        boolean exceptionThrown = false;
        try {
            Events.setEvent(events, "1/0 hello");
        }
        catch (InvalidDateInputException e) {
            exceptionThrown = true;
        }
        catch (ArrayIndexOutOfBoundsException e) {
            fail("Events.setEvent should not not have thrown ArrayIndexOutOfBoundsException.");
        }
        assertTrue(exceptionThrown);
    }

    @Test
    void testSetEventUpperBoundary() {
        events = new String[13][32];
        try {
            Events.setEvent(events, "12/31 Test_upper");
        }
        catch (InvalidDateInputException e) {
            fail("Events.setEvent should not not have thrown InvalidDateInputException.");
        }
        catch (ArrayIndexOutOfBoundsException e) {
            fail("Events.setEvent should not not have thrown ArrayIndexOutOfBoundsException.");
        }
        assertEquals("Test_upper", events[12][31]);
    }

    @Test
    void testSetEventMonthUpperBoundCollision() {
        events = new String[13][32];
        boolean exceptionThrown = false;
        try {
            Events.setEvent(events, "13/31 hello");
        }
        catch (InvalidDateInputException e) {
            exceptionThrown = true;
        }
        catch (ArrayIndexOutOfBoundsException e) {
            fail("Events.setEvent should not not have thrown ArrayIndexOutOfBoundsException.");
        }
        assertTrue(exceptionThrown);
    }

    @Test
    void testSetEventMonthLowerBoundCollision() {
        events = new String[13][32];
        boolean exceptionThrown = false;
        try {
            Events.setEvent(events, "1/0 hello");
        }
        catch (InvalidDateInputException e) {
            exceptionThrown = true;
        }
        catch (ArrayIndexOutOfBoundsException e) {
            fail("Events.setEvent should not not have thrown ArrayIndexOutOfBoundsException.");
        }
        assertTrue(exceptionThrown);
    }

    /*----------------------------------------------------------------------------------
    * getEvent /-> parse
    *---------------------------------------------------------------------------------*/

    @Test
    void testGetEvent() {
        events = new String[13][32];
        Events.setEvent(events, "6/20 Hello_world");

        assertEquals("Hello world", Events.getEvent(events, 6, 20));
    }

}
