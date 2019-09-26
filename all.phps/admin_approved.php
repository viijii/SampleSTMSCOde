<?php
      $corp_code 		= $_REQUEST['corp_code'];
      //$corp_code        ='sram';
      $_SESSION["corp_code"] = $corp_code; 
         
     include "connect.php";

if($_SERVER['REQUEST_METHOD']=="POST" || $_SERVER['REQUEST_METHOD'] == "GET")
		{
			$action=$_REQUEST['action'];
			$cId=$_REQUEST['empId'];
			$role=$_REQUEST['user_type'];
			
			//$action='requested';
			//$cId='1';
			//$role='admin';
			
			if($action=='requested'){
				
				if($role=='admin'){
			
			
			   $sql="SELECT t1.*, IFNULL(vm.vehicleType, 'NA') AS vehicleType, IFNULL(vm.vehicleModel, 'NA') AS model 
			   FROM (SELECT cc.complaintId, title, description, imagePath, IFNULL(ct.name, 'NA') AS name, 
			   IFNULL(ct.mobileNumber, 'NA') AS mobileNumber, cc.vehicleId, IFNULL(vd.vehicleTypeId, 'NA') AS a, COUNT(td.taskId) AS noTasks
			   FROM `customerComplaint` AS cc LEFT JOIN customerTable AS ct ON cc.customerId= ct.customerId LEFT JOIN vehicleDetails 
			   AS vd ON cc.vehicleId=vd.vehicleId LEFT JOIN taskDetails AS td ON td.complaintId= cc.complaintId WHERE approveStatus='approved' GROUP BY cc.complaintId) AS t1 LEFT JOIN vehicleModels AS 
			   vm ON t1.a= vm.vehicleTypeId";
			  }
			  else{
			$sql="SELECT t1.*, IFNULL(vm.vehicleType, 'NA') AS vehicleType, IFNULL(vm.vehicleModel, 'NA') AS model 
			   FROM (SELECT complaintId, title, description, imagePath, IFNULL(ct.name, 'NA') AS name, 
			   IFNULL(ct.mobileNumber, 'NA') AS mobileNumber, cc.vehicleId, IFNULL(vd.vehicleTypeId, 'NA') AS a 
			   FROM `customerComplaint` AS cc LEFT JOIN customerTable AS ct ON cc.customerId= ct.customerId LEFT JOIN vehicleDetails 
			   AS vd ON cc.vehicleId=vd.vehicleId WHERE approveStatus='approved' AND cc.customerId='$cId') AS t1
			    LEFT JOIN vehicleModels AS 
			   vm ON t1.a= vm.vehicleTypeId";
		 }
			$sql_res=mysqli_query($con,$sql);
			
			if(mysqli_num_rows($sql_res)>0){
			
			$r=0;
			
			while($fet=mysqli_fetch_array($sql_res))
			{
			$temp['complaintId']=$fet['complaintId'];
			$temp['title']=$fet['title'];	
			$temp['description']=$fet['description'];
			$temp['imagePath']=$fet['imagePath'];
			$temp['mobileNumber']=$fet['mobileNumber'];
			$temp['name']=$fet['name'];
            $temp['vehicleType']=$fet['vehicleType'];
            $temp['vehicleId']=$fet['vehicleId'];
            $temp['noTasks']=$fet['noTasks'];
            
			
				$data[$r]=$temp;
				$r++;
			}
		                $response['status'] = 'True';
						$response['message'] = 'Success';	
						$response['data'] = $data;
		    }
		    else if(mysqli_num_rows($sql_res)){
				$response['status'] = 'True';
				$response['message'] = 'Success';	
			    $response['data'] = $data;
				
				}
		    else{
				 $response['status'] = 'True';
				 $response['message'] = 'no data';	
			   
				
				}
		}
		    else {
			$response['status']='FALSE';
			$response['message']='fail';
		}
		
		}else {
			$response['status']='FALSE';
			$response['message']='Request method wrong!';
		}
		

		header("Content-Type:application/json");
		echo json_encode($response);	

?>

















