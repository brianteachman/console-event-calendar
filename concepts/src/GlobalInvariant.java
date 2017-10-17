/**
 * Brian Teachman
 * CS 140: Whatcom Community College
 * 10/17/2017
 *
 * concepts:
 */
public class GlobalInvariant {

    static int thing = 5;

    public static void main(String[] args) {
        foo();
        bar();
    }

    private static void bar() {
        for (int i=0; i < thing; i += 2) {
            System.out.println("foo");
        }
    }

    private static void foo() {
        for (int i=0; i < thing; i++) {
            System.out.println("baz");
        }
    }
}
