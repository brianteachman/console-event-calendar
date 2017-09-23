package clsnotes;

public class Figures1 {
    public static void main(String[] args) {
        egg();
        System.out.println();
        
        plantPot();
        System.out.println();
        
        stopSign();
        System.out.println();
        
        hat();
    }
    
    public static void egg() {
        top();
        bottom();
    }
    
    public static void plantPot() {
        bottom();
        System.out.println("+--------+");
    }
    
    public static void stopSign() {
        top();
        System.out.println("|  STOP  |");
        bottom();
    }
    
    public static void hat() {
        top();
        System.out.println("+--------+");
    }
    
    public static void top() {
        System.out.println("  ______");
        System.out.println(" /      \\"); // notice escapes
        System.out.println("/        \\");
    }
    public static void bottom() {
        System.out.println("\\        /"); // notice escapes
        System.out.println(" \\______/");
    }
}
