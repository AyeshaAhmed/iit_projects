//Lab2_extraCredit Java CSC 156.
//Date:9/10/13
//Author:Ayesha Ahmed

import java.util.*;

public class Lab2_extraCredit{
    public static void main(String[] args){
        Scanner console = new Scanner(System.in);
        System.out.println("Permutation Machine");
        int num, num1, num2, num3;
        System.out.println("This machine will ask you for three numbers \nand display all of their possible permutations.");
        System.out.print("Please enter three numbers: ");
        num = console.nextInt();
        if (num >= 100 && num <= 999){
            num = num;
            num3 = num % 10;
            num2 = ((num - num3)/10) % 10;
            num1 = ((num - ((num2 * 10) + num3))/100) % 10;
            
            System.out.println("There are a total of six permutations for three numbers." + 
            "\nThe six possible permutations for you three numbers are" + "\n1)" + num1 + num2 + num3 + 
            "\n2)" + num2 + num3 + num1 + "\n3)" + num3 + num1 + num2 + "\n4)" + num2 + num1 + num3 +
            "\n5)" + num3 + num2 + num1 + "\n6)" + num1 + num3 + num2 + ".");
            System.out.println("Thank you for using the Permutation Machine!");
            }
                else
                    System.out.println("Invalid entry. Try again.");
        }
    }