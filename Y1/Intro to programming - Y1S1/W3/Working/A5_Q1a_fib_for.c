#include <stdio.h>

int main(void)
{
	double b, a=1;
	for (b=1; b < 99999999999; a=b+a)
	{
		printf("%lf \n", b);
		b = b + a;
		printf("%lf \n", a);
	}
	return 0;
}