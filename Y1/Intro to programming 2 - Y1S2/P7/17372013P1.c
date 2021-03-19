#include <stdio.h>

#define MIN(a,b) ((a)<(b))?(a):(b)

int main(void)
{
	//Input numbers
	int a,b,c,d;
	
	printf("Input 4 numbers to compare\n");
	scanf("\n%d", &a);
	scanf("\n%d", &b);
	scanf("\n%d", &c);
	scanf("\n%d", &d);
	
	//Comparing the first 2 numbers
	printf("\nLowest of the first two numbers is %d", MIN(a,b));
	
	//Comparing all 4
	printf("\nLowest number is %d",MIN(MIN(a,b),MIN(c,d)));

	return 0;
}
//min(a,b) (((a)<(b))?(a):(b)
