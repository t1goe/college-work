//Name: Thomas Igoe		Student No:17372013
//To scan names of towns and apply the correct county data using nested structs
#include <stdio.h>
#include <string.h>
#include <stdlib.h>

struct county
{
	char *longName;
	char *shortName;
	unsigned int population;
};

struct town
{
	char *name;
	unsigned int population;
	struct county aCounty;
};

int main(void)
{
	struct town tempt;
	char *temps = malloc(128);
	//i counts outer loop (towns), j counts inner loop (counties)
	int i, j;
	
	//towns.txt
	FILE *cPtr1;
	//counties.txt
	FILE *cPtr2;
	
	//Allocates memeory to the char pointers to allow them to store strings
	tempt.name = malloc(128);
	tempt.aCounty.longName = malloc(128);
	tempt.aCounty.shortName = malloc(128);
	
	//Catches it if it can't open (missing files etc)
	if(((cPtr1 = fopen("towns.txt", "r")) == NULL) || ((cPtr2 = fopen("counties.txt", "r")) == NULL))
	{
		puts("One or more files couldn't be read");
	}
	else
	{
		//Loops to go to the towns
		for(i=0; i<5; i++)
		{
			//Scans from towns.txt
			fscanf(cPtr1, "%s %d %s\n", tempt.name, &tempt.population, tempt.aCounty.longName);
			
			//Loops through counties to find the correct one
			for(j=0; j<4; j++)
			{
				//Scans from counties.txt
				fscanf(cPtr2, "%s %s %d\n", temps, tempt.aCounty.shortName, &tempt.aCounty.population);
				
				//Test if the current county is == to the county being compared
				if(strcmp(tempt.aCounty.longName, temps) == 0)
				{
					break;
				}
			}
			//Resets county pointer to the start, so it can all be tested again
			fseek(cPtr2, 0, SEEK_SET);
			
			//Prints all info
			printf("%s %d %s %s %d\n", tempt.name, tempt.population, tempt.aCounty.longName, tempt.aCounty.shortName, tempt.aCounty.population);
		}
	}
	
	//Closes files
	fclose(cPtr1);
	fclose(cPtr2);
	
	return 0;
}