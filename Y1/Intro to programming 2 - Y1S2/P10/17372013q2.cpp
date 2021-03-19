#include <iostream>
using namespace std;

int subTwo(int x, int y)
{
	cout << "Integer: " << x << " - " << y << " = ";
	return x - y;
}

double subTwo(double x, double y)
{
	cout << "Double: " << x << " - " << y << " = ";
	return x - y;
}

int main(void)
{
	int a, b;
	cout << "Input 2 integers to subtract";
	cout << endl;
	cin >> a >> b;
	cout << subTwo(a, b);
	cout << endl;
	
	double c, d;
	cout << "Input 2 doubles to subtract";
	cout << endl;
	cin >> c >> d;
	cout << subTwo(c, d);
	cout << endl;

}