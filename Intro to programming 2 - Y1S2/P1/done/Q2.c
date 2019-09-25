//To calculate if a house is overcrowded
//Author: Thomas Igoe, Student #:17372013
#include <stdio.h>

int overCrowded (int rooms, int people);

int main(void)
{
	//Declare input varaible
	int room_no, person_no;
	
	//take input
	printf("Input number of rooms in the house\nNumber of rooms: ");
	scanf("%d", &room_no);
	printf("Input number of people in the house\nNumber of people: ");
	scanf("%d", &person_no);
	
	//Print output
	printf("%d", overCrowded(room_no, person_no));
	
	return 0;
}

//Funciton
int overCrowded (int rooms, int people)
{
	//declare answer variable and capacity of house
	int ans, cap;
	
	//calculate capacity
	cap = rooms*2;
	
	//compare capacity to number of occupants
	if(cap < people)
		ans = 1;
	else
		ans = 0;
	
	return ans;
}