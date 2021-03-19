//Written by Thomas Igoe	Student#: 17372013
//To interpret the values of a speed tracking device
#include <stdio.h>

void maxSpeed (int i[]);

void AverageSpeed(int i[]);

int main(void)
{
	int A[] = {3,2,6,7,10,16,6};
	int B[] = {5,5,7,4,3,5,6};
	int C[] = {1,2,2,1,1,2,3};
	maxSpeed(A);
	maxSpeed(B);
	maxSpeed(C);
	AverageSpeed(A);
	AverageSpeed(B);
	AverageSpeed(C);
	return 0;
}

void maxSpeed (int i[])
{
	int max = i[0];
	//For loop to go through the given array
	for(int a=0; a<7; a++)
	{
		//Checks if the current max is smaller than the number being checked
		if(max<i[a])
		{
			//If true, set new max
			max = i[a];
		}
	}
	
	//Print answer
	printf("The max speed is %d mph\n", max);
	//return 0;
}

void AverageSpeed(int i[])
{
	int average=0;
	//Goes through the array and adds all number together
	for(int a=0; a<7; a++)
	{
		average += i[a];
	}
	//Divides by seven to get average
	average /= 7;
	//Print answer
	printf("The average speed is : %d mph\n", average);
	
	//Prints answer if a discount is earned or not
	if(average>5)
	{
		printf("There is no discount!\n");
	}
	else
	{
		printf("You have earned a discount!\n");
	}
}