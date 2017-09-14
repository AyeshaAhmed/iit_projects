//Lab2_2 Java CSC 156.
//Date:8/27/13
//Author:Ayesha Ahmed
import java.util.*;
public class Lab2_2{
public static void main(String[] args){
Scanner console = new Scanner(System.in);
System.out.println("Summer Wage Calculator! - for 5 weeks");
double wage;
double weekHour;
double totalHours;
double grossPay;
double netPay;
double clothes;
double supplies;
double netRemain;
double savings;
double parentGive;
System.out.print("Enter your hourly wage: ");
wage = console.nextDouble();
System.out.print("Enter the number of hours you worked per week: ");
weekHour = console.nextDouble();
totalHours = weekHour * 5;
grossPay = wage * totalHours;
System.out.printf("Your income before tax is: %.2f.",grossPay);
System.out.println();
netPay = grossPay * 86/100;
System.out.printf("Your net income after tax is: %.2f.",netPay);
System.out.println();
clothes = netPay * 10/100;
System.out.printf("The amount you can spend on clothes is: %.2f.",clothes);
System.out.println();
supplies = netPay * 1/100;
System.out.printf("The amount you can spend on supplies is: %.2f.",supplies);
System.out.println();
netRemain = netPay * 89/100;
System.out.printf("The amount of money you have remaining is: %.2f.",netRemain);
System.out.println();
System.out.print("Enter the amount you wish to buy savings bonds with: ");
savings = console.nextDouble();
parentGive = savings * 50/100;
System.out.printf("The amount your parents can spend to buy additional savings bonds for you is: %.2f.",parentGive);
System.out.println();
System.out.println("Thank you for using the Summer Wage Calculator. Have a nice day!");
}
}