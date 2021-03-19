#include <stdio.h>

int main(void)
{
	int input, a[16], pos=0, count, temp, prin;
	
	//input lines
	printf("Enter integer to convert to binary:");
	scanf("%d", &input);
	
	while(input >= 1)
	{
		prin = input%2;
		printf("%d", prin);
		pos++;
		temp = input/2;
		input = input - temp;
	}
	
	//resetting position, while keeping track of how many spots in the array
	count=pos;
	pos = 0;
	
	/* while(pos <= count)
	{
		printf("%d", a[pos]);
		pos++;
	}*/
	
	
	return 0;
}