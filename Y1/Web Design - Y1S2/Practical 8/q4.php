<html>
<body>
<p>

<?php
$connection_var = new mysqli('localhost', 'student', 'comp20030',
'php2');

if (mysqli_connect_errno())
{
	printf("Connect failed: %s\n", mysqli_connect_error());
	exit();
}

$result = mysqli_query($connection_var,"SELECT * FROM coffees");

while($row = mysqli_fetch_array($result))
{
	if($row[1] === "101")
	{
		$price = $row[2];
	}
}

echo "Total cost is ".$price*$_POST["order"]." euro";

mysqli_close($connection_var);
?>

<p>
</body>
</html>