import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.Random;

/**
 * Brian Teachman
 * CS 140: Whatcom Community College
 * 11/14/2017
 *
 * lab6: File parsing and creating
 */
public class Lab6_Buffered {
    /*
    Step 1:  Create a program that will generate 1000 random Strings of 7
    length and place them into one file. The file that this program will
    create is to be the data source for the second part.

    Step 2: Read in the file you created in part 1. Sort the strings into
    three other files. Words beginning with A-G || a-g should be written
    to the first file. Words H-T || h-t should be written into the second
    file. Reverse all other words and place them into the third file.

    Print to console the number of words in the longest of the files.
    */

    public static void main(String[] args) throws Exception {

        /* ------------------------------------------------------------------
        Step 1:  Create a program that will generate 1000 random Strings of 7
        length and place them into one file. The file that this program will
        create is to be the data source for the second part.
        ------------------------------------------------------------------ */
        File rwords = new File("rand_test.txt");

        // Step 1
        PrintStream ps = new PrintStream(rwords);
        int i = 1;
        while (i <= 1000) {
            ps.print(Lab6.randomWord());
            if (i % 10 == 0) {
                ps.println();
            }
            i++;
        }

        /* ------------------------------------------------------------------
        Step 2: Read in the file you created in part 1. Sort the strings into
        three other files. Words beginning with A-G || a-g should be written
        to the first file. Words H-T || h-t should be written into the second
        file. Reverse all other words and place them into the third file.
        ------------------------------------------------------------------ */
        BufferedReader reader = new BufferedReader(new FileReader("rand_words.txt"));
        while (reader.ready()) {
            String word = reader.readLine();

        }
    }
}
