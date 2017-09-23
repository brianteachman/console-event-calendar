package clsnotes;

/*
   Brian Teachman
   CS 140
   9/22/2017
*/
public class DrawTextShapes {

   public static void main(String[] args) {
      printTriangle();
   }

   public static void printTriangle() {
      // I know this is redundant and contrived, however...
      for (int i=0; i<6; i++) {
         switch (i) {
            case 0:
               System.out.println("     ^");
               break;
            case 1:
               System.out.println("    / \\");
               break;
            case 2:
               System.out.println("   /   \\");
               break;
            case 3:
               System.out.println("  /  _  \\");
               break;
            case 4:
               System.out.println(" /  | |  \\");
               break;
            case 5:
               System.out.println("-----------");
               break;
         }
      }
   }
}