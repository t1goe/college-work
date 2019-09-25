#include <iostream>
using namespace std;

float triangle(float b = 2.3, float h = 1.0)
{
	cout << "Area of a tringale with base of " << b << " and height of " << h << " is ";
	return 0.5*b*h;
}

float square(float s = 10)
{
	cout << "Area of a square with side " << s << " is ";
	return s*s;
}

float rectangle(float h, float l = 10)
{
	cout << "Area of rectangle with height " << h << " and length " << l << " is ";
	return h*l;
}

float rhombus(float p = 10.2, float q = 3.8)
{
	cout << "Area of rhombus with Diagonal 1 length " << p << " and diagonal 2 length " << q << " is ";
	return (p*q)/2;
}

float circle(float r = 3.6)
{
	cout << "Area of circle with radius " << r << " is ";
	return 3.14*r*r;
}

float trapezoid(float a, float h, float b = 8)
{
	cout << "Area of a trapezoid with top length " << a << " bottom length " << b << "and height " << h;
	return ((a+b)/2)*h;
}

int main(void)
{
	float a, b, c;
	cout << "Enter paramaters to calculate the area of the following shapes" << endl << endl;
	
	//Triangle
	cout << "Triangle" << endl << "Base: ";
	cin >> a;
	cout << "Height: ";
	cin >> b;
	cout << triangle(a, b) << "\n";
	cout << triangle();
	
	//Square
	cout << endl << "Square" << endl << "Side: ";
	cin >> a;
	cout << square(a) << "\n";
	cout << square();
	
	//Rectangle
	cout << endl << "Rectangle" << endl << "Height: ";
	cin >> a;
	cout << "Length: ";
	cin >> b;
	cout << rectangle(a, b) << "\n";
	cout << rectangle(a);
	
	//Rhombus
	cout << endl << "Rhombus" << endl << "Diagonal 1: ";
	cin >> a;
	cout << "Diagonal 2";
	cin >> b;
	cout << rhombus(a, b) << "\n";
	cout << rhombus();
	
	//Circle
	cout << endl << "Circle" << endl << "Radius: ";
	cin >> a;
	cout << circle(a) << "\n";
	cout << circle();
	
	//Trapezoid
	cout << endl << "Trapezoid" << endl << "Height: ";
	cin >> a;
	cout << "Top: ";
	cin >> b;
	cout << "Bottom: ";
	cin >> c;
	cout << trapezoid(b, a, c) << "\n";
	cout << trapezoid(b, a);
}