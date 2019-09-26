<?php

	include 'connect.php';

       
	header('Access-Control-Allow-Origin: *');
	header('Access-Control-Allow-Methods: POST, GET, OPTIONS');

	if($_SERVER['REQUEST_METHOD']=="POST" || $_SERVER['REQUEST_METHOD'] == "GET")
	{
			$mobile= $_REQUEST['mobileNum'];
		
			//Checking the user already register or not
			$sql = "select * from `otp` where mobile='$mobile'";
		 
			$res = mysqli_query($con,$sql);
             
            $count=mysqli_num_rows($res);
            
        
           if($count>0)
           {
					//Random pin for otp
					$pin = mt_rand(1000, 9999);
					
					//Inserting the otp for mobilenumber
				    $ins = mysqli_query($con,"UPDATE otp SET `otpvalue` ='$pin' WHERE `mobile` ='$mobile'") or (logToFile($logfile,"Otp not Updated for ".$mobile));

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

					$response['status'] = 'True';
			
					$response['response'] = 'false';
			        
					$result['data'] = $data;	
				   
			  }
			  else
			  {
				   $result['status'] = 'false33';
				
					//Random pin for otp
					$pin = mt_rand(1000, 9999);
					
					//Inserting the otp for mobilenumber
					$ins = mysqli_query($con,"INSERT INTO otp (`otpvalue`,`mobile`) VALUES ('$pin', '$mobile')")  or (logToFile($logfile,"Otp not generated for ".$mobile));
					
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

			
					$response['status'] = 'True';
			
					$response['response'] = 'True';
			        
					$result['data'] = $data;	
			}
			
		}
		else{
			$response['status'] = 'false';
			logToFile($logfile,"Data is not getting from API otp.php");	
		}	  
		
	header("Content-Type:application/json");
	echo json_encode($response);
	/*original code ends*/
?>
