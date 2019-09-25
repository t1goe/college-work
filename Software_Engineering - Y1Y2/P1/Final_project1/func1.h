
#ifndef FUNC_H
#define FUNC_H
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <time.h>
#include "func1.h"
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

//Function to uppercase a string
void uppers(char string[NAMELENGTH]);

#endif // FUNC_H
