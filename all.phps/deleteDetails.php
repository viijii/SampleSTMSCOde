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
					$empId  	    	= $_REQUEST['empId'];
					$role  	    	    = $_REQUEST['role'];
			
		 if($action =='delete'){
				if($role=='customer'){	
					$sql_del ="DELETE FROM `customerTable` where customerId='$empId'";
				}else if($role=='vehicle'){
					$sql_del ="DELETE FROM `vehicleDetails` where vehicleId='$empId'";
					}	else{
						   $sql_del ="DELETE FROM `employeeTable` where empId='$empId'";
                           }
										
				$res_del = mysqli_query($con,$sql_del);
				if($res_del){
				
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
