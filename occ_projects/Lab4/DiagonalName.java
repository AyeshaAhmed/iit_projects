// Lab4 Java 156
// 9/17/13
// Author: Ayesha Ahmed
// Diagonal Name
import java.util.*;
public class DiagonalName{
    static Scanner console = new Scanner(System.in);
    public static void main(String[] args){
        String name, spaces;
        int length, spaceLocation;
        
        System.out.println("Please enter your first and last names: ");
        name = console.nextLine().toUpperCase();
        length = name.length();
        spaceLocation = name.indexOf(' ');
        spaces = " ";
        for(int i = 0; i<name.length(); i++){
                if((i-spaceLocation)*2 != 10)
                    if((i*2+1) != 11)
                        spaces += " ";
                if(i < spaceLocation)
                        System.out.println((i*2+1) + spaces + name.charAt(i));
                else if(i == spaceLocation)
                           System.out.println();
                else
                        System.out.println((i-spaceLocation)*2 + spaces + name.charAt(i));
                  }
}
}