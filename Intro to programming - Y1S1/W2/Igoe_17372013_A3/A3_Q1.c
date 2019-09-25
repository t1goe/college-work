//Modified by Thomas Igoe
//Sept 2017

#include <stdio.h>

int main(void)
{
	int day, i;
	
	for(day = 1; day <= 12; day = day + 1)
	{
		printf("On the %d day of Christmas, ", day);
		printf("my true love gave to me\n");
		for(i = day; i > 0; i = i - 1)
		{
			if(i == 1) 
			{
				if(day == 1)
				printf ("A ");
				else 	printf("And a ");
				printf("partridge in a pear tree. \n");
			}
			else if(i == 2)
				printf("Two turtledoves, \n");
			else if (i == 3)
				printf("Three french hens, \n");
			else if (i == 4)
				printf("Four Calling Birds\n");
			else if (i == 5)
				printf("Five Golden Rings\n");
			else if (i == 6)
				printf("Six Geese a Laying\n");
			else if (i == 7)
				printf("Seven Swans a Swimming\n");
			else if (i == 8)
				printf("Eight Maids a Milking\n");
			else if (i == 9)
				printf("Nine Ladies Dancing\n");
			else if (i == 10)
				printf("Ten Lords a Leaping\n");
			else if (i == 11)
				printf("Eleven Pipers Piping\n");
			else if (i == 12)
				printf("12 hoes a hoeing\n");
		}
	}
}