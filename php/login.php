<?php

include('dbConnect.php');
$username = $_POST['username'];
$password = $_POST['password'];
$userType = $_POST['userType'];

$sql = "SELECT * FROM users WHERE username = '$username' AND password = '$password' AND userType = '$userType'";
$query = $pdo->prepare($sql);
$query->execute();
$row = $query->fetch(PDO::FETCH_ASSOC);
//	echo "reached here";
if($row == null){
	$response["message"] =  "Invalid Login Details";
}

else{
	$response["message"] =  "Success";
}
echo json_encode($response);
?>

