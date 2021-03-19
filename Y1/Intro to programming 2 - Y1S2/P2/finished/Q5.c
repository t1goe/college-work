//DOES NOT WORK PROPERLY


//Author: Thomas Igoe	Student #: 17372013
//Output all numbers under the input that are both star and triangle numbers
#include <stdio.h>

//function prototype
int StarAndTriangleNumbers(int max);

int main(void)
{
	//declare vars, take input
	int max;
	printf("Input N: ");
	scanf("%d", &max);
	
	//call to function
	StarAndTriangleNumbers(max);
	
	return 0;
}

//function body
int StarAndTriangleNumbers(int max)
{
	//declare vars
	int snumber = 1, tnumber = 0, tcount = 1, scount = 1, j = 0, a[10] = {0};
	
	//Continue increasing through the triangle numbers and star numbers until star number index is above limit
	while(scount < max)
	{		
		//Start at the lowest star/triangle numbers, and increase which ever is lowest until they match. Save match and continue until
		//max is reached
		//Increase tnumber
		while(tnumber<snumber)
		{
			tnumber += tcount++;
			
			//If they match, then save
			if(tnumber == snumber && tnumber != 1)
			{
				a[j] = snumber;
				j++;
			}
		}
		
		//Increase snumber
		if(tnumber>=snumber)
		{
			snumber = 6*scount*(scount-1) + 1;
			scount++;
			
			//If they match, then save
			if(tnumber == snumber)
			{
				a[j] = snumber;
				j++;
				break;
			}
		}
	}
	
	for(j = 0; j<10; j++)
	{

		if(a[j]==0)
		{
			break;
		}
		
		printf("%d", a[j]);
		if(a[j+1]!=0)
			printf(",");
	}
	return 0;
}