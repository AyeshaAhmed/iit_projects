
 //The following program is the first program I wrote in Java. 
//Date: 08/20/13
//Author: Ayesha Ahmed
//This program will display the following message to the terminal screen: “Hello CSC 156!!!”. 
import java.util.*; 
public class Lab1{ 
public static void main(String[] args){ 
System.out.println("Hello CSC 156!!!"); 
int num1;
int num2;
int num3;
int sum;
Scanner console = new Scanner(System.in);
System.out.print("Enter first number: ");
num1 = console.nextInt();
System.out.print("Enter second number: ");
num2 = console.nextInt();
System.out.print("Enter third number: ");
num3 = console.nextInt();
sum = num1 + num2 + num3;
System.out.println("The sum of your numbers is " + sum + ".");
} 
}
