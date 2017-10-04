import java.util.Calendar;
import java.util.Date;

// https://docs.oracle.com/javase/8/docs/api/java/util/Calendar.html

public class App {

    int[] monthDays = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    static int month;
    static int day;

    public static void main(String[] args) {
        System.out.println("Your calandar, sir.");

        // get todays date
        Calendar cal = Calendar.getInstance();
        month = cal.get(Calendar.MONTH); // indexed month (Jan == 0)
        day = cal.get(Calendar.DAY_OF_MONTH);

        System.out.println(Calendar.DAY_OF_MONTH);
        System.out.println(Calendar.THURSDAY);
        System.out.println(Calendar.DATE);

        printMonth(5);
    }

    public static void rowHeader(int width) {
        for (int i=0; i<7; i++) {
            for (int j=0; j<width; j++) {
                System.out.print('=');
            }
        }
        System.out.print("=\n");
//        System.out.println();
    }

    public static void printMonth(int width) {

        rowHeader(5);
        for (int i = 0; i < 5; i++) {
            System.out.print("|");
            for (int j = 0; j < 7; j++) {
                for (int k = 0; k < width; k++) {
                    if (k == 1) {
                        // calendar day of the month goes here
                        System.out.print('x');
                    }
                    else if (k == (width-1) ) {
                        // end of cell
                        System.out.print("|");
                    }
                    else {
                        // pad cell
                        System.out.print(' ');
                    }
                }
            }
            System.out.print("\n");
            if (i != 5) {
                rowHeader(width);
            }
        }
    }

    public static int getDate() {
        return 1;
    }
}
