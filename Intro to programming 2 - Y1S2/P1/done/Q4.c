//To calculate BMI
//Author: Thomas Igoe, Student #:17372013
#include <stdio.h>

//which returns 1 for underweight, 0 for normal weight and 2 for overweight
int BMICalculator(double weight, double height);

int main(void)
{
	//dec variables and take input
	double H, W;
	printf("Input height (in cm) and weight (in KG) to calculate BMI.\nHeight: ");
	scanf("%lf", &H);
	printf("\nWeight: ");
	scanf("%lf", &W);
	
	//print output
	printf("BMI scale %d ", BMICalculator(W, H));
	
	return 0;
}

int BMICalculator(double weight, double height)
{
	//variables and calculate bmi. Convert cm to m. then use formula weight/(height^2)
	int ans;
	double bmi;
	double limit1=18.5, limit2=24;
	height = height/100;
	bmi = weight / height;
	bmi = bmi / height;
	
	//compare BMI to the upper and lower limit for healthy bmi and assign the correct number to ans which is being returned.
	if(bmi<=limit1)
	{
		ans = 1;
	}
	
	if(bmi>limit2)
	{
		ans = 2;
	}
	
	if(bmi>limit1 && bmi<limit2)
	{
		ans = 0;
	}
	
	return ans;
}