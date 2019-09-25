//Code by Thomas Igoe to display a factorial for number N using a for loop
#include <stdio.h>

int main(void)
{
	int fac, temp, ans = 1;
	printf("Please enter number N to find the factorial of:");
	scanf("%d", fac);
	for(temp = 0 ; temp<=fac ; temp++)
	{
		ans = ans * (fac - temp);
		printf("%d!=", fac);
		printf("%d", ans);
	}
	
	
	return 0;
}