/*Lab 04

Problem 1 */

#include <stdio.h>

int main ()
{
int b [4]={2,4,2,8};
int *p, *q, *r;

    p = &b[0];
    q = &b[1];
    r = &b[2];

    printf(" %d", *p);
    printf(" %d", *q);
    printf(" %d \n\n", *r);

    if (p == b);
        printf (" a) means is true\n");

    if (q == b + 1);
        printf (" b) is true\n ");

    if (q == (&b)+1);
        printf ("c) is true\n ");

    if (*q == *(r - 1));
        printf ("d) is true\n ");

    if (p[1] == r[-1]);
        printf ("e) is true\n");

    if (r-p == 2);
        printf ("f) is true\n");

    if (p != r && *p == *r);
        printf ("g) is true\n");

    if (q-b == &b[3] - &p[1]);
        printf ("h) is true\n");

    if (p < q && q < r);
        printf ("i) is true\n");

   // if (2*q - 2*p == 2); This is FALSE
    //    printf ("j) is true\n");


}


