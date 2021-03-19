//Name: Thomas Igoe		Student No:17372013
//To demonstrate the memory applications of a union
#include <stdio.h>

//Union decleration
union test
{
	char a;
	short b;
	int c;
	long d;
};

int main(void)
{
	union test cas;
	
	//Takes input for all elements of the union
	printf("Input data\nchar A: ");
	scanf("%c", &cas.a);
	printf("\nshort B: ");
	scanf("%d", &cas.b);
	printf("\nint C: ");
	scanf("%d", &cas.c);
	printf("\nlong D: ");
	scanf("%li", &cas.d);
	
	//Prints all elements of the union, showing that only the most recent input is stored, and that the size is == to the largest element of the union
	printf("\n\n\nA: %c\nB: %d\nC: %d\nD: %li\nSize of union: %d", cas.a, cas.b, cas.c, cas.d, sizeof(cas));
	
	return 0;
}

//The final printf uses the last entered value for all values. Giving a null character for char.
//The size of the union was the largest in the union