#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include "sorts.h"


int main()
{
    struct song songarray[12];
    int songnum[4]={0};

    //Fill input array with song structs, and records number of songs per artist
    songinput(songarray, songnum);

    //Sorts songs alphabetically, then artists
    songsort(songarray, songnum);

    //randomizes songs, and prints them
    songrand(songarray, songnum);


    return 0;
}
