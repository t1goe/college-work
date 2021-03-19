//Written by Thomas Igoe	Student#: 17372013
//To keep track of cars entering and exiting a car park.
#include <stdio.h>

int NumberOfCarsThatEnteredSoFar=0, NumberOfCarsThatLeftSoFar=0;

int carIn(int inBatch[]);
int carOut(int outBatch[]);
void currentState(int x, int y);
void salesMade(int i);

int main(void)
{
	int inBatch[] = {1,2,5};
	int outBatch[] = {1,2,1};
	carIn(inBatch);
	carOut(outBatch);
	currentState(NumberOfCarsThatEnteredSoFar,NumberOfCarsThatLeftSoFar);
	salesMade(NumberOfCarsThatLeftSoFar);
	return 0;
}

int carIn(int inBatch[])
{
	int lastHr=0;
	//Goes through array and adds cars to running total, and the hourly number
	for(int i = 0; i<3; i++)
	{
		NumberOfCarsThatEnteredSoFar+=inBatch[i];
		lastHr+=inBatch[i];
	}
	
	//Prints answer
	printf("Total vehicle entry: %d\n", NumberOfCarsThatEnteredSoFar);
	printf("Hourly vehicle entry: %d\n", lastHr);
	return NumberOfCarsThatEnteredSoFar;
}

int carOut(int outBatch[])
{
	int lastHr=0;
	//Goes through array and adds cars to running total, and the hourly number
	for(int i = 0; i<3; i++)
	{
		NumberOfCarsThatLeftSoFar+=outBatch[i];
		lastHr+=outBatch[i];
	}
	
	//Prints answer
	printf("Total vehicle exit: %d\n", NumberOfCarsThatLeftSoFar);
	printf("Hourly vehicle exit: %d\n", lastHr);
	return NumberOfCarsThatLeftSoFar;
}

void currentState(int x, int y)
{
	int ans;
	//Calculates number remaining
	ans = x - y;
	printf("Vehicles remaining in the park : %d\n", ans);
}

void salesMade(int i)
{
	double ans;
	//Calculates money made
	ans = 2.5*i;
	printf("The park has made Euros %.2lf\n", ans);
}