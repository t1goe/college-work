#include <stdio.h>

#define LIMIT 30

struct game
{
	char title[LIMIT];
	char platform[LIMIT];
	char developer[LIMIT];
	int release;
	char borrower[LIMIT];
	int borrowed;
}game;

struct date
{
	int day;
	int month;
	int year;
}date;

void addgame(void);
void updategame(void);
void removegame(void);

int main(void)
{
	printf("Enter 1 to add games, 2 to update games or 3 to remove games: ");
	int inp;
	scanf("%d", &inp);
	
	if(inp == 1)
		addgame();
	else if(inp == 2)
		updategame();
	else if(inp == 3);
		removegame();
	else
		printf("Invalid input");
}

void inputgame(void)
{
	int counter = 0;
	FILE *fPtr;
	if((fPtr = fopen("games.txt", "w")) == NULL)
	{
		puts("File could not be opened");
	}
	else
	{
		puts("Enter the title, platform, developer, year of release. ");
		puts("Enter EOF to end input");
		printf("%s", "? ");
		
		struct game gameinput;
		int inp;
		
		scanf("%29s%29s%29s%29s%29s%lf", gameinput.title, gameinput.platform, gameinput.developer, gameinput.release);
		//char borrower[LIMIT];
		//int borrowed;
		while(!feof(stdin))
		{
			fprintf(fPtr, "%s %s %s %s %s %.2lf\n", fname, sname, studentno, phoneno, study, gpa);
			printf("%s", "? ");
			scanf("%29s%29s%29s%29s%29s%lf", fname, sname, studentno, phoneno, study, &gpa);
		}
		fclose(fPtr);
	}
}