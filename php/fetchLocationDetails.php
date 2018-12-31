<?php
error_reporting(E_ALL);
ini_set('display_errors', '1');

include('dbConnect.php');

$username = $_POST['username'];

$sql="SELECT * FROM users WHERE username = '$username' AND userType = 'Patient'";
$query = $pdo->prepare($sql);
$query->execute();
$row = $query->fetch(PDO::FETCH_ASSOC);
$user_id = $row["user_id"];

$sql="SELECT * FROM location WHERE user_id = '$user_id'";
$query = $pdo->prepare($sql);
$query->execute();
$row = $query->fetchAll(PDO::FETCH_ASSOC);


foreach($row as $result)
{
	$output[] = $result;

}

print(json_encode($output));
?>
