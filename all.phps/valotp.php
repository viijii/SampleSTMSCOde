<?php


     $corp_code 		= $_REQUEST['corp_code'];
	//$corp_code 		= 'vehicletesting';
	 
   $_SESSION["corp_code"] = $corp_code; 
     include "connect.php";
	  
	header('Access-Control-Allow-Origin: *');
	header('Access-Control-Allow-Methods: POST, GET, OPTIONS');
	
	/*original code starts*/
	if($_SERVER['REQUEST_METHOD']=="POST" || $_SERVER['REQUEST_METHOD'] == "GET" )
	{
		 
		 
		 $otp      =  $_REQUEST['otp'];
		 $ideaId   =  $_REQUEST['ideaId'];
		 $taskId   =  $_REQUEST['taskId'];
		 $action   =  $_REQUEST['action'];
		 
		 
		/* $otp      = '2365';
		 $taskId   ='7';*/
		 
		 
		 
		 //current time
		 date_default_timezone_set('Asia/Kolkata');
         $time=date("Y-m-d H:i:s",time());
		 
		 //Getting the otp from user while login
		 $sql = "select otp from otpTable where `taskId` = '$taskId' ";
		 	
         $result = mysqli_fetch_array(mysqli_query($con,$sql));
         $realotp = $result['otp'];
         
        
        
         
         //Validating the otp
		 if($otp == $realotp)
		 {
			//status of user is changed while validate otp
			 $sql = "UPDATE otpTable SET  verifiedStatus ='1' WHERE taskId ='$taskId'";
			 $res =  mysqli_query($con,$sql);
			if($res){
			 $sql2 = "UPDATE `taskDetails` SET serviceStatus ='completed' WHERE taskId ='$taskId'";
			 $res2 =  mysqli_query($con,$sql2) or die('error');
			
		
			  $result['status'] = 'true';
		
			  
			  }else{
				   $result['status'] = 'false';
				  }
	
		 }
		 else
		 {
			 //status of user is changed while validate otp
			$sql = "UPDATE otpTable SET  verifiedStatus ='0' WHERE taskId ='$taskId'";
			$res =  mysqli_query($con,$sql);
			
			 /*$sql2="UPDATE `userSubTasks` SET  serviceStatus ='$action' WHERE taskId ='$taskId'";
			 $res2 =  mysqli_query($con,$sql2);*/
			$result['status'] = 'false';
	
		
		 }
    } 
      else
    {
			$result['status'] = 'false';
            
    }
     header("Content-Type:application/json"); 
     echo json_encode($result);
	/*original code ends*/
?>
