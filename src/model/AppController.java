/*---------------------------------------------------------------------
 * Author: Brian Teachman
 * Date: 11/27/2017
 * 
 * License: http://www.wtfpl.net/txt/copying/
 *-------------------------------------------------------------------*/
package model;

import java.util.HashMap;
import java.util.Scanner;

public class AppController {

    public final CalendarModel thisMonth;
    public CalendarModel deltaMonth;

    // Whitelist of controller actions
    private HashMap<String, CommandStrategy> commands;

    public AppController() {

        commands = new HashMap<String, CommandStrategy>();

        // Working calendars
        deltaMonth = new CalendarModel();
        thisMonth = new CalendarModel();
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
    * Manage events
    ----------------------------------------------------------------------*/

    public void loadEvents(String[][] events) {
        Events.loadEventFile(events,"src/calendarEvents.txt");
    }

    public void addEvent(String[][] events, String event) {
        Events.setEvent(events, event);
    }
}
