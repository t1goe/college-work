#include <stdio.h>

int main(void)
{
	//i keeps count in the for loops; msg[] stores string
	int i;
	char msg[50] = "Make this string uppercase.";
	
	//loop to convert all lowercase to uppercase by decreasing them by 32
	for(i=0; i<50; i++)
	{
		if(msg[i]>96 && msg[i]<123)
		{
			msg[i] -= 32;
		}
	}
	
	//loop to print array msg[]
	for(i=0; i<50; i++)
	{
		printf("%c", msg[i]);
	}
	
	return 0;
}