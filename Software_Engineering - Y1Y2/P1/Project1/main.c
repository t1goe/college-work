#include <stdio.h>
#include <string.h>
#include "sortAndShuffle.h"

int main(void)
{
    //The array containing artists names
    char artists[4][NAMELENGTH];
    //The array containing the sorted artists
    char sortedArtists[4][NAMELENGTH];
    //Songs for Artist 1
    char songsArtist0[3][NAMELENGTH];
    //Songs for Artist 2
    char songsArtist1[3][NAMELENGTH];
    //Songs for Artist 3
    char songsArtist2[3][NAMELENGTH];
    //Songs for Artist 4
    char songsArtist3[3][NAMELENGTH];
    //The total number of artists (Note it can be less than 4)
    int numOfArtists = 0;
    //The total number of songs for each artist (Note that less than 3 songs can be provided for each artist)
    int numSongsPerArtist[4];
    //This keeps track of the order of sorted artists. Ie artistorder[0] == 3 means that songsArtist0 is now in sortedArtists[3]
    int artistorder[4]={-1,-1,-1,-1};

    //i counts artists; j counts songs; cont1 conditional for artists; cont2 conditional for songs;
    int i, j, cont1 = 1, cont2;
    //temporary holding
    char temp[NAMELENGTH];

    /*
    * Use here functions that you should implement to insert artists and songs from the standard input.
    * Note that you also need to track the number of artists and the number of songs for each artist.
    */
    printf("Please input artists. Input ! to finish\n");
    for(i=1; i<=4 && cont1 == 1; i++)
    {
        printf("\nInput artist %d: ", i);
        //scanf("%s", artists[i-1]);
        fgets(artists[i-1], NAMELENGTH, stdin);

        if(artists[i-1][0]=='\n')
        {
            cont1 = 0;
            break;
        }
        else
        {
            artistorder[i-1]=i-1;
            numOfArtists++;
            printf("Input songs by %s\n", artists[i-1]);
            cont2 = 1;
        }

        numSongsPerArtist[i-1] = 0;
        for(j=1; j<=3 && cont1==1 && cont2==1; j++)
        {
            //temp[0]='a';
            printf("  Song %d: ", j);
            //scanf("%s", temp);
            fgets(temp, NAMELENGTH, stdin);
            if(temp[0]=='\n')
            {
                cont2 = 0;
            }
            else
            {
                numSongsPerArtist[i-1]++;
                switch(i)
                {
                case'0':
                    strncpy(songsArtist0[j], temp, 80);
                    break;
                case'1':
                    strncpy(songsArtist1[j], temp, 80);
                    break;
                case'2':
                    strncpy(songsArtist2[j], temp, 80);
                    break;
                case'3':
                    strncpy(songsArtist3[j], temp, 80);
                    break;
                }
            cont1 = 1;
            }

        }
    }

    //Transfering artists[] to sorted artists to be used
    for(i=0; i<4; i++)
    {
        //strcpy(destination, source)
        strcpy(sortedArtists[i], artists[i]);
    }
/*
 * Use here the function sortArtists to sort the array of the artists and sortSongs to sort the songs of each artist
 * Print each artist (alphabetically) and for each of them print the list of songs sorted alphabetically
 */
    sortArtists(sortedArtists[][NAMELENGTH], numOfArtists, artistorder[]);


 /*
 * Use here the function shuffleSongs to shuffle all the songs
 * Print the list of shuffled songs
 */
 //Call to sort songs multiple times to sort each artists' songs
    for(i=0; i<=numOfArtists; i++)
    {
        sortSongs(songsOfAnArtist[i][80], numOfArtists);
    }


    //shuffleSongs(songsToBeShuffled[][80], numOfSongs);

  return 0;
}
