<?php
 
 // mobile photo
 include("connect.php");
 
 if($_SERVER['REQUEST_METHOD']=='POST')
 {
 
	$image = $_POST['image'];
	$name = $_POST['mobile'];
	$filename = $_POST['filename'];
	
	
	$path = "./uploads/$filename";
	
	file_put_contents($path,base64_decode($image));
 
	$eve2 = "Insert into `customerComplaints`(`title`,`imagePath`,complaintId)VALUES('title','$filename','$name')";
	
	$re = mysqli_query($con, $eve2);

 
 
	$result="1";
	$msg="Photo Updated";
		
	$posts[] = array('p1'=> $result,'p2'=> $msg);
	
	$response['posts'] = $posts;
	echo stripslashes(json_encode( array('item' => $posts)));

 
 }
 
 
 ?>
