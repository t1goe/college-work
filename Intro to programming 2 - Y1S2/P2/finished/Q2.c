//Author: Thomas Igoe	Student #: 17372013
//Inputs DOB and outputs day of the week you were born on

#include <stdio.h>
#include <string.h>	

int birthdayDay(int days, int month, int year);

int main(void)
{
	//dec vars
	int d, m, y;
	
	//take inputs
	printf("Input date of birth. \nDay: ");
	scanf("%d", &d);
	printf("\nMonth (in number format): ");
	scanf("%d", &m);
	printf("\nYear: ");
	scanf("%d", &y);
	
	//Calls function
	birthdayDay(d, m, y);
	
	return 0;
}

int birthdayDay(int days, int month, int year)
{
	int ans, centYear, cent;
	char day[] = "         ";
	
	//converts months (Jan/Feb) to format
	if(month == 1 || month == 2)
	{
		month += 12;
	}
	
	//Calculates the year in the century and the century 
	centYear = year % 100;
	cent = year / 100;
	
	//Calculates Zeller's Congruence 
	ans = month + 1;
	ans *= 13;
	ans /= 5;
	ans += days;
	ans += centYear;
	ans -= (2*cent);
	ans += (centYear/4);
	ans += (cent/4);
	ans--;
	ans = ans % 7;
	
	
	//converts number to day
	switch(ans)
	{
		case 0:
			strncpy(day, "Saturday", 8);
			break;
		case 1:
			strncpy(day, "Sunday", 6);
			break;
		case 2:
			strncpy(day, "Monday", 6);
			break;
		case 3:
			strncpy(day, "Tuesday", 7);
			break;
		case 4:
			strncpy(day, "Wednesday", 9);
			break;
		case 5:
			strncpy(day, "Thursday", 8);
			break;
		case 6:
			strncpy(day, "Friday", 6);
			break;
	}
	
	//print output
	printf("You were born on %s", day);
	
	return 0;
}