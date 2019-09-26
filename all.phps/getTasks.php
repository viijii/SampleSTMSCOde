<?php

  $corp_code 		= $_REQUEST['corp_code'];
  //$corp_code 		= 'sram';
   $_SESSION['corp_code'] = $corp_code;
  	include 'connect.php';
       
	header('Access-Control-Allow-Origin: *');
	header('Access-Control-Allow-Methods: POST, GET, OPTIONS');
	
	if($_SERVER['REQUEST_METHOD']=="POST" || $_SERVER['REQUEST_METHOD'] == "GET")
		{
                  
		    $action     = $_REQUEST['action'];
		    $ideaid     = $_REQUEST['idea_id'];
		    $userType	= $_REQUEST['user_type'];
		    $empid	= $_REQUEST['empId'];
	    
	    
	  /*  $action     = 'pending';
	  $empid	= '4';*/
	  
		  if($action =='pending')
		  {
            if( $userType=='admin')
             { 
				$sql="SELECT t2.*, IFNULL(vm.vehicleType, 'NA') AS vehicleType, IFNULL(vm.vehicleModel, 'NA') AS model FROM (SELECT t1.*, IFNULL(vd.vehicleTypeId, 'NA') AS a, IFNULL(ct.name, 'NA') AS cname, IFNULL(vd.trackerId, 'NA') AS trackerId , IFNULL(vd.trackerPassword, 'NA') AS trackerPassword
				 FROM (SELECT `taskId`, `taskTitle`, td.`complaintId`, `assignedTo`, assignedBy, IFNULL(et1.name, 'NA') AS assignedToName, IFNULL(et2.name, 'NA') AS assignedByName, `assignedtime`, `targetTime`, `completedTime`,`taskStatus`,
				`status`, cc.vehicleId, cc.customerId, cc.imagePath FROM `taskDetails` as td INNER JOIN customerComplaint AS cc ON
				 cc.complaintId=td.complaintId LEFT JOIN employeeTable AS et1 ON td.assignedTo= et1.empId LEFT JOIN employeeTable AS et2 ON et2.empId= td.assignedBy WHERE status='pending') as t1 LEFT JOIN customerTable as ct
				  ON t1.customerId= ct.customerId LEFT JOIN vehicleDetails  AS vd ON vd.vehicleId= t1.vehicleId) AS t2 LEFT
                  JOIN vehicleModels AS vm ON t2.a= vm.vehicleTypeId ";
			}else{
				$sql="SELECT t2.*, IFNULL(vm.vehicleType, 'NA') AS vehicleType, IFNULL(vm.vehicleModel, 'NA') AS model FROM (SELECT t1.*, IFNULL(vd.vehicleTypeId, 'NA') AS a, IFNULL(ct.name, 'NA') AS cname, IFNULL(vd.trackerId, 'NA') AS trackerId , IFNULL(vd.trackerPassword, 'NA') AS trackerPassword
				 FROM (SELECT `taskId`, `taskTitle`, td.`complaintId`, `assignedTo`, assignedBy, IFNULL(et1.name, 'NA') AS assignedToName, IFNULL(et2.name, 'NA') AS assignedByName, `assignedtime`, `targetTime`, `completedTime`,`taskStatus`,`serviceStatus`,
				`status`, cc.vehicleId, cc.customerId, cc.imagePath FROM `taskDetails` as td INNER JOIN customerComplaint AS cc ON
				 cc.complaintId=td.complaintId LEFT JOIN employeeTable AS et1 ON td.assignedTo= et1.empId LEFT JOIN employeeTable AS et2 ON et2.empId= td.assignedBy WHERE status='pending' && assignedTo= '$empid') as t1 LEFT JOIN customerTable as ct
				  ON t1.customerId= ct.customerId LEFT JOIN vehicleDetails  AS vd ON vd.vehicleId= t1.vehicleId) AS t2 LEFT
                  JOIN vehicleModels AS vm ON t2.a= vm.vehicleTypeId ";
			}
				 $sql_res=mysqli_query($con,$sql);
				 
			
                   if(mysqli_num_rows($sql_res)>0){
					   
					    $r=0;
					   
					    while($fet=mysqli_fetch_assoc($sql_res)){
							
					
					      $temp['taskId']        			= $fet['taskId'];
					      $temp['taskTitle']  			= $fet['taskTitle'];
					       $temp['complaintId']  			= $fet['complaintId'];
					      $temp['assignedTime']     		= $fet['assignedtime'];
					      $temp['targetTime']      	    = $fet['targetTime'];
					      $temp['completedTime']   		= $fet['completedTime'];
					      $temp['status']       		= $fet['status'];
					      $temp['taskStatus']       	= $fet['taskStatus'];
					      $temp['vehicleId']  		= $fet['vehicleId'];
					      $temp['customerId']          = $fet['customerId'];
					      $temp['vehicleType']          = $fet['vehicleType'];
					       $temp['model']          = $fet['model'];
					       $temp['imagePath']          = $fet['imagePath'];
					      $temp['name']  = $fet['cname'];
					       $temp['trackerId']          = $fet['trackerId'];
					      $temp['trackerPassword']    = $fet['trackerPassword'];
					      $temp['serviceStatus']     = $fet['serviceStatus'];
					      $temp['assignedTo']     = $fet['assignedTo'];
					      $temp['assignedToName']     = $fet['assignedToName'];
					      $temp['assignedByName']     = $fet['assignedByName'];
					       $targetTime=$temp['targetTime'];
					      
					      
					      
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
			
			
			$temp['timeLeft']=$days .'days '. $hours .':'.$minutes .':'.$seconds;
					      
					      
					 
							
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
				 	
		  }else if($action =='completed')
		  {
            if( $userType=='admin')
             { 
				$sql="SELECT t2.*, IFNULL(vm.vehicleType, 'NA') AS vehicleType, IFNULL(vm.vehicleModel, 'NA') AS model FROM (SELECT t1.*, IFNULL(vd.vehicleTypeId, 'NA') AS a, IFNULL(ct.name, 'NA') AS cname, IFNULL(vd.trackerId, 'NA') AS trackerId , IFNULL(vd.trackerPassword, 'NA') AS trackerPassword
				 FROM (SELECT `taskId`, `taskTitle`, td.`complaintId`, `assignedTo`, assignedBy, IFNULL(et1.name, 'NA') AS assignedToName, IFNULL(et2.name, 'NA') AS assignedByName, `assignedtime`, `targetTime`, `completedTime`,`taskStatus`,
				`status`, cc.vehicleId, cc.customerId, cc.imagePath FROM `taskDetails` as td INNER JOIN customerComplaint AS cc ON
				 cc.complaintId=td.complaintId LEFT JOIN employeeTable AS et1 ON td.assignedTo= et1.empId LEFT JOIN employeeTable AS et2 ON et2.empId= td.assignedBy WHERE status='completed') as t1 LEFT JOIN customerTable as ct
				  ON t1.customerId= ct.customerId LEFT JOIN vehicleDetails  AS vd ON vd.vehicleId= t1.vehicleId) AS t2 LEFT
                  JOIN vehicleModels AS vm ON t2.a= vm.vehicleTypeId ";
			}else{
				$sql="SELECT t2.*, IFNULL(vm.vehicleType, 'NA') AS vehicleType, IFNULL(vm.vehicleModel, 'NA') AS model FROM (SELECT t1.*, IFNULL(vd.vehicleTypeId, 'NA') AS a, IFNULL(ct.name, 'NA') AS cname, IFNULL(vd.trackerId, 'NA') AS trackerId , IFNULL(vd.trackerPassword, 'NA') AS trackerPassword
				 FROM (SELECT `taskId`, `taskTitle`, td.`complaintId`, `assignedTo`, assignedBy, IFNULL(et1.name, 'NA') AS assignedToName, IFNULL(et2.name, 'NA') AS assignedByName, `assignedtime`, `targetTime`, `completedTime`,`taskStatus`,
				`status`, cc.vehicleId, cc.customerId, cc.imagePath FROM `taskDetails` as td INNER JOIN customerComplaint AS cc ON
				 cc.complaintId=td.complaintId LEFT JOIN employeeTable AS et1 ON td.assignedTo= et1.empId LEFT JOIN employeeTable AS et2 ON et2.empId= td.assignedBy WHERE status='completed' && assignedTo= '$empid') as t1 LEFT JOIN customerTable as ct
				  ON t1.customerId= ct.customerId LEFT JOIN vehicleDetails  AS vd ON vd.vehicleId= t1.vehicleId) AS t2 LEFT
                  JOIN vehicleModels AS vm ON t2.a= vm.vehicleTypeId ";
			}
				 $sql_res=mysqli_query($con,$sql);
				 
			
                   if(mysqli_num_rows($sql_res)>0){
					   
					    $r=0;
					   
					    while($fet=mysqli_fetch_assoc($sql_res)){
							
					
					      $temp['taskId']        			= $fet['taskId'];
					      $temp['taskTitle']  			= $fet['taskTitle'];
					       $temp['complaintId']  			= $fet['complaintId'];
					      $temp['assignedTime']     		= $fet['assignedtime'];
					      $temp['targetTime']      	    = $fet['targetTime'];
					      $temp['completedTime']   		= $fet['completedTime'];
					      $temp['status']       		= $fet['status'];
					      $temp['taskStatus']       		= $fet['taskStatus'];
					      $temp['vehicleId']  		= $fet['vehicleId'];
					      $temp['customerId']          = $fet['customerId'];
					      $temp['vehicleType']          = $fet['vehicleType'];
					      $temp['name']  = $fet['name'];
					       $temp['trackerId']          = $fet['trackerId'];
					      $temp['trackerPassword']  = $fet['trackerPassword'];
					      $temp['assignedTo']     = $fet['assignedTo'];
					      $temp['assignedToName']     = $fet['assignedToName'];
					      $temp['assignedByName']     = $fet['assignedByName'];
					      
					      $targetTime=$temp['targetTime'];
					      $temp['timeLeft']="";
					
							
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
				 	
		  }else if($action =='verified')
		  {
            if( $userType=='admin')
             { 
				$sql="SELECT t2.*, IFNULL(vm.vehicleType, 'NA') AS vehicleType, IFNULL(vm.vehicleModel, 'NA') AS model FROM (SELECT t1.*, IFNULL(vd.vehicleTypeId, 'NA') AS a, IFNULL(ct.name, 'NA') AS cname, IFNULL(vd.trackerId, 'NA') AS trackerId , IFNULL(vd.trackerPassword, 'NA') AS trackerPassword
				 FROM (SELECT `taskId`, `taskTitle`, td.`complaintId`, `assignedTo`, assignedBy, IFNULL(et1.name, 'NA') AS assignedToName, IFNULL(et2.name, 'NA') AS assignedByName, `assignedtime`, `targetTime`, `completedTime`,`taskStatus`,
				`status`, cc.vehicleId, cc.customerId, cc.imagePath FROM `taskDetails` as td INNER JOIN customerComplaint AS cc ON
				 cc.complaintId=td.complaintId LEFT JOIN employeeTable AS et1 ON td.assignedTo= et1.empId LEFT JOIN employeeTable AS et2 ON et2.empId= td.assignedBy WHERE status='verified') as t1 LEFT JOIN customerTable as ct
				  ON t1.customerId= ct.customerId LEFT JOIN vehicleDetails  AS vd ON vd.vehicleId= t1.vehicleId) AS t2 LEFT
                  JOIN vehicleModels AS vm ON t2.a= vm.vehicleTypeId";
			}else{
				$sql="SELECT t2.*, IFNULL(vm.vehicleType, 'NA') AS vehicleType, IFNULL(vm.vehicleModel, 'NA') AS model FROM (SELECT t1.*, IFNULL(vd.vehicleTypeId, 'NA') AS a, IFNULL(ct.name, 'NA') AS cname, IFNULL(vd.trackerId, 'NA') AS trackerId , IFNULL(vd.trackerPassword, 'NA') AS trackerPassword
				 FROM (SELECT `taskId`, `taskTitle`, td.`complaintId`, `assignedTo`, assignedBy, IFNULL(et1.name, 'NA') AS assignedToName, IFNULL(et2.name, 'NA') AS assignedByName, `assignedtime`, `targetTime`, `completedTime`,`taskStatus`,
				`status`, cc.vehicleId, cc.customerId, cc.imagePath FROM `taskDetails` as td INNER JOIN customerComplaint AS cc ON
				 cc.complaintId=td.complaintId LEFT JOIN employeeTable AS et1 ON td.assignedTo= et1.empId LEFT JOIN employeeTable AS et2 ON et2.empId= td.assignedBy WHERE status='verified' && assignedTo= '$empid') as t1 LEFT JOIN customerTable as ct
				  ON t1.customerId= ct.customerId LEFT JOIN vehicleDetails  AS vd ON vd.vehicleId= t1.vehicleId) AS t2 LEFT
                  JOIN vehicleModels AS vm ON t2.a= vm.vehicleTypeId ";
			}
				 $sql_res=mysqli_query($con,$sql);
				 
			
                   if(mysqli_num_rows($sql_res)>0){
					   
					    $r=0;
					   
					    while($fet=mysqli_fetch_assoc($sql_res)){
							
					
					      $temp['taskId']        			= $fet['taskId'];
					      $temp['taskTitle']  			= $fet['taskTitle'];
					       $temp['complaintId']  			= $fet['complaintId'];
					      $temp['assignedTime']     		= $fet['assignedtime'];
					      $temp['targetTime']      	    = $fet['targetTime'];
					      $temp['completedTime']   		= $fet['completedTime'];
					      $temp['status']       		= $fet['status'];
					      $temp['taskStatus']       		= $fet['taskStatus'];
					      $temp['vehicleId']  		= $fet['vehicleId'];
					      $temp['customerId']          = $fet['customerId'];
					      $temp['vehicleType']          = $fet['vehicleType'];
					      $temp['name']  = $fet['name'];
					       $temp['trackerId']          = $fet['trackerId'];
					      $temp['trackerPassword']  = $fet['trackerPassword'];
					      $temp['serviceStatus']        			= $fet['serviceStatus'];
					      $temp['assignedTo']     = $fet['assignedTo'];
					      $temp['assignedToName']     = $fet['assignedToName'];
					      $temp['assignedByName']     = $fet['assignedByName'];
					      
					      $targetTime=$temp['targetTime'];
					      $temp['timeLeft']="";
					
							
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
			
				  
			
	  }
						   
	 else     
		{
			$result['status']='FALSE';
			$result['message']='Request method wrong!';
		}
     header("Content-Type:application/json"); 
     echo json_encode($result);
    ?>
