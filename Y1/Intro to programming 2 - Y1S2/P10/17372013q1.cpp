#include <iostream>
using namespace std;

float addTwo(float x, float y)
{
	cout << "Float: " << x << " + " << y << " = ";
	return x + y;
}

int addTwo(int x, int y)
{
	cout << "Integer: " << x << " + " << y << " = ";
	return x + y;
}

double addTwo(double x, double y)
{
	cout << "Double: " << x << " + " << y << " = ";
	return x + y;
}

long addTwo(long x, long y)
{
	cout << "Longs: " << x << " + " << y << " = ";
	return x + y;
}

int main(void)
{
	cout << addTwo(1, 2);
	cout << endl;
	cout << addTwo(1.1, 2.2);
	cout << endl;
	cout << addTwo(1.5245, 3.2134);
	cout << endl;
	cout << addTwo(12463.2344, 1345.2345);
	cout << endl;
}