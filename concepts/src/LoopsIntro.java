/**
 * Created by BTEACHMAN on 9/27/2017.
 */
public class LoopsIntro {
    public static void main(String[] args) {

        int bookCount = 56;

//        for (int i=1; i<bookCount+1; i++ ) {
//            System.out.println("I have "+i+" books.");
//        }
        for (double d=1.0; d<2e10; d=(d*5)+1.3456 ) {
            System.out.println("I have "+d+" books.");
        }
        System.out.println("Finished counting books.");

        System.out.println();

        for (char c = 'a'; c <= 'z'; c++) {
            System.out.print(c+" ");
        }
        System.out.print("\nNow I know my ABC's!\n");

        for (int i = 97; i <= 122; i++) {
            System.out.print((char)i+" ");
        }
        System.out.print("\nReady.\n");

        for (int i = 122; i >= 97; i--) {
            System.out.print((char)i+" ");
        }
        System.out.print("\nThere you go ocifer.\n");

        for (int i = 97; i <= 122; i++) {
            System.out.print((i-96)+". "+(char)i+" \n");
        }
        System.out.print("\nAs a table.\n");

        // nested loops
        for (int i=0; i<7; i++) {
            for (int j=0; j<i; j++) {
                System.out.print("*");
            }
            System.out.println();
        }
    }
}
