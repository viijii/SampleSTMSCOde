<?php

session_start();

      $corp_code 		= $_REQUEST['corp_code'];
      $corp_code        ='sram';
      $_SESSION["corp_code"] = $corp_code; 
         
     include "connect.php";

if($_SERVER['REQUEST_METHOD']=="POST" || $_SERVER['REQUEST_METHOD'] == "GET")
		{
			 $file_path = "uploads/";
    $file_path = $file_path . basename( $_FILES['uploaded_file']['name']);
    if(move_uploaded_file($_FILES['uploaded_file']['tmp_name'], $file_path) ){
        echo "success";
    } else{
        echo "fail";
    }
    }
    else{
 $result['status'] = 'false';
}

 header("Content-Type:application/json");
    echo json_encode($result);
    ?>
