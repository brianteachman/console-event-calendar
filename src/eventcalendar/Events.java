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

import exceptions.EventsFileNotFound;
import exceptions.InvalidDateInputException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Events {

    // model.Event Planning: when "ev" is entered, start a new event planning action:
    // a. Prompt user for an event in the form of "MM/DD event_title".
    // b. Parse and store event in global array, events[12][31].
    public static void setEvent(String[][] events, String event) throws InvalidDateInputException {
        String[] part = event.split("\\s+"); // any whitespace
        if (part.length > 1) {
            int month = DateParser.monthFromDate(part[0]);
            int day = DateParser.dayFromDate(part[0]);
            events[month][day] = DateParser.tidyDateString(month, day) + " " + part[1];
        }
    }

    public static String getEvent(String[][] events, int month, int day) {
//        return parse(events[month][day]);
        return parseEvent(events[month][day]);
    }

    public static void loadEventFile(String[][] events, String filename) throws EventsFileNotFound {
        try {
            Scanner file = new Scanner(new File(filename));
            while (file.hasNextLine()) {
                setEvent(events, file.nextLine());
            }
        } catch (FileNotFoundException e) {
            throw new EventsFileNotFound();
        }
    }

    public static String parse(String str) {
        return str.replaceAll("_", " ");
    }

    public static String parseEvent(String str) {
        return str.substring(str.indexOf(" ")+1, str.length())
                  .replaceAll("_", " ");
    }

    public static void listAll(String[][] events, StringBuilder output) {
        for (String[] month : events) {
            for (String day : month) {
                if (day != null) {
                    output.append(Events.parse(day)).append(View.EOL);
                }
            }
        }
    }

    public static boolean dayHasEvent(CalendarModel c, String[][] events, int dayOfMonth) {
        return events[c.getMonth()][c.getDay()] != null;
//        return events[c.getMonth()][dayOfMonth] != null;
}

    public static void getEventsForMonth(String[][] events, int month, StringBuilder output) {
        for (String day : events[month]) {
            if (day != null) {
                output.append(Events.parse(day)).append(View.EOL);
            }
        }
    }
}
