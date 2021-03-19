# include <stdio.h>
# include <math.h>

double quad_1(double x, double y, double z);
double quad_2(double x, double y, double z);

int main(void)
{
	double a=0, b, c, sroot;
	int question;
	
	printf("To calculate the roots of a quadriatic equation in the form ax^2 + bx + c = y \n");
	
	while(a==0)
	{
		printf("Enter coefficent of x^2, a \n");
		scanf("%lf", &a);
		//To catch 0 coefficents, which are invalid
		if(a==0)
		{
			printf("a cannot be equal to zero \n");
		}
	}
	
	printf("Enter coefficent of x, b \n");
	scanf("%lf", &b);
	
	printf("Enter constant, c \n");
	scanf("%lf", &c);
	
	//To calculate the determinant of the equation
	sroot = b * b;
	sroot =- 4 * a * c;
	if(sroot<=0)
	{
		printf("Roots are imaginary");
	}
	else
	{
		//to ask which root the user wants, and to keep asking until a valid answer
		for(question = 3; question != 1 && question != 2; )
		{
			printf("Enter 1 for the bigger root, or 2 for the smaller root");
			scanf("%d", &question);
			if(question != 1 && question != 2)
			{
				printf("That is not a valid response, please enter a valid response \n");
			}	
		}
		
		//Prints the correct root
		if(question == 1)
		{
			printf("The greater root is: %lf", quad_1(a, b, sroot));
		}
		else
		{
			printf("The smaller root is: %lf", quad_2(a, b, sroot));
		}
	}	

	return 0;
}

//Greater root function
double quad_1(double x, double y, double z)
{	
	z = sqrt(z);
	return (z - y)/(2*x);	
}

//Lesser root function
double quad_2(double x, double y, double z)
{
	z = sqrt(z);
	return (z + y)/(-2*x);
}