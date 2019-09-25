//Author: Thomas Igoe		Student #:17372013
//To find if a number is a prime and intepret it
#include <stdio.h>

void primeByRef(int *i);

int primeByVal(int i);

int main(void)
{
	//Taking input and output
	int n;
	scanf("%d",&n);
	printf("The value of n is: %d \n",primeByVal(n));
	primeByRef(&n);
	printf("The new value of n is: %d \n", n);
	return 0;
}

//Function by ref
void primeByRef(int *i)
{
	int isprime = 1;
	int max = *i / 2;
	
	//Loop goes through all possible divisors and states whether is a prime or not
	for(int a=2; a<=max; a++)
	{
		if(*i%a==0)
		{
			isprime=0;
			break;
		}
	}
	
	//Outputs number if it is a prime
	if(isprime==1)
	{
		*i = (*i) * (*i);
	}
	else
	{
		*i=0;
	}
}

//Funciton by value
int primeByVal(int i)
{
	int isprime = 1;
	int max = i / 2;
	
	//Loop goes through all possible divisors and states whether s a prime or no
	for(int a=2; a<=max; a++)
	{
		if(i%a==0)
		{
			isprime=0;
			break;
		}
	}
	
	//Interprets and outputs answer
	if(isprime==1)
	{
		int square = i*i;
		return square;
	}
	else
	{
		return 0;
	}	
}