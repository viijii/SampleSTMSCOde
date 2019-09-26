<?php
     $corp_code 		= $_REQUEST['corp_code'];
        // $corp_code 		= 'sram';
     $_SESSION["corp_code"] = $corp_code ;

	include "connect.php";
     
    header('Access-Control-Allow-Origin: *');
	header('Access-Control-Allow-Methods: POST, GET, OPTIONS');
	
	 if($_SERVER['REQUEST_METHOD']=="POST" || $_SERVER['REQUEST_METHOD'] == "GET")
        {
			date_default_timezone_set('Asia/Kolkata'); 
			$complaintDate= date("Y-m-d H:i:s");
			
			
			$action = $_REQUEST['action'];
			$user_type = $_REQUEST['user_type'];
			
			
			//$action='requested';
			
			 if($action == 'requested'){
				
				$sql="SELECT quoteId, saleType, ct.name AS customerName, ct.email, ct.mobileNumber, vm.vehicleType,status,
				 vm.vehicleModel FROM `quotation` AS qt INNER JOIN customerTable AS ct ON ct.customerId= qt.customerId INNER JOIN 
				 vehicleModels AS vm ON qt.vehicleTypeId= vm.vehicleTypeId WHERE status='pending'";
				$result_sql = mysqli_query($con, $sql) or die('error');
				if($result_sql){
					
					$r=0;
		
			while($fet=mysqli_fetch_array($result_sql))
			{
			$temp['quotationId']=$fet['quoteId'];
			$temp['customerName']=$fet['customerName'];
			$temp['vehType']=$fet['vehicleType'];
			$temp['vehModel']=$fet['vehicleModel'];
			$temp['quotationStatus']=$fet['status'];
			$temp['mobileNumber']=$fet['mobileNumber'];
			
			$temp['email']=$fet['email'];
			
           
			
			
				$data[$r]=$temp;
				$r++;
				
					
			}
			$result['status'] = 'TRUE';
			$result['message']='Sucess';
			$result['data'] = $data;
		}
			else{
				
				$result['status'] = 'False';
					$result['message']='failure';
				}
				 
			 
		 }
		 else if($action == 'responded'){
				
				$sql="SELECT quoteId, saleType, ct.name AS customerName, ct.email, ct.mobileNumber, vm.vehicleType,status,
				 vm.vehicleModel FROM `quotation` AS qt INNER JOIN customerTable AS ct ON ct.customerId= qt.customerId INNER JOIN 
				 vehicleModels AS vm ON qt.vehicleTypeId= vm.vehicleTypeId WHERE status='responded'";
				$result_sql = mysqli_query($con, $sql) or die('error');
				if($result_sql){
					
					$r=0;
		
			while($fet=mysqli_fetch_array($result_sql))
			{
			$temp['quotationId']=$fet['quoteId'];
			$temp['customerName']=$fet['customerName'];
			$temp['vehType']=$fet['vehicleType'];
			$temp['vehModel']=$fet['vehicleModel'];
			$temp['quotationStatus']=$fet['status'];
			$temp['email']=$fet['email'];
			$temp['mobileNumber']=$fet['mobileNumber'];
			
           
			
			
				$data[$r]=$temp;
				$r++;
			
				
			}
			$result['status'] = 'TRUE';
			$result['message']='Sucess';
			$result['data'] = $data;
			
			
		}
			else{
				
				$result['status'] = 'False';
				$result['message']='failure';
					
				}
				 
			 
		 }
		 
		 
	 }
		 else{
	
				$result['status'] = 'false';
				$result['message']='request method wrong';
		
		}
		
    header("Content-Type:application/json");
    echo json_encode($result);
   ?>
