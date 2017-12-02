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
package model;

import exceptions.InvalidDateInputException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Events {

    // TASK 1
    // model.Event Planning: when "ev" is entered, start a new event planning action:
    // a. Prompt user for an event in the form of "MM/DD event_title".
    // b. Parse and store event in global array, events[12][31].
    public static void setEvent(String[][] events, String event) throws InvalidDateInputException {
        String[] part = event.split("\\s+"); // any whitespace
        if (part.length > 1) {
            int month = DateParser.monthFromDate(part[0]);
            int day = DateParser.dayFromDate(part[0]);
//            events[month][day] = part[1];
            events[month][day] = DateParser.tidyDateString(month, day) + " " + part[1];
        }
    }

    // c. Display events from array in correct cell (day) of displayed calendar.
    public static String getEvent(String[][] events, int month, int day) {
//        return parse(events[month][day]);
        return parseEvent(events[month][day]);
    }

    // TASK 2
    // File Reading: if event file exists, load events into thisMonth calendar.
    // a. If found, load a file named "calendarEvents.txt".
    // b. Add loaded events from file into the events array by date.
    // c. Events are processed in task 1.c.
    // Extra credit: open a given filename and handle errors on fail.
    public static void loadEventFile(String[][] events, String filename) {
        try {
            Scanner file = new Scanner(new File(filename));
            while (file.hasNextLine()) {
                setEvent(events, file.nextLine());
            }
        }
        catch (FileNotFoundException e) {}
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
                    output.append(Events.parse(day)).append(ViewModel.EOL);
                }
            }
        }
    }

    public static boolean dayHasEvent(CalendarModel c, String[][] events, int dayOfMonth) {
        return events[c.getMonth()][c.getDay()] != null;
    }

    public static void getEventsForMonth(String[][] events, int month, StringBuilder output) {
        for (String day : events[month]) {
            if (day != null) {
                output.append(Events.parse(day)).append(ViewModel.EOL);
            }
        }
    }
}
