//Thomas Igoe, Student no: 17372013
//To convert seconds to days/hours/seconds/minutes by using struct time

# include <stdio.h>

//Declaring struct time
typedef struct Time
{
	int days;
	int hours;
	int minutes;
	int seconds;
} Time;

Time convert_time (Time y);

int main (void)
{
	struct Time x;
	x.days = 0;
	x.hours = 0;
	x.minutes = 0;
	x.seconds = 0;
	
	//taking input
	while(x.seconds<=0)
	{
		printf("Enter number of total seconds to convert to time: \n");
		scanf("%d", &x.seconds);
		if(x.seconds<0)
		{
			printf("Input is not valid\n");
		}
	}
	
	//Printing and calculating at the same time
	printf("%d Days %d Hours %d Minutes %d Seconds", convert_time(x));
	
	return 0;
}


Time convert_time (Time y)
{
	Time t;
	int remaining_secs = y.seconds;
	
	t.days = remaining_secs / (24*60*60);
	remaining_secs %= (24*60*60);
	
	t.hours = remaining_secs / (60*60);
	remaining_secs %= (60*60);
	
	t.minutes = remaining_secs / 60;
	remaining_secs %= 60;
	
	t.seconds = remaining_secs;
	return t;
}