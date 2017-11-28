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

    // Whitelist of controller actions
    private HashMap<String, CommandStrategy> commands;

    public CalendarModel delta;
    public CalendarModel current;

    Scanner prompt; // user prompt

    public AppController() {

        commands = new HashMap<String, CommandStrategy>();

        // Working calendars
        delta = new CalendarModel();
        current = null;

        prompt = new Scanner(System.in);
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
    * Run given controller action
    ----------------------------------------------------------------------*/

    public void run(String action, StringBuilder output) {

        CommandStrategy cmd;
        // using dictionary of commands to control flow
        if (commands.containsKey(action.toLowerCase())) {
            cmd = commands.get(action.toLowerCase());
            cmd.execute(this, prompt, output);
        } else {
            output.append("\nPlease enter a valid command.\n");
        }
    }

    /*----------------------------------------------------------------------
    * Manage events
    ----------------------------------------------------------------------*/

    public String[][] initEvents() {
        String[][] events = new String[13][32];
        Events.loadEventFile(events,"src/calendarEvents.txt");
        return events;
    }
}
