#include <stdio.h>

#define LIMIT 30

int main(void)
{
	FILE *fPtr;
	if((fPtr = fopen("students.txt", "w")) == NULL)
	{
		puts("File could not be opened");
	}
	else
	{
		puts("Enter the student's first name, surname, student #, phone #, field of study and GPA");
		puts("Enter EOF to end input");
		printf("%s", "? ");
		
		char fname[LIMIT], sname[LIMIT], study[LIMIT], studentno[LIMIT], phoneno[LIMIT];
		double gpa;
		
		scanf("%29s%29s%29s%29s%29s%lf", fname, sname, studentno, phoneno, study, &gpa);
		
		while(!feof(stdin))
		{
			fprintf(fPtr, "%s %s %s %s %s %.2lf\n", fname, sname, studentno, phoneno, study, gpa);
			printf("%s", "? ");
			scanf("%29s%29s%29s%29s%29s%lf", fname, sname, studentno, phoneno, study, &gpa);
		}
		fclose(fPtr);
	}
}