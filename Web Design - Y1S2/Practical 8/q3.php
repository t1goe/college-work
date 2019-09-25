<?php
$connection_var = new mysqli('localhost', 'student', 'comp20030',
'php2');
if (mysqli_connect_errno()) {
 printf("Connect failed: %s\n", mysqli_connect_error());
 exit();
}

$result1 = mysqli_query($connection_var,"SELECT * FROM coffees");
$result2 = mysqli_query($connection_var,"SELECT * FROM suppliers");

echo "<table><tr><th>COF_NAME</th><th>SUPPLIER NAME</th><th>TOTAL</th></tr>";
while($row1 = mysqli_fetch_array($result1))
{
	echo "<tr>";
	echo "<td>".$row1[0]."</td>";
	
	echo "<td>";
	while($row2 = mysqli_fetch_array($result2))
	{
		if($row1[1] === $row2[0])
		{
			echo $row2[1];
		}
	}
	echo "</td>";
	
	//$row2 = 0;
	//reset($row2);????????????????????
	//mysqli_data_seek($row2, 0);
	
	echo "<td>".$row1[4]."</td>";
	echo "</tr>";
}

echo "</table>";

mysqli_close($connection_var);
?>