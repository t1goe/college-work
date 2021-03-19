//Author: Thomas Igoe	Student #: 17372013
//Recursive function to print the first N fibonacci numbers

#include <stdio.h>

int fibonacci(int n);
int fibonacci2(int n);

int main(void)
{
	//declare variables and take input
	int n;
	printf("To get the first N fibonacci numbers, input N: ");
	scanf("%d", &n);
	
	//call to function
	fibonacci(n);
	
	return 0;
}

//Recursive calculation function
int fibonacci2(int n)
{
	//Base case
	if(0==n||1==n)
	{
		return n;
	}
	else//Recursive step
	{
		return fibonacci2(n-1) + fibonacci2(n-2);
	}
}

//Print function
int fibonacci(int n)
{
	//printing each of the numbers up to n
	for(int j=0;j<n;j++)
	{
		printf("%d ", fibonacci2(j));
	}
	return 0;
}