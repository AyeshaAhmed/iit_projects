import java.util.*;

public class Lab7_ex6_test{
    public static void main(String[] args){
        Scanner console = new Scanner(System.in);
        int FirstNum, SecondNum, ThirdNum;
        double FirstD, SecondD, ThirdD, FirstPow, SecondPow, ThirdPow, SumPow;
        boolean value;
        
        System.out.println();
        System.out.println("Enter any 3 integers to check if" + 
                        "\nthe sum of the first and second number to the quantity squared" + 
                        "\nequals the third number to the quantity squared.");
        System.out.println();
        System.out.print("Enter your first number: ");
        FirstNum = console.nextInt();
        System.out.print("Enter your second number: ");
        SecondNum = console.nextInt();
        System.out.print("Enter your third number: ");
        ThirdNum = console.nextInt();
        System.out.println();
        
        value = isPythag(FirstNum, SecondNum, ThirdNum);
        FirstD = beDouble(FirstNum);
        SecondD = beDouble(SecondNum);
        ThirdD = beDouble(ThirdNum);
        
        FirstPow = Math.pow(FirstD, 2.0);
        SecondPow = Math.pow(SecondD, 2.0);
        ThirdPow = Math.pow(ThirdD, 2.0);
        
        SumPow = FirstPow + SecondPow;
        
                    if (value == true){
                        System.out.println("Yes,the sum of the first and second number to the quantity squared" + 
                                            "\nequals the third number to the quantity squared.");
                        System.out.print("Let's see how it figures out: ");
                        System.out.printf("%n(%d * %d) + (%d * %d) ?= (%d * %d)", FirstNum, FirstNum, SecondNum, SecondNum, ThirdNum, ThirdNum);
                        System.out.printf("%n%.0f + %.0f ?= %.0f", FirstPow, SecondPow, ThirdPow);
                        System.out.printf("%n%.0f = %.0f", SumPow, ThirdPow);
                        System.out.println();
                        System.out.print("Correct!");
                    }else{
                        System.out.println("No,the sum of the first and second number to the quantity squared" + 
                                            "\nDOES NOT equal the third number to the quantity squared.");
                        System.out.print("Let's see how it figures out: ");
                        System.out.printf("%n(%d * %d) + (%d * %d) ?= (%d * %d)", FirstNum, FirstNum, SecondNum, SecondNum, ThirdNum, ThirdNum);
                        System.out.printf("%n%.0f + %.0f ?= %.0f", FirstPow, SecondPow, ThirdPow);
                        System.out.printf("%n%.0f ?= %.0f", SumPow, ThirdPow);
                        System.out.println();
                        System.out.print("Incorrect.");
                        }
        
    }
    public static boolean isPythag (int x, int y, int z){
        boolean value;
        double First, Second, Third, Check1, Check2;
        First = x;
        Second = y;
        Third = z;
        Check1 = (Math.pow(First, 2.0))+ (Math.pow(Second, 2.0));
        Check2 = (Math.pow(Third, 2.0));
        value = (Check1 == Check2);
                if (value == true){
                    return true;
                }else                
                    return false;
    }
    public static double beDouble (int x){
        double ConDouble;
        ConDouble = x;
        return ConDouble;
    }
}