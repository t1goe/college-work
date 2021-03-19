#include <stdio.h>
#include <stdbool.h>

int main(void)
{
  double x1 = 0.3;
  double x2 = 0.1+0.1+0.1;
 
  {
    double c=1.0;
    int maxiters=10000000;
    double EPSILON = 0.0;


    printf("------------------------------------------------------------------\n");
    printf("Try the following square-root algorithm\n");
    printf("Enter a positive number and its square root is returned\n");
    printf("Enter a negative number to exit\n");
    printf("HOWEVER -- sometimes the program gets stuck in an infinite loop\n");
    printf("Try 20.0 for example\n");


    scanf("%lf", &c);
    while (c>0.0)
	{
		double t = c;
		int numiters=0;
		/* compute the square root of a number c*/

		while (numiters<maxiters && (t*t - c > EPSILON))
		{
	    t = (c/t + t) / 2.0;
	    numiters++;
		}
		printf("Loop terminated after %d iterations\n", numiters);
		printf("%.20f root=%.20f root*root=%.20f\n", c, t, t*t);
		scanf("%lf", &c);
      }

    printf("The lesson of this exercise is that \n");
    printf("(a) we cannot expect exact answers using floating point \n");
    printf("    but should seek an answer within some tolerance e.g. EPSILON=10e-15\n");
    printf("(b) be careful to choose the right guard in a loop using floating points\n");
    printf("    Do not use x==0.0\n");
    printf("    A better choice in this case is to test the relative absolute error\n");
    printf("    while ((fabs(t*t - c)>c*EPSILON))\n");

  }

  printf("------------------------------------------------\n");

  printf("Press a key to continue ...\n");
  getchar();
  {

    int sizeN[6]={100000,1000000,1000000, 100000000, 1000000000,2000000000};
    int i,j;
    printf("------------------------------------------------\n");
    printf("Harmonic Sum\n");
    printf("------------------------------------------------\n");

    printf("N\t\t\tSingle RtoL\tSingle LtoR\tDouble RtoL\tDouble LtoR\n");
    for (j=0;j<6;j++)
      {
	int N = sizeN[j];
	float sum1, sum2;
	double sum3, sum4;
	printf("%d\t\t",N);
	if (j<3)	printf("\t");
	sum1 = 0.0f;
	for (i = 1; i <= N; i++)
	  sum1 = sum1 + 1.0f / i;
	printf("%f\t", sum1);

	/* using single precision, right-to-left */
	sum2 = 0.0f;
	for (i = N; i >= 1; i--)
	  sum2 = sum2 + 1.0f / i;
  
	printf("%f\t", sum2);

      /* using double precision, left-to-right */
	sum3 = 0.0;
	for (i = 1; i <= N; i++)
	  sum3 = sum3 + 1.0 / i;

	printf("%f\t", sum3);
      
      /* using double precision, right-to-left */
	sum4 = 0.0;
	for (i = N; i >= 1; i--)
	  sum4 = sum4 + 1.0 / i;
  
	printf("%f\n", sum4);
      }
    printf("Using single precision, the harmonic sum converges to a finite value.\n");
    printf("However, it is known that the harmonic sum diverges to infinity.\n");    
  }

  return 0;
}

