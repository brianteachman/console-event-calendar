/*-----------------------------------------------------------------------------
* Author: Brian Teachman
* Date:   11/26/2017
*
* Strategy pattern for processing console UI commands
*----------------------------------------------------------------------------*/

import java.util.Scanner;

public interface CommandStrategy {
    public void execute(Scanner in, StringBuilder out, CalendarModel a, CalendarModel b);
}
