//Names: Tom Igoe, Ronan Kelly
//Student Numbers: Tom's = 17372013    Ronan's = 17325601
//Assignment: 2
#include <stdio.h>

#define LIMIT 20

//Player struct that keeps track of the players and their scores
struct player
{
	char name[LIMIT];
	int score;
	char disk;
};

//Declaring player structs.
struct player p1;
struct player p2;

//Declaring the two dimensional global array for the board.
char board[8][8];

//Declaring and initialising the number of spaces available.
int numOfSpacesLeft = 64;

void printBoard(void);

int main(void)
{	
	//Asking for name inputs
	printf("\nEnter name player 1: ");
	scanf("%s", &p1.name);
	printf("\nEnter name player 2: ");
	scanf("%s", &p2.name);
	
	
	//Assigning disk to player.
	p1.disk = '0';
	p2.disk = '1';
	
	
	int i, j;
	/* This loop fills the 8x8 empty board spaces with an x. */
	for (i = 0;i < 8;i++)
	{
		for (j = 0;j < 8;j++)
		{
			board[i][j] = 'x';
		}
	}
	printBoard();
	
	/* Putting the starting positions onto the board. */
	board[3][3] = '1';
	board[4][4] = '1';
	board[3][4] = '0';
	board[4][3] = '0';
	
	//Setting the scores.
	p1.score = 2;
	p2.score = 2;
	numOfSpacesLeft -= 4;
	printBoard();

	return 0;
}

void printBoard(void)
{
	int i, j;
	//Printing the board.
	printf("   1  2  3  4  5  6  7  8\n");
	for (i = 0;i < 8;i++)
	{
		printf("%d ", i+1);
		for (j = 0;j < 8;j++)
		{
			printf("|%c|", board[i][j]);
		}
	printf("\n");	
	}
	//Printing player and disk information.
	printf("0: %s = %d disks\n", p1.name, p1.score);
	printf("1: %s = %d disks\n", p2.name, p2.score);
	printf("Number of available spaces left: %d\n", numOfSpacesLeft);
}