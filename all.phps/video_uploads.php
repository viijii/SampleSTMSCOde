<?php

	session_start();

		$corp_code 		= $_REQUEST['corp_code'];
	//	$corp_code        ='sram';
		$_SESSION["corp_code"] = $corp_code; 
         
		include "connect.php";

	 if($_SERVER['REQUEST_METHOD']=='POST'){
 $file_name = $_FILES['myFile']['name'];
 $file_size = $_FILES['myFile']['size'];
 $file_type = $_FILES['myFile']['type'];
 $temp_name = $_FILES['myFile']['tmp_name'];
 
 $title = $_POST['titleName'];
 $location = $_POST['locationName'];
 
 /*$title ='kkk';
 $location ='yyyy';*/

 $location1 =  "uploads/";
     			//$role=$_REQUEST['user_type'];

			
    
   
     $ServerURL = "http://115.98.3.215:90/sRamapptest/".$location1.$file_name;
     $InsertSQL = mysqli_query($con,"INSERT INTO `fieldActivity`(`title`,`location`,`path`) VALUES ('$title','$location','$ServerURL')");
 
 
       move_uploaded_file($temp_name,"uploads/".$file_name) or die("error") ;
     
   //   echo "http://toobworks.com/testcommuride/poolridepassengerprofiles/".$file_name;
     
      echo "http://115.98.3.215:90/sRamapptest/uploads/".$file_name;
 
 // echo"Your Video Has been Uploaded";
 
 }else{
  echo "failed to upload";
 
}

 header("Content-Type:application/json");
    echo json_encode($result);
?>
