//Author: Thomas Igoe		Student #:17372013
//Obtain the Public's Opinion on topics
#include <stdio.h>

#define ISSUES 5
#define RATINGS 10

// function prototypes	
void recordResponse(int issue);  //^^implement this
void highestRatings(); 
void lowestRatings(); //^^implement this
void displayResults();

int responses[ISSUES][RATINGS]; // Two-dimensional array of poll-ratings
int user; //Keeps track of number of users
const char *topics[ISSUES] = {"Topic 1", "Topic 2", "Topic 3", "Topic 4", "Topic 5"}; // array of opinion topics

int main (void)
{
	int response, i, r;
	user = 0;

	// Administer the poll.
	do{
		user++;
		//Ask the user to rate the 5 topics in the topics array (for loop) remember to check for for valid entries and record the response.
		for(i=0; i<ISSUES; i++)
		{
			recordResponse(i);
		}
		printf("Enter 1 to stop or 0 to allow a new person to rate the issues again: "); // Ask if the user wants to stop
		scanf("%d", &response); // get the user's response
	} while(response != 1);
	
	displayResults();
} 

// Records the user's response to a topic located at index i
void recordResponse(int issue)
{
	int input;
	do
	{
		printf("\n\tInput your rating for %s: ", topics[issue]);
		scanf("%d", &input);
		if(input<0 || input>10)
		{
			printf("Invalid input, please try again");
		}
		else
		{
			responses[issue][input]++;
		}
	}while(input<0 || input>10);
	
}

// get the highest rated topic (highest point total)
void highestRatings(void)
{
	int highRating = 0; 
	int highTopic = 0; 	
   
	for (unsigned int i = 0; i < ISSUES ; i++)
	{
		int topicRating = 0;

		for(unsigned int j = 0; j < RATINGS ; j++)
		{
			topicRating += responses[i][j]*(j+1);
		}

		if (highRating < topicRating)
		{
			highRating = topicRating;
			highTopic = i;// index of the highest rated topic
		}
	}

   printf("The highest rated topic was %s with a total rating of %d\n", 
   topics[highTopic], highRating);
} 

// get the lowest rated topic (lowest point total)
void lowestRatings(void) 
{
	int lowRating = 0; 
	int lowTopic = 0; 
   
	for (unsigned int i = 0; i < ISSUES ; i++)
	{
		int topicRating = 0;

		for(unsigned int j = 0; j < RATINGS ; j++)
		{
			topicRating += responses[i][j]*(j+1);
		}

		if (lowRating > topicRating)
		{
			lowRating = topicRating;
			lowTopic = i;// index of the lowest rated topic
		}
	}

   printf("The lowest rated topic was %s with a total rating of %d\n", 
   topics[lowTopic], lowRating);
   
   //printf("The lowest rated topic was %s with a total rating of %d\n", topics[lowTopic], lowRating);
}	

// Output in tabular form the number of ratings per issue
void displayResults()
{
	float current_avg;
	
	// Output table header
	printf("%20s", "Topic");
	for(unsigned int i = 1; i <= RATINGS; ++i)
	{
		printf("%4d", i);
	}
	printf("%20s", "Average Rating");
	
	//Print the issues followed by the numbers
	for(unsigned int i = 0; i<ISSUES; ++i)
	{
		current_avg = 0;
		printf("\n%20s", topics[i]);
		for(unsigned int j = 0; j<RATINGS; ++j)
		{
			printf("%4d", responses[i][j]);
			current_avg += responses[i][j]*(j+1);
		}
		current_avg /= user;
		printf("%20.2f", current_avg);
	}
	
	puts("");
	//Output the highest and lowest ratings for this poll
	highestRatings();
	lowestRatings();
} 