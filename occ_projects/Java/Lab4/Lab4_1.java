// Lab4_1 Java 156 
// 9/17/13
// Author: Ayesha Ahmed
// Body Fat Percentage Calculator
import java.util.*;
import java.lang.Math.*;

public class Lab4_1{
    static Scanner console = new Scanner(System.in);
    public static void main(String[] args ){
        double A1, A2, A3, A4, A5, B, bodyWeight, bodyFat, bodyFatPercentage, wrist, waist, hip, forearm;
        char gender;
        System.out.println("Body Fat Percentage");
        System.out.print("Enter your gender type, \nfemale or male: ");
        gender = console.next().charAt(0);
        System.out.println();
        
        switch (gender){
            case 'f':
            case 'F':
                System.out.print("Enter your weight: ");
                bodyWeight = console.nextDouble();
                A1 = (bodyWeight * 0.732) + 8.987;
                System.out.println();
                System.out.print("Enter wrist measurement (at fullest point): ");
                wrist = console.nextDouble();
                A2 = wrist / Math.PI;
                System.out.println();
                System.out.print("Enter your waist measurement (at navel): ");
                waist = console.nextDouble();
                A3 = waist * 0.157;
                System.out.println();
                System.out.print("Enter your hip measurement (at fullest point): ");
                hip = console.nextDouble();
                A4 = hip * 0.249;
                System.out.println();
                System.out.print("Enter your forearm measurement (at fullest point): ");
                forearm = console.nextDouble();
                A5 = forearm * 0.434;
                System.out.println();
                B = A1 + A2 + A3 - A4 + A5;
                bodyFat = bodyWeight - B;
                bodyFatPercentage = (bodyFat * 100) / bodyWeight;
                System.out.printf("Your body fat percentage is: %.2f.", bodyFatPercentage);
                break;
            case 'm':
            case 'M':
                System.out.print("Enter your weight: ");
                bodyWeight = console.nextDouble();
                A1 = (bodyWeight * 1.082) + 94.42;
                System.out.println();
                System.out.print("Enter your waist measurement (at navel): ");
                waist = console.nextDouble();
                A2 = waist * 4.15;
                System.out.println();
                B = A1 - A2;
                bodyFat = bodyWeight - B;
                bodyFatPercentage = (bodyFat * 100) / bodyWeight;
                System.out.printf("Your body fat percentage is: %.2f.", bodyFatPercentage);
                break;
            default:
                System.out.println("Invalid gender type. Please try again.");
            }
        }
    }