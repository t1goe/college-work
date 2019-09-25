//Thomas Igoe, 17372013
//To calculate the cubed root of a number with Newton's algorithim

#include <stdio.h>
#include <math.h>

double cube_root(double calc, double guess);

int main(void)
{
	double input1, input2;
	
	//Takes inputs
	printf("Enter number to find the cubed root of using Newton's cube root algorithm: ");
	scanf("%lf", &input1);
	printf("Now guess the cubed root of your input: ");
	scanf("%lf", &input2);
	
	//Printf output
	printf("Using math.h pow function the cubed root of %lf is %lf \nUsing Newton's algorithm it is %lf", input1, cbrt(input1), cube_root(input1, input2));
	
	return 0;
}

//Function to calculate square root with Newton's algorithim
double cube_root(double calc, double guess)
{
	double i, x;
	x = guess;
	
	//Iterative loop to do actual calculations
	for(i=0;i<10;i++)
	{
		x = x-((pow(x,3)-calc)/(3*pow(x,2)));
	}
	
	return x;
}