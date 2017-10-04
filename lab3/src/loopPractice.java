/**
 * Brian Teachman
 * CS 140: Whatcom Community College
 * 10/3/2017
 *
 * lab3:
 */

class loopPractice{

   public static void main(String[] args){
      System.out.println("Problem 1:");
      countDown3();
      
      System.out.println("Problem 2:");
      countDownX(43,5);
      countDownX(108,4);
      
      System.out.println("Problem 3:");
      printABC();
      
      System.out.println("Problem 4:");
      pow(6,4);
      pow(2,10);
      
      System.out.println("Problem 5:");
      decCount();
      
      System.out.println("Problem 6:");
      printBox(15);
   }
   
   //Change the functionality of the folowing method.
   //Cause it to count down from 19 by threes. 
   //Use a for loop and print all numbers on the same line. Separate the numbers by a space
   public static void countDown3(){
      int start = 19;
      for (int i=19; i>0; i-=3) {
         System.out.println(i);
      }
      System.out.println();
   }
   
   //Change the functionality of the following function.
   //Cause it to count down from start by x. 
   //Use a for loop and print all numbers on the same line. Separate the numbers by a space
   public static void countDownX(int start, int x) {
      for (int i=start; i>0; i-=x) { // start at given value and decrement by x
         System.out.print(i);
         System.out.print(' ');
      }
      System.out.println();
   }
   
   //After running this program the first time, You should see that the line in the edit block
   //prints the 'a' character. Use this to print out the entire alphabet. 
   public static void printABC(){
       // starting at the ascii code for a, increment and display up to z
      for (int i='a'; i<'z'+1; i++ ) {
         System.out.print(((char)i) + " ");
      }
      System.out.println();
   }
   
   //In the edit block, write a loop where total = a^b by the end of the loop.
   public static void pow(int a, int b){
      int total = a;
      // total already accounts for one 'a', range is 1 through b-1
      for (int i=1; i<b; i++) {
         total *= a; // total equals a * a, b number of times
      }
      System.out.println(total);
      System.out.println();
   }
   
   //This method will print out the sequence 999999999888888887777777666666555554444333221.
   // It will be useful to use nested for loops to solve this problem.
   public static void decCount(){
      for (int i=9; i>0; i--) {
         for (int j=i; j>0; j--) {
            System.out.print(i);
         }
      }
      System.out.println();
   }

   //The following method prints a box. However it is not scalable by size.
   //Every run of the function the box will be the same size and the size
   //variable will not effect it.
   //
   //Together in class we will come up with a solution using nested for loops
   //that will allow us to resize the box.
   public static void printBox(int size){
//      System.out.println("************");
//      System.out.println("|          |");
//      System.out.println("|          |");
//      System.out.println("|          |");
//      System.out.println("|          |");
//      System.out.println("|          |");
//      System.out.println("************");

       printRowHeader(size);
       for (int i=0; i<5;i++) {  // cell is five rows of empty space
          System.out.print("|"); // start cell with a pipe (|)
          for (int j = 0; j < size - 2; j++) { // cell width minus start and end glyph
              System.out.print(" ");
          }
          System.out.println("|"); // end cell with a pipe (|)
      }
       printRowHeader(size);
   }

    // Print row of stars some given size wide
   public static void printRowHeader(int size) {
       for (int i=0; i<size; i++) {
           System.out.print("*"); // print row of '*', size wide
       }
       System.out.println(); // new line
   }
}