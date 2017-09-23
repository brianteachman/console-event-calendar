package clsnotes;

/*
   Brian Teachman
   CS 140
   9/22/2017
*/
public class soMany {

   public static void main(String[] args) {
//       greeting();
      for (int i=0; i<10; i++) {
         greeting("Student " + i);
      }
   }
   
   public static void greeting(String name) {
      System.out.println("Hello " + name);
      System.out.println("Sunny sun");
      System.out.println("You good?");
   }
}