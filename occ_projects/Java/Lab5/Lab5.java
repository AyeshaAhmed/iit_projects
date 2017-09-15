// Lab 5 - vowel counter - using isVowel - Java 156
// Date: 10/1/13
// Author: Ayesha Ahmed

import java.util.*;

public class Lab5{
    static Scanner console = new Scanner(System.in);
    public static void main(String[] args){
        char entry;
        String sentence, lowerCase, entryString;
        int length, count, vowels = 0;
        boolean done;
        
        System.out.println("Vowel Counter");
        System.out.println("Enter a sentence to count how many vowels it has: ");
        sentence = console.nextLine();
        length = sentence.length();
        lowerCase = sentence.toLowerCase();
        System.out.println();
                
        for (count = 0;count < length;count++){
            entry = lowerCase.charAt(count);
            done = isVowel(entry);
                if (done == true){
                    vowels++;
                }    
        }

        System.out.println("There are " + vowels + " vowels in " + sentence + ".");
    }
    public static boolean isVowel (char ch){
        boolean value;
        char charA = 'a', charE = 'e', charI = 'i', charO = 'o', charU = 'u', lowerCase;
        value = (ch == charA) || (ch == charE) || (ch == charI) || (ch == charO) || (ch == charU);
                if (value == true){
                    return true;
                }else                
                    return false;
    }
}
