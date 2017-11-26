import java.util.ArrayList;
import java.util.Scanner;

public class CalController {

    /*----------------------------------------------------------------------------
     * UI
     *--------------------------------------------------------------------------*/

    // The selected menu item. ("e", "t", "n", "p", or "q")
    private static char menu(Scanner input) {
        StringBuilder menuOptions = new StringBuilder();
        menuOptions.append(CalView.EOL)
            .append("Please type a command").append(CalView.EOL)
            .append("    \"e\" to enter a date and display the corresponding").append(CalView.EOL)
            .append("    \"t\" to get todays date and display today's calendar").append(CalView.EOL)
            .append("    \"n\" to display the next month").append(CalView.EOL)
            .append("    \"p\" to display the previous month").append(CalView.EOL)
            .append("    \"q\" to quit the program").append(CalView.EOL)
            .append("> ");
        stream(menuOptions.toString());
        String command = input.nextLine();
        if (command.length() > 0) {
            return command.charAt(0);
        }
        return 'x';
    }

    // Returns a date string in the format mm/dd
    private static String promptForDate(Scanner input) {
        stream("What date would you like like to look at? (mm/dd): ");
        return input.nextLine(); // in-line processing
    }

    private static void setDateFromPrompt(CalModel c, Scanner input) {
        Delimiter delim = Delimiter.getInstance();
        String formattedDate = promptForDate(input); // in-line processing
        try {
            int delimiterIndex = delim.getIndex(formattedDate);
            int month = monthFromDate(formattedDate, delimiterIndex);
            int day = dayFromDate(formattedDate, delimiterIndex);
            c.setDate(month, day, 2017);
        }
        catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            setDateFromPrompt(c, input); // fail gracefully (recursive reprompt)
        }
    }

    // Given a date as a String, extract an integer value for the day and return it.
    private static int dayFromDate(String formatedDate, int delimiter) throws IllegalArgumentException {
        String dd = formatedDate.substring(delimiter+1);
        return validateAndReturnInt(dd, 31, "Days");
    }

    // Given a date as a String, extract an integer value for the month and return it.
    private static int monthFromDate(String formatedDate, int delimiter) throws IllegalArgumentException {
        String mm = formatedDate.substring(0, delimiter);
        return validateAndReturnInt(mm, 12, "Months");
    }

    // Check date segments for valid, correctly formatted input
    private static int validateAndReturnInt(String dateSegment, int upperBound, String segmentName)
            throws IllegalArgumentException {
        dateSegment = removeLeadingZero(dateSegment);
        ArrayList<String> validSegments = getValidList(upperBound);
        if ( ! validSegments.contains(dateSegment)) { // check input is in valid month range
            throw new IllegalArgumentException(String.format("\n%s range from 1 to %d. %s given.\n",
                    segmentName, upperBound, dateSegment));
        }
        return Integer.parseInt(dateSegment);
    }

    // Build a list of ascii integers representing months and days (for easy range checking)
    private static ArrayList<String> getValidList(int upperBound) {
        ArrayList<String> list = new ArrayList<String>();
        for (int i=1; i<=upperBound; i++) {
            list.add("".valueOf(i));
        }
        return list;
    }

    // If string segment starts with a zero, remove it
    private static String removeLeadingZero(String dateSegment) {
        if (dateSegment.startsWith("0")) {
            dateSegment = dateSegment.substring(1);
        }
        return dateSegment;
    }

    /*----------------------------------------------------------------------
    * Calendar client
    ----------------------------------------------------------------------*/
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in); // user prompt
        String eol = CalView.EOL; // "\n"

        CalModel delta = new CalModel();
        CalModel current = null;

        StringBuilder header = new StringBuilder();
        header.append(eol)
              .append("Welcome to my Doctor who themed calendar.")
              .append(eol);
        ArtModel.drawHeaderArt(header);
        stream(header.toString());

        boolean running = true;
        while (running) {
            StringBuilder output = new StringBuilder();
            switch (menu(input)) {
                case 'e':
                    setDateFromPrompt(delta, input);
                    ArtModel.drawBanner(output);
                    CalView.drawMonth(delta, output);
                    CalView.drawCurrentMonth(current, output);
                    break;

                case 't':
                    ArtModel.drawBanner(output);
                    CalView.drawCurrentMonth(current, output);
                    delta.setDateSetFlag(true);
                    break;

                case 'n':
                    if (delta.isDateSet()) {
                        delta.nextMonth();
                        ArtModel.drawBanner(output);
                        CalView.drawMonth(delta, output);
                        CalView.drawCurrentMonth(current, output);
                    }
                    else {
                        output.append(eol)
                              .append("You need to have a calendar displayed first.")
                              .append(eol);
                    }
                    break;

                case 'p':
                    if (delta.isDateSet()) {
                        delta.previousMonth();
                        ArtModel.drawBanner(output);
                        CalView.drawMonth(delta, output);
                        CalView.drawCurrentMonth(current, output);
                    }
                    else {
                        output.append(eol)
                              .append("You need to have a calendar displayed first.")
                              .append(eol);
                    }
                    break;

                case 'q':
                    running = false;
                    output.append(eol)
                          .append("That's one way to do it, good day.")
                          .append(eol);
                    break;

                default:
                    output.append("Please enter a valid command.")
                          .append(eol);
                    break;
            }

            // display output to StdOut
            stream(output.toString());
        }
    }

    private static void stream(String thing) {
        System.out.print(thing);
    }
}
