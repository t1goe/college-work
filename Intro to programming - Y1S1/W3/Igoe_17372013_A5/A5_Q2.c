//Code by Thomas Igoe to print N pell numbers from user input

#include <stdio.h>

int main(void)
{
	int in, out=0, count=0, n1=1, n2=0;
	printf("To calculate the first N Pell numbers, enter N: ");
	scanf("%d", &in);
	printf("0\n1\n");
	
	while(count <= in)
	{
		out = (2 * n1) + n2;
		printf("%d\n", out);
		n2 = n1;
		n1 = out;
		count++;
	}
	return 0;
}