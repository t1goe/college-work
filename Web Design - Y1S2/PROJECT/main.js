function openBar() {
	document.getElementById("mySidebar").style.width = "250px";
}
			
function closeBar() {
	document.getElementById("mySidebar").style.width = "0";
}

//BMI calculator
function bmicalc(height, weight, bmi)
{
	var h = document.getElementById(height);
	var w = document.getElementById(weight);
	
	//Checks if inputs are NaN and if so, then doesn't execute
	if(isNaN(h.value)===false)
	{
		if(isNaN(w.value)===false)
		{
			var b = (w.value/h.value)/h.value;
			document.getElementById(bmi).value = b;
			
			if(b<=18.5)
			{
				var ans = "BMI Classification: Underweight";
			}
			else if(b>18.5 && b<=24.9)
			{
				var ans = "BMI Classification: Normal Weight";
			}
			else if(b>24.9 && b<=29.9)
			{
				var ans = "BMI Classification: Overweight";				
			}
			else
			{
				var ans = "BMI Classification: Obese";
			}
			
			var ans
			document.getElementById("bmiclass").innerHTML = ans;
		}
	}
}