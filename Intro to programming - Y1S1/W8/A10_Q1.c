//Written by: Thomas Igoe 		Student No: 17372013
//To calculate the area of a triangle

#include <stdio.h>

double tri_area(double x, double y);
int main(void)
{
	double base, height;
	printf("Enter the dimensions of the triangle you want to find the area of: \n\n Base:");
	scanf("%lf", &base);
	printf(" Perpendicular Height:");
	scanf("%lf", &height);
	
	printf("\nThe area of the triangle is %lf\n", tri_area(base, height));
	
	return 0;
}

double tri_area(double x, double y)
{
	return 0.5 * x * y;
}