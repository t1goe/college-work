//To price beans
//Author: Thomas Igoe, Student #:17372013
#include <stdio.h>

void beanPricer(int &n);

int main(void)
{
	//take input
	int beans;
	printf("Input number of beans:");
	scanf("%d", &beans);
	
	printf("That will cost: %d", beanPricer(beans));
}

int beanPricer(int n)
{
	int count, temp;
	count = n;
	
	//Compare the number to the different possible ranges, and do the corrisponding calculations.
	if(count>=700)
	{
		temp = n - 700;
		n = 1850 + temp;
		count = -1;
	}
	
	if(count>=250)
	{
		temp = n - 250;
		n = 950 + 2*temp;
		count = -1;
	}
	
	if(count>=100)
	{
		temp = n - 100;
		n = 500 + 3*temp;
		count = -1;
	}
	
	if(count>=0)
	{
		n = 5 * n;
	}
	return n;
}