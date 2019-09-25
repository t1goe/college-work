#include <stdio.h>

int main(void)
{
	/* take the character of the array, detect if it's uppercase or lowercase,
	then find that value in the key array and assign it that*/
	char key[26] = "sphinxofblackqrtzjudgemyvw";
	char enc[50] = "I am doin an angry right now.";
	int i;
	
	//for loop to go through the array and test all chars
	for(i=0; i<50; i++)
	{
		if(enc[i]>96 && enc[i]<123)
		{
			enc[i] = key[(enc[i])-97];
		}
		else if(enc[i]>64 && enc[i]<91)
		{
			enc[i] = key[(enc[i])-65];
			enc[i] -= 32;
		}
	}
	
	//for loop to print array
	for(i=0; i<50; i++)
	{
		printf("%c", enc[i]);
	}
	return 0;
}