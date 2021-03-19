//Author: Tom Igoe Student#: 17372013
//To interface with a car sales database
#include <stdio.h>

#define LIMIT 20

void addData(void);
void printData(void);
void printStats(void);

int main(void)
{
	printf("\n+++SALES RECORD EDITOR+++\n\n");
	int input = 1;
	while(1<=input && input<=3)
	{
		printf("\n\nEnter 1 to add more data to the list, 2 to print current data to the terminal, 3 to print current statistics or 4 to quit: ");
		scanf("%d", &input);
		
		if(input == 1)
		{
			printf("+Adding data+\n");
			addData();
		}
		else if(input == 2)
		{
			printf("+Printing Data+\n");
			printData();
		}
		else if(input == 3)
		{
			printf("+Printing statistics\n");
			//printStats();
		}
		else if(input == 4)
		{
			printf("+Quitting+");
		}
		else
		{
			printf("\nInvalid input. Please enter a valid input.\n");
		}
	}
	
	return 0;
}


void addData(void)
{
	FILE *fP;
	if((fP = fopen("MarchSales.txt", "a")) == NULL)
	{
		puts("File could not be opened");
	}
	else
	{
		printf("Enter, licence plate, make, model, shape, color, mileage, cost price, selling price, and sales person id");
		puts("Enter EOF to end input");
		printf(":: ");
		
		char licence[LIMIT], make[LIMIT], model[LIMIT], shape[LIMIT], color[LIMIT], milage[LIMIT];
		int cost, sold, saleid;
		int datid = 9;//Should read from previous line to find what the currernt database id is, but no time to do this
		
		scanf("%19s%19s%19s%19s%19s%19s%d%d%d", licence, make, model, shape, color, milage, &cost, &sold, &saleid);
		while(!feof(stdin))
		{
			fprintf(fP, "\n%d %s %s %s %s %s %s %d %d %d", datid, licence, make, model, shape, color, milage, cost, sold, saleid);
			printf(":: ");
			scanf("%19s%19s%19s%19s%19s%19s%d%d%d", licence, make, model, shape, color, milage, &cost, &sold, &saleid);
			datid++;
		}
		fclose(fP);
	}
}


void printData(void)
{
	FILE *fP;
	if((fP = fopen("MarchSales.txt", "r")) == NULL)
	{
		puts("File could not be opened");
	}
	else
	{
		int w, x, y, z, temp;
		char a[LIMIT], b[LIMIT], c[LIMIT], d[LIMIT], e[LIMIT], f[LIMIT];
		while(temp != w)
		{
			temp = w;
			fscanf(fP, "%d %s %s %s %s %s %s %d %d %d\n", &w, &a, &b, &c, &d, &e, &f, &x, &y, &z);
			printf("%d %s %s %s %s %s %s %d %d %d\n", w, a, b, c, d, e, f, x, y, z);
		}
		fclose(fP);
	}
}

/*
void printStats(void)
{
	
}
*/