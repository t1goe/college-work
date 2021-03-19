#include <stdio.h>

task main()
{
	while(SensorValue(sonarSensor) > 45)
	{
		motor[motorC]=100;
		motor[motorD]=100;
	}
	
}