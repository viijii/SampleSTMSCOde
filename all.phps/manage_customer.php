<?php
session_start();

$corp_code 		= $_REQUEST['corp_code'];
//$corp_code 		= 'sram';

$_SESSION["corp_code"] = $corp_code;
	include "connect.php";	
	
	header('Access-Control-Allow-Origin: *');
	header('Access-Control-Allow-Methods: POST, GET, OPTIONS');
	
	
	if($_SERVER['REQUEST_METHOD']=="POST" || $_SERVER['REQUEST_METHOD'] == "GET")
		{
			date_default_timezone_set('Asia/Kolkata'); 
			$time= date("Y-m-d H:i:s");
			

			
			    	$action  	    	= $_REQUEST['action'];
					$fullName  	    	= $_REQUEST['fullName'];
					$email  	    	= $_REQUEST['email'];
					$mobileNumber    	= $_REQUEST['mobileNumber'];
					$city  		        = $_REQUEST['city'];
					$aadhar  	       	= $_REQUEST['aadhar'];
					$currentAddress  	= $_REQUEST['currentAddress'];
					$permanentAddress 	= $_REQUEST['permanentAddress'];
					$username  	     	= $_REQUEST['userName'];
					$password  	     	= $_REQUEST['password'];
					
					$workingStatus      = $_REQUEST['userStatus'];
					$modifiedBy  	    = $_REQUEST['createdBy'];
					$customerId  	    	= $_REQUEST['empId'];
					
					/*$action ='save';
					//$customerId  = '1';
					
					$fullName='sssss';
					$email = 'ssssssss';
					$mobileNumber = '3726873268';
					$aadhar = '38763268623';
                    $username  	     	='iosuaof';
					$password  	     	= 'oiaufos';
					$workingStatus='active';
					$city='aaaa';
					$currentAddress='aaaaaa';
					$permanentAddress='aaaaaa';		*/		
					
					
					
					
					if($action =='save'){
						
					
					$sql_ins ="INSERT INTO `customerTable`(`name`,`email`,`mobileNumber`,`aadhar`,`username`,`password`,`activeStatus`,`role`)
					VALUES ('$fullName','$email','$mobileNumber','$aadhar','$username','$password','$workingStatus','customer')";
										
				$res_ins = mysqli_query($con,$sql_ins) or die('error');
				
			
				
				if($res_ins){
					
					$res_insert=mysqli_query($con,"select * from `customerTable` where mobileNumber='$mobileNumber'");
					
					if($res_insert){
						
					$fet=mysqli_fetch_assoc($res_insert);
					
					$userId=$fet['customerId'];
					$userType=$fet['role'];
					
					
					
					$sql_ins1 ="INSERT INTO `addressTable`(`userId`,`role`,`city`,`currentAddress`,`permanentAddress`) 
					VALUES('$userId','$userType','$city','$currentAddress ','$permanentAddress')";
			    	$res_ins1 = mysqli_query($con,$sql_ins1);
			    	
			    	
			    		if($res_ins1){
				
			$result['status']='TRUE';
			$result['message']='Sucess updated';
		}
		else{
			
			$result['status']='False';
			$result['message']='fail';
			
			}
		}
		else{
			
			$result['status']='fail';
			$result['message']='address not inserted';
			
			}
		}
		
		else{
		  $result['status']='fail';
			$result['message']='failed';
			}
									
			}	
			
			
		else if($action =='update'){
					
					$sql_ins ="Update `customerTable` set `name`='$fullName',`email`='$email',`mobileNumber`='$mobileNumber',`aadhar`='$aadhar',
					`username`='$username',`password`='$password',
					`activeStatus`='$workingStatus' where `customerId`='$modifiedBy'";
					
										
				$res_ins = mysqli_query($con,$sql_ins);
				if($res_ins){
				
			$result['status']='TRUE';
			$result['message']='Sucess';
		}
		else{
		  $result['status']='fail';
			$result['message']='failed to update';
			}	
			
	
			
	}
}	
	else
		{
			$result['status']='FALSE';
			$result['message']='Request method wrong!';
		}
	header("Content-Type:application/json");
	echo json_encode($result);		
?>			
