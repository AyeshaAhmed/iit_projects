    //Lab3_1 Java 156 - Lab3. 
    //Date: 08/20/13
    //Author: Ayesha Ahmed

	import java.io.*;
	import java.util.*;
	       
    public class Lab3_1{ 
        public static void main(String[] args) throws FileNotFoundException { 
                String firstName;
                String lastName;
                double salary;
                double increase;
                int year;
                
                Scanner inFile = new Scanner(new FileReader("Lab3_input.txt"));
                PrintWriter outFile = new PrintWriter("Lab3_input.txt");
                
                firstName = inFile.next();
                lastName = inFile.next();
                
                outFile.println("Student Name: " + firstName + " " + lastName);

                
                outFile.printf("%10s %10s %10.2f: %10.2f %10.2f");
                
                //loop structure
                while (inFile.hasNext()){
                }
                
                while (inFile.hasNext()){
                }
                //Display the sum.
                
            }
        }
