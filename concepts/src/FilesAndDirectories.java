import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

/**
 * Brian Teachman
 * CS 140: Whatcom Community College
 * 11/13/2017
 */
public class FilesAndDirectories {

    public static void main(String[] args) throws Exception {

        // Free text samples: http://www.gutenberg.org/
        String filePath = "C://Users/bteachman/cs140-java";
//        File file = new File(filePath + "/data/whizbang.txt");
//        File file = new File( "data/whizbang.txt");

        File file = new File( "data/lines-double.txt");


        Scanner fin = new Scanner(file);
        double sum = 0.0;
        int count = 0;
        while (fin.hasNext()) {
            sum += fin.nextDouble();
            count++;
//            System.out.println(fin.nextLine());
        }
        System.out.printf("The avereage: %.2f", sum/count);

        // Check out:
        // FileReader and BufferedReader
//        FileReader fr = new FileReader(file);
//        BufferedReader br = new BufferedReader(fr);
    }
}
