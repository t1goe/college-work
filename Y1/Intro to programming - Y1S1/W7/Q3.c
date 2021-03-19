//Written by Thomas Igoe, studen no: 17372013
//To remove punctuation from a string

#include <stdio.h>

int main(void)
{
	char msg [60];
	int i, k;

	//Get string to compute
	printf("Enter string to remove punctuation from: \n");
	fgets (msg, 60, stdin);
	
	//for loop to detect punctuation then move all chars after back 1 space
	for(i=0; msg[i]!='\0'; i++)
	{
		if(msg[i]=='!' || msg[i]=='?' || msg[i]=='.' || msg[i]==',' || msg[i]==';' || msg[i]==':' || msg[i]==39)
		{
			for(k=i; k<j; k++)
			{
				msg[k] = msg[k+1];
			}
			i--;
		}
	}
	
	printf("%s", msg);
	
	return 0;
}