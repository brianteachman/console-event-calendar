/* ------------------------------------------------------------------
 * Brian Teachman
 * CS 140: Whatcom Community College
 * 11/15/2017
 *
 * Lab 6: File parsing and creating
 ------------------------------------------------------------------ */

import java.io.File;
import java.io.PrintStream;
import java.util.Random;
import java.util.Scanner;

public class Lab6 {

    public static void main(String[] args) throws Exception {

        /* ------------------------------------------------------------------
        Step 1:  Create a program that will generate 1000 random Strings of 7
        length and place them into one file. The file that this program will
        create is to be the data source for the second part.
        ------------------------------------------------------------------ */
        File rwords = new File("rand_words.txt");

        // Step 1
        PrintStream ps = new PrintStream(rwords);
        int i = 1;
        while (i <= 1000) {
            ps.print(randomWord());
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
        Scanner reader = new Scanner(rwords);
        PrintStream atog = new PrintStream(new File("atog.txt"));
        PrintStream htot = new PrintStream(new File("htot.txt"));
        PrintStream utoz = new PrintStream(new File("utoz.txt"));
        int sum1 = 0;
        int sum2 = 0;
        int sum3 = 0;
        while (reader.hasNext()) {
            String word = reader.next();
            char firstChar = word.toLowerCase().charAt(0);
            if (firstChar >= 'a' && firstChar <= 'g') {
                atog.append(word).append(" ");
                if (++sum1 % 10 == 0) atog.append("\n");
            }
            else if (firstChar >= 'h' && firstChar <= 't') {
                htot.append(word).append(" ");
                if (++sum2 % 10 == 0) htot.append("\n");
            }
            else if (firstChar >= 'u' && firstChar <= 'z') {
//                String rword = "";
//                for (int j = 0; j < word.length(); j++) {
//                    rword += word.charAt((word.length()-1)-j);
//                }
                StringBuilder sb = new StringBuilder();
                utoz.append(sb.append(word).reverse().append(" ").toString());
                if (++sum3 % 10 == 0) utoz.append("\n");
            }
        }

        // Print out which file has the largest count
        if (sum1 < sum3 && sum2 < sum3) {
            System.out.println("File utoz.txt has the greatest word count. ("+sum3+" words)");
        }
        else if (sum1 < sum2 && sum3 < sum2) {
            System.out.println("File htot.txt has the largest distribution of random words everytime. ("+sum2+" words)");
        }
        else if (sum2 < sum1 && sum3 < sum1) {
                System.out.println("File atog.txt has the greatest word count. ("+sum1+" words)");
        }
    }

    public static String randomWord() {
        StringBuilder s = new StringBuilder();
        Random rgen = new Random();
        for (int i=0; i < 7; i++) {
            int rmod = 1 + rgen.nextInt(5);
            int rchar = rgen.nextInt(26);
            // tried to even out distribution, ...still the same shape
//            int j = rmod;
//            while (0 < j--) rchar = rgen.nextInt(26);
            int out;
            if (i % rmod == 0 || i % rmod == 2) {
                out = 97 + rchar;
            }
            else {
                out = 65 + rchar;
            }
            s.append((char) out);
        }
        s.append(" ");
        return s.toString();
    }
}
