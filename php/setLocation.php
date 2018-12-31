<?php
error_reporting(E_ALL);
ini_set('display_errors', '1');

include('dbConnect.php');

$username = $_POST['username'];
$longitude = $_POST['longitude'];
$latitude = $_POST['latitude'];
$time = $_POST["time"];

$sql="SELECT * FROM users WHERE username = '$username' AND userType = 'Patient'";
$query = $pdo->prepare($sql);
$query->execute();
$row = $query->fetch(PDO::FETCH_ASSOC);
$user_id = $row["user_id"];

$sql="INSERT INTO location VALUES ('$user_id', '$longitude', '$latitude', '$time')";
$query = $pdo->prepare($sql);
	if($query->execute())
	{
		$response["message"] =  "Success";
	}
	else
		$response["message"] =  "User already exists.";
		
	echo json_encode($response);


?>
