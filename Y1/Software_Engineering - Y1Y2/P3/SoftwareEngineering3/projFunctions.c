#include <stdio.h>
#include "projFunctions.h"

//Player struct that keeps track of the players and their scores
struct player
{
	char name[LIMIT];
	int score;
	char Disk;
};

//DECLARING GLOBAL VARIABLES:
//Declaring player structs.
struct player p1;
struct player p2;

//Declaring the two dimensional global array for the board.
char board[8][8];

//Declaring an array to hold the coordinates.
char coordinates[2];

//Declaring variable to see if there are any possible moves to make on the board at all.
int nomoves;


//FUNCTIONS:
//Prints board
void printBoard(int numOfSpacesLeft)
{
	int i, j;
	//Printing the board.
	printf("   1  2  3  4  5  6  7  8\n");
	for (i = 0;i < 8;i++)
	{
		printf("%c ", 97+i);
		for (j = 0;j < 8;j++)
		{
			printf("|%c|", board[j][i]);
		}
		printf("\n");	
	}
	//Printing player and disk information.
	numOfSpacesLeft = 60 - (p1.score + p2.score);
	checkScores(); //Checks current scores.
	printf("Player 1: %s: %d disks\n", p1.name, p1.score);
	printf("Player 2: %s: %d disks\n", p2.name, p2.score);
	printf("Number of available spaces left: %d\n", numOfSpacesLeft);
}

//Returns a number to indicate what ways are valid moves
//10000000 indicates that up is a valid move but nothing else
//EG: 1 indicates valid move, and 0 indicates invalid
//going clockwise
int moveDirection(int turn, char x, char y)
{
	int ans=0, i;
	//int mult = 1;
	//playercurrent, player opposite
	char pc, po;

	//tempx, tempy
	int tx = convertCoord(x);
	int ty = convertCoord(y);
	
	//setting what gamepieces are for each character
	if(turn == 1)
	{
		pc = p1.Disk;
		po = p2.Disk;
	}
	else if(turn == 2)
	{
		pc = p2.Disk;
		po = p1.Disk;
	}
	else
	{
		printf("+\n+\n+\nSOMETHING HAS GONE HORRIBLY WRONG IN MOVEDIRECTION\n+\n+\n+");
	}
	
	//Actually checking each of the 8 directions which might make this space a valid move
	if(board[tx][ty] == 'x')
	{
		//Straight up
		if((ty-1>-1) && (board[tx][ty-1] == po))
		{
			i = 1;
			while((board[tx][ty-i] == po) && ((ty-i)>-1))
			{
				i++;
			}
			
			//If first non-opposite piece is currentpiece then valid, otherwise invalid
			if((board[tx][ty-i] == pc) && ((ty-i)>-1))
			{
				ans += 1;
			}
		}
		ans *= 10;
		
		//Up-right
		if((tx+1<8) && (ty-1>-1) && (board[tx+1][ty-1] == po))
		{
			i = 1;
			while((board[tx+i][ty-i] == po) && ((ty-i)>-1) && ((tx+i)<8))
			{
				i++;
			}
			
			//If first non-opposite piece is currentpiece then valid, otherwise invalid
			if((board[tx+i][ty-i] == pc) && ((ty-i)>-1) && ((tx+i)<8))
			{
				ans += 1;
			}
		}
		ans *= 10;
		
		//Right
		if((tx+1<8) && (board[tx+1][ty] == po))
		{
			i = 1;
			while((board[tx+i][ty] == po) && ((tx+i)<8))
			{
				i++;
			}
			
			//If first non-opposite piece is currentpiece then valid, otherwise invalid
			if((board[tx+i][ty] == pc) && ((tx+i)<8))
			{
				ans += 1;
			}
		}
		ans *= 10;
		
		//Down-right
		if((tx+1<8) && (ty+1<8) && (board[tx+1][ty+1] == po))
		{
			i = 1;
			while((board[tx+i][ty+i] == po) && ((ty+i)<8) && ((tx+i)<8))
			{
				i++;
			}
			
			//If first non-opposite piece is currentpiece then valid, otherwise invalid
			if((board[tx+i][ty+i] == pc) && ((ty+i)<8) && ((tx+i)<8))
			{
				ans += 1;
			}
		}
		ans *= 10;
		
		//Down
		if((ty+1<8) && (board[tx][ty+1] == po))
		{
			i = 1;
			while((board[tx][ty+i] == po) && ((ty+i)<8))
			{
				i++;
			}
			
			//If first non-opposite piece is currentpiece then valid, otherwise invalid
			if((board[tx][ty+i] == pc) && ((ty+i)<8))
			{
				ans += 1;
			}
		}
		ans *= 10;
		
		//Down-left
		if((tx-1>-1) && (ty+1>8) && (board[tx-1][ty+1] == po))
		{
			i = 1;
			while((board[tx-i][ty+i] == po) && ((ty+i)<8) && ((tx-i)>-1))
			{
				i++;
			}
			
			//If first non-opposite piece is currentpiece then valid, otherwise invalid
			if((board[tx-i][ty+i] == pc) && ((ty+i)<8) && ((tx-i)>-1))
			{
				ans += 1;
			}
		}
		ans *= 10;
		
		//Left
		if((tx-1>-1) && (board[tx-1][ty] == po))
		{
			i = 1;
			while((board[tx-i][ty] == po) && ((tx-1)>-1))
			{
				i++;
			}
			
			//If first non-opposite piece is currentpiece then valid, otherwise invalid
			if((board[tx-i][ty] == pc) && ((tx-i)>-1))
			{
				ans += 1;
			}
		}
		ans *= 10;
		
		//Up-left
		if((tx-1>-1) && (ty-1>-1) && (board[tx-1][ty-1] == po))
		{
			i = 1;
			while((board[tx-i][ty-i] == po) && ((ty-i)>-1) && ((tx-i)>-1))
			{
				i++;
			}
			
			//If first non-opposite piece is currentpiece then valid, otherwise invalid
			if((board[tx-i][ty-i] == pc) && ((ty-i)>-1) && ((tx-i)>-1))
			{
				ans += 1;
			}
		}
		ans *= 10;
	}
	return ans;
}//COMPLETED

//Checks a single space to see if it's a valid move
//Inputs who's turn it is, and x/y coordinates
//Outputs 1 if valid, 0 if not
int validMove(int turn, char x, char y)
{
	if(moveDirection(turn, x, y)==0)
		return 0;
	else
		return 1;
}//COMPLETED

//Prints all valid moves for a given player
void showMoves(int turn)
{
	int i, j;
	char x, y;
	nomoves = 1;
	printf("\n---List of possible moves---\n");
	for(i=0; i<8; i++)
	{
		for(j = 0;j < 8;j++)
		{
			x = 49+i;
			y = 97+j;
			if(validMove(turn, x, y))
			{
				printf("\t(%c, %c)\n", 49+i, 97+j);
				nomoves = 0;
			}
		}
	}
}//Completed, due for Tom's approval
//Ehhhhh

//Func to make a move
//Input 1 for player 1, or 2 for player 2
void makeMove(int turn)
{
	int x, y;
	int pmoves;
	int tempx,tempy;
	//Shows possible moves.
	showMoves(turn);

	do
	{
		printf("\nEnter your desired coordinates player %d", turn);
		getchar(); //Bug fix - stops program from skipping X input.
		printf("\nX coordinate:");
		scanf("%c", &coordinates[0]);
		getchar();//Bug fix - stops program from skipping Y input.
		printf("\nY coordinate:");
		scanf("%c", &coordinates[1]);
		
		printf("\nTESSST----%c%c\n", coordinates[0], coordinates[1]);

		//Check if inputted move is valid.
		if(!validMove(turn, coordinates[0], coordinates[1]))
		{
			printf("Invalid move.");
		}
	}while(!validMove(turn, coordinates[0], coordinates[1]));
		
	//Converting char coordinates to integer equivalents.
	x = convertCoord(coordinates[0]);
	y = convertCoord(coordinates[1]);
	tempx = x;
	tempy = y;
	
	pmoves = moveDirection(turn, coordinates[0], coordinates[1]);
	
	if(turn == 1)
		board[x][y] = p1.Disk;
	else if(turn == 2)
		board[x][y] = p2.Disk;


	//printf("\n-----Pmoves: %d\n", pmoves);
	pmoves /= 10;
	//Up-Left.
	if((pmoves%10) != 0)
	{
		while(board[x][y] != board[tempx-1][tempy-1])
		{
			board[tempx-1][tempy-1] = board[x][y];
			tempx--;
			tempy--;
		}
	}
	pmoves /= 10;
	tempx = x;
	tempy = y;
	
	//Left.
	if((pmoves%10) != 0)
	{
		while(board[x][y] != board[tempx-1][tempy])
		{
			board[tempx-1][tempy] = board[x][y];
			tempx--;
		}
	}
	pmoves /= 10;
	tempx = x;
	tempy = y;
	
	//Down-Left.
	if((pmoves%10) != 0)
	{
		while(board[x][y] != board[tempx-1][tempy+1])
		{
			board[tempx-1][tempy+1] = board[x][y];
			tempx--;
			tempy++;
		}
	}
	pmoves /= 10;
	tempx = x;
	tempy = y;
	
	//Down.
	if((pmoves%10) != 0)
	{
		while(board[x][y] != board[tempx][tempy+1])
		{
			board[tempx][tempy+1] = board[x][y];
			tempy++;
		}
	}
	pmoves /= 10;
	tempx = x;
	tempy = y;
	
	//Down-Right
	if((pmoves%10) != 0)
	{
		while(board[x][y] != board[tempx+1][tempy+1])
		{
			board[tempx+1][tempy+1] = board[x][y];
			tempx++;
			tempy++;
		}
	}
	pmoves /= 10;
	tempx = x;
	tempy = y;
	
	//Right.
	if((pmoves%10) != 0)
	{
		while(board[x][y] != board[tempx+1][tempy])
		{
			board[tempx+1][tempy] = board[x][y];
			tempx++;
		}
	}
	pmoves /= 10;
	tempx = x;
	tempy = y;
	
	//Up-Right
	if((pmoves%10) != 0)
	{
		while(board[x][y] != board[tempx+1][tempy-1])
		{
			board[tempx+1][tempy-1] = board[x][y];
			tempx++;
			tempy--;
		}
	}
	pmoves /= 10;
	tempx = x;
	tempy = y;
	
	//Up
	if((pmoves%10) != 0)
	{
		while(board[x][y] != board[tempx][tempy-1])
		{
			board[tempx][tempy-1] = board[x][y];
			tempy--;
		}
	}
	
	
}//Complete using a combination of getMove and validMove

//Converts the input coordinate to a int, which can be used in the 2d array for the board
int convertCoord(char x)
{
	//return x;
	/*printf("\n%c, %d ", x, x);*/
	if(x >= 'a' && x <= 'h')
	{
		//printf("Letter");
		//printf("\t\t%d", x);
		return (x - 97);
	}
	else if(x >= '1' && x <= '8')
	{
		//printf("Number");
		//printf("\n%d", x);
		return (x - 49);
	}
	else
	{
		return x;
	}
}//Completed

void checkScores(void)
{
	int i, j;
	p1.score = 0;
	p2.score = 0;
	for(i = 0;i < 8;i++)
	{
		for(j = 0;j < 8;j++)
		{
			if(board[j][i] == '0')
				p1.score++;
			else if(board[j][i] == '1')
				p2.score++;
		}
	}
}//Completed