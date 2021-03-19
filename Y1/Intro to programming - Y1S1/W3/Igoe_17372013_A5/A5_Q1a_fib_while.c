#include <stdio.h>

int main(void)
{
	int b=1, a=1;
	while (b < 1000)
	{
		printf("%d \n", b);
		b = b + a;
		printf("%d \n", a);
		a = b + a;
	}
	return 0;
}