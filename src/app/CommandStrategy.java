/*-----------------------------------------------------------------------------
* Author: Brian Teachman
* Date:   11/26/2017
*
* Strategy pattern for processing console UI commands
*----------------------------------------------------------------------------*/
package app;

import java.util.Scanner;

public interface CommandStrategy {
//    public void execute(Scanner in, StringBuilder out, CalendarModel a, CalendarModel b);
    public void execute(AppController app, Scanner in, StringBuilder out);
}
