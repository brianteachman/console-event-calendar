import exceptions.InvalidDateInputException;
import model.*;

import java.io.File;
import java.io.PrintStream;
import java.util.Scanner;

public class Main {

    private static String[][] events; // global events array

    /*----------------------------------------------------------------------
    * Calendar client
    ----------------------------------------------------------------------*/

    public static void main(String[] args) {

        AppController app = new AppController();
        /*------------------------------------------------------------------
        * Initialize controller actions
        ------------------------------------------------------------------*/
        app.addCommand("e", new EnterDate());
        app.addCommand("t", new TodaysDate());
        app.addCommand("n", new NextMonth());
        app.addCommand("p", new PreviousMonth());
        app.addCommand("ev", new EventPlanning());
        app.addCommand("l", new EventListing());
        app.addCommand("fp", new PrintToFile());
        app.addCommand("q", new SwitchState());
        /*------------------------------------------------------------------
        * Initialize events array
        ------------------------------------------------------------------*/
        events = new String[13][32];
        app.loadEvents(events);

        /*------------------------------------------------------------------
        * Add header to output
        ------------------------------------------------------------------*/
        StringBuilder header = new StringBuilder();
        addLine(header, "Welcome to my Doctor who themed calendar.");
        Theme.drawHeaderArt(header);
        stream(header.toString());

        /*------------------------------------------------------------------
        * Main application loop
        ------------------------------------------------------------------*/
        while (SwitchState.isRunning()) {
            String action = menu(new Scanner(System.in));
            StringBuilder output = new StringBuilder();
            //
            app.run(action, output);
            //
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

    // Returns a model.Event string in the format "mm/dd event_name"
    private static String promptForEvent(Scanner prompt) {
        stream("Enter new model.Event in the format \"MM/DD event_title\": ");
        return prompt.nextLine(); // in-line processing
    }

    // Returns a date string in the format mm/dd
    private static String promptForDate(Scanner prompt) {
        stream("What date would you like like to look at? (mm/dd): ");
        return prompt.nextLine(); // in-line processing
    }

    /*----------------------------------------------------------------------------
     * Command Strategies (Controller Actions)
     *--------------------------------------------------------------------------*/

    private static class EnterDate implements CommandStrategy {
        public void execute(AppController app, Scanner in, StringBuilder out) {
            String formattedDate = promptForDate(in); // in-line processing
            try {
                int month = DateParser.monthFromDate(formattedDate);
                int day = DateParser.dayFromDate(formattedDate);
                app.deltaMonth.setDate(month, day, 2017);
            }
            catch (Exception e) {
                stream(e.getMessage() + ViewModel.EOL);
                app.getCommand("e").execute(app, in, out);
            }
            drawCalendars(app, out);
        }
    }

    private static class TodaysDate implements CommandStrategy {
        public void execute(AppController app, Scanner in, StringBuilder out) {
            Theme.drawBanner(out);
            ViewModel.drawCurrentMonth(app.thisMonth, out);
//            addLine(out, "Events for " + app.thisMonth.getMonthName() + ": ");
//            addHeaderedListOfEventsForMonth(app.thisMonth, out);
            app.setDateSetFlag(true);
        }
    }

    private static class NextMonth implements CommandStrategy {
        public void execute(AppController app, Scanner in, StringBuilder out) {
            if (app.isDateSet()) {
                app.nextMonth();
                drawCalendars(app, out);
            } else {
                addLine(out, "You need to have a calendar displayed first.");
            }
        }
    }

    private static class PreviousMonth implements CommandStrategy {
        public void execute(AppController app, Scanner in, StringBuilder out) {
            if (app.isDateSet()) {
                app.previousMonth();
                drawCalendars(app, out);
            } else {
                addLine(out, "You need to have a calendar displayed first.");
            }
        }
    }

    private static class EventPlanning implements CommandStrategy {
        public void execute(AppController app, Scanner in, StringBuilder out) {
            // TASK 1 - Event Planning:
            // when "ev" is entered, start a new event planning action:

            // a. Prompt user for an event in the form of "MM/DD event_title".
            String ev = null;
            try {
                ev = promptForEvent(in);

                // b. Parse and store event in global array, events[12][31].
                app.addEvent(events, ev);
                int month = app.getDateFromEventString(ev)[0];

                // c. Display events from array in correct cell (day) of displayed calendar.
                addLine(out, "Added the following event to " + month + "'s calendar:\n" + ev);
            }
            catch (InvalidDateInputException e) {
                stream(e.getMessage() + ViewModel.EOL);
                app.getCommand("ev").execute(app, in, out);
            }
        }
    }

    private static class EventListing implements CommandStrategy {
        public void execute(AppController app, Scanner in, StringBuilder out) {
            String[] months = app.getMonthNames();
            addLine(out, "Event Listing for current year:");
            for (int i = 0; i < 12; i++) {
                out.append(ViewModel.EOL).append(months[i]).append(": ").append(ViewModel.EOL);
                addHeaderedListOfEventsForMonth(i+1, out);
            }
        }
    }

    private static class PrintToFile implements CommandStrategy {
        public void execute(AppController app, Scanner in, StringBuilder out) {

            // TASK 3
            // File Printing: when "fp" is entered, write calendar of given date to a file.

            int month = -1;
            String filename = null;
            try {
                // a. Prompt user for month to print.
                stream("Enter month to print: ");
                month = in.nextInt();
                in.nextLine(); // clear buffer

                // b. Prompt user for name of file to write.
                stream("Enter filename to save as: ");
                filename = in.nextLine();
            }
            catch (RuntimeException e) {
                stream("Whoops, try again.\n");
                in.nextLine(); // clear buffer, accounts for recursive case passing a not flushed Scanner
                app.getCommand("fp").execute(app, in, out);
                return;
            }

            // c. Write calendar and events to said file.
            StringBuilder s = new StringBuilder();
            app.setDate(month, 1, 2017);
            try {
                PrintStream file = new PrintStream(new File(filename));
                //-- PRINT >>------------------------------------------
                Theme.drawBanner(s);
                ViewModel.drawMonth(app.deltaMonth, s);
                addLine(s, "Events for " + app.getMonthName() + ": ");
                addHeaderedListOfEventsForMonth(app.deltaMonth.getMonth(), s);
                file.println(s.toString());
                //--<< END --------------------------------------------

                // d. Close pointer to file.
                file.close();
            }
            catch (Exception e) { /*stream("Shitty: "+e.getMessage());*/ }

            addLine(out, "Calendar and events for " + app.getMonthName()
                    + " was printed to "+filename);
        }
    }

    // application state action
    private static class SwitchState implements CommandStrategy {
        private static boolean running = true;
        public static boolean isRunning() {
            return running;
        }
        public void execute(AppController app, Scanner in, StringBuilder out) {
            running = false;
            addLine(out, "That's one way to do it, good day.");
        }
    }

    /*----------------------------------------------------------------------------
     * Command Action Helpers
     *--------------------------------------------------------------------------*/

    // drop a line, add text, drop a line
    private static void addLine(StringBuilder s, String text) {
        s.append(ViewModel.EOL).append(text).append(ViewModel.EOL);
    }

    // print to StdOut
    private static void stream(String thing) {
        System.out.print(thing);
    }

    private static void addHeaderedListOfEventsForMonth(int month, StringBuilder out) {
        // add events for the month
        ViewModel.drawHeader(out, "-", 70); //TODO: add calWidth field to ViewModel
        Events.getEventsForMonth(events, month, out);
    }

    private static void drawCalendars(AppController app, StringBuilder out) {
        Theme.drawBanner(out);

        ViewModel.drawMonth(app.deltaMonth, out);
//        addLine(out, "Events for " + app.deltaMonth.getMonthName() + ": ");
//        addHeaderedListOfEventsForMonth(app.deltaMonth, out);

        ViewModel.drawCurrentMonth(app.thisMonth, out);
//        addLine(out, "Events for " + app.thisMonth.getMonthName() + ": ");
//        addHeaderedListOfEventsForMonth(app.thisMonth, out);
    }
}
