#include <stdio.h>
#include <stdlib.h>
#include "library.h"
#define MAX_SIZE  10
int main()
{
 	//result of the average function
	double average = 0;
	//numbers to be averaged
	int numbers[10];
	insertNumbers(numbers, MAX_SIZE);
  	average = avg(numbers,MAX_SIZE);
	 printf ("\nThe average is: %.2f", average);
}
