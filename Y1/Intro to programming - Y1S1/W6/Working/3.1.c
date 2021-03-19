//Written by Thomas Igoe, Student No 17372013
//To convert array dec[] using the key[] array

#include <stdio.h>

int main(void)
{
	/* take the character of the array, detect if it's uppercase or lowercase,
	then find that value in the key array and assign it that*/
	char key[] = "sphinxofblackqrtzjudgemyvwSPHINXOFBLACKQRTZJUDGEMYVW";
	char alp[] = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	char dec[] = "B sk irbq sq sqojv jbofd qrm.                     ";
	char blank[] = "                                                  ";
	int i, j;
	
	//for loop to go through the array and test all chars
	// uppercase is 1st if, lowercase is 2nd if, other is 3rd if
	for(i=0; i<50; i++)
	{
		for(j=0; j<52; j++)
		{
			if(dec[i] == key[j])
			{
				blank[i] = alp[j];
			}
		}
	}
	
	printf("\n \n");
	
	//for loop to print array
	for(i=0; i<50; i++)
	{
		printf("%c", blank[i]);
	}
	return 0;
}