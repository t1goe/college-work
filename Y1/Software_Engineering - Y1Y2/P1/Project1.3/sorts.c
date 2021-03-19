#include <stdio.h>
#include <string.h>
#include "sorts.h"


//Fill input array with song structs, and records number of songs per artist
void songinput(struct song songarray[12], int songnum[4])
{
    printf("Input artists, and songs");
    //i counts artists in outer loop, j counts songs in inner loop
    int i, j;
    for(i=0; i<4; i++)
    {
        printf("Input artist %d", i-1);
        fgets(songarray[i].artist, NAMELENGTH, stdin);
    }
}

//Sorts songs alphabetically, then artists
void songsort(struct song songarray[], int songnum[4])
{

}

//randomizes songs, and prints them
void songrand(struct song songarray[], int songnum[4])
{

}
