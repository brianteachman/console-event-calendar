public class App {

    public static void main(String[] args) {
        System.out.println("Your calandar, sir.");

        // get todays date
//        Calendar cal = new Calendar();
//        cal.get(Calendar.MONTH);

        rowHeader(5);
        rowContent(5);
        rowHeader(5);
    }

    public static void rowHeader(int width) {
        for (int i=0; i<7; i++) {
            for (int j = 0; j < width; j++) {
                System.out.print('=');
            }
        }
        System.out.print("=\n");
    }

    public static void rowContent(int width) {

        for (int i = 0; i < 3; i++) {
            System.out.print("|");
            for (int j = 0; j < 7; j++) {
                for (int k = 0; k < width; k++) {
                    if (k == 1) {
                        System.out.print('x');
                    }
                    else if (k == (width-1) ) {
                        System.out.print("|");
                    }
                    else {
                        System.out.print(' ');
                    }
                }
            }
            System.out.print("\n");
        }
    }
}
