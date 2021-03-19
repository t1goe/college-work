//Code by Thomas Igoe to find factorial of number N
#include <stdio.h>

int main(void)
{
	int fac=0, tem=0, ans;
	printf("Please enter number N to find factorial of: \n");
	scanf("%d", &fac);
	
	if(fac <= 0)
	{
		printf("Cannot factorize numbers less than or equal to zero. \n");
	}
	else
	{
		for(ans=1; tem < fac; tem++)
		{
			ans = ans * (fac - tem);
		}
		printf("%d!=", fac);
		printf("%d", ans);
	}
	return 0;
}