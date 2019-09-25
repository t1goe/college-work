//To put spaces inbetween numbers and calulate the total
//Author: Thomas Igoe, Student #:17372013
#include <stdio.h>

int spacer(int x);

int main(void)
{
	//Declare input variable and take input
	int input;
	printf("Please a 6-digit input number to process: ");
	scanf("%d", &input);
	
	//Print output
	printf("%d", spacer(input));
	
	return 0;
}

int spacer(int x)
{
	//Declare variables and go through process of using int division to calculate the first number, then removing it from the input by use of modulo
	//Print number and 4 spaces, and add to the running total. Return answer.
	int ans=0, i, div = 100000, temp;
	for(i=0; i<6; i++)
	{
		temp = x / div;
		x = x % div;
		printf("%d    ", temp);
		div = div / 10;
		ans += temp;
	}
	
	
	return ans;
}