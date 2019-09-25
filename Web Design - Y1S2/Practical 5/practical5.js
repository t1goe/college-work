function myAdd(tb1, tb2, tb3)
{
	var t1 = document.getElementById(tb1);
	var t2 = document.getElementById(tb2);
	
	//Checks if t1 or t2 is NaN and if so, then doesn't execute
	if(isNaN(t1.value)===false)
	{
		if(isNaN(t2.value)===false)
		{
			var t3 = parseInt(t1.value)+parseInt(t2.value);
			document.getElementById(tb3).value = t3;
		}
	}
}

function giveClass()
{
	document.getElementById("myid").classList.add("tomatoSoup");
}

//function 1 switches back to original when moused off of
function switch1(currentImage)
{
	currentImage.src="image1.gif";
}

//function 2 switches to new image
function switch2(currentImage)
{
	currentImage.src="image2.gif";
}