import app.AppController;
import app.CommandStrategy;
import exceptions.EventsFileNotFound;
import exceptions.InvalidDateInputException;
import eventcalendar.*;

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
        app.addCommand("ev", new AddEvent());
        app.addCommand("l", new ListEvents());
        app.addCommand("i", new ImportEvents());
        app.addCommand("fp", new PrintToFile());
        app.addCommand("q", new SwitchState());
        /*------------------------------------------------------------------
        * Initialize controller dependencies
        ------------------------------------------------------------------*/
        app.addService("Events", new EventManager());
        app.addService("Calendar", new CalendarManager(app.getServiceManager()));
        app.addService("View", new View(app.getServiceManager()));

        /*------------------------------------------------------------------
        * Initialize events array
        ------------------------------------------------------------------*/
        events = new String[13][32];
        ((EventManager) app.get("Events")).loadEvents(events);
//        CalendarController cal = (CalendarController) app.get("Calendar");
        // TODO: How do I fix this: ^^^^^^^^^^^^^^^^^^ ugly casting issue
//        cal.loadEvents(events);

        // feel free to pragmatically add events
//        cal.addEvent(events, "2/17 Feb,_17_Event_1370);

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
            //>
            app.run(action, output);
            //<
            stream(output.toString());
        }
    }

    /*----------------------------------------------------------------------------
     * UI: Prompts
     *--------------------------------------------------------------------------*/

    // The selected menu item. ("e", "t", "n", "p", or "q")
    private static String menu(Scanner prompt) {
        StringBuilder menuOptions = new StringBuilder();
        String eol = View.EOL;
        menuOptions.append(eol)
                .append("Please type a command").append(eol)
                .append("    \"e\" to enter a date and display the corresponding").append(eol)
                .append("    \"t\" to get todays date and display today's calendar").append(eol)
                .append("    \"n\" to display the next month").append(eol)
                .append("    \"p\" to display the previous month").append(eol)
                .append("    \"ev\" to add a new event").append(eol)
                .append("    \"l\" to list calendar events").append(eol)
                .append("    \"i\" to import an events file").append(eol)
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

    private static String promptForEventFileName(Scanner prompt) {
        stream("Enter the name of the events file in the data/ folder: ");
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
            CalendarManager cal = (CalendarManager) app.get("Calendar");
            String formattedDate = promptForDate(in); // in-line processing
            try {
                int month = DateParser.monthFromDate(formattedDate);
                int day = DateParser.dayFromDate(formattedDate);
                cal.setDate(month, day, 2017);
            }
            catch (Exception e) {
                stream(e.getMessage() + View.EOL);
                app.getCommand("e").execute(app, in, out);
            }
            drawCalendars(app, out);
        }
    }

    private static class TodaysDate implements CommandStrategy {
        public void execute(AppController app, Scanner in, StringBuilder out) {
            CalendarManager cal = (CalendarManager) app.get("Calendar");
            View view = (View) app.get("View");
            Theme.drawBanner(out);
            view.drawCurrentMonth(cal.thisMonth, events, out);
            cal.setDateSetFlag(true);
            //
            addLine(out, "Events for " + cal.thisMonth.getMonthName() + ": ");
            addHeaderedListOfEventsForMonth(app, cal.thisMonth.getMonth(), out);
        }
    }

    private static class NextMonth implements CommandStrategy {
        public void execute(AppController app, Scanner in, StringBuilder out) {
            CalendarManager cal = (CalendarManager) app.get("Calendar");
            if (cal.isDateSet()) {
                cal.nextMonth();
                drawCalendars(app, out);
            } else {
                addLine(out, "You need to have a calendar displayed first.");
            }
        }
    }

    private static class PreviousMonth implements CommandStrategy {
        public void execute(AppController app, Scanner in, StringBuilder out) {
            CalendarManager cal = (CalendarManager) app.get("Calendar");
            if (cal.isDateSet()) {
                cal.previousMonth();
                drawCalendars(app, out);
            } else {
                addLine(out, "You need to have a calendar displayed first.");
            }
        }
    }

    // when "ev" is entered, start a new event planning action:
    private static class AddEvent implements CommandStrategy {
        public void execute(AppController app, Scanner in, StringBuilder out) {
            EventManager evm = (EventManager) app.get("Events");

            String ev = null;
            try {
                // prompt user for an event in the form of "MM/DD event_title".
                ev = promptForEvent(in);

                // parse and store event in global array, events[12][31].
                evm.addEvent(events, ev);
                int month = evm.getDateFromEventString(ev)[0];

                // display events from array in correct cell (day) of displayed calendar.
                addLine(out, "Added the following event to " + month + "'s calendar:\n" + ev);
            }
            catch (InvalidDateInputException e) {
                stream(e.getMessage() + View.EOL);
                app.getCommand("ev").execute(app, in, out);
            }
        }
    }

    private static class ListEvents implements CommandStrategy {
        public void execute(AppController app, Scanner in, StringBuilder out) {
            CalendarManager cal = (CalendarManager) app.get("Calendar");
            String[] months = cal.getMonthNames();
            addLine(out, "Event Listing for current year:");
            for (int i = 0; i < 12; i++) {
                out.append(View.EOL).append(months[i]).append(": ").append(View.EOL);
                addHeaderedListOfEventsForMonth(app, i+1, out);
            }
        }
    }

    private static class ImportEvents implements CommandStrategy {
        public void execute(AppController app, Scanner in, StringBuilder out) {
            EventManager evm = (EventManager) app.get("Events");
            try { // load events
                String filename = promptForEventFileName(in);
                evm.loadEvents(events, filename);
                addLine(out, "Loaded event file " + filename + ".");
            }
            catch (EventsFileNotFound e) {
                stream("File not found.");
                app.getCommand("i").execute(app, in, out);
            }
        }
    }

    // when "fp" is entered, write calendar of given date to a file.
    private static class PrintToFile implements CommandStrategy {
        public void execute(AppController app, Scanner in, StringBuilder out) {
            View view = (View) app.get("View");

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

            // write calendar and events to said file.
            StringBuilder s = new StringBuilder(); // TODO: add factories for StringBuilder, CalendarModel, and PrintStream in the ServiceManager
            CalendarModel cal = new CalendarModel();
            cal.setDate(month, 1, 2017);
            try {
                PrintStream file = new PrintStream(new File("data/"+filename));
                //-- PRINT >>------------------------------------------
                Theme.drawBanner(s);
                view.drawMonth(cal, events, s);
                addLine(s, "Events for " + cal.getMonthName() + ": ");
                addHeaderedListOfEventsForMonth(app, cal.getMonth(), s);
                file.println(s.toString());
                //--<< END --------------------------------------------
                file.close(); // close pointer to file
            }
            catch (Exception e) { /*stream("Shitty: "+e.getMessage());*/ }

            addLine(out, "Calendar and events for " + cal.getMonthName()
                    + " was printed to data/"+filename+" in this calendar's data folder.");
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
        s.append(View.EOL).append(text).append(View.EOL);
    }

    // print to StdOut
    private static void stream(String thing) {
        System.out.print(thing);
    }

    private static void addHeaderedListOfEventsForMonth(AppController app, int month, StringBuilder out) {
        ((View) app.get("View")).drawHeader(out, "-", 70);
        ((EventManager) app.get("Events")).listEventsForMonth(events, month, out);
    }

    private static void drawCalendars(AppController app, StringBuilder out) {
        CalendarManager cal = (CalendarManager) app.get("Calendar");
        View view = (View) app.get("View");

        Theme.drawBanner(out);

        cal.setThisMonth(false);
        view.drawMonth(cal.deltaMonth, events, out);
        addLine(out, "Events for " + cal.getMonthName() + ": ");
        addHeaderedListOfEventsForMonth(app, cal.getMonth(), out);

        cal.setThisMonth(true);
        view.drawCurrentMonth(cal.thisMonth, events, out);
        addLine(out, "Events for " + cal.getMonthName() + ": ");
        addHeaderedListOfEventsForMonth(app, cal.getMonth(), out);
    }
}
