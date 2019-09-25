#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <time.h>
//#include "sorts.h"

#define NAMELENGTH 80

struct song
{
    char artist[NAMELENGTH];
    char name[NAMELENGTH];
};

//Fill input array with song structs, and records number of songs per artist
void songinput(struct song songarray[12], int songnum[4]);

//Sorts songs alphabetically, then artists
void songsort(struct song songarray[12], int songnum[4]);

//randomizes songs, and prints them
void songrand(struct song songarray[12], int songnum[4]);

//Test function, to print songarray[]
void songprint(struct song songarray[12], int songnum[4]);


int main()
{
    struct song songarray[12];
    int songnum[4]={0};

    //Fill input array with song structs, and records number of songs per artist
    songinput(songarray, songnum);
	
	//Test function, to print songarray[]
	//songprint(songarray, songnum);
	
    //randomizes songs, and prints them
    songrand(songarray, songnum);
	
    //Sorts songs alphabetically, then artists
    //songsort(songarray, songnum);

	//Test function, to print songarray[]
	songprint(songarray, songnum);
	

    return 0;
}

////////////////////////////////////
//#include <stdio.h>
//#include <string.h>
//#include "sorts.h"


//Fill input array with song structs, and records number of songs per artist
void songinput(struct song songarray[12], int songnum[4])
{
    //i counts the total number of songs, j counts the songs of the current artist, k counts the artists
	//songnum records how many songs per artist
	//Each 3 in songarray will be dedicated for each artist, regardless of number of songs
    int i=0, j=0, k=0;
	char temp[NAMELENGTH];
	
    printf("Input artists, and songs\n\n");
    for(i=0; i<12 && k<4; k++)
    {
        printf("Input artist %d: ", k+1);
        fgets(temp, NAMELENGTH, stdin);
		
		//If input is blank, then exit loop
		if(temp[0]=='\n')
		{
			break;
		}
		else
		{
			printf("\nInput songs of %s", temp);
			for(j=0; j<3; j++)
			{
				printf("\tInput song %d: ", j+1);
				fgets(songarray[j+k*3].name, NAMELENGTH, stdin);
				
				if(songarray[j+k*3].name[0]=='\n')
				{
					//If no songs have been added to a given artist, then artist is not counted.
					if(j==0)
					{
						k--;
					}
					break;
				}
				else
				{
					strncpy(songarray[j+k*3].artist, temp, NAMELENGTH);
					//songarray[j+k*3].artist[NAMELENGTH - 1] = '\0';
					songarray[j+k*3].artist[strlen(songarray[j+k*3].artist) - 1] = '\0';
					songnum[k]++;
					i++;
				}
			}
		}
    }
}

//Sorts songs alphabetically, then artists
void songsort(struct song songarray[12], int songnum[4])
{
	int i=0, k=0, j=0;
    int minIndex=0;
    int minIndexChanged=0;

    char swap[NAMELENGTH];
	int numOfSongs = songnum[0] + songnum[1] + songnum[2] + songnum[3];
	int numOfArtists = 0, temp;
	
	//Calculates number of artist by adding 1 for every non-zero element in number of songs
	for(k=0; k<4; k++)
	{
		if(songnum[k]>0)
		{
			numOfArtists++;
		}
	}
		
    printf("\nThe number of songs is %d\nThe number of artists is %d\n", numOfSongs, numOfArtists);
	
	//Loop for sorting songs
	for(k=0; k<4; k+=1)
	{
		for(i=0; i<(songnum[k]-1); i++)
		{
			minIndex = i+3*k;
			for(j=i+1; j<songnum[k]; j++)
			{
				//printf("i: %d, j: %d, minIndex: %d\n", i, j,minIndex);
				if(strcmp(songarray[j+3*k].name, songarray[minIndex].name) < 0)
				{
					minIndex = j+3*k;
					minIndexChanged = 1;
				}
			}
	
			if(minIndexChanged == 1)
			{
				//memset(swap, '$', NAMELENGTH-2);
				//swap[NAMELENGTH-1] = '\0';
				//printf("%s\n", swap);
				strcpy(swap,songarray[i+3*k].name);
				strcpy(songarray[i+3*k].name, songarray[minIndex].name);
				strcpy(songarray[minIndex].name, swap);
				minIndexChanged = 0;
			}
		}
	}

	//Loop for sorting artists
	for(i=0; i<(numOfArtists-1); i++)
	{
        minIndex = i;
        for(j=i+1; j<numOfArtists; j++)
		{
            //printf("i: %d, j: %d, minIndex: %d\n", i, j,minIndex);
            if(strcmp(songarray[j*3].artist, songarray[minIndex*3].artist) < 0)
			{
                minIndex = j;
                minIndexChanged = 1;
            }
        }

        if(minIndexChanged == 1)
		{
            //swap[NAMELENGTH -1] = '\0';
            //printf("%s\n", swap);
			
			//Swaps songcount numbers
			temp = songnum[minIndex];
			songnum[minIndex] = songnum[i];
			songnum[i] = temp;
			
			for(k=0; k<3; k++)
			{	
				//Swap song names
				//memset(swap, '$', NAMELENGTH-2);
				strcpy(swap,songarray[k+i*3].name);
				strcpy(songarray[k+i*3].name, songarray[k+minIndex*3].name);
				strcpy(songarray[k+minIndex*3].name, swap);
				
				//Swap artist names
				//memset(swap, '$', NAMELENGTH-2);
				strcpy(swap,songarray[i*3].artist);
				strcpy(songarray[i*3].artist, songarray[minIndex*3].artist);
				strcpy(songarray[minIndex*3].artist, swap);
				
				minIndexChanged = 0;
			}
        }
    }
}

//randomizes songs, and prints them
void songrand(struct song songarray[12], int songnum[4])
{
	int played[12] = {0}, recent[12] = {0};
	int a, b=1, i=0, j;
	srand(time(NULL));
	
	printf("++SHUFFLE MODE++\n");
	while(i<24)
	{
		a = rand()%12;
		if(played[a]<2 && recent[0]!=a && recent[1]!=a && recent[1]!=a && recent[1]!=a && recent[1]!=a)
		{
			if(songarray[a].name[0]!='\n')
			{
	            //songarray[a].artist[NAMELENGTH - 1] = '\0';
				//songarray[a].name[NAMELENGTH - 1] = '\0';

				printf("Song %d: %s - %s\n", b, songarray[a].artist, songarray[a].name);
				b++;
			}
			played[a] += 1;
			i++;
		}
	}
	
}

void songprint(struct song songarray[12], int songnum[4])
{
	int i;
	for(int a=0; a<4; a++)
	{
		for(i=0; i<songnum[a]; i++)
		{
			//songarray[i+a*3].artist[strlen(songarray[i+a*3].artist) - 1] = '\0';
			printf("%s - %s\n", songarray[i+a*3].artist, songarray[i+a*3].name);
		}
	}
	
	for(i=0; i<4; i++)
	{
		if(songnum[i]!=0)
		{
			printf("Number of songs by artist %d: %d\n", i+1, songnum[i]);
		}
	}
}
