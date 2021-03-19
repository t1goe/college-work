#include <stdio.h>

int main(void)
{
	int a, b=1 ;
	printf("1 \n");
	for ( a = 1; a <1000; a = b + a)
	{
		printf ("%d \n", a);
		b = b + a;
		printf("%d \n", b);
	}
}