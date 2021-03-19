//Author: Thomas Igoe	Student #: 17372013
//To calculate the volume of a square based pyramid where V=b^2 * h/3

#include <stdio.h>

//function prototype
double pyVolCalculator(double i, double j);

int main(void)
{
	//declare vars
	double height, blength;
	
	//Taking inputs
	printf("To calculate volume of a square-based pyramid.\n Input the length of the side of the base: ");
	scanf("%lf", &blength);
	printf("\nInput the height of the pyramid: ");
	scanf("%lf", &height);
	
	//Call to function, and print result
	printf("The volume of a pyramid base lenght %lf and height %lf is %lf", blength, height, pyVolCalculator(blength, height));
	return 0;
}

double pyVolCalculator(double i, double j)
{
	//declare vars
	double ans;
	i = i * i;
	j = j / 3;
	ans = i * j;
	
	return ans;
}