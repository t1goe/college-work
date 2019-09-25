#include <stdio.h>

int main(void)
{
	float w=0, x=0, y=0, z=0, temp=0, count=0;
	printf("Enter values w, x, y, and z to be sorted\n");
	
	printf("w=");
	 scanf("%f", &w);
	 
	printf("x=");
	 scanf("%f", &x);
	 
	printf("y=");
	 scanf("%f", &y);
	 
	printf("z=");
	 scanf("%f", &z);
	
	while(count < 10)
	{
		if(w>=x)
		{
			temp = w;
			w = x;
			x = temp;
		}
		if(x>=y)
		{
			temp = x;
			x = y;
			y = temp;
		}
		if(y>=z)
		{
			temp = y;
			y = z;
			z = temp;
		}
		
		count++;
	}
	
	printf("\n answers: \n");
	printf("w=%f\n", w);
	printf("x=%f\n", x);
	printf("y=%f\n", y);
	printf("z=%f\n", z);
	
	return 0;
}