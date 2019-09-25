#include <stdio.h>

int main(void)
{
	int count = 0, a[16], in, ver=1, xcd=1;
	printf("Enter number N to conver to binary:");
	scanf("%d", &in);
	
	/* finds first value in the binary string*/
	
	while (in >= ver)
	{
		ver = ver * 2;
		//printf("%d \n", ver);
		//printf("\n%d \n", i);
		xcd++;
	}
	
	ver = ver / 2;
	//printf("%d", ver);
	 /*loop to convert number to binary*/
	
	while(ver >= 1)
	{
		a[count] = in % 2;
		printf("%d", in);
		//printf("%d \n", a[count]);
		in = in - ver;
		count++;
		ver = ver / 2;
	}
	
	/*printf of the binary*/
	printf("\n Binary:");
	for(count=0; count<xcd; count++)
	{
		//printf("%d", a[count]);
	}
	return 0;
}