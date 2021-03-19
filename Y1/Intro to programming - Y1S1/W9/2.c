//Thomas Igoe, Student no: 17372013
//To factorize an imput number with a function

#include <stdio.h>

void fac(int x);

int main(void)
{
	//Taking input (prob should of used a dowhile loop)
	int inp = -1;
	while(inp<0)
	{
		printf("Enter number to be factorized: \n");
		scanf("%d", &inp);
		if(inp<0)
		{
			printf("Input is not valid\n");
		}
	}
	
	//function commmand
	fac(inp);
	
	return 0;
}

void fac(int x)
{
	int i, max;
	max = x;
	
	printf("{");
	
	//Loop to find the next factor, print that, and then divide the number by that factor
	for(i=2; i<max; i++)
	{
		while(max%i==0)
		{
			max = max/i;
			if(i != 1)
			{
				printf("%d,", i);
			}
		}
	}
	
	printf(" }");
	
	return;
}