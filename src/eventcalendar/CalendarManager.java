/*---------------------------------------------------------------------
 * Author: Brian Teachman
 * Date: 12/2/2017
 * 
 * License: http://www.wtfpl.net/txt/copying/
 *-------------------------------------------------------------------*/
package eventcalendar;

import app.Service;
import app.ServiceManager;
import exceptions.EventsFileNotFound;
import exceptions.InvalidDateInputException;

public class CalendarManager implements Service {

    public final CalendarModel thisMonth;
    public CalendarModel deltaMonth;
    private boolean isThisMonth;

    ServiceManager deps;

    public CalendarManager(ServiceManager dependencies) {
        deltaMonth = new CalendarModel();
        thisMonth = new CalendarModel();
        isThisMonth = true; // used for calendar output
        this.deps = dependencies;
    }

    /*----------------------------------------------------------------------
    * Thin calendar wrapper to enforce Demeter's Law in AppController
    ----------------------------------------------------------------------*/

    public void setDate(int month, int day, int year) {
        if (month < 0) throw new InvalidDateInputException("month", 12, "-1");
        deltaMonth.setDate(month, day, year);
        isThisMonth = false;
    }

    public String getMonthName() {
        if (isThisMonth) return thisMonth.getMonthName();
        return deltaMonth.getMonthName();
    }

    public void setThisMonth(boolean is) {
        isThisMonth = is;
    }

    public void setDateSetFlag(boolean isDateSet) {
        deltaMonth.setDateSetFlag(isDateSet);
    }

    public void nextDay() {
        deltaMonth.nextDay();
    }

    public void nextMonth() {
        deltaMonth.nextMonth();
    }

    public void previousMonth() {
        deltaMonth.previousMonth();
    }

    public boolean isDateSet() {
        return deltaMonth.isDateSet();
    }

    public String[] getMonthNames() {
        return thisMonth.getMonthNames();
    }

}
