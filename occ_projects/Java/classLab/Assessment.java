//Ayesha Ahmed
//11-19-13

import java.util.*;

public class Assessment{
    public static void main(String[] args){
        Scanner console = new Scanner(System.in);
        System.out.println("Enter any 30 integers.");
        boolean value;
        double prevSum = 0, nowSum;
        for (int i=0; i<30; i++){
            double input = console.nextInt();
            value = isEven(input);
            if (value == true){
                nowSum = prevSum + input;
                System.out.println("The even number you entered is: " + input);
                System.out.println("The sum of the even numbers entered thus far is: " + nowSum);
                prevSum = nowSum;
            }
                System.out.println("Please enter another number: ");
                System.out.println();
        }
        System.out.println("You have reached the limit of 30 integers!");
        System.out.println("Have a nice day!");
    }
    
    public static boolean isEven(double num){
        double checkNum, remain;
        checkNum = num/2;
        remain = checkNum%1;
            if (remain == 0.5){
                return false;
            }
        return true;
    }
}