//Code by Thomas Igoe to find factorial of number N
#include <stdio.h>

int main(void)
{
	int fac, tem=0, ans=1;
	printf("Please enter number N to find factorial of: \n");
	scanf("%d", &fac);
	
	if(fac <= 0)
	{
		printf("Cannot factorize numbers less than or equal to zero. \n");
	}
	else
	{
		while(tem <= fac)
		{
			ans = ans * (fac - tem);
			tem = tem + 1;
		}
		printf("%d factorial =", &fac);
		printf("%d", ans);
	}
	return 0;
}