// ** ** <-- remove this line and complete the program
// *** Ayesha Ahmed CS350-01 (replace this with the correct information ***
// (Work on alpha.cs.iit.edu; compile with gcc -Wall -std=c99 -lm filename.c.)

// CS 350, Spring 2015
// Lab 1: Fix syntax & semantics errors
//
// Illinois Institute of Technology, (c) 2014, James Sasaki

#include <stdio.h>    // to access printf, scanf
#include <string.h>   // to access strlen

int main() {
    printf("CS 350 Lab 1 for %s\n\n", "Ayesha Ahmed CS350-02");

    // Declare and initialize an array of length 7; calculate and
       // print the sum and average of the array
    int n = 7, a[7] = {1, 3, 5, 7, 8, 27, 87};
    int sum = 0, prev, c, i;
    double avg;

	      for (i = 0; i < n; i++) {
	      	     prev = a[i];
		     sum = sum + prev;
	      }avg = sum/n;
				printf("The sum of the array is %d. Its average is %.2f\n",
					    sum, avg );

					    // A string is like an array of characters, and characters are
					    // represented by integers; we'll calculate and print the sum
					    // and average of the integers
					    //
					    char str[] = "myxkptlk 37?#_MXY";
					    n = strlen(str);
					    sum = 0;
					    for (i = 0; i < n; i++) {
					    	sum += str[i];
					    }avg = sum/n;
						printf("The sum of the string's characters is %d; its average is %.2f\n\n",
							    sum, avg );

							    // Declare an integer variable y and initialize it using a
							    // hexadecimal constant. Print y in decimal, hex, with leading
							    // spaces, and leading zeros so that we get the output
							    //
							    // y = 4011 = fab = 0xfab =      fab = 00000fab
							    //
						
						int y = 0xabcd;
						printf("%d = %x = %#x = \t%x = %08x\n", y, y, y, y, y);

							    // Repeat, but this time prompt for the integer first and read
							    // it in.  Keep repeating until the user enters a zero.
							    //
							    printf("Enter an integer: ");
							    scanf("%d", &c);                // Read in the integer
							    printf("Your number is: %d\n", c);
							    while (c != 0){ 
							      printf("%d = %x = %#x\n", y, y, y);
								  	     printf("Enter an integer (0 to quit): ");
									     scanf("%d", &c);
									     printf("Your number is now: %d\n\n", c);
									     }
									     printf("Quitting\n");
									     return 0;
}
