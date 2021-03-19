//Authors:
//Thomas Igoe, student#: 17372013
//Ronan Kelly, student#: 17325601
//Comp10050 Assignment 3
#include <stdio.h>
#include "projFunctions.h"

#define LIMIT 20

//test change 
//Player struct that keeps track of the players and their scores
struct player
{
	char name[LIMIT];
	int score;
	char Disk;
};

//Declaring player structs.
struct player p1;
struct player p2;

//Declaring the two dimensional global array for the board.
char board[8][8];

//Declaring an array to hold the coordinates.
char coordinates[2];

//Declaring variable to see if there are any possible moves to make on the board at all.
int nomoves;

int main(void)
{
	int numOfSpacesLeft;
	int numOfMovesLeft;
	int turn;
	int i, j;
	char count;
	
	//Assigning disk to player.
	p1.Disk = '0';
	p2.Disk = '1';
	
	//Asking for name inputs
	printf("\nEnter name player 1: ");
	scanf("%s", &p1.name);
	printf("Your disk is %c\n", p1.Disk);
	printf("\nEnter name player 2: ");
	scanf("%s", &p2.name);
	printf("Your disk is %c\n\n", p2.Disk);
	
	/* This loop fills the 8x8 empty board spaces with an x. */
	for (i = 0;i < 8;i++)
	{
		for (j = 0;j < 8;j++)
		{
			board[i][j] = 'x';
		}
	}
	
	/* Putting the starting positions onto the board. */
	board[3][3] = p2.Disk;
	board[4][4] = p2.Disk;
	board[3][4] = p1.Disk;
	board[4][3] = p1.Disk;
	
	//Printing starting board.
	printBoard(numOfSpacesLeft);
	
	//MAIN LOOP
	//Getting the user to input their moves
	for(numOfMovesLeft=30; numOfMovesLeft>0; numOfMovesLeft--)
	{
		turn = 1;
		makeMove(turn);
		printBoard(numOfSpacesLeft);
		
		turn = 2;
		makeMove(turn);
		printBoard(numOfSpacesLeft);
		
		//If the game is over before all the spaces have been used (no more possible moves).
		if(nomoves == 1)
			break;
	}
		//Put a conditional to see who wins. COMPLETED

	//Checks who the winner is
	if(p1.score > p2.score)//Player 1 wins
		printf("\nCongratulations! %s is the winner!", p1.name);
	else if(p1.score < p2.score)//Player 2 wins
		printf("\nCongratulations! %s is the winner!", p2.name);
	else if(p1.score == p2.score)
		printf("\nGame has ended in a tie!");
	else
		printf("Wait what\nSomething has gone wrong in the winner's circle");//Should never occur

	return 0;
}