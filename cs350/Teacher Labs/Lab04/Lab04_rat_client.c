// CS 350, Spring 2015
// Lab 4
// Rational number structure
//

#include <stdio.h>
#include "Lab04_rational.h"

// main program declares and prints some rationals
//
int main() {
	Rational r1_val, *r1 = &r1_val; set_rat(r1, 9, 0);	// 0/1
	Rational r2_val, *r2 = &r2_val; set_rat(r2, 0, -1);	// 0/1
	Rational r3_val, *r3 = &r3_val; set_rat(r3, 12, 8);	// 3/2
	Rational r4_val, *r4 = &r4_val; set_rat(r4, 24, -9);	// -8/3

	printf("\nr1 = 9/0 = "); print_ratio(r1);
	printf("\nr2 = 0/-1 = "); print_ratio(r2);
	printf("\nr3 = 12/8 = "); print_ratio(r3);
	printf("\nr4 = 24/-9 = "); print_ratio(r4);
	printf("\n");

	printf("\nprinting ");  print_ratio(r4);  printf(" to");
	printf("\n  0 places: "); print_rat(r4, 0);
	printf("\n  1 place : "); print_rat(r4, 1);
	printf("\n 10 places: "); print_rat(r4, 10);
	printf("\n 99 places: "); print_rat(r4, 99);
	printf("\n");
	
	add_rat(r1, r2);
	printf("\nAfter r1 += r2, r1 = "); print_ratio(r1);
	mul_rat(r3, r3);
	printf("\nAfter r3 *= r3, r3 = "); print_ratio(r3);
	sub_rat(r3, r4);
	printf("\nAfter r3 -= r4, r3 = "); print_ratio(r3);
	div_rat(r4, r4);
	printf("\nAfter r4 /= r4, r3 = "); print_ratio(r4);	
	printf("\n");
}
