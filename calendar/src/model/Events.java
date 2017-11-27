/*---------------------------------------------------------------------
 * Author: Brian Teachman
 * Date: 11/27/2017
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
        int month = DateParser.monthFromDate(part[0]);
        int day = DateParser.dayFromDate(part[0]);
        events[month-1][day-1] = part[1];
    }
    // c. Display events from array in correct cell (day) of displayed calendar.

    // TASK 2
    // File Reading: if event file exists, load events into current calendar.
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
        catch (FileNotFoundException e) {
            //
        }
    }
}
