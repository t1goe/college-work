//Written by Thomas Igoe Student #:17372013

#include <stdio.h>

struct zip_custs
{
	int zip_code;
	int customer_count;
};
 
int main(void)
{
	int myCustomers [5][2] = {{86956, 1}, {36568, 3}, {6565, 0}, {999555, 22}, {85446, 88}};
	struct zip_custs cust[5];
	
	FILE *fP;
	
	if((fP = fopen("customers.dat", "w")) == NULL)
	{
		puts("File couldn't be opened");
	}
	else
	{
		for(int i=0; i<5; i++)
		{
			//Fills struct array
			cust[i].zip_code = myCustomers[i][0];
			cust[i].customer_count = myCustomers[i][1];
		
			//Scans to file
			fwrite(&cust[i], sizeof(struct zip_custs), 	1, fP);
		}
	}
	
	//Closes files
	fclose(fP);
	
	return 0;
}
