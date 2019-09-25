<?php
$connection_var = new mysqli('localhost', 'student', 'comp20030',
'php2');
if (mysqli_connect_errno()) {
 printf("Connect failed: %s\n", mysqli_connect_error());
 exit();
}

$result = mysqli_query($connection_var,"SELECT * FROM coffees");


while($row = mysqli_fetch_array($result))
{
	echo "Coffee Name: ".$row[0]."<br>";
	echo "Price: ".$row[2]."<br>";
	echo "Total Sold: ".$row[3]."<br>";
	echo "Total Earnings: ".$row[4]."<br><br>";
}

mysqli_close($connection_var);
?>