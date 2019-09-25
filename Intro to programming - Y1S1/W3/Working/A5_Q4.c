//Code by Thomas Igoe to print N Pavodan numbers from user input

#include <stdio.h>

int main(void)
{
	double in, out=0, count=0, n1=1, n2=1, n3=1;
	printf("To calculate the first N Pavodan numbers, enter N: ");
	scanf("%lf", &in);
	
	while(count <= in)
	{
		if(count < 3)
		{
			printf("1.000000\n");
			count++;
		}
		else
		{
			out = n2 + n3;
			printf("%lf\n", out);
			n3 = n2;
			n2 = n1;
			n1 = out;
			count++;
		}	
	}
	return 0;
}