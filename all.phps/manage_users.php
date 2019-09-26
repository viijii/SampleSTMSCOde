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
					$empId  	    	= $_REQUEST['empId'];
					$city  		        = $_REQUEST['city'];
					$aadhar  	       	= $_REQUEST['aadhar'];
					$currentAddress  	= $_REQUEST['currentAddress'];
					$permanentAddress 	= $_REQUEST['permanentAddress'];
					$joiningDate  		= $_REQUEST['joiningDate'];
					$username  	     	= $_REQUEST['userName'];
					$password  	     	= $_REQUEST['password'];
					$userType  	    	= $_REQUEST['userType'];
					$workingStatus      = $_REQUEST['userStatus'];
					$designation  	    = $_REQUEST['designation'];
					$modifiedBy  	    = $_REQUEST['createdBy'];
					
					/*$action  ='get';
					$empId   = '2';	*/
				
					/*$fullName='lkjslfkajs';
					$email = 'saakdjslkfs';
					$mobileNumber = '8374983279';
					$aadhar = '82642164982147';
					$username='lkajsl';
					$password = 'lajsdl';
					$joiningDate ='2019-08-30';
					$designation  	    = 'android';
					$empId  	    	= '6286492';
					$userType='EMP';
					$workingStatus='active';*/					
					
					
					
					
					if($action =='save'){
						
					
					$sql_ins ="INSERT INTO employeeTable(`empId`,`name`,`email`,`mobileNumber`,`aadhar`,`username`,`password`,`joiningDate`,`designation`,`role`,`activeStatus`)
					VALUES ('$empId','$fullName','$email','$mobileNumber','$aadhar','$username','$password','$joiningDate','$designation','$userType','$workingStatus')";
										
				$res_ins = mysqli_query($con,$sql_ins);
			
				
				if($res_ins){
					
					$sql_ins1 ="INSERT INTO addressTable(`userId`,`role`,`city`,`currentAddress`,`permanentAddress`) 
					VALUES('$empId','$userType','$city','$currentAddress ','$permanentAddress')";
			    	$res_ins1 = mysqli_query($con,$sql_ins1);
					if($res_ins1){
				
			$result['status']='TRUE';
			$result['message']='Sucess';
		}
		else{
			
			$result['status']='False';
			$result['message']='fail';
			
			}
		}
		
		else{
		  $result['status']='fail';
			$result['message']='failed';
			}
									
			}	
			else if($action =='update'){
					
					$sql_ins ="Update employeeTable set `name`='$fullName',`email`='$email',`mobileNumber`='$mobileNumber',`aadhar`='$aadhar',
					`username`='$username',`password`='$password',`joiningDate`='$joiningDate',`designation`='$designation',`role`='$userType',
					`activeStatus`='$workingStatus' where `empId`='$empId' ";
					
										
				$res_ins = mysqli_query($con,$sql_ins);
				if($res_ins){
				
			$result['status']='TRUE';
			$result['message']='Sucess';
		}
		
		else{
		  $result['status']='fail';
			$result['message']='failed';
			}
									
			}	
			
			else if($action =='get'){
				
				
					
					$sql ="SELECT `empId`, `name`, `email`, `mobileNumber`, `aadhar`, `username`, `password`, `joiningDate`, `designation`, et.`role`, `activeStatus`,  
					IFNULL(at.permanentAddress, 'NA') AS permanentAddress, IFNULL(at.currentAddress, 'NA') AS currentAddress,
					 IFNULL(at.city, 'NA') AS city FROM `employeeTable` AS et LEFT JOIN addressTable AS at ON et.empId= at.userId
					  where et.empId='$empId'";
										
				$res = mysqli_query($con,$sql) or die("error");
				
				//echo mysqli_num_rows($res);
				if(mysqli_num_rows($res)){
					
					$r=0;
					
					while($fet=mysqli_fetch_assoc($res)){
						
						
						$temp['fullName']= $fet['name'];
						$temp['empId']= $fet['empId'];
						$temp['mobileNumber']= $fet['mobileNumber'];
						$temp['aadhar']= $fet['aadhar'];
						$temp['email']= $fet['email'];
						$temp['currentAddress']= $fet['currentAddress'];
						$temp['joiningDate']= $fet['joiningDate'];
						$temp['permanentAddress']= $fet['permanentAddress'];
						$temp['designation']= $fet['designation'];
						$temp['userName']= $fet['username'];
						$temp['password']= $fet['password'];
						$temp['city']= $fet['city'];
						$temp['userType']= $fet['role'];
						$temp['userStatus']= $fet['activeStatus'];
						
						
						$data[$r]=$temp;
						$r++;
					}
				
			$result['status']='TRUE';
			$result['message']='Success';
			$result['data']=$data;
		
			
			
		}
		
		else{
			//echo $empId;
		  $result['status']='fail';
			$result['message']='failed to get details';
			}
									
			}
			
			
			else if($action =='getCustomer'){
				
				
					
					$sql ="SELECT `customerId`, `name`, `email`, `mobileNumber`, `aadhar`, `username`, `password`, et.`role`, `activeStatus`, at.permanentAddress,
					 at.currentAddress,at.city FROM `customerTable` AS et INNER JOIN addressTable AS at ON et.customerId= at.userId where et.customerId='$empId'";
										
				$res = mysqli_query($con,$sql) or die("error");
				if(mysqli_num_rows($res)>0){
					
					$r=0;
					
					while($fet=mysqli_fetch_assoc($res)){
						
						
						$temp['fullName']= $fet['name'];
						$temp['empId']= $fet['empId'];
						$temp['mobileNumber']= $fet['mobileNumber'];
						$temp['aadhar']= $fet['aadhar'];
						$temp['email']= $fet['email'];
						$temp['currentAddress']= $fet['currentAddress'];
						
						$temp['permanentAddress']= $fet['permanentAddress'];
						$temp['userName']= $fet['username'];
						$temp['password']= $fet['password'];
						$temp['city']= $fet['city'];
						$temp['userType']= $fet['role'];
						$temp['userStatus']= $fet['activeStatus'];
						
						
						$data[$r]=$temp;
						$r++;
					}
				
			$result['status']='TRUE';
			$result['message']='Success';
			$result['data']=$data;
		
			
			
		}
		
		else{
		  $result['status']='fail';
			$result['message']='failed';
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
