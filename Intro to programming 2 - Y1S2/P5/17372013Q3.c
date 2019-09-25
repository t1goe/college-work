//Author: Thomas Igoe		Student #:17372013
//To capatilize a string using 
#include <stdio.h>

#define SIZE 5

void uppercase(char *input);

int main(void)
{
	//Array to be capatilize
	char a[SIZE]={"abcde"};
	
	//Calls to the function
	uppercase(&a[0]);
	
	return 0;
}

void uppercase(char *input)
{
	//Counts up through the array
	for(int i=0; i<SIZE; i++)
	{
		//If its a lowercase
		if(*input>96 && *input<123)
		{
			//Make uppercase
			*input -= 32;
		}
		//Print output
		printf("%c",*input);
		input++;
	}
}