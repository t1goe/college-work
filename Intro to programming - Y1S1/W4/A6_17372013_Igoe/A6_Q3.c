#include <stdio.h>

int main(void)
{
	/*Array that increases value of corrisponding number by one each time,
	then sorts to find largest number in counting array*/
	int a[16]={2,3,2,2,2,2,2,1,4,5,2,3,1,2,2,2}, b[6]={0}, count1, count2=0, asize, temp, j;
	
	/*to set all b[] to 0 	wait nevermind
	for(i=0; i < 6; i++)
	{
		b[i] = 0;
	}*/
	
	//increases the corrisponding b[] counter value for each value in the a[]
	for(count1=0;count1<16;count1++)
	{
		temp = a[count1];
		//printf("temp= %d \n", temp);
		b[temp]++;
		// testing printf     printf("b[temp] = %d \n", b[temp]);
	}
	
	asize = (16/2) + 1;
	//prints the values over asize
	printf("All values over (A/2) + 1:\n");
	for (j=0;j<16;j++)
	{
		if(b[count2]>asize)
		{
			printf("%d \n", count2);
		}
		count2++;
	}
	
	
	
	return 0;
}