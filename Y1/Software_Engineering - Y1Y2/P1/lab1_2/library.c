#include <stdio.h>
#include "library.h"

double avg(int numbers[], int size){
	int i =0;
	double result =0;
	 for(i=0; i< size; i++)
		 result += numbers[i];
	 return result/size;
}

void insertNumbers(int numbers[], int maxSize){
	int i =0;
	 for(i =0; i<maxSize;i++){
		printf("insert an integer number: ");
		scanf ("%d", &numbers[i]);    }
}
