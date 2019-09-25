//Author: Thomas Igoe	Student #: 17372013
//To convert a base 10 number to binary

#include <stdio.h>

//Func prototype
long long base10to2(int base10_number);

int main(void)
{
	//declare vars, take input
	int base10_number;
	printf("Input number to convert to binary: ");
	scanf("%d", &base10_number);
	
	//call to function
	printf("%lli", base10to2(base10_number));
	
	return 0;
}

//conversion function
long long base10to2(int base10_number)
{
	if(base10_number == 0)//base case
	{
		return 0;
	}
	else//recursive step
	{
		return (base10_number % 2) + 10 * base10to2(base10_number/2);
	}
}