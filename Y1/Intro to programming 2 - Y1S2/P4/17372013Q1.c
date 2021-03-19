//Author: Thomas Igoe		Student #:17372013
//To print multiplaction table of input
#include <stdio.h>

void multiplicationTable(int n);

int main(void)
{
	//Taking input
	int number;
	printf("Enter a value: ");
	scanf("%d",&number);
	multiplicationTable(number);
	
	return 0;
}

//Multiplication table print function
void multiplicationTable(int n)
{
	int a;
	//Loop to go through the multiples and print
	for(int i=1; i<=15; i++)
	{
		a = n*i;
		printf("%d x %d = %d\n", n, i, a);
	}
}