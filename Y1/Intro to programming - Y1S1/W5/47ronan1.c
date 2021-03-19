/*
Written by: Ronan Kelly
Student No: 17325601
Question: 1 - Large Sorted Testcase
Objective: To find a pair of lengths amongst the testcase_large_sorted.txt file that equate to the target length in nanometres.*/

#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>

#define N 1000000 /* Being defined as an array of size 1000000. */ 

int main(void)
{
	int n, x, i, nano, trixi, pair;
	int lengths[N];
	int m = 1;
	
	FILE *fp;
	
	fp = fopen("testcase_large_sorted.txt", "r");
	
	fscanf(fp, "%d", &x);
	fscanf(fp, "%d", &n);
	
	for (i = 0;i < n;i++)
	{
		fscanf(fp, "%d", &lengths[i]); /* Scanning the values in the text file into the array lengths[]*/
	}
	fclose(fp);
	
	nano = x*10000000; /* Changing 15cm to nm and assigning it to the variable nano. */ 
	trixi = true; /* Boolean value used for if no pair of lengths equate to the target length. */
		for (i = 0;i < N;i++)
		{
			pair = lengths[i]+lengths[m];
		
			if (pair == nano)
			{
				printf("The pairs are: \n %d and", lengths[i]);
				printf(" %d.\n", lengths[m]);
				trixi = false; /* If at least on pair of lengths equate to the target value the variable trixi changes to false. */
			}
			if (pair != nano)
			{
				m = m;
			}
			/* This if statement is to make i=0 and repeat the for loop. */
			if (i == N-1)
			{
				i = 0;
				m++; /* For each iteration of the for loop, m increases by 1. */
			}
			/* Ends the loop when there are no more pairs to test. */
			if (m == N-1)
			{
				break;
			}
			
		}
		/* If there are no pair of lengths that equate to the target length, then it prints the statement below. */
		if (pair != nano && trixi == true)
		{
			printf("There are no pair(s) of lengths that equate to the target length. Soz x.");
		}
	
return 0;
}	