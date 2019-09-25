//Author: Thomas Igoe		Student #:17372013
//To reverse an array, and to randomize an array with nested function prototypes

#include <stdio.h>
#include <stdlib.h>
#include <time.h>

void reverseArray(int arraySize, int a[]);
void randomiseArray(int arraySize, int a[]);
void printArray(int arraySize, int a[], void (*arrayFunction)(int arraySize, int a[]));

int main(void)
{
	//Array to be processed
	int myArray[] = {2,4,6,8,10,12,14,16,18,20};
	int input;
	
	//Prompt
	printf("Enter 1 for randomized array, and enter 2 for reversed array: ");
	scanf("%d", &input);
	
	//If to use the correct function
	if(input == 1)
	{
		printArray(10, myArray, randomiseArray);
	}
	else if(input == 2)
	{
		printArray(10, myArray, reverseArray);
	}
	else
	{
		printf("Invalid input");
	}
	
	return 0;
}

void reverseArray(int arraySize, int a[])
{
	int temp;
	int i;
	
	//Goes through half of the array and swaps the first and last, the second and second last etc
	for(i=0; i<(arraySize/2); i++);
	{
		temp = a[i];
		a[i] = a[arraySize-i-1];
		a[arraySize-i] = temp;
	}
}

void randomiseArray(int arraySize, int a[])
{
	srand(time(NULL));
	int temp, r1, r2;
	
	//Goes through the array and swaps 2 random numbers
	for(int i=0; i<arraySize; i++)
	{
		r1 = rand()%arraySize;
		r2 = rand()%arraySize;
		temp = a[r1];
		a[r1] = a[r2];
		a[r2] = temp;
	}
}

void printArray(int arraySize, int a[], void (*arrayFunction)(int arraySize, int a[]))
{
	(*arrayFunction)(arraySize, a);
	for(int i=0; i<arraySize; i++)
	{
		printf("%d,", a[i]);
	}
}