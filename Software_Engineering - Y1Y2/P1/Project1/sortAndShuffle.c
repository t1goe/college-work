#include <stdio.h>
#include <string.h>
#include "sortAndShuffle.h"



/*
* This method sorts the artists alphabetically. It takes as input:
* - sortedArtists: the array of artists that that should be sorted
* - numOfArtists: the total number of artists
*
*/
void sortArtists(char sortedArtists[][80] , int numOfArtists, int artistorder[4])
{
    int i =0;
    int j=0;
    int minIndex = 0;
    int minIndexChanged = 0;

    char swap [NAMELENGTH];

    printf("\nThe number of songs is %d\n", numOfSongs);


    for(i=0; i< (numOfSongs-1); i ++){
        minIndex = i;
        for(j=i+1; j<numOfSongs; j++){
            printf("i: %d, j: %d, minIndex: %d\n", i, j,minIndex);
            if(strcmp(songs[j], songs[minIndex]) < 0){
                minIndex = j;
                minIndexChanged =1;
            }
        }

            if(minIndexChanged == 1){
                memset(swap, '$', NAMELENGTH-2);
                swap[NAMELENGTH -1] = '\0';
                printf("%s\n", swap);
                strcpy(swap,songs[i]);
                strcpy(songs[i], songs[minIndex]);
                strcpy(songs[minIndex], swap);
                minIndexChanged =0;
            }

    }
}

/*
* This method sorts the songs of a specific artist alphabetically. It takes as input:
* - songsOfAnArtist: the array of the songs of an artist that was provided from the standard input
* - numOfArtists: the number of artists provided from the standard input
*/
void sortSongs(char songsOfAnArtist[][NAMELENGTH], int numOfArtists)
{
    int i =0;
    int j=0;
    int minIndex = 0;
    int minIndexChanged = 0;

    char swap [NAMELENGTH];

    printf("\nThe number of songs is %d\n", numOfSongs);

    for(i=0; i<(numOfSongs-1); i ++)
	{
        minIndex = i;
        for(j=i+1; j<numOfSongs; j++)
		{
            printf("i: %d, j: %d, minIndex: %d\n", i, j,minIndex);
            if(strcmp(songs[j], songs[minIndex]) < 0)
			{
                minIndex = j;
                minIndexChanged =1;
            }
        }

        if(minIndexChanged == 1)
		{
            memset(swap, '$', NAMELENGTH-2);
            swap[NAMELENGTH -1] = '\0';
            printf("%s\n", swap);
            strcpy(swap,songs[i]);
            strcpy(songs[i], songs[minIndex]);
            strcpy(songs[minIndex], swap);
            minIndexChanged =0;
        }
    }
}

/*
* This method shuffles a set of songs. It takes as input:
* - songsToBeShuffled: the array of the songs that should be shuffled
* - numOfSongs: the number of songs to be shuffled
*/
void shuffleSongs(char songsToBeShuffled[][80], int numOfSongs)
{

}
