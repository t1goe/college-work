//Written by: Thomas Igoe 		Student No: 17372013
//To count the words in a string

#include <stdio.h>

//Declares Function
int countw(char x[]);

int main(void)
{
	char msg[60];
	
	printf("Word counter \nEnter string to be counted\n\n");
	
	//Takes string to be processed
	fgets(msg, 60, stdin);
	
	printf("The number of words in this string is:%d", countw(msg));
	
	return 0;
}

//Function Body
int countw(char x[])
{
	int i, count=1;
	
	//Goes through all characters in the array, if there is a character followed
	//by a space/ null term, +1 word count
	for(i=0; x[i]!='\0'; i++)
	{
		if((x[i]==' ' || x[i]=='\0') && x[i-1]!=' ')
		{
			count++;
		}
	}
	return count;
}