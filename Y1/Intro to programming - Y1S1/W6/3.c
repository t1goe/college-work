#include <stdio.h>

int main(void)
{
	/* take the character of the array, detect if it's uppercase or lowercase,
	then find that value in the key array and assign it that*/
	char key[] = "sphinxofblackqrtzjudgemyvw";
	char alp[] = "abcdefghijklmnopqrstuvwxyz";
	char dec[] = "B sk irbq sq sqojv jbofd qrm.                     ";
	char blank[] = "                                                  ";
	int i, j;
	
	//for loop to go through the array and test all chars
	// uppercase is 1st if, lowercase is 2nd if
	for(i=0; i<50; i++)
	{
		if(dec[i]>64 && dec[i]<91)
		{
			dec[i] +=32;
			for(j=0; j<=26; j++)
			{
				if(dec[i] == key[j])
				{
					blank[i] = alp[j];
					blank[i] -=32;
					//printf("%c", dec[i]);
				}
			}
		}
		
		if(dec[i]>96 && dec[i]<123)
		{
			for(j=0; j<=26; j++)
			{
				if(dec[i] == key[j])
				{
					blank[i] = alp[j];
					//printf("%c", dec[i]);
				}
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