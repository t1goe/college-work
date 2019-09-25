/*
Written by Thomas Igoe
*/

#include <stdio.h>

void Go(int steps);

int main(void)
{
		int sx, sy, dx, dy;
		int diff_x, diff_y;
		
		/*input */
		sx = 3;
		sy = 5;
		dx = 7;
		dy = 2;
		
		diff_x = dx - sx;
		diff_y = dy - sy;
		
	if (diff_x>0 && diff_y > 0)
		{
			printf("Right Right ");
			Go(diff_y);
			printf("Left ");
			Go(diff_x);
		}
		
	if (diff_x>0 && diff_y < 0)
		{
			Go(-diff_y);
			printf("Roight ");
			Go(diff_x);
		}
		
	if (diff_x<0 && diff_y > 0)
		{
			printf("Left Left ");
			Go(diff_y);
			printf("Right ");
			Go(-diff_x);
		}
		
	if (diff_x<0 && diff_y < 0)
		{
			Go(-diff_y);
			printf("Left ");
			Go(-diff_x);
		}
		
	printf("\n");
	return 0;
}
void Go(int steps)
{
	int i;
	for (i=0;i<steps;i++)
	{
		printf("Walk ");
	}
}