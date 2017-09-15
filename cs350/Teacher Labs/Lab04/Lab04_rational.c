// --> Don't forget to include your name!

// CS 350, Spring 2015
// Lab 4
// Rational number structure
//

#include <stdio.h>
#include "Lab04_rational.h"

// Utility: If x, y > 0, return greatest common divisor
// else return 0
//
int gcd(int x, int y) {
	// gcd of current x, y = gcd of original x, y
	// because gcd(x,y) = gcd(x-y,y) [if x > y] or gcd(x, y-x) [if x < y]
	// and gcd(x,x) = x
	//
	while (x != y) {
		if (x < y)
			y = y-x;
		else
			x = x-y;
	}
	return x;
}

// ... replace this comment with implementations of the other routines ...