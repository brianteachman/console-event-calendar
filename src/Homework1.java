/**
 * Created by BTEACHMAN on 9/25/2017.
 */
public class Homework1 {

    public static void fig1(){
        bar();
        cross();
    }

    public static void fig2(){
        fig1();
        bar();
    }

    public static void star(){
        System.out.println("  *");
        System.out.println("  *");
        System.out.println("  *");
        fig1();
    }

    public static void bar(){
        System.out.println("*****");
        System.out.println("*****");
    }

    public static void cross(){
        System.out.println(" * * ");
        System.out.println("  *  ");
        System.out.println(" * * ");
    }

    public static void main(String[] args){

/*        fig1();
        System.out.println();
        fig2();
        System.out.println();
        star();*/


    }
}
