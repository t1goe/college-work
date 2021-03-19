//Written by Thomas Igoe	Student#: 17372013
//To reverse a string

#include <stdio.h>

char* reverseString(char text[], int numberOfLetters);

int main(void)
{
	char text[120];
	int numberOfLetters = 0;
	scanf("%s",text);
	printf("\n");
	scanf("%d",&numberOfLetters);
	reverseString(text,numberOfLetters);
}

char* reverseString(char text[], int numberOfLetters)
{
	//Print the letters in the string in reverse order
	for(int i=0;i<=numberOfLetters; i++)
	{
		//Starts at end of string given by numberOfLetters, and counts backwards
		printf("%c", text[numberOfLetters-i]);
	}
	return 0;
}