#define NAMELENGTH 80

struct song
{
    char artist[NAMELENGTH];
    char name[NAMELENGTH];
};

//Fill input array with song structs, and records number of songs per artist
void songinput(struct song songarray[12], int songnum[4]);

//Sorts songs alphabetically, then artists
void songsort(struct song songarray[], int songnum[4]);

//randomizes songs, and prints them
void songrand(struct song songarray[], int songnum[4]);

