## TASK 1
**Event Planning**: when "`ev`" is entered, start a new event planning action:

1. Prompt user for an event in the form of `"MM/DD event_title"`.
2. Parse and store event in global array, `events[12][31]`.
3. Display events from array in correct cell (day) of displayed calendar.

## TASK 2
**File Reading**: if event file exists, load events into current calendar.

1. If found, load a file named "calendarEvents.txt".
2. Add loaded events from file into the events array by date.
3. Events are processed in task 1.c.

*Extra credit*: open a given filename and handle errors on fail.

## TASK 3
**File Printing**: when "fp" is entered, write calendar of given date to a file.

1. Prompt user for month to print.
2. Prompt user for name of file to write.
3. Write calendar and events to said file.
4. Close pointer to file.

*Extra credit*: support adding a commands to an allowed commands list and enforcing functionality.