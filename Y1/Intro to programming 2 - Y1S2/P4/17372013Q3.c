//Author: Thomas Igoe		Student #:17372013
//To print the addresses of various arrays
#include <stdio.h>

#define SIZE 5

int main(void)
{
	int i, j;
	int array1x1[SIZE], array1x2[SIZE][SIZE];
	float array2x1[SIZE], array2x2[SIZE][SIZE];
	double array3x1[SIZE], array3x2[SIZE][SIZE];
	char array4x1[SIZE], array4x2[SIZE][SIZE];
	
	//Repeating the same lines for each of the different types of arrays so only gonna comment the first one.
	//Start of repeat
	printf("The address of a 1d int array: %p\nThe addresses of the elements of said array:\n", *array1x1);
	
	//Prints addresses of all elements of the single array of this type
	for(i=0; i<SIZE; i++)
	{
		printf("%p\n", &array1x1[i]);
	}

	printf("The address of a 2d int array: %p\nThe addresses of the elements of said array:\n", *array1x2);
	
	//Prints addresses
	//I goes through rows
	//J goes through columns
	for(i=0; i<SIZE; i++)
	{
		for(j=0; j<SIZE; j++)
		{
			printf("%p ", &array1x2[i][j]);
		}
		printf("\n");
	}
	//End of repeat
	
	printf("\n\n\nThe address of a 1d float array: %p\nT	he addresses of the elements of said array:\n", *array2x1);
	for(i=0; i<SIZE; i++)
	{
		printf("%p\n", &array2x1[i]);
	}

	printf("\nThe address of a 2d float array: %p\nThe addresses of the elements of said array:\n", *array2x2);
	for(i=0; i<SIZE; i++)
	{
		for(j=0; j<SIZE; j++)
		{
			printf("%p ", &array2x2[i][j]);
		}
		printf("\n");
	}
	
	////////////
	printf("\n\n\nThe address of a 1d double array: %p\nThe addresses of the elements of said array:\n", *array3x1);
	for(i=0; i<SIZE; i++)
	{
		printf("%p\n", &array3x1[i]);
	}

	printf("\nThe address of a 2d double array: %p\nThe addresses of the elements of said array:\n", *array3x2);
	for(i=0; i<SIZE; i++)
	{
		for(j=0; j<SIZE; j++)
		{
			printf("%p ", &array3x2[i][j]);
		}
		printf("\n");
	}	
	
	////////////
	printf("\n\n\nThe address of a 1d char array: %p\nThe addresses of the elements of said array:\n", *array4x1);
	for(i=0; i<SIZE; i++)
	{
		printf("%p\n", &array4x1[i]);
	}

	printf("\nThe address of a 2d char array: %p\nThe addresses of the elements of said array:\n", *array4x2);
	for(i=0; i<SIZE; i++)
	{
		for(j=0; j<SIZE; j++)
		{
			printf("%p ", &array4x2[i][j]);
		}
		printf("\n");
	}	
	return 0;
}