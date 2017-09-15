// Lab6 - Palindrome - Java 156
// Author: Ayesha Ahmed
// Date: 10/15/13

import java.util.*;
import java.lang.*;

public class Lab6_1{
    static Scanner console = new Scanner(System.in);
    public static void main(String[] args){
        boolean value;
        String checkString, entry;
        int checkNum;
        char testChar;
        
        System.out.println("Palindrome Identifier");
        System.out.println("Enter a word or positive integer");
        System.out.print("to find out if it is a Palindrome: ");
        entry = console.nextLine();
        testChar = entry.charAt(0);
                
        if (Character.isLetter(testChar)){
                checkString = entry;
                System.out.println();
        
                value = isPalindrome(checkString);
        
                if (value == true){
                    System.out.println("Yes, the word " + checkString + " is a Palindrome!");
                }else
                    System.out.println("No, the word " + checkString + " is not a Palindrome.");
        
                }else if (Character.isDigit(testChar)){
                    checkNum = Integer.parseInt(entry);
                    System.out.println();
        
                    value = isPalindrome(checkNum);
        
                    if (value == true){
                        System.out.println("Yes, the number " + checkNum + " is a Palindrome!");
                    }else
                        System.out.println("No, the number " + checkNum + " is not a Palindrome.");
        
                    }else 
                        System.out.println("Invalid entry please try again.");
    }
    
    public static boolean isPalindrome(String str){
        int length = str.length(), j = length - 1, i;
        String lowerCase = str.toLowerCase();
        
        for (i = 0;i <= (length - 1) / 2;i++){
            if (lowerCase.charAt(i) != lowerCase.charAt(j)){
                return false;
            }
            j--;
        }
        return true;
    }
    
    public static boolean isPalindrome(int num){
        int newnum, remainder, countx = 0, mod, i;
        double backwards = 0;
        
        newnum = num;
        
        do{
            remainder = num % 10;
            newnum = (newnum - remainder)/10;
            countx++;
        }while (newnum > 0);
            newnum = num;
        for (i = countx;i > 0;i--){
            mod = newnum % 10;
            backwards = backwards + (mod * Math.pow(10.0, i-1));
            newnum = (newnum - mod) / 10;
        }
        if (backwards != num){
                return false;
            }else
                return true;
    }
}