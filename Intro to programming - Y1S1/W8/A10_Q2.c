//Written by: Thomas Igoe 		Student No: 17372013
//To compute the area of a circle
#include <stdio.h>
double c_area(double x);

int main(void)
{
	double radius;
	
	printf("\nEnter the radius of the circle, you want to find the area of:");
	scanf("%lf", &radius);
	
	printf("\nThe area of the circle is %lf\n", c_area(radius));
	
	return 0;
}

double c_area(double x)
{
	double pi=3.141592653;
	return pi * x * x;
}