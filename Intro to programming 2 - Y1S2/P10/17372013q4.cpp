#include <iostream>
#include <string>
using namespace std;

class StudentData
{
public:
	//Input functions
	void snoi(void)
	{
		cout<<"\nInput student number";
		cin>>studentNo;
	}
	void namei(void)
	{
		cout<<"\nInput student's name";
		cin>>name;
	}
	void addressi(void)
	{
		cout<<"\nInput student's address";
		cin>>address;
	}
	void phonei(void)
	{
		cout<<"\nInput student's phone number";
		cin>>phone;
	}
	void fieldi(void)
	{
		cout<<"\nInput student's field of study";
		cin>>field;
	}
	void stagei(void)
	{
		cout<<"\nInput student's stage";
		cin>>stage;
	}
	void gpai(void)
	{
		cout<<"Input student's GPA";
		cin>>gpa;
	}
	
	void printall(void)
	{
		cout<<"\nStudent Number: "<<studentNo<<"\nName: "<<name<<"\nAddress: "<<address<<"\nPhone Number: "<<phone<<"\nField of Study: "<<"\nStage: "<<stage<<"\nGPA: "<< gpa;
	}
	
	//Altering functions
	float gpaa(float add)
	{
		gpa += add;
		return gpa;
	}
	int stagea(int add)
	{
		stage += add;
		return stage;
	}
	
private:
	string studentNo;
	string name;
	string address;
	string phone;
	string field;
	int stage;
	float gpa;
};

int main(void)
{
	StudentData student;
	student.snoi();
	student.namei();
	student.addressi();
	student.phonei();
	student.fieldi();
	student.gpai();
	
	student.printall();
	
	//Updating code
	int cont;
	cout<<"Enter 1 to update, anything else to continue";
	cin>>cont;
	while(cont == 1)
	{
		student.snoi();
		student.namei();
		student.addressi();
		student.phonei();
		student.fieldi();
		student.gpai();
		
		student.printall();
		
		cout<<"Enter 1 to continue updating, anything else to quit";
	}
	
	//Altering GPA and Stage
	int temp1;
	float temp2;
	cout<<"Enter 1 to alter GPA, anything else to continue";
	cin>>cont;
	while(cont == 1)
	{
		cout<<"Enter number to add to GPA";
		cin>>temp2;
		cout<<"\n\nNew GPA: "<<student.gpaa(temp2);
		
		cout<<"Enter 1 to continue updating, anything else to quit";		
	}
	
	cout<<"Enter 1 to alter stage, anything else to continue";
	cin>>cont;
	while(cont == 1)
	{
		cout<<"Enter how many stages to progress";
		cin>>temp1;
		cout<<"\n\nNew stage: "<<student.stagea(temp1);
		
		cout<<"Enter 1 to continue updating, anything else to quit";		
	}
	
	student.printall();
}