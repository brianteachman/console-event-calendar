import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.Random;
import java.util.Scanner;

/**
 * Brian Teachman
 * CS 140: Whatcom Community College
 * 11/14/2017
 *
 * lab6: File parsing and creating
 */
public class Lab6 {
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
        Random rgen = new Random();

        // Step 1
        File fin = new File("rand_words.txt");
        PrintStream ps = new PrintStream(fin);
        int i = 1;
        while (i <= 1000) {
            ps.print(randomWord(rgen));
//            System.out.print(randomWord(rgen));
            if (i % 10 == 0) {
                ps.println();
//                System.out.println();
            }
            i++;
        }

        /* ------------------------------------------------------------------
        Step 2: Read in the file you created in part 1. Sort the strings into
        three other files. Words beginning with A-G || a-g should be written
        to the first file. Words H-T || h-t should be written into the second
        file. Reverse all other words and place them into the third file.
        ------------------------------------------------------------------ */
        Scanner reader = new Scanner(fin);
        PrintStream atog = new PrintStream(new File("atog.txt"));
        PrintStream htot = new PrintStream(new File("htot.txt"));
        PrintStream utoz = new PrintStream(new File("utoz.txt"));
        int sum1 = 0;
        int sum2 = 0;
        int sum3 = 0;
        while (reader.hasNext()) {
            String word = reader.next();
            if (word.toLowerCase().charAt(0) >= 'a'
                    && word.toLowerCase().charAt(0) <= 'g') {
                atog.append(word + ' ');
                sum1++;
            }
            else if (word.toLowerCase().charAt(0) >= 'h'
                    && word.toLowerCase().charAt(0) <= 't') {
                htot.append(word + ' ');
                sum2++;
            }
            else if (word.toLowerCase().charAt(0) >= 'u'
                    && word.toLowerCase().charAt(0) <= 'z') {
                String rword = "";
                for (int j = 0; j < word.length(); j++) {
                    rword += word.charAt((word.length()-1)-j);
                }
                utoz.append(rword + ' ');
                sum3++;
            }
        }

        System.out.println(sum1 + " " + sum2 + " "+sum3);


        // Print out which file has the largest count
        if (sum1 < sum2) {
            if (sum2 < sum3) {
                System.out.println("The file utoz.txt has the greatest word count. ("+sum3+" words)");
            }
            else {
                System.out.println("The file htot.txt has the greatest word count. ("+sum2+" words)");
            }
        }
        else {
            if (sum1 > sum3) {
                System.out.println("The file utot.txt has the greatest word count. ("+sum3+" words)");
            }
            else {
                System.out.println("The file utot.txt has the greatest word count. ("+sum2+" words)");
            }
        }
    }

    private static String randomWord(Random rgen) {
        StringBuilder s = new StringBuilder();
        for (int i=0; i < 7; i++) {
            int rchar = rgen.nextInt(26);
            int rmod = 1+ rgen.nextInt(5);
            int out ;
            if (i % rmod == 0) {
                out = 65 + rchar;
            }
            else {
                out = 97 + rchar;
            }
            s.append((char) out);
        }
        s.append(" ");
        return s.toString();
    }
}
