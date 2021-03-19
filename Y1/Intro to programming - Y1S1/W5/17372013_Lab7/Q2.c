//Name: Thomas Igoe
//Studen#: 17372013

#include <stdio.h>

int main(void)
{
	//input is self explainatory; j is factor being checked;
	//k counts up numbers %-ed to j;
	//i first fills array, then moves all numbers down when a comp# is found
	int input, i, j, k;
	printf("Enter number N to find all prime numbers lower than N:");
	scanf("%d", &input);
	
	int a[50];
	
	//fills array with values 1 to i
	for(i=0; (input-2)>i; i++)
	{
		a[i] = i+2;
		printf("%d ", a[i]);
	}
	
	printf("\n\n\n\n");
	
	//filter nloop
	for(j=2; j<(input-2); j++)
	{
		//printf("%d ", a[j]);
		for(k=j; k<(input-2); k++)
		{	
			//printf("%d ", a[k]%(j+1));
			
			//If %=0 true, then is a composite number and should be delt with
			//Take next value above discovered and move 1 down in array, repeat
			if((a[k]%(j))==0)
			{
				for(i=k; i<(input-2); i++)
				{
					a[i] = a[i+1];
					//printf("%d", a[i]);
				}
				input--;
			}
		}
		printf("\n");
	}
	
	//prints all a[] values
	printf("All prime numbers under N:");
	for(i=0; a[i]; i++)
	{
		printf("%d ", a[i]);
	}
	
	return 0;
}