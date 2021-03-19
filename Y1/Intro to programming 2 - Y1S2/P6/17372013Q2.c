#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define LIMIT 30

int main(void)
{
	FILE *fPtr;
	if((fPtr = fopen("students.txt", "r")) == NULL)
	{
		puts("File could not be opened");
	}
	else
	{
		char a[LIMIT], b[LIMIT], c[LIMIT], d[LIMIT], e[LIMIT], gpa[LIMIT], temp[LIMIT];
		double grade;
		//printf("%10s\n", "GPA");
		
		//printf("%s", gpa);
		
		do
		{
			strcpy(temp, a);
			fscanf(fPtr, "%s %s %s %s %s %s\n", a,b,c,d,e,gpa);
			grade = atof(gpa);
			if(grade>=3.2 && strcmp(temp, a)!=0)
			{
				printf("%s %s %s %s %s %.2lf\n",a,b,c,d,e,grade);
			}
			else if(grade<3.2)
			{
				printf("***********************\n");
			}
		}while(strcmp(temp, a)!=0);
		fclose(fPtr);
	}
}