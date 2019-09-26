<?php


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
					$taskId	    	    = $_REQUEST['taskId'];
					
					$empId              =$_REQUEST['empId'];
					$complaintId        =$_REQUEST['complaintId'];
					$startTime          =$_REQUEST['strDate'];
					$title              =$_REQUEST['title'];
					$instructions       =$_REQUEST['instructions'];
					$spinner            =$_REQUEST['spinner'];
					$days               =$_REQUEST['days'];
					
					
				//	$action  	    	= 'modify';
			//		$taskId	    	    = '37';
					
					
			
		 if($action =='modify'){
			 
		
			$sql="SELECT t2.*, IFNULL(vm.vehicleType, 'NA') AS vehicleType, IFNULL(vm.vehicleModel, 'NA') AS vehicleModel
			 FROM (SELECT t1.*, IFNULL(vd.vehicleTypeId, 'NA') AS vehicleTypeId, IFNULL(ct.name, 'NA') AS name, et.name AS 
			 assignedToName FROM (SELECT `taskId`, `taskTitle`, td.`complaintId`, `assignedTo`, `assignedtime`, `targetTime`,
			  `completedTime`, `status`,`instruments`, cc.vehicleId, cc.customerId FROM `taskDetails` as td INNER JOIN customerComplaint
			   AS cc ON cc.complaintId=td.complaintId WHERE taskId= '$taskId') as t1 LEFT JOIN customerTable as ct
			    ON t1.customerId= ct.customerId LEFT JOIN vehicleDetails AS vd ON vd.vehicleId= t1.vehicleId INNER JOIN 
			    employeeTable AS et ON et.empId= t1.assignedTo) AS t2 LEFT JOIN vehicleModels AS vm on vm.vehicleTypeId= t2.vehicleTypeId";
			
			$sql_res=mysqli_query($con,$sql);
				 
			
                   if(mysqli_num_rows($sql_res)>0){
					   
					    $r=0;
					   
					    while($fet=mysqli_fetch_assoc($sql_res)){
							
					
					      $temp['taskId']        			= $fet['taskId'];
					      $temp['taskTitle']  				= $fet['taskTitle'];
					       $temp['complaintId']  			= $fet['complaintId'];
					      $temp['assignedTime']     		= $fet['assignedtime'];
					      $temp['targetTime']      		    = $fet['targetTime'];
					      $temp['completedTime']  	 		= $fet['completedTime'];
					      $temp['taskStatus']       		= $fet['status'];
					      $temp['vehicleId']  				= $fet['vehicleId'];
					      $temp['customerId']       	    = $fet['customerId'];
					      $temp['vehicleType']      	    = $fet['vehicleType'];
					      $temp['name']             	    = $fet['name'];
					       $temp['assignedToName']             	    = $fet['assignedToName'];
					      
					       $temp['trackerId']       	    = $fet['trackerId'];
					      $temp['trackerPassword']  	    = $fet['trackerPassword'];
					      $temp['serviceStatus']  		    = $fet['serviceStatus'];
					      $temp['assignedTo']    		    = $fet['assignedTo'];
					      $temp['instruments']    		    = $fet['instruments'];
					      
					      
					      $date1 = $temp['assignedTime'];
					      $date2=$temp['targetTime'];
					     $date1 = strtotime($date1);  
						$date2 = strtotime($date2);  
  
// Formulate the Difference between two dates 
						$diff = abs($date2 - $date1);  

						$years = floor($diff / (365*60*60*24));  
  
						$months = floor(($diff - $years * 365*60*60*24) 
                               / (30*60*60*24));  
                               
						$days = floor(($diff - $years * 365*60*60*24 -  
						$months*30*60*60*24)/ (60*60*24)); 
            
					    $temp['estimatedDays']=  $days;
					      
			
						$data[$r]=$temp;
					     
					     $r++;
						}
					   
					  $result['status']='true';
					  $result['message']='success';
					  $result['data']=$data;
					   
					   }
					   else{
						    $result['status']='Failed';
					  $result['message']='no rows';
					  }
			 
		 }	
		 
		 else if($action =='updateTask'){
			 
			 $cenvertedTime = date('Y-m-d H:i:s', strtotime($time. ' +'.$days.'days'));
			 
			    $update_sql = "UPDATE `taskDetails` SET `taskTitle`='$title',`assignedTo`='$spinner',
			    `assignedBy`='$empId',`assignedtime`='$time', `targetTime`='$cenvertedTime',`instruments`='$instructions',
			    `status`='pending',`modifiedDate`='$time',`modifiedBy`='$empId' where `taskId` ='$taskId' ";
				$result_sql = mysqli_query($con, $update_sql) or die("error");
				if($result_sql){
			
				$result['status'] = 'TRUE';
				$result['message']='Sucess';
			
			}
			
			else{
				
				$result['status'] = 'False';
				$result['message']='Fail';
				
				}
			 
			 }
			 
		 else if($action =='verify'){	
			 
			 $sql="UPDATE `taskDetails` SET `status`='verified',`completedTime`='$time' where `taskId` ='$taskId'";
			 $res_sql= mysqli_query($con, $sql) or die("error");
			 
			 if($res_sql){
			
				$result['status'] = 'TRUE';
				$result['message']='Sucess';
			
			}
			
			else{
				
				$result['status'] = 'False';
				$result['message']='Fail';
				
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
