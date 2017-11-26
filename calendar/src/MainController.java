/*-----------------------------------------------------------------------------
* Author: Brian Teachman
* Date:   11/26/2017
*
* Application controller and calendar client
*----------------------------------------------------------------------------*/

import java.io.File;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Scanner;

public class MainController {

    private static String[][] events; // global events array

    private static HashMap<String, CommandStrategy> buildWhitelist() {
        HashMap<String, CommandStrategy> commands = new HashMap<String, CommandStrategy>();

        // available actions
        commands.put("e", new EnterDate());
        commands.put("t", new TodaysDate());
        commands.put("n", new NextMonth());
        commands.put("p", new PreviousMonth());
        commands.put("ev", new EventPlanning());
        commands.put("fp", new PrintToFile());
        commands.put("q", new SwitchState());
        return commands;
    }

    /*----------------------------------------------------------------------
    * Calendar client
    ----------------------------------------------------------------------*/
    public static void main(String[] args) {

        events = new String[12][31];
        //TODO: load events from file on init

        // create editable "whitelist" of allow commands
        HashMap<String, CommandStrategy> commands = buildWhitelist();

        CalendarModel delta = new CalendarModel();
        CalendarModel current = null;

        Scanner prompt = new Scanner(System.in); // user prompt

        StringBuilder header = new StringBuilder();
        addLine(header, "Welcome to my Doctor who themed calendar.");
        ArtModel.drawHeaderArt(header);
        stream(header.toString());

        while (SwitchState.isRunning()) {
            StringBuilder output = new StringBuilder();
            String arg = menu(prompt);

            CommandStrategy cmd;
            // using dictionary of commands to control flow
            if (commands.containsKey(arg.toLowerCase())) {
                cmd = commands.get(arg.toLowerCase());
                cmd.execute(prompt, output, delta, current);
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
    private static String menu(Scanner prompt) {
        StringBuilder menuOptions = new StringBuilder();
        String eol = ViewModel.EOL;
        menuOptions.append(eol)
                .append("Please type a command").append(eol)
                .append("    \"e\" to enter a date and display the corresponding").append(eol)
                .append("    \"t\" to get todays date and display today's calendar").append(eol)
                .append("    \"n\" to display the next month").append(eol)
                .append("    \"p\" to display the previous month").append(eol)
                .append("    \"ev\" to add a new event").append(eol)
                .append("    \"fp\" to write a given month w/events to file").append(eol)
                .append("    \"q\" to quit the program").append(eol)
                .append("> ");
        stream(menuOptions.toString());
        if (prompt.hasNextLine()) {
            return prompt.nextLine();
        }
        return null;
    }

    // Returns a Event string in the format "mm/dd event_name"
    private static String promptForEvent(Scanner prompt) {
        stream("Enter new Event in the format \"MM/DD event_title\": ");
        return prompt.nextLine(); // in-line processing
    }

    // Returns a date string in the format mm/dd
    private static String promptForDate(Scanner prompt) {
        stream("What date would you like like to look at? (mm/dd): ");
        return prompt.nextLine(); // in-line processing
    }

    private static void setDateFromPrompt(CalendarModel c, Scanner prompt) {
        String formattedDate = promptForDate(prompt); // in-line processing
        try {
            int month = DateDelimiter.monthFromDate(formattedDate);
            int day = DateDelimiter.dayFromDate(formattedDate);
            c.setDate(month, day, 2017);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            setDateFromPrompt(c, prompt); // fail gracefully (recursive reprompt)
        }
    }

    /*----------------------------------------------------------------------------
     * Command Strategies
     *--------------------------------------------------------------------------*/

    private static class EnterDate implements CommandStrategy {
        public void execute(Scanner in, StringBuilder out,
                            CalendarModel delta, CalendarModel current) {
            setDateFromPrompt(delta, in);
            ArtModel.drawBanner(out);
            ViewModel.drawMonth(delta, out);
            ViewModel.drawCurrentMonth(current, out);
        }
    }

    private static class TodaysDate implements CommandStrategy {
        public void execute(Scanner in, StringBuilder out,
                            CalendarModel delta, CalendarModel current) {
            ArtModel.drawBanner(out);
            ViewModel.drawCurrentMonth(current, out);
            delta.setDateSetFlag(true);
        }
    }

    private static class NextMonth implements CommandStrategy {
        public void execute(Scanner in, StringBuilder out,
                            CalendarModel delta, CalendarModel current) {
            if (delta.isDateSet()) {
                delta.nextMonth();
                ArtModel.drawBanner(out);
                ViewModel.drawMonth(delta, out);
                ViewModel.drawCurrentMonth(current, out);
            } else {
                addLine(out, "You need to have a calendar displayed first.");
            }
        }
    }

    private static class PreviousMonth implements CommandStrategy {
        public void execute(Scanner in, StringBuilder out,
                            CalendarModel delta, CalendarModel current) {
            if (delta.isDateSet()) {
                delta.previousMonth();
                ArtModel.drawBanner(out);
                ViewModel.drawMonth(delta, out);
                ViewModel.drawCurrentMonth(current, out);
            } else {
                addLine(out, "You need to have a calendar displayed first.");
            }
        }
    }

    private static class EventPlanning implements CommandStrategy {
        public void execute(Scanner in, StringBuilder out,
                            CalendarModel delta, CalendarModel current) {
            // TASK 1
            // Event Planning: when "ev" is entered, start a new event planning action:

            // a. Prompt user for an event in the form of "MM/DD event_title".
            String ev = promptForEvent(in);

            // b. Parse and store event in global array, events[12][31].
            String[] part = ev.split(" ");
            int month = DateDelimiter.monthFromDate(part[0]);
            int day = DateDelimiter.dayFromDate(part[0]);
            events[month][day] = part[1];

            // c. Display events from array in correct cell (day) of displayed calendar.
            out.append("Event: ").append(events[month][day]).append(ViewModel.EOL);
        }
    }

    private static class PrintToFile implements CommandStrategy {
        public void execute(Scanner in, StringBuilder out,
                            CalendarModel delta, CalendarModel current) {
            // TASK 3
            // File Printing: when "fp" is entered, write calendar of given date to a file.

            // a. Prompt user for month to print.
            stream("Enter month to print: ");
            int month = in.nextInt();
            in.nextLine(); // clear buffer

            // b. Prompt user for name of file to write.
            stream("Enter filename to save as: ");
            String filename = in.nextLine();

            // c. Write calendar and events to said file.
            StringBuilder s = new StringBuilder();
            try {
                File fp = new File(filename);
                PrintStream ps = new PrintStream(fp);
                delta.setDate(month, 1, 2017);
                ArtModel.drawBanner(s);
                ViewModel.drawMonth(delta, s);
                ps.println(s.toString());

                // d. Close pointer to file.
                fp = null; //TODO: check this
                ps = null;
            }
            catch (Exception e) {
//                stream("Shitty");
            }

            out.append("File printed to ").append(filename).append(ViewModel.EOL);
        }
    }

    private static class SwitchState implements CommandStrategy {
        private static boolean running = true;
        public static boolean isRunning() {
            return running;
        }
        public void execute(Scanner in, StringBuilder out,
                            CalendarModel delta, CalendarModel current) {
            running = false;
            addLine(out, "That's one way to do it, good day.");
        }
    }

    /*----------------------------------------------------------------------------
     * Helpers
     *--------------------------------------------------------------------------*/

    // drop a line, add text, drop a line
    private static void addLine(StringBuilder s, String text) {
        s.append(ViewModel.EOL).append(text).append(ViewModel.EOL);
    }

    // print to StdOut
    private static void stream(String thing) {
        System.out.print(thing);
    }
}
