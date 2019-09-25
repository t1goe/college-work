//Thomas Igoe, Student no: 17372013
//To convert seconds to days/hours/minutes/seconds by use of pointers and arguments

# include <stdio.h>

void convert_time(int *days, int *hours, int *minutes, int *seconds);

int main (void)
{
	//Declare variables
	int days=0, hours=0, minutes=0, seconds=0;
	
	//input seconds
	while(seconds<=0)
	{
		printf("Enter number of total seconds to convert to time: \n");
		scanf("%d", &seconds);
		if(seconds<0)
		{
			printf("Input is not valid\n");
		}
	}
	
	//function command
	convert_time(&days, &hours, &minutes, &seconds);
	
	//print output
	printf("%d Days %d Hours %d Minutes %d Seconds", days, hours, minutes, seconds);
	
	return 0;
}

//implementation fo remining_seconds function
void convert_time(int *days, int *hours, int *minutes, int *seconds)
{
	int remaining_secs;
	remaining_secs = *seconds;
	
	*days = remaining_secs / (24*60*60);
	remaining_secs %= (24*60*60);
	
	*hours = remaining_secs / (60*60);
	remaining_secs %= (60*60);
	
	*minutes = remaining_secs / 60;
	remaining_secs %= 60;
	
	*seconds = remaining_secs;
	return ;
}