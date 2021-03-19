/*
Written by: Ronan Kelly*
Student No: 17325601
Question: 1 - Small Testcase
Objective: To find a pair of lengths amongst the .txt file that equate to the target length in nanometres.*/
#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#define N 500

int main(void)
{
	int n, x, i, j, nano, pair, trixi;
	int lengths[N];
	int m = 1;
	
	FILE *fp;
	
	{
	fp = fopen("testcase_small_sorted.txt", "r");
	
	fscanf(fp, "%d", &x);
	fscanf(fp, "%d", &n);
	
	for (i = 0;i < n;i++)
	{
		fscanf(fp, "%d", &lengths[i]);
	}
	fclose(fp);
	
	}
	
	nano = x*10000000; /* Changing 15cm to nm and assigning it to the variable nano. */ 
	trixi = true;
		for (i = 0;i < N;i++)
		{
			pair = lengths[i]+lengths[m];
		
			if (pair == nano)
			{
				printf("The pairs are: \n %d and", lengths[i]);
				printf(" %d.\n", lengths[m]);
				trixi = false;
			}
			if (pair != nano)
			{
				m = m;
			}
			if (i == N-1)
			{
				i = 0;
				m++;
			}
			if (m == N-1)
			{
				break;
			}
		}
		if (pair != nano && trixi == true)
		{
			printf("There are no pair(s) of lengths that equate to the target length. Soz x.");
		}
return 0;
}	