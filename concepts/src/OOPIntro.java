/**
 * Brian Teachman
 * CS 140: Whatcom Community College
 * 11/20/2017
 */
public class OOPIntro {
    public static int add(int... nums) {
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }
        return sum;
    }
}
