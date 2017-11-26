import java.util.ArrayList;
import java.util.Scanner;

public class CalController {

    /*----------------------------------------------------------------------------
     * UI: Prompts
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
        String formattedDate = promptForDate(input); // in-line processing
        try {
            int month = DateDelimiter.monthFromDate(formattedDate);
            int day = DateDelimiter.dayFromDate(formattedDate);
            c.setDate(month, day, 2017);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            setDateFromPrompt(c, input); // fail gracefully (recursive reprompt)
        }
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
        addLine(header, "Welcome to my Doctor who themed calendar.");
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
                    } else {
                        addLine(output, "You need to have a calendar displayed first.");
                    }
                    break;

                case 'p':
                    if (delta.isDateSet()) {
                        delta.previousMonth();
                        ArtModel.drawBanner(output);
                        CalView.drawMonth(delta, output);
                        CalView.drawCurrentMonth(current, output);
                    } else {
                        addLine(output, "You need to have a calendar displayed first.");
                    }
                    break;

                case 'q':
                    running = false;
                    addLine(output, "That's one way to do it, good day.");
                    break;

                default:
                    addLine(output, "Please enter a valid command.");
                    break;
            }

            // pass output to StdOut
            stream(output.toString());
        }
    }

    // drop a line, add text, drop a line
    private static void addLine(StringBuilder s, String text) {
        s.append(CalView.EOL).append(text).append(CalView.EOL);
    }

    // print to StdOut
    private static void stream(String thing) {
        System.out.print(thing);
    }
}
