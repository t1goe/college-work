//Written by Thomas Igoe	Student#: 17372013
//To compute the det of a 3x3 matrix

#include <stdio.h>

void detCalculator(int matrix[3][3], int order);

int main(void)
{
	int matrix1[3][3] = {{ 1,2,5 }, {0, 2,1 }, { 5, 6,8 }};
	detCalculator(matrix1,3);
	
	return 0;
}

void detCalculator(int matrix[3][3], int order)
{
	int ans, det1, det2, det3;
	/*
	For my own refrence
	
	a00	b01	c02
	d10 e11 f12
	g20 h21 i22
	
	det1 = (ei − fh)a
	det2 = (di − fg)b
	det3 = (dh − eg)c
	ans = det1 − det2 + det3
	*/
	
	//Actual calculations
	det1 = (matrix[1][1]*matrix[2][2] - matrix[1][2]*matrix[2][1]) * matrix[0][0];
	det2 = (matrix[1][0]*matrix[2][2] - matrix[1][2]*matrix[2][0]) * matrix[0][1];
	det3 = (matrix[1][0]*matrix[2][1] - matrix[1][1]*matrix[2][0]) * matrix[0][2];
	ans = det1 - det2 + det3;
	
	//Print answer
	printf("%d", ans);
}