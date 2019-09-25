//Written by: Thomas Igoe 		Student No: 17372013
//To convert Euros to Sterling at a rate of 1=0.8

#include <stdio.h>

double convert(double x);

int main(void)
{
	double euro, temp;
	printf("\n Enter the number of Euros to convert to Sterling:");
	scanf("%lf", &euro);
	printf("\n %lf Euros is %lf Sterling.\n", euro, convert(euro));
	
	return 0;
}

double convert(double x)
{
	return x * 0.8;
}