#include <stdio.h>
#include <string.h>
#include <stdlib.h>

#define LIMIT 30

void convert(char *string);

int main(void)
{
	char strang[LIMIT]={"string"};
	
	printf("%s\n", strang);
	convert(&strang[30]);
	printf("TEST\n");
	return 0;
}

void convert(char *string)
{
	char *ptr = string;
	//uppercase part
	for(int i=0; i<30; i++)
	{
		//if lowercase, make upper
		if(96<*ptr && 123>*ptr)
		{
			*ptr -= 32;
		}
		*ptr++;
	}
	
	char temp[LIMIT];
	*ptr = string;
	strcpy(temp, *string);
	//remove consonants
	for(int i=0; i<30; i++)
	{
		//if lowercase, make upper
		if((65<*ptr && 69>*ptr) || (69<*ptr && 73>*ptr) || (*ptr>73 && 79>*ptr) || (*ptr>79 && 85>*ptr) || (*ptr>85 && *ptr>91))
		{
			//do nothing
		}
		else
		{
			printf("%c", *ptr)
		}
		*ptr++;
	}
}