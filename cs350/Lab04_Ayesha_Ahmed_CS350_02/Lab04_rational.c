// --> Ayesha Ahmed
//
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

void set_rat(Rational *r, int n, int d){
  r -> numerator = n;
  r -> denominator = d;

  normalize (r);
}

void copy_rat(Rational *r, Rational *s){
  *r = *s;
  normalize (r);
}

double value(Rational *r){
  return((double)(r -> numerator)) / ((double) (r -> denominator));
}

void add_rat(Rational *r, Rational *s){
  (r -> numerator) = (r -> numerator) + (s -> numerator);
  (r -> denominator) = (r -> denominator) + (s -> denominator);
  normalize (r);
}

void sub_rat(Rational *r, Rational *s){
  (r -> numerator) = (r -> numerator) - (s -> numerator);
  (r -> denominator) = (r -> denominator) * (s -> denominator);
  normalize (r);
}

void mul_rat(Rational *r, Rational *s){
  (r -> numerator) = (r -> numerator) * (s -> numerator);
  (r -> denominator) = (r -> denominator) * (s -> denominator);
  normalize (r);
}

void div_rat(Rational *r, Rational *s){
  (r -> numerator) = (r -> numerator) * (s -> denominator);
  (r -> denominator) = (r -> denominator) * (s -> numerator);
  normalize (r);
}

void print_rat(Rational *r, int p){
  normalize (r);
  double num = value(r);
  printf(" %.*f", p, num);
}

void print_ratio(Rational *r){
  normalize (r);
  printf(" %d/%d", (r -> numerator), (r -> denominator));
}

void normalize(Rational *r){
  int x = r -> numerator;
  int y = r -> denominator;
  if(x<0 && y<0){
    x *= -1;
    y *= -1;

    (r -> numerator) *= -1;
    (r -> denominator) *= -1;
  }else if(x>0 && y<0){
    y *= -1;

    (r -> numerator) *= -1;
    (r -> denominator) *= -1;
  }else if(x<0 && y>0){
    x *= -1;
  }

  if(x==0 || y==0){
    x = 0;
    y = 1;

    (r -> numerator) = 0;
    (r -> denominator) = 1;
  }else{
    int greatest = gcd(x, y);
    if(greatest > 0){
      (r -> numerator) = (r -> numerator) / greatest;
      (r -> denominator) = (r -> denominator) / greatest;
    }
  }
}
