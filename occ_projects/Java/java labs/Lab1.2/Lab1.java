//The following program is the first program I wrote in Java.
//Date:
//Author:
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
System.out.print("Enter your first number: ");
num1 = console.nextInt();
System.out.print("Now enter a second number: ");
num2 = console.nextInt();
System.out.print("Just go ahead and enter a third number: ");
num3 = console.nextInt();
sum = num1 + num2 + num3;
System.out.println("The sum of your numbers is " + sum + ".");
}
}