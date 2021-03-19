#include <stdio.h>

#define PRONT(x)                \
  for (int i = 0; i < 5; i++) { \
    printf("%d ", x[i]);       \
}

#define PRENT(x)			\
	for(int i=4; i>=0; i--){	\
	printf("%d ",x[i]);	 \
}
int main(void)
{
	int myArray[5] = {1,4,8,16,32};
	
	PRONT(myArray);
	puts("");
	PRENT(myArray);
	
	return 0;
}