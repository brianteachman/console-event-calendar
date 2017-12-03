package eventcalendar;/*---------------------------------------------------------------------
 * Author: Brian Teachman
 * Date: 12/2/2017
 * 
 * License: http://www.wtfpl.net/txt/copying/
 *-------------------------------------------------------------------*/

import app.Service;
import exceptions.EventsFileNotFound;
import exceptions.InvalidDateInputException;

import java.io.FileNotFoundException;

public class CalendarController extends Service {

    public final CalendarModel thisMonth;
    public CalendarModel deltaMonth;
    private boolean isThisMonth;

    // reference to events array
    private String[][] events;

    public CalendarController(String[][] events/*, PrintStream logger*/) {
        super();
        deltaMonth = new CalendarModel();
        thisMonth = new CalendarModel();
        isThisMonth = true; // used for calendar output

//        events = new String[13][32];
        this.events = events;
//        logger.println("model.CalendarController");
    }

    /*----------------------------------------------------------------------
    * Thin calendar wrapper to enforce Demeter's Law in AppController
    ----------------------------------------------------------------------*/

    public void setDate(int month, int day, int year) {
        if (month < 0) throw new InvalidDateInputException("month", 12, "-1");
        deltaMonth.setDate(month, day, year);
        isThisMonth = false;
    }

    public String getMonthName() {
        if (isThisMonth) return thisMonth.getMonthName();
        return deltaMonth.getMonthName();
    }

    public void setThisMonth(boolean is) {
        isThisMonth = is;
    }

    public void setDateSetFlag(boolean isDateSet) {
        deltaMonth.setDateSetFlag(isDateSet);
    }

    public void nextMonth() {
        deltaMonth.nextMonth();
    }

    public void previousMonth() {
        deltaMonth.previousMonth();
    }

    public boolean isDateSet() {
        return deltaMonth.isDateSet();
    }

    public String[] getMonthNames() {
        return thisMonth.getMonthNames();
    }

    /*----------------------------------------------------------------------
    * Manage events
    ----------------------------------------------------------------------*/

    public void loadEvents(String[][] events, String filename) throws EventsFileNotFound {
        Events.loadEventFile(events, "data/" + filename);
    }

    public void loadEvents(String[][] events) {
        try {
            Events.loadEventFile(events,"data/calendarEvents.txt");
        }
        catch (EventsFileNotFound e) {}
    }

    public void addEvent(String[][] events, String event) {
        Events.setEvent(events, event);
    }

    public String[][] getEvents() {
        return events;
    }

    public int[] getDateFromEventString(String event) {
        int[] date = new int[2];
        String[] part = event.split("\\s+");
        date[0] = DateParser.monthFromDate(part[0]);
        date[1] = DateParser.dayFromDate(part[0]);
        return date;
    }
}
