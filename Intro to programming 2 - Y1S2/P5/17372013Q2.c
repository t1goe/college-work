//Author: Thomas Igoe		Student #:17372013
//To multiply a number by 10 using pointers
#include <stdio.h>

#define SIZE 5

//Func prototype
void multiply10(int *input);

int main(void)
{
	//Array to be multiplied
	int a[SIZE]={1,2,20,4,5};
	
	//Calling to the function
	multiply10(&a[0]);
	
	return 0;
}

void multiply10(int *input)
{
	//Going up through the array
	for(int i=0; i<SIZE; i++)
	{
		//Multiplying the current number
		*input = *input * 10;
		
		//Print the new number
		printf("%d\n",*input);
		
		//Counts up in memory
		input++;
	}
}