//To convert temperature
//Author: Thomas Igoe, Student #:17372013

#include <stdio.h>

//Convert C to F function
double tempConverterCelc( double tempCelcius);
//Convert F to C function
double tempConverterFah( double tempFah);

int main(void)
{
	//declare variables
	double C, F;
	
	//take input
	printf("Please input the degrees to be converted\nCelsius: ");
	scanf("%lf", &C);
	printf("Fahrenheit: ");
	scanf("%lf", &F);
	
	//Prints outputs through functions
	printf("%.2f degrees Celsius in Fahrenheit is %.2f\n\n", C, tempConverterCelc(C));
	printf("%.2f degrees Fahrenheit in Celsius is %.2f", F, tempConverterFah(F));
	
	return 0;
}


//process: Delcare answer variable, do calculation and return answer.
//function C to F
double tempConverterCelc( double tempCelcius)
{
	double ans;
	ans = (tempCelcius*9/5) + 32;
	return ans;
}

//Function F to C
double tempConverterFah( double tempFah)
{
	double ans;
	ans = (tempFah - 32) * 5/9;
	return ans;
}