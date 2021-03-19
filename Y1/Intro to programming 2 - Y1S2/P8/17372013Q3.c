//Name: Thomas Igoe		Student No:17372013
//To average numbers (floats or ints) using variable sized arrays
#include <stdio.h>
#include <stdlib.h>

int rtotal;

int main(void)
{
	int iorf, cont;
	
	printf("Enter 1 for integers, or 2 for floats as your input: ");
	scanf("%d", &iorf);
	
	//INTS
	if(iorf == 1)
	{
		int *ptr;
		int temp, currentp, i, ans, nofn = 0;
		
		printf("\nEnter number of inputs: ");
		scanf("%d", &temp);
		nofn += temp;
		ptr = (int*)calloc(nofn, sizeof(int));
		
		for(i = 0; i<temp; i++)
		{
			printf("Number %d: ", i+1);
			scanf("%d", &ptr[i]);
		}
		
		for(i = 0; i<nofn; i++)
		{
			rtotal += ptr[i];
		}
		
		ans = rtotal/nofn;
		printf("Average: %f\n", ans);
		
		currentp += nofn;
		
		printf("Enter 1 to input more numbers, enter 0 to quit: ");
		scanf("%d", &cont);
	
		//printf("\n\n\n+++TEST+++\n\n\n");
		if(cont > 1 || cont < 0)
		{
			printf("Invalid input, quitting");
		}
		
		while(cont == 1)
		{
			printf("\nEnter number of inputs: ");
			scanf("%d", &temp);
			nofn += temp;
			ptr = (int*)realloc(ptr, sizeof(int)*nofn);
			
			for(i = 0; i<temp; i++)
			{
				printf("Number %d: ", i+1);
				scanf("%d", &ptr[i+(nofn-temp)]);
			}
		
			rtotal = 0;
			for(i = 0; i<nofn; i++)
			{
				rtotal += ptr[i];
			}
			
			ans = rtotal/nofn;
			printf("Average: %f\n", ans);
			currentp += nofn;
			
			printf("Enter 1 to input more numbers, enter 0 to quit: ");
			scanf("%d", &cont);
	
			if(cont > 1 || cont < 0)
			{
				printf("Invalid input, quitting");
			}
	
		}
	}
	else if(iorf == 2)
	{
		float *ptr;
		float currentp, ans, nofn = 0;
		int i, temp, temp2;
		
		printf("\nEnter number of inputs: ");
		scanf("%d", &temp);
		nofn += temp;
		ptr = (float*)calloc(nofn, sizeof(float));
		
		for(i = 0; i<temp; i++)
		{
			printf("Number %d: ", i+1);
			scanf("%f", &ptr[i]);//PROBLEM
		}
		
		for(i = 0; i<nofn; i++)
		{
			rtotal += ptr[i];
		}
		
		ans = rtotal/nofn;
		printf("Average: %f\n", ans);
		
		currentp += nofn;
		
		printf("Enter 1 to input more numbers, enter 0 to quit: ");
		scanf("%f", &cont);
	
		if(cont > 1 || cont < 0)
		{
			printf("Invalid input, quitting");
		}
		
		while(cont == 1)
		{
			printf("\nEnter number of inputs: ");
			scanf("%f", &temp);
			nofn += temp;
			ptr = (float*)realloc(ptr, sizeof(float)*nofn);
			
			for(i = 0; i<temp; i++)
			{
				printf("Number %f: ", i+1);
				temp2=i+(nofn-temp);
				scanf("%f", &ptr[temp2]);
			}
		
			rtotal = 0;
			for(i = 0; i<nofn; i++)
			{
				rtotal += ptr[i];
			}
			
			ans = rtotal/nofn;
			printf("Average: %f\n", ans);
			currentp += nofn;
			
			printf("Enter 1 to input more numbers, enter 0 to quit: ");
			scanf("%f", &cont);
	
			//printf("\n\n\n+++TEST+++\n\n\n");
			if(cont > 1 || cont < 0)
			{
				printf("Invalid input, quitting");
			}
		}
	}
	else
	{
		printf("Invalid input");
	}
	
}