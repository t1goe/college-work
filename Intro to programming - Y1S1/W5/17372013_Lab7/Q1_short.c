//Name: Thomas Igoe
//Studen#: 17372013

#include <stdio.h>
#include <stdlib.h>

#define N 500

int main(void)
{
	//st=first entry in input (target length);  nd=second input entry (number of entries)
	//i,k=counters; match=bool for exiting all counting loops
	int i, k=N, st, nd, match=0;
	int a[N];
	
	//utalize the input file
	FILE *fp;
	fp = fopen("testcase_small_sorted.txt", "r");
	
	fscanf(fp, "%d", &st);
	fscanf(fp, "%d", &nd);
		
	for(i=0; i<N; i++)
	{
		fscanf(fp, "%d", &a[i]);
	}
	fclose(fp);
	
	//Converts cm -> nm
	st = st*10000000;
	
	//Outside loop is lower number; inside is upper number
	i=2;
	while(i<N && match==0)
	{
		while((st-a[i])<(a[k]) && match==0)
		{
			if(st == a[i]+a[k])
			{
				match = 1;
			}
			k--;
		}
		i++;
	}
	
	if(i=N && match==0)
	{
		printf("No matches are available");
	}
	else
	{
		printf("Sum of numbers to reach target length: \n");
		printf("%d \n", a[i]);
		printf("%d", a[k]);
	}
	
	
	
	
	return 0;
}