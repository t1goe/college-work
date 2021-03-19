//Written by Thomas Igoe	Student#: 17372013
//To create a NxN matrix which counts up in 1s starting at 1

#include <stdio.h>

void create2DMatrix(int i);

int main(void)
{
	int order;
	scanf("%d",&order);
	create2DMatrix(order);
	return 0;
}

void create2DMatrix(int i)
{
	int count = 1;
	//Outer loop goes vertically 
	for(int a=0; a<i; a++)
	{
		//Inner loop goes horizontally
		for(int b=0; b<i; b++)
		{
			printf("%d |", count);
			count++;
		}
		printf("\n");
	}	
}