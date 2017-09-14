//Fibonacci using recursion!
//Java 156 - chapter 13?
//Ayesha Ahmed
//11-26-2013
import java.util.*;
public class Fib{
    public static void main(String[] args){
        System.out.println("Enter the index of the element (starting at 1): ");
        Scanner input = new Scanner(System.in);
        double index = input.nextDouble();
        System.out.println(fib(index-1));
    }
    public static double fib(double i){
        if(i == 0 || i == 1){//Base Case
            return 1;
        }else
            return fib(i-1) + fib(i-2);
        }
    }
            