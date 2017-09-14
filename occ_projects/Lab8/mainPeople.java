//Ivan Temesvari
//CSC 156-050
//Spring '08
//This is a driver program to test the class People.

import java.awt.*;
import javax.swing.*;

public class mainPeople{

    public static void main(String[] args){ 
	int[] my_labs = new int[10];
	People[] java_class = new People[5];
	for(int i = 0; i < my_labs.length; i++)
	    my_labs[i] = 100;
	//We have to create a new People for each of the 5 People in the array java_class
	java_class[0] = new People("Ivan",'A',my_labs);


	//Read input from the user in a Frame: names (5 People), and lab scores.
	//Update that People with the name and lab scores and calculate their grade; update their grade.
	//Then output a message identifying that persons name and lab grade average.

	System.out.println("Hello: " + java_class[0]);

    }
}
