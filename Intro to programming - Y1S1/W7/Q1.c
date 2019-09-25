//Written by Thomas Igoe, studen no: 17372013
//To count words in a string, where the definition of a word is
//a string of characters seperated by spaces

#include <stdio.h>
int main (void)
{
	//Vari declare
	char msg [60];
	int i, wc=1;
	
	//Get input
	printf("Enter string to count words of: \n");
	fgets (msg, 60, stdin);
	
	//Counts all instances where a character is followed by a space/null terminator
	for(i=0; msg[i+1]!='\0'; i++)
	{
		if((msg[i]==' ' ||  msg[i]=='\0') && msg[i-1]!=' ')
		{
			wc++;
			//printf("%c");
		}
	}
	
	//print results
	printf("This message contains %d words", wc);
	
	return 0;
}