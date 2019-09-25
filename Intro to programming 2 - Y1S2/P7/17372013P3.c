#include <stdio.h>
#include <stdlib.h>
#include <string.h>

//Jumble the order of words
void jumble(char *input[]);

//prints all the words
void pront(char *input[]);

//Selects ONE random word
void rando(char *input[]);

int main(void)
{
	//List of words
	char *words[12] = {"COMP10120", "is", "my", "favourite", "module", "and", "I", "learn", "lots", "of", "interesting", "things"};
	
	jumble(words);
	pront(words);
	rando(words);
	
	return 0;
}

//Jumble the order of words
void jumble(char *input[])
{
	char *temp;
	int r1,r2;
	
	for(int i=0; i<40; i++)
	{
		r1 = rand()%12;
		r2 = rand()%12;
		temp = input[r1];
		input[r1] = input[r2];
		input[r2] = temp;
	}
}

//prints all the words
void pront(char *input[])
{
	for(int i=0; i<12; i++)
		printf("%s ", input[i]);
}

//Selects ONE random word
void rando(char *input[])
{
	printf("\n\n%s ", input[rand()%12]);
}