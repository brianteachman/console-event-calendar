/*-----------------------------------------------------------------------------
* Strategy pattern for processing UI commands
*----------------------------------------------------------------------------*/

import java.util.Scanner;

public interface CommandStrategy {
    public void execute(Scanner in, StringBuilder out, CalendarModel a, CalendarModel b);
}
