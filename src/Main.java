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
        app.addCommand("fp", new PrintToFile());
        app.addCommand("q", new SwitchState());
        /*------------------------------------------------------------------
        * Initialize events array
        ------------------------------------------------------------------*/
        events = app.initEvents();

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

    private static void setDateFromPrompt(CalendarModel c, Scanner prompt) {
        String formattedDate = promptForDate(prompt); // in-line processing
        try {
            int month = DateParser.monthFromDate(formattedDate);
            int day = DateParser.dayFromDate(formattedDate);
            c.setDate(month, day, 2017);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            setDateFromPrompt(c, prompt); // fail gracefully (recursive reprompt)
        }
    }

    /*----------------------------------------------------------------------------
     * Command Strategies (Controller Actions)
     *--------------------------------------------------------------------------*/

    private static class EnterDate implements CommandStrategy {
        public void execute(AppController app, Scanner in, StringBuilder out) {
            setDateFromPrompt(app.deltaMonth, in);
            Theme.drawBanner(out);
            ViewModel.drawMonth(app.deltaMonth, out);
            ViewModel.drawCurrentMonth(app.thisMonth, out);
        }
    }

    private static class TodaysDate implements CommandStrategy {
        public void execute(AppController app, Scanner in, StringBuilder out) {
            Theme.drawBanner(out);
            ViewModel.drawCurrentMonth(app.thisMonth, out);
//            Events.getEventsForMonth(events, app.thisMonth.getMonth(), out);
            app.deltaMonth.setDateSetFlag(true);
        }
    }

    private static class NextMonth implements CommandStrategy {
        public void execute(AppController app, Scanner in, StringBuilder out) {
            if (app.deltaMonth.isDateSet()) {
                app.deltaMonth.nextMonth();
                Theme.drawBanner(out);
                ViewModel.drawMonth(app.deltaMonth, out);
                ViewModel.drawCurrentMonth(app.thisMonth, out);
            } else {
                addLine(out, "You need to have a calendar displayed first.");
            }
        }
    }

    private static class PreviousMonth implements CommandStrategy {
        public void execute(AppController app, Scanner in, StringBuilder out) {
            if (app.deltaMonth.isDateSet()) {
                app.deltaMonth.previousMonth();
                Theme.drawBanner(out);
                ViewModel.drawMonth(app.deltaMonth, out);
                ViewModel.drawCurrentMonth(app.thisMonth, out);
            } else {
                addLine(out, "You need to have a calendar displayed first.");
            }
        }
    }

    private static class EventPlanning implements CommandStrategy {
        public void execute(AppController app, Scanner in, StringBuilder out) {
            // TASK 1
            // model.Event Planning: when "ev" is entered, start a new event planning action:

            // a. Prompt user for an event in the form of "MM/DD event_title".
            String ev = null;
            try {
                ev = promptForEvent(in);

                // b. Parse and store event in global array, events[12][31].
                app.addEvent(events, ev);
            }
            catch (InvalidDateInputException e) {
                stream(e.getMessage() + ViewModel.EOL);
                app.getCommand("ev").execute(app, in, out);
            }

            // c. Display events from array in correct cell (day) of displayed calendar.
//            out.append("model.Event: ").append(events[month][day]).append(ViewModel.EOL);
        }
    }

    private static class PrintToFile implements CommandStrategy {
        public void execute(AppController app, Scanner in, StringBuilder out) {
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
            app.deltaMonth.setDate(month, 1, 2017);
            try {
                PrintStream file = new PrintStream(new File(filename));
                //-- PRINT >>------------------------------------------
                Theme.drawBanner(s);
                ViewModel.drawMonth(app.deltaMonth, s);
                file.println(s.toString());
                //--<< END --------------------------------------------

                // d. Close pointer to file.
                file = null; //TODO: check this
            }
            catch (Exception e) {
//                stream("Shitty");
            }

            addLine(out, "Calendar and events for month "+month+" was printed to "+filename);
        }
    }

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