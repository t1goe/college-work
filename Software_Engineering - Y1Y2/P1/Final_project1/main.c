#include <stdio.h>
#include <string.h>

#include "func1.h"

int main()
{
    struct song songarray[12];
	//loop to clear the songarray
	for(int i = 0; i < 12; i++)
	{
		songarray[i].name[0] = '~';
		songarray[i].artist[0] = '~';
	}

    int songnum[4]={0};

    //Fill input array with song structs, and records number of songs per artist
    songinput(songarray, songnum);

    //Sorts songs alphabetically, then artists
    songsort(songarray, songnum);

	//Test function, to print songarray[]
	songprint(songarray, songnum);

    //randomizes songs, and prints them
    songrand(songarray, songnum);


    return 0;
}
