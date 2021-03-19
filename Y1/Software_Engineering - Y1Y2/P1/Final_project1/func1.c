#include "func1.h"

//Fill input array with song structs, and records number of songs per artist
void songinput(struct song songarray[12], int songnum[4])
{
    //i counts the total number of songs, j counts the songs of the current artist, k counts the artists
	//songnum records how many songs per artist
    int i=0, j=0, k=0;
	char temp[NAMELENGTH];

    printf("Input artists, and songs\n\n");
    for(i=0; i<12 && k<4; k++)
    {
		//Get input
        printf("Input artist %d: ", k+1);
        fgets(temp, NAMELENGTH, stdin);

		//If input is blank, then exit loop
		if(temp[0]=='\n')
		{
			break;
		}
		else
		{
			//Input songs of artist
			printf("\nInput songs of %s", temp);

			//Loop to get songs for artist
			for(j=0; j<3; j++)
			{
				//Ask for song name
				printf("\tInput song %d: ", j+1);
				fgets(songarray[i].name, NAMELENGTH, stdin);

				//If enter (\n) is pressed then go down a level
				if(songarray[i].name[0]=='\n')
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
					//Copy the artist name to the song
					strncpy(songarray[i].artist, temp, NAMELENGTH);

					//Change last character to \0 from \n to stop it form creating unnecessary new lines
					songarray[i].artist[strlen(songarray[i].artist) - 1] = '\0';

					//Counts the song in the array
					songnum[k]++;

					//Counts the songs in the current loops
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

    char swap[NAMELENGTH], temp1[NAMELENGTH], temp2[NAMELENGTH];
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

	//Sort ALL songs
	for(i=0; i<numOfSongs-1; i++)
	{
        minIndex = i;
        for(j=i+1; j<numOfSongs; j++)
		{
			//Compares uppercase of string as to not affect the sorting
			strcpy(temp1, songarray[j].name);
			strcpy(temp2, songarray[minIndex].name);
			uppers(temp1);
			uppers(temp2);
            if(strcmp(temp1, temp2) < 0)
			{
                minIndex = j;
                minIndexChanged =1;
            }
        }

        if(minIndexChanged == 1)
		{
            memset(swap, '$', NAMELENGTH-2);
            swap[NAMELENGTH -1] = '\0';

			//Swaps song names
            strcpy(swap,songarray[i].name);
            strcpy(songarray[i].name, songarray[minIndex].name);
            strcpy(songarray[minIndex].name, swap);

			//Swaps artists
			strcpy(swap,songarray[i].artist);
            strcpy(songarray[i].artist, songarray[minIndex].artist);
            strcpy(songarray[minIndex].artist, swap);

            minIndexChanged =0;
        }
    }

	//Sorts ALL artists
	for(i=0; i<numOfSongs-1; i++)
	{
        minIndex = i;
        for(j=i+1; j<numOfSongs; j++)
		{
			//Compares the Uppercase of the strings, so the case does not impact the sorting
			strcpy(temp1, songarray[j].artist);
			strcpy(temp2, songarray[minIndex].artist);
			uppers(temp1);
			uppers(temp2);
            if(strcmp(temp1, temp2) < 0)
			{
                minIndex = j;
                minIndexChanged =1;
            }
        }

        if(minIndexChanged == 1)
		{
            memset(swap, '$', NAMELENGTH-2);
            swap[NAMELENGTH -1] = '\0';

			//Switches the songs
            strcpy(swap,songarray[i].name);
            strcpy(songarray[i].name, songarray[minIndex].name);
            strcpy(songarray[minIndex].name, swap);

			//Switches the artists
			strcpy(swap,songarray[i].artist);
            strcpy(songarray[i].artist, songarray[minIndex].artist);
            strcpy(songarray[minIndex].artist, swap);

			//Reset the index changed state
            minIndexChanged =0;
        }
    }
}

//randomizes songs, and prints them
void songrand(struct song songarray[12], int songnum[4])
{
	srand(time(NULL));
	int played[12] = {0}, recent[12] = {0};
	int a, b=1, i=0, j;
	int numOfSongs = songnum[0] + songnum[1] + songnum[2] + songnum[3];

	printf("++SHUFFLE MODE++\n");
	while(i<(2*numOfSongs))
	{
		//Pick random numbers
		a = rand()%numOfSongs;

		//Checks if the song has been played more than twice, or if it has come up in the last 5 songs
		if(played[a]<2 && recent[0]!=a && recent[1]!=a && recent[2]!=a && recent[3]!=a && recent[4]!=a)
		{
			//Doesn't print if it's an empty array
			if(songarray[a].name[0]!='\n')
			{
				printf("Song %d: %s - %s\n", b, songarray[a].artist, songarray[a].name);
				b++;
			}

			//Moves the songs up in the recent array
			recent[4] = recent[3];
			recent[3] = recent[2];
			recent[2] = recent[1];
			recent[1] = recent[0];
			recent[0] = a;

			//Keeps track of how many times each has been played, and increments the counter for songs
			played[a] += 1;
			i++;
		}
	}

}

void songprint(struct song songarray[12], int songnum[4])
{
	int i, numOfArtists=0;
	int numOfSongs = songnum[0] + songnum[1] + songnum[2] + songnum[3];
	//Calculates number of artist by adding 1 for every non-zero element in number of songs
	for(i=0; i<4; i++)
	{
		if(songnum[i]>0)
		{
			numOfArtists++;
		}
	}

	//Goes through the loop to print all songs with something in
	for(i=0; i<numOfSongs; i++)
	{
		if(songarray[i].name[0] != '~')
		{
			printf("%s - %s\n", songarray[i].artist, songarray[i].name);
		}
	}

	//Prints the songs by the artist
	for(i=0; i<4; i++)
	{
		if(songnum[i]!=0)
		{
			printf("Number of songs by artist %d: %d\n", i+1, songnum[i]);
		}
	}
}

void uppers(char string[NAMELENGTH])
{
	for(int i=0; i<NAMELENGTH; i++)
	{
		//If lowercase
		if(string[i]>96 && string[i]<123)
		{
			//make uppercase
			string[i]-=32;
		}
	}
}
