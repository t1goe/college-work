//Thomas Igoe, 17372013
//To calculate sin with an iterative function and compare it's accuracy

#include <stdio.h>
#include <math.h>

double sin_function(double input);

int main(void)
{
	double acc, z;
	
	//Input
	printf("Enter number to be calculated: ");
	scanf("%lf", &z);
	
	//Print output
	printf("Sin of input while using math.h library: %lf\n", sin(z));
	printf("Sin of input while using sin_function: %lf\n", sin_function(z));
	
	//Calculates/prints accuracy
	acc = sin_function(z) - sin(z);
	acc = acc/sin_function(z);	
	acc = acc*100;
	acc = fabs(acc);
	acc = 100 - acc;
	printf("\nPercentage accuracy:%lf\n", acc);
	
	return 0;
}

//function to calculate sin
double sin_function(double input)
{
	double result = 0, term, i;
	term = input;
	
	//Iterative loop
	for(i=3; 0.0<fabs(term); i=i+2)
	{
		//Adds term
		result += term;
		
		//Modifies term to be new one, and inverts it
		term *= -(pow(input,2))/(i*(i-1));
		
		//Bugtester
		//printf("%lf %lf\n", result, term);
	}
	
	return result;
}