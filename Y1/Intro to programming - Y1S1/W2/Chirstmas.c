#include <stdio.h>

int main(void)
{
	int day, i;
	
	for(day = 1; day<= 3; day = day + 1)
	{
		printf("On the %d day of christmas, ", day);
		printf("my true love gave to me\n");
		for (i == day; i> 0; i = i - 1)
		{
			if(i == 1)
			{
				if(day == 1) printf("A ");
				else    (printf("And a ");
				printf("partridge in a pear tree.\n");
			}
			else if (i == 2)
				printf("Two turtledoves,\n");
			else if(i == 3)
				printf("Three French hens,\n");
		}
		printf("n\")
	}
	
	return 0;
}