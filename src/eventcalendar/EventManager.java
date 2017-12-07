/*---------------------------------------------------------------------
 * Author: Brian Teachman
 * Date: 11/27/2017
 *
 * Manages a 2-dimensional array, events[month][day], that represents
 * calendar events.
 *
 * One event per day, per spec.
 *
 * License: http://www.wtfpl.net/txt/copying/
 *-------------------------------------------------------------------*/
package eventcalendar;

import app.Service;
import exceptions.EventsFileNotFound;
import exceptions.InvalidDateInputException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class EventManager implements Service {

    public EventManager() {
    }

    /*------------------------------------------------------------------
    * Private array mutators
    ------------------------------------------------------------------*/

    private void setEvent(String[][] events, String event) throws InvalidDateInputException {
        String[] part = event.split("\\s+"); // any whitespace
        if (part.length > 1) {
            int month = DateParser.monthFromDate(part[0]);
            int day = DateParser.dayFromDate(part[0]);
            events[month][day] = DateParser.tidyDateString(month, day) + " " + part[1];
        }
    }

    private void loadEventFile(String[][] events, String filename) throws EventsFileNotFound {
        try {
            Scanner file = new Scanner(new File(filename));
            while (file.hasNextLine()) {
                setEvent(events, file.nextLine());
            }
        } catch (FileNotFoundException e) {
            throw new EventsFileNotFound();
        }
    }

    /*----------------------------------------------------------------------
    * Event Manager API
    ----------------------------------------------------------------------*/

    public void loadEvents(String[][] events, String filename) throws EventsFileNotFound {
        loadEventFile(events, "data/" + filename);
    }

    public void loadEvents(String[][] events) {
        try {
            loadEventFile(events,"data/calendarEvents.txt");
        }
        catch (EventsFileNotFound e) {/* log it! */}
    }

    public void addEvent(String[][] events, String event) {
        setEvent(events, event);
    }

    public boolean dayHasEvent(String[][] events, int month, int day) {
        return events[month][day] != null;
    }

    public String[] getEventsForMonth(String[][] events, int month) {
        return events[month];
    }

    public String getEvent(String[][] events, int month, int day) {
//        return parse(events[month][day]);
        return parseEvent(events[month][day]);
    }

    public void listAllEvents(String[][] events, StringBuilder output) {
        for (String[] month : events) {
            for (String day : month) {
                if (day != null) {
                    output.append(parse(day)).append(View.EOL);
                }
            }
        }
    }

    public void listEventsForMonth(String[][] events, int month, StringBuilder output) {
        for (String day : events[month]) {
            if (day != null) {
                output.append(parse(day)).append(View.EOL);
            }
        }
    }

    /*------------------------------------------------------------------
    * Event helpers
    ------------------------------------------------------------------*/

    public int[] getDateFromEventString(String event) {
        int[] date = new int[2];
        String[] part = event.split("\\s+");
        date[0] = DateParser.monthFromDate(part[0]);
        date[1] = DateParser.dayFromDate(part[0]);
        return date;
    }

    public String parse(String str) {
        return str.replaceAll("_", " ");
    }

    public String parseEvent(String str) {
        return str.substring(str.indexOf(" ")+1, str.length())
                .replaceAll("_", " ");
    }
}
