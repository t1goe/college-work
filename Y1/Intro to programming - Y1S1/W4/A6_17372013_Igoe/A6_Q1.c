#include <stdio.h>
/*
  Written by: Neil Hurley (neil.hurley@ucd.ie)
  Program to compute the kth smallest value in an array
  Input: a[]: the array values. Note the value of k is written
  directly into the code and currently has the value k=MAX_SIZE/2, so 
  that the middle value of the array is computed i.e. the median
  Output: the kth smallest value in the array.
*/

#define MAX_SIZE 5000

int main(void)
{
  int i, j, k, maxindx, inp;
  double a[MAX_SIZE];
  double tmp, max;
  
  printf("Please enter value K to find the Kth highest value of the array:");
  scanf("%d", &inp);
  FILE *fp;

  fp = fopen("floats.txt", "r");

  for (i = 0;i<MAX_SIZE;i++)
    {
      fscanf(fp, "%lf", &a[i]);
    }
  fclose(fp);
  k = MAX_SIZE/2;  /* the median position */

	 /* Run the outer loop k times, each time around the outer loop
     the (j+1)st smallest value is found and placed in position j
     of the loop */
  
  for (j=0;j<k;j++)
    {
      max = a[j];
      maxindx=j;

      /* the inner loop starts are i=j+1 i.e. it starts at
	 the next element of the array AFTER the position in which
	 the previous smallest value was placed. Therefore it finds
	 the next smallest value, excluding all values already found
	 and stored in a[0],...,a[j]
      */
    for (i = j+1;i<MAX_SIZE;i++)
		{
		if (a[i]>max)
			{
			max = a[i];
			maxindx = i;
			}
		}
      /* swap a[j] and a[minindx] */
      tmp = a[j];
      a[j] = a[maxindx];
      a[maxindx] = tmp;
    }
	if(a[inp] < 0.5)
	{
		printf("%d largest value is less than 0.5", inp);
	}
	else
	{
		printf("The Kth largest value in the array is %lf\n", a[inp]);
	}
  
  return 0;
  
}

