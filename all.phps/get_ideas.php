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
			
			//echo '1';
			
			date_default_timezone_set('Asia/Kolkata'); 
			$time= date("Y-m-d H:i:s");
			
					$action  	    	= $_REQUEST['action'];
					$cId  	    	= $_REQUEST['customerId'];
					$role  	    	    = $_REQUEST['role'];
					
					
					/*$action  	    	= 'completed';
					$cId  	    	= '121';*/
					
			
		 if($action =='completed'){
			 
			 //echo '2';
			 
			$sql="SELECT t2.*, IFNULL(vm.vehicleType, 'NA') AS vehicleType, IFNULL(vm.vehicleModel, 'NA') AS vehicleModel FROM 
			(SELECT t1.*, IFNULL(vd.vehicleTypeId, 'NA') AS vehicleTypeId  FROM (SELECT IF((COUNT(*) /SUM(status='completed'))<=1, 1, 0) 
             as completeStatus, cc.complaintId, cc.title, cc.vehicleId, td.taskId FROM taskDetails AS td INNER JOIN 
             customerComplaint AS cc ON cc.complaintId = td.complaintId WHERE cc.customerId='$cId' GROUP BY cc.complaintId) AS t1 LEFT JOIN 
              vehicleDetails AS vd ON t1.vehicleId = vd.vehicleId WHERE t1.completeStatus=1) AS t2 LEFT JOIN 
              vehicleModels AS vm ON vm.vehicleTypeId=t2.vehicleTypeId";
			
			$sql_res=mysqli_query($con,$sql) or die('error');
			
			
			
			if($sql_res){
			$r=0;
			
			//echo '3';
			while($fet=mysqli_fetch_array($sql_res))
			{
			$temp['complaintId']=$fet['complaintId'];
			$id=$fet['complaintId'];
			
			$sql1="Update `customerComplaint`  set approveStatus='completed' where complaintId='$id'";
			
		$sql_res1=mysqli_query($con,$sql1) or die('error');	
			
			$temp['title']=$fet['title'];	
			$temp['vehicleId']=$fet['vehicleId'];
			$temp['vehicleType']=$fet['vehicleType'];
			$temp['completeStatus']=$fet['completeStatus'];
			$temp['taskId']=$fet['taskId'];
			
			
           
			
			
				$data[$r]=$temp;
				$r++;
				
				
				
				
				}
			
						
						$response['status'] = 'True';
						$response['message'] = 'Success';	
						$response['data'] = $data; 
			 }
			 else{
				 
				 
				 $response['status'] = 'Fail';
				$response['message'] = 'failure';	
			    $response['data'] = 'No data'; 
				 
				 }
			 
			 
		 }
		 }	
	else
		{
			$response['status']='FALSE';
			$response['message']='Request method wrong!';
		}
	header("Content-Type:application/json");
	echo json_encode($response);		
?>			
