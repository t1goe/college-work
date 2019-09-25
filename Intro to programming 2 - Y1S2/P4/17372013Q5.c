//Author: Thomas Igoe		Student #:17372013
//Simulated horse race
// Exercise 7.17 Solution
#include <stdio.h>
#include <stdlib.h>
#include <time.h>

// function prototypes
void moveNapoleon( int *NapoleonPtr);
void movePegasus( int *PegasusPtr);
void printCurrentPositions(unsigned int, unsigned int);

int main()
{ 
	srand(time(NULL));
	
	int Napoleon = 1; // Napoleon current position
	int Pegasus = 1; // Pegasus current position      
	int counter = 0; // counter for the for loop controlling the race.
	
	puts("The Race has started");
   
	//loop through the progress on the track
	while (Napoleon != 100 && Pegasus != 100) 
	{ 
		//neither of the horses has reached the end of the track
		//call functions to move cars on the track
		movePegasus(&Pegasus);
		moveNapoleon(&Napoleon);
	
		printCurrentPositions(Napoleon, Pegasus);
	} 
	
	// determine the winner and print message - one car has passed the end as it is outside the loop.
	if(Napoleon > Pegasus) 
	{
		puts("Napoleon wins!");
	} 
	
	if(Napoleon < Pegasus) 
	{
		puts("Pegasus wins!");
	}
	
	if(Napoleon == Pegasus)
	{
		puts("It's a tie.");
	} 
	
	printf("iterations: %u \n", counter);
} 

// progress for Pegasus
void movePegasus( int *PegasusPtr)
{ 
	// generate random number from 1-10
	int x = rand()%10;
	
	// determine progress
	if(x >= 1 && x <= 5)
		*PegasusPtr += 3;
	
	if(x == 6)
		*PegasusPtr -= 2;
	
	if(x>6)
		*PegasusPtr += 1;
		
	// check boundaries
	if(*PegasusPtr < 1)
		*PegasusPtr = 1;
	
	if(*PegasusPtr > 100)
		*PegasusPtr = 100;
} 

// progress for Napoleon
void moveNapoleon( int *NapoleonPtr)
{ 
	int x = rand()%10;
	// generate random number from 1-10
	
	
	// determine progress
	if(x >= 1 && x <= 5)
		*NapoleonPtr += 3;
	
	if(x == 6)
		*NapoleonPtr -= 2;
	
	if(x>6)
		*NapoleonPtr += 1;
		
	// check boundaries
	if(*NapoleonPtr < 1)
		*NapoleonPtr = 1;

	if(*NapoleonPtr > 100)
		*NapoleonPtr = 100;
	//enter code here 
} 

// display new position
void printCurrentPositions(unsigned int Napoleon, unsigned int Pegasus)
{ 
	// loop through race
	for (unsigned int count = 1; count <= 100; ++count) 
	{	
		//this loop will print will 100 characters at each run: either P,N,T or a space to 
		//give the user an idea of where the horse at every iteration
		
		if (count == Napoleon && count == Pegasus) 
		{
			printf("T");
		} 
		else if (count == Pegasus)
		{
			printf("P");
		} 
		else if (count == Napoleon)
		{
			printf("N");
		} 
		else
		{
			printf(" ");
		}
	}

	puts("");
} 
