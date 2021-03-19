//To do some basic calculations to 3 integers
//Author: Thomas Igoe, Student #:17372013
#include <stdio.h>

int mathTest(int x, int y, int z);

int main(void)
{
	//Declare variables
	int num1,num2,num3;
	
	//Take input numbers
	printf("Input three digits\n");
	scanf("%d",&num1);
	scanf("%d",&num2);
	scanf("%d",&num3);
	
	//print answer
	mathTest(num1,num2,num3);
}

int mathTest(int x, int y, int z)
{
	double avg;
	int product, sum, largest, middle, smallest, temp, i;
	
	//calculate product
	product = x*y*z;
	
	//calculate sum
	sum = x+y+z;
	
	//calculate average
	avg = x+y+z;
	avg = avg/3;
	
	//order the 3 numbers from largest to smallest
	largest = x;
	middle = y;
	smallest = z;
	for(i=0; i<3; i++)
	{
		if(largest < middle)
		{
			temp = middle;
			middle = largest;
			largest = temp;
		}
		
		if(middle < smallest)
		{
			temp = smallest;
			smallest = middle;
			middle = temp;
		}
	}
	
	//print answer
	printf("The three numbers are %d, %d and %d. The average is %.2f. The product is %d. The sum is %d. The largest is %d. The smallest is %d.", x, y, z, avg, product, sum, largest, smallest);
	return 0;
}