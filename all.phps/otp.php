<?php

	 $corp_code 		= $_REQUEST['corp_code'];
	//$corp_code 		= 'sram';
	 
   $_SESSION["corp_code"] = $corp_code; 
     include "connect.php";

       
	header('Access-Control-Allow-Origin: *');
	header('Access-Control-Allow-Methods: POST, GET, OPTIONS');
	/*original code starts*/
	if($_SERVER['REQUEST_METHOD']=="POST" || $_SERVER['REQUEST_METHOD'] == "GET")
	{
		
		$idea      = $_REQUEST['idea'];
		$task_id   = $_REQUEST['taskId'];
		$empId     = $_REQUEST['empId'];

		
		
      /* $idea= '149';
       $task_id='30';
       $empId     = '1234';*/
		
		
		$sql1="SELECT complaintId, cc.customerId, ct.mobileNumber FROM `customerComplaint` AS cc LEFT JOIN 
		customerTable as ct on ct.customerId= cc.customerId WHERE complaintId= '$idea'";
     $res=mysqli_query($con,$sql1);
     $row=mysqli_fetch_assoc($res);
     $mobile=$row['mobileNumber'];
     $idea_id=$row['complaintId'];
     $custmId=$row['customerId'];
     
   
		
		
		//Getting the mobile number from generateOTP api
		//$mobile= $_REQUEST['mobileNum'];
		
			//Checking the user already register or not
			$sql = "select count(*) as count from `otpTable` where mobileNumber='$mobile' and taskId='$task_id'";
		 
			$res = mysqli_query($con,$sql);
             
            $rowv=mysqli_fetch_assoc($res);
            $count= $rowv['count'];
     
        
           if($count > 0)
           {
					//Random pin for otp
					$pin = mt_rand(1000, 9999);
					
					//Inserting the otp for mobilenumber
				    $ins = mysqli_query($con,"UPDATE otpTable SET `otp` ='$pin' WHERE `mobileNumber` ='$mobile' and taskId='$task_id'");
					// Authorisation details.
					$username = "sms@novisync.com";
					$hash = "b84caa55f9b0516e4b07b1da86ad93192adc84a42db39590284fbac6daf95e25";

					// Config variables. Consult http://api.textlocal.in/docs for more info.
					$test = "0";

					// Data for text message. This is the text message data.
					$sender = "CADRAC"; // This is who the message appears to be from.
					$numbers = $mobile; // A single number or a comma-seperated list of numbers
					$message = 'Thank You for Choosing Our Service.%nYour Service OTP : '.$pin;

				
					// 612 chars or less
					// A single number or a comma-seperated list of numbers
					$message = urlencode($message);
					$data = "username=".$username."&hash=".$hash."&message=".$message."&sender=".$sender."&numbers=".$numbers."&test=".$test;
					$ch = curl_init('http://api.textlocal.in/send/?');
					curl_setopt($ch, CURLOPT_POST, true);
					curl_setopt($ch, CURLOPT_POSTFIELDS, $data);
					curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
					$result = curl_exec($ch); // This is the result from the API
					curl_close($ch);
					
					if($ins){

					$response['status'] = 'True';
			
					$response['response'] = 'sucess';
			        
					$result['data'] = $data;	
				 }
				   else{
					   $response['status'] = 'false';
			
					$response['response'] = $task_id;
					   }
			  }
			  else
			  {

 
				
					//Random pin for otp
					$pin = mt_rand(1000, 9999);
					
					//Inserting the otp for mobilenumber
					$ins = mysqli_query($con,"INSERT INTO `otpTable`(`otp`,`mobileNumber`,`ideaId`,`taskId`,`receiverId`,`userId`)VALUES ('$pin','$mobile','$idea_id','$task_id','$custmId','$empId')");
					// Authorisation details.
					$username = "sms@novisync.com";
					$hash = "b84caa55f9b0516e4b07b1da86ad93192adc84a42db39590284fbac6daf95e25";

					// Config variables. Consult http://api.textlocal.in/docs for more info.
					$test = "0";

					// Data for text message. This is the text message data.
					$sender = "CADRAC"; // This is who the message appears to be from.
					$numbers = $mobile; // A single number or a comma-seperated list of numbers
					$message = 'Thank You for Choosing Our Service.%nYour Service OTP : '.$pin;

				
					// 612 chars or less
					// A single number or a comma-seperated list of numbers
					$message = urlencode($message);
					$data = "username=".$username."&hash=".$hash."&message=".$message."&sender=".$sender."&numbers=".$numbers."&test=".$test;
					$ch = curl_init('http://api.textlocal.in/send/?');
					curl_setopt($ch, CURLOPT_POST, true);
					curl_setopt($ch, CURLOPT_POSTFIELDS, $data);
					curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
					$result = curl_exec($ch); // This is the result from the API
					curl_close($ch);
		if($ins){
					$response['status'] = 'True';
			
					$response['response'] = 'sucess';
			        
					$result['data'] = $mobile;
			}
				else{
					$response['status'] = 'failed to generate';
			
					$response['response'] = $idea;
					
					}	
			}
			
		}
		else{
			$response['status'] = 'false';
			
		}	  
		
	header("Content-Type:application/json");
	echo json_encode($response);
	/*original code ends*/
?>
