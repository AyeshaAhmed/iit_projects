// isVowel Lab 5 Java 156
// Date: 10/1/13
// Author: Ayesha Ahmed

public class isVowel{
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