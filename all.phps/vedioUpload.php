<?php

session_start();

      $corp_code 		= $_REQUEST['corp_code'];
      $corp_code        ='sram';
      $_SESSION["corp_code"] = $corp_code; 
         
     include "connect.php";

if($_SERVER['REQUEST_METHOD']=="POST" || $_SERVER['REQUEST_METHOD'] == "GET")
		{
 $file_name = $_FILES['myFile']['name'];
 $file_size = $_FILES['myFile']['size'];
 $file_type = $_FILES['myFile']['type'];
 $temp_name = $_FILES['myFile']['tmp_name'];
 
 $title=$_SERVER['titleName'];
 $loca=$_POST['locationName'];
  
  
     $location =  "videoUpload/";
     $ServerURL = "http://115.98.3.215:90/sRamapptest/".$location.$file_name;
     $InsertSQL = mysqli_query($con,"INSERT INTO `fieldActivity`(`title`,`location`,`path`)VALUES('$title','$loca','$ServerURL')");
  
  
       move_uploaded_file($temp_name,$location.$file_name) or die("error");
      
   //   echo "http://toobworks.com/testcommuride/poolridepassengerprofiles/".$file_name;
     
  echo   "http://115.98.3.215:90/sRamapptest/".$location.$file_name;
 
 echo"Your Video Has been Uploaded";
  
 }else{
 $result['status'] = 'false';
}

 header("Content-Type:application/json");
    echo json_encode($result);
?>
