//Author: Thomas Igoe		Student #:17372013
//To print the size of varibles in memory
#include <stdio.h>

int main(void)
{
	//Declaring variables:
	//char, int, long, long long, double, long double, float, an array of integers, a pointer to an array
	char character;
	int integers;
	long longs;
	long long xlong;
	double times2;
	long double long2;
	float boyant;
	int array[5];
	int *pointer;
	
	//Printing the size of the array
	printf("A char takes up %u bytes\n", sizeof(character));
	printf("A int takes up %u bytes\n", sizeof(integers));
	printf("A long takes up %u bytes\n", sizeof(longs));
	printf("A long long takes up %u bytes\n", sizeof(xlong));
	printf("A double takes up %u bytes\n", sizeof(times2));
	printf("A long double takes up %u bytes\n", sizeof(long2));
	printf("A float takes up %u bytes\n", sizeof(boyant));
	printf("A int array of 5 elements takes up %u bytes\n", sizeof(array));
	printf("A int pointer takes up %u bytes\n", sizeof(*pointer));

	
	return 0;
}