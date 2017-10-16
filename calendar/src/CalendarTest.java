import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalendarTest {

    static Calendar cal = Calendar.getInstance();

    public static void main(String[] args) {

//        tenDaysInThePast();

        setCalendarMonth(10);
    }

    public static void tenDaysInThePast() {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Date myDate = new Date(System.currentTimeMillis());
        System.out.println("result is "+ dateFormat.format(myDate));

        cal.setTime(myDate);
        cal.add(Calendar.DATE, -10);
        System.out.println(dateFormat.format(cal.getTime()));
    }

    public static void setCalendarMonth(int month) {
        // https://stackoverflow.com/a/2938209/503781

        // get today and clear time of day
        cal.set(Calendar.HOUR_OF_DAY, 0); // ! clear would not reset the hour of day !
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);
        // first day of this month
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        // get start of given month
        int thisMonth = cal.get(Calendar.MONTH);
        int delta = month - thisMonth;
        if (thisMonth > month) {
            cal.add(Calendar.MONTH, delta);
        }
        else if (thisMonth < month) {
            cal.add(Calendar.MONTH, delta);
        }
//        System.out.println("This month:      " + thisMonth);
//        System.out.println("Requested month: " + month);
//        System.out.println("The delta:       " + delta);

        System.out.println("Start of the next month: " + cal.getTime());
        System.out.println("Day of the week number:  " + cal.get(Calendar.DAY_OF_WEEK));
        System.out.println("Last day of the month: " + cal.getActualMaximum(Calendar.DAY_OF_MONTH));
    }

}
