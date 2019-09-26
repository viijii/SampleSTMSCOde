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
			
		 	$action  		= $_REQUEST['action'];
		 	$empId  		= $_REQUEST['empId'];
		 	$role  		    = $_REQUEST['user_type'];
		 	$mobileNumber  = $_REQUEST['number'];
					
                        /*$action  		= 'get';
                        $empId  		= '1';
                        $role           = 'customer';*/
                        
		 		 	
		 	if($action == 'get')
				{
					
					if($role=='customer'){
						//$sql_add="SELECT * FROM `customerTable` where customerId='$empId'";
						$sql_add="SELECT e.*,IFNULL(a.city, 'NA') AS city,IFNULL(a.currentAddress, 'NA') AS currentAddress,
						 IFNULL(a.permanentAddress, 'NA') AS permanentAddress FROM `customerTable`e left outer JOIN addressTable
						  a on e.customerId=a.userId where e.customerId='$empId' and e.role='$role'";
			    	$sql_add_res=mysqli_query($con,$sql_add);
			    	
                                
			    	if(mysqli_num_rows($sql_add_res) > 0)
				    {                       
					$r = 0;
					while($fet = mysqli_fetch_assoc($sql_add_res))
					    {
						
					$temp['empid']           =  $fet['customerId'];	
					$temp['fullname']            =  $fet['name'];
					$temp['mobile']    =  $fet['mobileNumber'];
					$temp['email']           =  $fet['email'];
					$temp['username']        =  $fet['username'];
					$temp['role']            =  $fet['role'];
					$temp['city']     =  $fet['city'];
					$temp['currentAddress']     =  $fet['currentAddress'];
					
					
			
					$data[$r]= $temp;
							
							$r++;
							
						}
						$result['status'] = 'True';
						$result['message'] = 'Success';	
						$result['data'] = $data;
						
					
					}else{
						
				        $result['status'] = 'False';
						$result['message'] = 'No Data Available';	
					
					 }
						
						
						}
						else{
			    	//$sql_add="SELECT * FROM `employeeTable` where empId='$empId'";
			    	$sql_add="SELECT e.*,IFNULL(a.city, 'NA') AS city,IFNULL(a.currentAddress, 'NA') AS currentAddress,
			    	IFNULL(a.permanentAddress, 'NA') AS permanentAddress FROM `employeeTable`e left outer JOIN 
			    	addressTable a on e.empId=a.userId where e.empId='$empId' and e.role='$role'";
			    	
			    	$sql_add_res=mysqli_query($con,$sql_add);
			    	
                                
			    	if(mysqli_num_rows($sql_add_res) > 0)
				    {                       
					$r = 0;
					while($fet = mysqli_fetch_assoc($sql_add_res))
					    {
						
					$temp['empid']                   =  $fet['empId'];	
					$temp['fullname']                =  $fet['name'];
					$temp['mobile']                  =  $fet['mobileNumber'];
					$temp['email']                   =  $fet['email'];
					$temp['username']                =  $fet['username'];
					$temp['role']            =  $fet['role'];
					$temp['designation']     =  $fet['designation'];
					$temp['city']     =  $fet['city'];
					$temp['currentAddress']     =  $fet['currentAddress'];
					
					
			
					$data[$r]= $temp;
							
							$r++;
							
						}
						$result['status'] = 'True';
						$result['message'] = 'Success';	
						$result['data'] = $data;
						
					
					}else{
						
				        $result['status'] = 'False';
						$result['message'] = 'No Data Available';	
					
					 }
				 }
					
					
				}if($action == 'update')
				{
					if($role='customer'){
						
						$sql_up="update `customerTable` set mobileNumber='$mobileNumber' where customerId='$empId' ";
						
						}
						else{
					
					$sql_up="update `employeeTable` set mobileNumber='$mobileNumber' where empId='$empId'";
				}
					$sql_up_res=mysqli_query($con,$sql_up);
					
						$result['status'] = 'True';
						$result['message'] = 'Success';	
						
					
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
