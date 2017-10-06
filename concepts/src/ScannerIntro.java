import java.util.Scanner;
//import java.util.* // imports entire library
/**
 * Brian Teachman
 * CS 140: Whatcom Community College
 * 10/6/2017
 * <p>
 * concepts: PACKAGE_NAME
 */
public class ScannerIntro {
    /*
    * Echo out console input
    */
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter some text: \n>>>");
        String line = input.nextLine();
        System.out.println("Echo: " + line);
//        for(;true;) {
//            System.out.println("Echo: " + line);
//        }
    }
}
