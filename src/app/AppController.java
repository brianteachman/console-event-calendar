/*---------------------------------------------------------------------
 * Author: Brian Teachman
 * Date: 11/27/2017
 * 
 * License: http://www.wtfpl.net/txt/copying/
 *-------------------------------------------------------------------*/
package app;

import java.util.HashMap;
import java.util.Scanner;

public class AppController {

    // Whitelist of controller actions
    private HashMap<String, CommandStrategy> commands;

    // Dependency container
    private ServiceManager deps;

    public AppController() {
        commands = new HashMap<String, CommandStrategy>();
        deps = new ServiceManager();
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
    * Controller services setter/getter
    ----------------------------------------------------------------------*/

    public void addService(String key, Service service) {
        deps.add(key, service);
    }

    public Service get(String key) {
        if (deps.has(key)) {
            return deps.get(key);
        }
        return null;
    }
}
