#include <stdio.h>

int main(void)
{
	int input, a[16], pos=0, count=0, temp, number;
	
	//input lines
	printf("Enter integer to convert to binary:");
	scanf("%d", &input);
	
	while(input > 0)
	{		
		a[pos] = input%2;
		printf("%d", a[pos]);
		pos++;
		//printf("%d", temp);
		input = input/2;
	}

	
	return 0;
}