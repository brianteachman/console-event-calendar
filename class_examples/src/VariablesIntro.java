/**
 * Created by BTEACHMAN on 9/26/2017.
 */
public class VariablesIntro {

/*    int someNum; // defaults to 0
    boolean isWarpSpeed; // defaults to false
    char firstLetter; // defaults to '\u0000'
    double howFast; // defaults to 0.0*/

    //  boolean isSet = null;

    public static void main(String[] args) {
        int lifeNumber = 42;
        showNumber(lifeNumber);
        showMessage(lifeNumber);

        double pizzaSlice = 5.67;
        pizzaSlice = lifeNumber; //OK:     int -> double
//        lifeNumber = pizzaSlice; //NOT OK: double -> int
        lifeNumber = (int) pizzaSlice; //OK: cast double -> int
        showNumber(lifeNumber);

        showNumber('r');

//        String someData = 'x'; // char -> string
        String someData1 = 'x'+"";
    }

    private static void showMessage(int lifeNumber) {
        // concatenation
        System.out.println("The meaning of life is " + lifeNumber + ".");
    }

    private static void showNumber(int lifeNumber) {
        System.out.println(lifeNumber);
    }

    private static void showString(String lifeNumber) {
        System.out.println(lifeNumber);
    }
}
