// ** Skeleton version 2015-01-14 ** <-- remove this line and complete the program
// *** Your name and section (replace this with the correct information ***
// (Also rename this file to Lab01_YourName_Section.c and remove this comment line)
// (Work on alpha.cs.iit.edu; compile with gcc -Wall -std=c99 -lm filename.c.)

// CS 350, Spring 2015
// Lab 1: Fix syntax & semantics errors
//
// Illinois Institute of Technology, (c) 2014, James Sasaki

#include <stdio.h>	// to access printf, scanf
#include <strlen.h>	// to access strlen

int main() {
	printf("CS 350 Lab 1 for %s\n\n", "***Fill in your name and section ***");  // <-- Fill in your name and section and remove this comment

	// Declare and initialize an array of length 7; calculate and
	// print the sum and average of the array
	int n = 7, a[7] = {1, 3, 5, 7, 8, 27, 87}
	int sum;

	for (i = 1; i < n; i++) {
		sum = a[i];
	}
	printf("The sum of the array is %d; its average is %f\n,
		sum, sum/n );

	// A string is like an array of characters, and characters are
	// represented by integers; we'll calculate and print the sum
	// and average of the integers
	//
	char str[] = "myxkptlk 37?#_MXY";
	n = strlen(string);
	sum = 0;
	for (int i = 0; i < n; i++) {
		sum += str[i];
	}
	printf("The sum of the string's characters is $d; its average is %f\n\n",
		sum, sum/n );

	// Declare an integer variable y and initialize it using a
	// hexadecimal constant. Print y in decimal, hex, with leading
	// spaces, and leading zeros so that we get the output
	//
	// y = 4011 = fab = 0xfab =      fab = 00000fab
	//
	int y = xabcd;
	printf("%d = %x = #%x", y, y, y, y);

	// Repeat, but this time prompt for the integer first and read
	// it in.  Keep repeating until the user enters a zero.
	//
	printf("Enter an integer: %d");
	scanf("%d", y);					// Read in the integer
	while (y = 0); 
		printf("%d = %x = %#x\n", y)
		printf("Enter an integer (0 to quit): ");
	}
	printf("Quitting");
	Return 0;
}
