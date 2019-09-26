<?php

session_start();

$corp_code 		= $_REQUEST['corp_code'];



$_SESSION["corp_code"] = $corp_code;
//$corp_code 		= 'sram';


	include "connect.php";	
	
	header('Access-Control-Allow-Origin: *');
	header('Access-Control-Allow-Methods: POST, GET, OPTIONS');
	
	
	if($_SERVER['REQUEST_METHOD']=="POST" || $_SERVER['REQUEST_METHOD'] == "GET")
		{
			date_default_timezone_set('Asia/Kolkata'); 
			$time= date("Y-m-d H:i:s");
			
			$username  			= $_REQUEST['username'];
			$password  			= $_REQUEST['password'];
			$utype  			= $_REQUEST['utype'];
		
		
		
		   /* $username  			= 'krishna';
			$password  			= 'krishna';
			$utype  			= 'customer';*/
		
		
	if($utype=='admin')
	{
			$sql = "select * from employeeTable where username = '$username' && password = '$password' && role = '$utype' && activeStatus='active'";
		
		
		$res_sel = mysqli_query($con,$sql);
			
			if(mysqli_num_rows($res_sel) > 0)
				{
					$row = mysqli_fetch_array($res_sel);
					
				
						
					

		    		$result['id']=$row["empId"];
		    		$result['username']=$row["username"];
		    		$result['fullname']=$row["name"];
		    		$result['mobileNumber']=$row["mobileNumber"];
		    		$result['role']=$row["role"];
		    		$result['activeStatus']=$row["activeStatus"];
		    		$result['email']=$row["email"];
		    		$result['joiningDate']=$row["joiningDate"];
		    		$result['designation']=$row["designation"];
		    		$result['aadhar']=$row["aadhar"];
		    		$result['password']=$row["password"];
		    		
		    		
				
							$result['status']='TRUE';
		    				$result['message']='Success';
						}
					
					else
						{
							$result['status']='False';
							$result['message']='Account Deactivated Contact to Admin!';
						}		
				

}
		
			else if($utype == 'emp'){
				
			$sql2 = "select * from employeeTable where username = '$username' && password = '$password' && role<>'admin' && activeStatus='active'";
			$res_sel2 = mysqli_query($con,$sql2);
			
			if(mysqli_num_rows($res_sel2) > 0)
				{
					$row = mysqli_fetch_array($res_sel2);
					
						
					
		    	
		    		$result['id']=$row["empId"];
		    		$result['username']=$row["username"];
		    		$result['fullname']=$row["name"];
		    		$result['mobileNumber']=$row["mobileNumber"];
		    		$result['role']=$row["role"];
		    		$result['activeStatus']=$row["activeStatus"];
		    		$result['email']=$row["email"];
		    		$result['joiningDate']=$row["joiningDate"];
		    		$result['designation']=$row["designation"];
		    		$result['aadhar']=$row["aadhar"];
		    		$result['password']=$row["password"];
				
							$result['status']='TRUE';
		    				$result['message']='Success';
						}
						else
		{
			$result['status']='FALSE';
			$result['message']='Account Deactivated Contact to Admin!';
		   }
	   }
						
						else if($utype == 'customer'){
				
			$sql3 = "select * from customerTable where username = '$username' && password = '$password' && activeStatus = 'active' && role = 'customer'";
			$res_sel3 = mysqli_query($con,$sql3) ;
			
			if(mysqli_num_rows($res_sel3) > 0)
				{
					$row = mysqli_fetch_array($res_sel3);
					
					
		    	
		    		$result['id']=$row["customerId"];
		    		$result['username']=$row["username"];
		    		$result['fullname']=$row["name"];
		    		$result['mobileNumber']=$row["mobileNumber"];
		    		$result['aadhar']=$row["aadhar"];
		    		$result['activeStatus']=$row["activeStatus"];
		    		$result['email']=$row["email"];
		    		$result['username']=$row["username"];
		    		$result['password']=$row["password"];
		    		$result['registeredDate']=$row["registeredDate"];
		    		$result['role']=$row["role"];
				
							$result['status']='TRUE';
		    				$result['message']='Success';
						}
			
			
			         		
	else
		{
			$result['status']='FALSE';
			$result['message']='Account Deactivated Contact to Admin!!';
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
