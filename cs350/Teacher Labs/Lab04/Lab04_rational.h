// CS 350, Spring 2015
// Lab 4
// Rational number structure

// Lab04_rational.h: header file contains definition of Rational
// structure plus prototypes of Rational functions.
//
#include <stdio.h>

#ifndef RATIONAL_H
#define RATIONAL_H

// A rational number has a numerator dividied by a denominator
//
	typedef struct {
		int numerator;
		int denominator;
	} Rational;

// Prototypes
//
void set_rat(Rational *r, int n, int d);	// Set r to n/d
void copy_rat(Rational *r, Rational *s);	// Set r to s
double value(Rational *r);					// return r as a double

void add_rat(Rational *r, Rational *s);			// Set r to r + s
void sub_rat(Rational *r, Rational *s);			// Set r to r - s
void mul_rat(Rational *r, Rational *s);			// Set r to r * s
void div_rat(Rational *r, Rational *s);			// Set r to r / s

void print_rat(Rational *r, int p);		// Print r to p decimal places
void print_ratio(Rational *r);			// Print r as a ratio num/denom

void normalize(Rational *r);		// Put r into lowest terms
int gcd(int x, int y);				// greatest common divisor

#endif