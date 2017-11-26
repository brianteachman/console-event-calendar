import java.util.HashMap;
import java.util.Scanner;

public class Main {

    /*----------------------------------------------------------------------
    * Calendar client
    ----------------------------------------------------------------------*/
    public static void main(String[] args) {

        // create editable "whitelist" of allow commands
        HashMap<String, CommandStrategy> commands = new HashMap<String, CommandStrategy>();
        commands.put("e", new EnterDate());
        commands.put("t", new TodaysDate());
        commands.put("n", new NextMonth());
        commands.put("p", new PreviousMonth());
        commands.put("ev", new EventPlanning());
        commands.put("fp", new PrintToFile());
        commands.put("q", new SwitchState());

        Scanner input = new Scanner(System.in); // user prompt
        String eol = CalView.EOL; // "\n"

        CalModel delta = new CalModel();
        CalModel current = null;

        StringBuilder header = new StringBuilder();
        addLine(header, "Welcome to my Doctor who themed calendar.");
        ArtModel.drawHeaderArt(header);
        stream(header.toString());

        while (SwitchState.running) {
            StringBuilder output = new StringBuilder();
            String arg = menu(input);

            CommandStrategy cmd;
            if (commands.containsKey(arg.toLowerCase())) {
                cmd = commands.get(arg.toLowerCase());
                cmd.execute(input, output, delta, current);
            } else {
                addLine(output, "Please enter a valid command.");
            }

            // pass output to StdOut
            stream(output.toString());
        }
    }

    /*----------------------------------------------------------------------------
     * UI: Prompts
     *--------------------------------------------------------------------------*/

    // The selected menu item. ("e", "t", "n", "p", or "q")
    private static String menu(Scanner input) {
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
            return command;
        }
        return "x";
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

    /*----------------------------------------------------------------------------
     * Command Strategies
     *--------------------------------------------------------------------------*/

    private static class EnterDate implements CommandStrategy {
        public void execute(Scanner in, StringBuilder out, CalModel a, CalModel b) {
            setDateFromPrompt(a, in);
            ArtModel.drawBanner(out);
            CalView.drawMonth(a, out);
            CalView.drawCurrentMonth(b, out);
        }
    }

    private static class TodaysDate implements CommandStrategy {
        public void execute(Scanner in, StringBuilder out, CalModel a, CalModel b) {
            ArtModel.drawBanner(out);
            CalView.drawCurrentMonth(b, out);
            a.setDateSetFlag(true);
        }
    }

    private static class NextMonth implements CommandStrategy {
        public void execute(Scanner in, StringBuilder out, CalModel delta, CalModel current) {
            if (delta.isDateSet()) {
                delta.nextMonth();
                ArtModel.drawBanner(out);
                CalView.drawMonth(delta, out);
                CalView.drawCurrentMonth(current, out);
            } else {
                addLine(out, "You need to have a calendar displayed first.");
            }
        }
    }

    private static class PreviousMonth implements CommandStrategy {
        public void execute(Scanner in, StringBuilder out, CalModel delta, CalModel current) {
            if (delta.isDateSet()) {
                delta.previousMonth();
                ArtModel.drawBanner(out);
                CalView.drawMonth(delta, out);
                CalView.drawCurrentMonth(current, out);
            } else {
                addLine(out, "You need to have a calendar displayed first.");
            }
        }
    }

    private static class EventPlanning implements CommandStrategy {
        public void execute(Scanner in, StringBuilder out, CalModel delta, CalModel current) {
            // TASK 1
            // Event Planning: when "ev" is entered, start a new event planning action:
            // a. Prompt user for an event in the form of "MM/DD event_title".
            // b. Parse and store event in global array, events[12][31].
            // c. Display events from array in correct cell (day) of displayed calendar.
            out.append("Event");
        }
    }

    private static class PrintToFile implements CommandStrategy {
        public void execute(Scanner in, StringBuilder out, CalModel delta, CalModel current) {
            // TASK 3
            // File Printing: when "fp" is entered, write calendar of given date to a file.
            // a. Prompt user for month to print.
            // b. Prompt user for name of file to write.
            // c. Write calendar and events to said file.
            // d. Close pointer to file.
            out.append("Print to file");
        }
    }

    private static class SwitchState implements CommandStrategy {
        public static boolean running = true;
        public void execute(Scanner in, StringBuilder out, CalModel delta, CalModel current) {
            running = false;
            addLine(out, "That's one way to do it, good day.");
        }
    }

    /*----------------------------------------------------------------------------
     * Helpers
     *--------------------------------------------------------------------------*/

    // drop a line, add text, drop a line
    private static void addLine(StringBuilder s, String text) {
        s.append(CalView.EOL).append(text).append(CalView.EOL);
    }

    // print to StdOut
    private static void stream(String thing) {
        System.out.print(thing);
    }
}
