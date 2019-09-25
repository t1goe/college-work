# include <stdio.h>
# include <math.h>

int main(void)
{
	double a, b, c, r1, r2, root, inverse_root;
	printf("To calculate the roots of a quadriatic equation in the form ax^2 + bx + c = y \n");
	
	printf("Enter coefficent of x^2, a \n");
	scanf("%lf", &a);
	
	if (a == 0)
	{
		printf("a cannot be equal to zero");
	}
	
	printf("Enter coefficent of x, b \n");
	scanf("%lf", &b);
	
	printf(" Enter constant, c \n");
	scanf("%lf", &c);
	
	root = b*b;
	root = root - (4*a*c);
	
	if (root <= 0)
	{
		printf("Has imaginary roots \n");
		
	}
	else
	{
		root = sqrt(root);
		r1 = root - b;
		r1 = r1 / (2*a);
	
		r2 = root + b;
		r2 = r2 * -1;
		r2 = r2 / (2*a);
	
		printf("root 1: %lf\n", r1);
		printf("root 2: %lf", r2);
	} 
	
	
	return 0;
}