import java.util.Calendar;

// https://docs.oracle.com/javase/8/docs/api/java/util/Calendar.html

public class App {

    public static void main(String[] args) {
        System.out.println("Your calandar, sir.");

        // get todays date
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        System.out.println(month + " : " + day);
//        System.out.println(month);
//        rowContent(5);
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

    public static void rowContent(int width) {

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
}
