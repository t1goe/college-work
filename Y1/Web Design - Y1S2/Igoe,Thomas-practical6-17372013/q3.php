<!DOCTYPE html>
<html lang="en"​>
	<head>
		<meta charset="utf-8"​>
		<title>COMP20030 HTML5 Template​</title>
	</head>
	<body>
	<?php
		$fname = "Tom";
		$sname = "Tgoe";
		$fullname = $fname." ".$sname;
		echo $fullname;
		if($fname[0] === $sname[0])
		{
			echo "<br>Alleritave name";
		}
		
		if(strlen($fname)<7)
		{
			echo "<br>Short name";
		}
		
		if(strlen($fullname)-1===8)
		{
			echo "<br>That is a moderately long name";
		}
		else
		{
			echo "<br>"."<b>That's a long name</b>";
		}
	?>
	</body>
</html>