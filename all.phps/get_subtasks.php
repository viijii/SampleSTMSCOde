<?php
    $corp_code 		= $_REQUEST['corp_code'];
   $_SESSION["corp_code"] = $corp_code; 
     include "connect.php";



if($_SERVER['REQUEST_METHOD']=="POST" || $_SERVER['REQUEST_METHOD'] == "GET")
		{
			date_default_timezone_set('Asia/Kolkata'); 
			$time= date("Y-m-d H:i:s");
			
			$action  		= $_REQUEST['action'];
		 	$empId  		= $_REQUEST['empId'];
		 	$userType		= $_REQUEST['userType'];
		 	$mainTaskId	    = $_REQUEST['mainTaskId'];
		 	$task_id  		= $_REQUEST['task_id'];
		 	$task_status  	= $_REQUEST['task_status'];
		 	$task_status_desc = $_REQUEST['task_status_desc'];
		 	$task_complete_status  = $_REQUEST['task_completed_status'];
		 	
		 	
		 	
		 	/*$action  		= 'update';
            $empId  		= '1121';
            $task_id  		='1';
            $task_complete_status='1';*/
            
			
			if($action=='pending'){
			
			$sql=" SELECT ust1.subTaskId, ust1.taskTitle as taskTitle, umt.taskTitle as mainTaskTitle, ust1.taskDesc as subTaskDesc, eu3.username as assignedBy, ust1.assignedDate, ust1.targetDate, ust1.taskEndDate, eu1.username as assignedTo, ust1.taskStatus, ust1.status, ust1.dependencyId, ust2.taskTitle as dependencyTitle, eu2.username AS dependencyUser, ust1.modifiedDate, ust1.taskStatusDesc
					FROM `userSubTasks` AS ust1
					INNER JOIN userMainTasks AS umt ON ust1.mainTaskId = umt.id
					INNER JOIN emsUsers AS eu1 ON ust1.assignedTo = eu1.empId
					LEFT JOIN userSubTasks AS ust2 ON ust1.dependencyId = ust2.subTaskId
					LEFT JOIN emsUsers AS eu2 ON ust2.assignedTo = eu2.empId inner join emsUsers as eu3 on ust1.assignedBy=eu3.empId where
					ust1.status='pending' and ust1.assignedTo='$empId'";
			$sql_res=mysqli_query($con,$sql);
			
			$r=0;
			
			while($fet=mysqli_fetch_assoc($sql_res))
			{
				$dependencyId=$fet['dependencyId'];
				$dependencyUser=$fet['dependencyUser'];
				$targetTime=$fet['targetDate'];
			
			if($dependencyId=='NA'){
				
				$fet['dependencyTitle']='NA';
				$fet['dependencyUser']='NA';
				
			}
			
		$cenvertedTime = date('Y-m-d H:i:s',strtotime($targetTime));
		$datetime = date("Y-m-d H:i:s");
		

		$date1 = strtotime($datetime);  
		$date2 = strtotime($cenvertedTime);  
		

		$diff = abs($date2 - $date1); 
		

		// To get the year divide the resultant date into 
		// total seconds in a year (365*60*60*24) 
		$years = floor($diff / (365*60*60*24));  
		  
		  
		// To get the month, subtract it with years and 
		// divide the resultant date into 
		// total seconds in a month (30*60*60*24) 
		$months = floor(($diff - $years * 365*60*60*24) 
									   / (30*60*60*24));  
		
		$days = floor(($diff - $years * 365*60*60*24 -   $months*30*60*60*24)/ (60*60*24)); 
		$hours = floor(($diff - $years * 365*60*60*24  - $months*30*60*60*24 - $days*60*60*24) / (60*60));
		$minutes = floor(($diff - $years * 365*60*60*24  - $months*30*60*60*24 - $days*60*60*24  - $hours*60*60)/ 60);  
  
		// To get the minutes, subtract it with years, 
		// months, seconds, hours and minutes  
		$seconds = floor(($diff - $years * 365*60*60*24  
				 - $months*30*60*60*24 - $days*60*60*24 
						- $hours*60*60 - $minutes*60));
			
			
			$fet['timeLeft']=$days .'days '. $hours .':'.$minutes .':'.$seconds;
			
			
			
			
			
				$data[$r]=$fet;
				$r++;
				
				
				
				}
			
						
						$response['status'] = 'True';
						$response['message'] = 'Success';	
						$response['data'] = $data;
					}
					else{
						$response['status'] = 'false';
						$response['message'] = 'no data';	
						
						
						}
						
						
				if($action=='setdependency'){
				 
				 $sql="SELECT ust. * , eu1.username AS assignedByName, eu2.username AS assignedToName
					FROM  `userSubTasks` AS ust
					INNER JOIN emsUsers AS eu1 ON ust.assignedBy = eu1.empId
					INNER JOIN emsUsers AS eu2 ON ust.assignedTo = eu2.empId
					WHERE  `status` =  'pending'
					AND dependencyId =  'NA'";
				/* $sql="SELECT eu1.username, eu2.username
		FROM  `userSubTasks` AS ust
		INNER JOIN emsUsers AS eu1 ON ust.assignedBy = eu1.empId
		INNER JOIN emsUsers AS eu2 ON ust.assignedTo = eu2.empId
		WHERE  `status` =  'pending'
		AND dependencyId =  'NA'";*/
				 
				 $sql_res=mysqli_query($con,$sql);
				 
			
                   if(mysqli_num_rows($sql_res)>0){
					   
					    $r=0;
					   
					    while($fet=mysqli_fetch_assoc($sql_res)){
							
					
					      $temp['title']  = $fet['taskTitle'];
					      $temp['description']  = $fet['taskDesc'];
					      $temp['username']  = $fet['assignedToName'];
					      $temp['id']  = $fet['subTaskId'];
					      $temp['maintaskid']  = $fet['mainTaskId'];
					      
					      
					      
							
							$data[$r]=$temp;
					     
					     $r++;
						}
					   
					  $response['status']='true';
					  $response['message']='success';
					 $response['data']=$data;
					   
					   }else{
					 
					  $response['status']='false';
					  $response['message']='no data';
					 }
				 
				 }
				 
				 if($action=='getsubtasks'){
					 
				//	 $sql="SELECT * FROM `userSubTasks` where `mainTaskId`='$mainTaskId'";
				
				$sql="SELECT ust1.subTaskId, ust1.taskTitle AS taskTitle, umt.taskTitle AS mainTaskTitle, ust1.taskDesc AS subTaskDesc, eu3.username AS assignedBy, ust1.assignedDate, ust1.targetDate, ust1.taskEndDate, eu1.username AS assignedTo, ust1.taskStatus, ust1.status, ust1.dependencyId, IFNULL( ust2.taskTitle, 'NA' ) AS dependencyTitle, IFNULL( eu2.username, 'NA' ) AS dependencyUser, ust1.modifiedDate, ust1.taskStatusDesc
		FROM `userSubTasks` AS ust1
		INNER JOIN userMainTasks AS umt ON ust1.mainTaskId = umt.id
		INNER JOIN emsUsers AS eu1 ON ust1.assignedTo = eu1.empId
		LEFT JOIN userSubTasks AS ust2 ON ust1.dependencyId = ust2.subTaskId
		LEFT JOIN emsUsers AS eu2 ON ust2.assignedTo = eu2.empId
		INNER JOIN emsUsers AS eu3 ON ust1.assignedBy = eu3.empId
		WHERE ust1.mainTaskId = '$mainTaskId'";
					 
					  $sql_res=mysqli_query($con,$sql);
				 
			
                   if(mysqli_num_rows($sql_res)>0){
					   
					    $r=0;
					   
					    while($fet=mysqli_fetch_assoc($sql_res)){
							
					
					     $temp['subTaskId']        = $fet['subTaskId'];
					      $temp['mainTaskId']      = $fet['mainTaskTitle'];
					      $temp['moduleId']        = $fet['moduleId'];
					      $temp['taskTitle']       = $fet['taskTitle'];
					      $temp['taskDesc']        = $fet['subTaskDesc'];
					      $temp['assignedBy']      = $fet['assignedBy'];
					      $temp['assignedTo']      = $fet['assignedTo'];
					      $temp['taskStatusDesc']  = $fet['taskStatus'];
					      $temp['assignedDate']    = $fet['assignedDate'];
					      $temp['targetDate']      = $fet['targetDate'];
					      $temp['taskEndDate']     = $fet['taskEndDate'];
					      $temp['taskstatus']      = $fet['status'];
					     // $temp['modifiedBy']     = $fet['modifiedBy'];
					      $temp['modifiedDate']    = $fet['modifiedDate'];
					      $temp['maintaskid']   = $fet['mainTaskTitle'];
					      $temp['dependencyId']           = $fet['dependencyTitle'];
					      $temp['dependencyname']           = $fet['dependencyUser'];
					      
							
							$data[$r]=$temp;
					     
					     $r++;
						}
					   
					  $response['status']='true';
					  $response['message']='success';
					 $response['data']=$data;
					   
					   }else{
					 
					  $response['status']='false';
					  $response['message']='no data';
					 }
					 
					 
					 }	
					 
					 
					 if($action=='update')
					 {
						 
						 if($task_complete_status=='1')
						 {
					
				 $sql="update userSubTasks set `taskStatus`='$task_status',`taskStatusDesc`='$task_status_desc',`status`='completed',
				 `modifiedDate`='$time',`taskEndDate`='$time' where `subTaskId`='$task_id'";
					 
					 $sql_res=mysqli_query($con,$sql) or die("errorupdate");
					 
					 if($sql_res){
						 
						 
						 $response['status'] = 'True';
						$response['message'] = 'Success';
						 
						 }else{
						$response['status'] = 'false';
						$response['message'] = 'not updated';
							 }
			 
					 }
					 else{
						 
					  $sql="update userSubTasks set `taskStatus`='$task_status ',`taskStatusDesc`='$task_status_desc',
					 `modifiedDate`='$time' where `subTaskId`='$task_id'";
					 
					 $sql_res=mysqli_query($con,$sql);
					 
					   $response['status'] = 'True';
						$response['message'] = 'Success';
								 
						 }
								 
			
			/*$response['status']='false';
			$response['message']='no data';*/	 
						 
						 
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
