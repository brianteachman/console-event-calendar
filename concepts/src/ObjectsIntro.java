/**
 * Brian Teachman
 * CS 140: Whatcom Community College
 * 10/6/2017
 *
 * concepts: ObjectsIntro
 */
public class ObjectsIntro {

    public static void main(String[] args) {
        // http://docs.oracle.com/javase/8/docs/api/java/lang/String.html

        String name = "Brian Teachman";
        System.out.println(name);
        int len = name.length();
        System.out.println(len);

        System.out.println(name.substring(7));
        System.out.println(name.substring(3, 9)); // from index 3 to 10

        int firstI = name.indexOf('a');
        System.out.println(firstI); // first occurrence == 3
        System.out.println(name.indexOf('a', firstI+1)); // find next occurrence == 8

        name = name.toUpperCase();
        System.out.println(name);
        System.out.println(name.substring(2, name.length()));

        System.out.println(name == "Brian Teachman");
        System.out.println(name.equalsIgnoreCase("Brian Teachman"));

        System.out.println(name.replace("A", "Brian"));

        // --------------------------------------------------------------------

    }
}
