//Written by Thomas Igoe, studen no: 17372013
//To count the number of instances where "dogs" appears (case insenseitive)

#include <stdio.h>

int main(void)
{
	char msg [60];
	int i, dc=0;
	
	//
	printf("Enter string to detect how many times the word dogs appears (case insenseitive): \n");
	fgets (msg, 60, stdin);
	
	for(i=0; msg[i+1]!='\0'; i++)
	{
		if(msg[i]=='d' || msg[i]=='D')
		{
			if(msg[i+1]=='o' || msg[i+1]=='O')
			{
				if(msg[i+2]=='g' || msg[i+2]=='G')
				{
					if(msg[i+3]=='s' || msg[i+3]=='S')
					{
						dc++;
					}
				}
			}
		}
	}
	
	printf("There are %d dogs in this sentance", dc);
	
	return 0;
}