//Author: Thomas Igoe	Student #: 17372013
//Simulate a game of "Beat that"
#include <stdio.h>
#include <stdlib.h>
#include <time.h>

//function prototype
int roll(void);

int main(void)
{
	//delcare variables
	int die1, die2, sum1, sum2, blank;
	
	//roll dice
	die1 = roll();
	die2 = roll();
	
	//order them into a single number
	if(die1>die2)
	{
		die1 = die1*10;
		sum1 = die1 + die2;
	}
	else
	{
		die2 = die2*10;
		sum1 = die1 + die2;
	}

	//Print the number to be beat
	printf("The number to beat is %d.", sum1);
	
	//Sets them as the same, so then if the dice roll is the same, auto repeats
	sum2 = sum1;
	while(sum2 == sum1)
	{
		//repeats the roll dice and ordering from above.
		die1 = roll();
		die2 = roll();
	
		if(die1>die2)
		{
			die1 = die1*10;
			sum2 = die1 + die2;
		}
		else
		{
			die2 = die2*10;
			sum2 = die1 + die2;
		}
		printf("\n\nRollled %d\n", sum2);
		
		//Outputs 0 for loss, 1 for win and reroll statement
		if(sum2 < sum1)
			printf("0");
		
		if(sum2 > sum1)
			printf("1");
		
		if(sum2 == sum1)
			printf("Tied");
	}	
	
	return 0;
}

//roll function
int roll(void)
{
	srand(rand());
	int i;
	i = 1 + (rand()%6);
	return i;
}