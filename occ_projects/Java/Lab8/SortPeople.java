//Lab8 - Excercise7 - sort people - Java 156
//Date: 10/29/13
//Author: Ayesha Ahmed
import java.io.*;
import java.util.*;
public class SortPeople{
    public static void main (String[] args) throws FileNotFoundException {
        int i = 0, min, j = 0, saveindex, saveindex2;
        Scanner inFile = new Scanner(new FileReader("People.txt"));
        PrintWriter outFile = new PrintWriter("SortedPeople.txt");
        String[] names = new String [5];
        int[] numbers = new int [5];
        
        while (inFile.hasNext()){
            names[i] = inFile.next();
            numbers[i] = inFile.nextInt();
            i++;
        }
                
        for (i = 0;i<5;i++){
            saveindex = i;
            min = numbers[i];
            for(j = 0;j < 5;j++){
                if(min > numbers[j]){
                    min = numbers[j];
                    saveindex = j;
                }
            }
            outFile.printf("%s %d%n",names[saveindex], numbers[saveindex]);
            numbers[saveindex] = 1000;
        }
        inFile.close();
        outFile.close();
    }
}