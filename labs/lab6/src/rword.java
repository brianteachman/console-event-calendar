/**
 * Brian Teachman
 * CS 140: Whatcom Community College
 * 11/14/2017
 * <p>
 * lab6: PACKAGE_NAME
 */
public class rword {
    public static void main(String[] args) {
        String word = "Hello";
        String rword = "";
        for (int j = 0; j < word.length(); j++) {
            rword += word.charAt((word.length()-1)-j);
        }
        System.out.println(rword);
    }
}
