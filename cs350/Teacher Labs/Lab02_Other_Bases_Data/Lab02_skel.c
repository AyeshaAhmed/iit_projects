// *** SKELETON -- Replace the STUB comments with real code ****

// CS 350, Spring 2015
// Lab 2 -- Converting an integer to a given base
//
// Illinois Institute of Technology, (c) 2014, James Sasaki
//

// This program repeatedly converts a value to a given base.
// The value is converted to an equivalent array of digits
// (each >= 0 and < base), and then we print the array.
// We convert value & base pairs until the value is < 1 or base < 2.

#include <stdio.h>
#include <strings.h>

// Define array length to be large enough to hold the
// longest possible output (max positive int in base 2).
//
#define ARRAYLEN 32

int main() {
	printf("CS 350 Lab 2 for %s\n\n", "*** STUB ***");

	// Read an integer value to convert and the base to
	// convert it to 
	//
	int value, base;
	printf("Enter an integer and base:\n");
	printf("(int >= 1 and 2 <= base <= 36 or we quit): ");
	scanf("%d %d", &value, &base);

	// Process the value and base input and prompt for and
	// read in another value and base.  Repeat until the the
	// value is < 1 or the base is < 2 or > 36.
	//
	while ( /* *** STUB *** */ ) {
		// Convert the value into the desired base and put the
		// result into digit[0..column-1].  In general, we've
		// calculated the digits whose powers are less than the
		// current column, with to_convert as the unconverted
		// part of the original value.
		// 
		// value == to_convert * (base ^ column) + digits calculated so far
		// with column_value = base ^ column
		//
		int column = 0, to_convert = value;
		int digit[ARRAYLEN];

		// *** STUB ***
		
		if (column >= ARRAYLEN || to_convert < 0) {
			printf("Ran out of room or to_convert is < 0");
			printf(" -- This should never happen!\n");
		}
		else {
			// Print out the digits (note they're stored in
			// reverse: digit[0] is the rightmost digit,
			// ..., digit[column-1] is the leftmost digit
			//
			char prt_digits[37] = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
			// *** STUB ***
		}
		printf("\n");

		// Get next value, base pair to process
		//
		
		// *** STUB ***
	}
}
