/*---------------------------------------------------------------------
 * Author: Brian Teachman
 * Date: 11/27/2017
 * 
 * License: http://www.wtfpl.net/txt/copying/
 *-------------------------------------------------------------------*/
package model;

import exceptions.InvalidDateInputException;

import java.util.HashMap;
import java.util.Scanner;

public class AppController {

    public final CalendarModel thisMonth;
    private boolean isThisMonth;

    public CalendarModel deltaMonth;

    // Whitelist of controller actions
    private HashMap<String, CommandStrategy> commands;

    // reference to events array
    private String[][] events;

    public AppController() {

        commands = new HashMap<String, CommandStrategy>();

        // Working calendars
        deltaMonth = new CalendarModel();
        thisMonth = new CalendarModel();
        isThisMonth = true; // used for calendar output
    }

    /*----------------------------------------------------------------------
    * Run given controller action using a search table of strategy actions
    ----------------------------------------------------------------------*/

    public void run(String action, StringBuilder output) {

        CommandStrategy cmd;
        // using dictionary of commands to control flow
        if (commands.containsKey(action.toLowerCase())) {
            cmd = commands.get(action.toLowerCase());
            cmd.execute(this, new Scanner(System.in), output);
        } else {
            output.append("\nPlease enter a valid command.\n");
        }
    }

    /*----------------------------------------------------------------------
    * Controller action setter/getter
    ----------------------------------------------------------------------*/

    public void addCommand(String key, CommandStrategy cmd) {
        commands.put(key, cmd);
    }

    public CommandStrategy getCommand(String key) {
        return commands.get(key);
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

    public void loadEvents(String[][] events) {
        this.events = events;
        Events.loadEventFile(events,"src/calendarEvents.txt");
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
