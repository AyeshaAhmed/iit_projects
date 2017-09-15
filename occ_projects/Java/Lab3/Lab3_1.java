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
                double newSalary;
                double oldSalary;
                double rate;
                double increase;
                int year;
                
                Scanner inFile = new Scanner(new FileReader("Lab3_input.txt"));
                
                System.out.println("First Name \tLast Name \t1st Year Salary : Increase \t2nd Year Salary : Increase \t3rd Year Salary : Increase \t4th Year Salary : Increase \t5th Year Salary : Increase \t6th Year Salary : Increase \t7th Year Salary : Increase \t8th Year Salary : Increase \t9th Year Salary : Increase \t10th Year Salary : Increase"); 
               //loop structure
                while(inFile.hasNext()){
                lastName = inFile.next();
                firstName = inFile.next();
                
                salary = inFile.nextDouble();
                rate = inFile.nextDouble()/100;
                
                oldSalary = salary;
                
                System.out.printf("%n");
                System.out.printf("%-15s %-15s ", firstName, lastName);
                                
                 for(int N=0; N<10; N++){
                  newSalary = salary * Math.pow((1 + rate), N);
                  increase = newSalary - oldSalary;
                  oldSalary = newSalary;
                                    
                  System.out.printf("$%-14.2f : $%-9.2f    ", newSalary, increase);
                }
                
            }
                inFile.close();
        }
    }
