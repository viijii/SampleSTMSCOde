<?php

	 $corp_code 		= $_REQUEST['corp_code'];
	//$corp_code 		= 'vehicletesting';
	 
   $_SESSION["corp_code"] = $corp_code; 
     include "connect.php";

       
	header('Access-Control-Allow-Origin: *');
	header('Access-Control-Allow-Methods: POST, GET, OPTIONS');
	/*original code starts*/
	if($_SERVER['REQUEST_METHOD']=="POST" || $_SERVER['REQUEST_METHOD'] == "GET")
	{
		
		date_default_timezone_set('Asia/Kolkata'); 
			$time= date("Y-m-d H:i:s");
	
		$task_id   = $_REQUEST['taskId'];
		
		
		 $sql="update taskDetails set `taskStatus`='100',`status`='completed',`modifiedDate`='$time',
		 `completedTime`='$time' where `taskId`='$task_id'";
					 
					 $sql_res=mysqli_query($con,$sql) or die("errorupdate");
					 
					 if($sql_res){
						 
						 
						 $response['status'] = 'True';
						$response['response'] = 'Success';
						 
						 }else{
						$response['status'] = 'false';
						$response['response'] = 'not updated';
							 }
		
		
		
		}else{
			
			$response['status'] = 'false';
			}	  
		
	header("Content-Type:application/json");
	echo json_encode($response);
	/*original code ends*/
?>
