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
			$time= date("Y-m-d");
			
		 	$action  		= $_REQUEST['action'];
		 	
		 	
		 	//$action='save';
		 	
		 	
		 	if($action == 'save')
				{
				$vId  		= $_REQUEST['vId'];
				$customer  		    = $_REQUEST['customer'];
				$vtype  		    = $_REQUEST['vtype'];
				$vmodel  		    = $_REQUEST['vehModel'];
				$tId  		        = $_REQUEST['tID'];
				$tPwd  		        = $_REQUEST['tPwd'];
				$next_service  		= $_REQUEST['next_service'];
				$purchase_date  	= $_REQUEST['purchase_date'];
				$lId  		        = $_REQUEST['loginId'];
				
				
				
				/*$customer  	= 'tulasi';
				$vtype  	= 'tractor';
				$vmodel     ='model1';
				$tId  		= '2335';
				$tPwd  		= 'hello66';
				$next_service ='kkk';*/
				
				
				
				
				
				$sql="SELECT `customerId` FROM `customerTable` WHERE name='$customer'";		
					$sql1=mysqli_query($con,$sql);
					
					if(mysqli_num_rows($sql1)>0){
					   
					    $r=0;
					   // echo '1';
					    
					   
					   
					    while($fet=mysqli_fetch_assoc($sql1)){
							
					
					      $empId        = $fet['customerId'];
					     
					     // echo $empId;
					      
					       $r++;
						}
						
						
						$sql2="SELECT vehicleTypeId FROM `vehicleModels` where vehicleModel='$vmodel' AND vehicleType='$vtype'";		
					$sql2=mysqli_query($con,$sql2);
					
					if(mysqli_num_rows($sql2)>0){
					   
					    $r=0;
					   // echo '2';
					    
					   
					   
					    while($fet=mysqli_fetch_assoc($sql2)){
							
					
					      $vtypeId        = $fet['vehicleTypeId'];
					     
					     // echo $vtypeId;
					      
					       $r++;
						}
						
				
				$sql_add="Insert into vehicleDetails(`customerId`,`vehicleTypeId`,`vehicleId`,`trackerId`,`trackerPassword`,`purchaseDate`,`nextService`,`createdBy`) 
				values ('$empId','$vtypeId','$vId','$tId','$tPwd','$purchase_date','$next_service','$lId')";		
					$sql_add_res=mysqli_query($con,$sql_add);
					
					//echo '3';
					if($sql_add_res){
							
					$result['status'] = 'True';
					$result['message'] = 'Success';	
				}
				else{
					$result['status'] = 'false';
					$result['message'] = 'failed to insert';	
					}
						
		 	}
		 	else{
					$result['status'] = 'false';
					$result['message'] = 'fail to get vehicleModel';	
					}
		 	
		}
				else{
					$result['status'] = 'false';
					$result['message'] = 'fail to get customer';	
					}
		
		}
		else if($action == 'update')
				{
					//echo '233';
				$vId  		= $_REQUEST['vId'];
				$customer  		= $_REQUEST['customer'];
				$vtype  		= $_REQUEST['vtype'];
				$vmodel  		= $_REQUEST['vehModel'];
				$tId  		= $_REQUEST['tID'];
				$tPwd  		= $_REQUEST['tPwd'];
				$next_service  		= $_REQUEST['next_service'];
				$purchase_date  		= $_REQUEST['purchase_date'];
				$lId  		= $_REQUEST['loginId'];
				
				
				
				/*$vId  		= '1';
				$customer  	= 'krishna';
				$vtype  	= 'bus';
				$tId  		= '123';
				$tPwd  		= '3222';
				$next_service  = '212';
				$purchase_date = '28_05_1995';
				$lId  		= '1';*/
				
				$sql="SELECT `customerId` FROM `customerTable` WHERE name='$customer'";		
					$sql1=mysqli_query($con,$sql);
					
					if(mysqli_num_rows($sql1)>0){
					   
					    $r=0;
					    //echo '1';
					    
					   
					   
					    while($fet=mysqli_fetch_assoc($sql1)){
							
					
					      $empId        = $fet['customerId'];
					     
					     // echo $empId;
					      
					       $r++;
						}
						$sql2="SELECT vehicleTypeId FROM `vehicleModels` where vehicleModel='$vmodel' AND vehicleType='$vtype'";		
					$sql2=mysqli_query($con,$sql2);
					
					if(mysqli_num_rows($sql2)>0){
					   
					    $r=0;
					   // echo '2';
					    
					   
					   
					    while($fet=mysqli_fetch_assoc($sql2)){
							
					
					      $vtypeId        = $fet['vehicleTypeId'];
					     
					     // echo $vtypeId;
					      
					       $r++;
						}
						
						
				$sql_add="Update vehicleDetails set `customerId`='$empId',`vehicleTypeId`='$vtypeId',`trackerId`=$tId,
				`trackerPassword`='$tPwd',`purchaseDate`='$purchase_date',`nextService`='$next_service' where vehicleId='$vId'";
						
					$sql_add_res=mysqli_query($con,$sql_add);
					
					if(sql_add_res){
							
					$result['status'] = 'True';
					$result['message'] = 'Success';	
				}
				else{
					$result['status'] = 'false';
					$result['message'] = 'fail';	
					}
				}
				else{
					$result['status'] = 'false';
					$result['message'] = 'fail';	
					}
				
			}
			else{
					$result['status'] = 'false';
					$result['message'] = 'fail';	
					}
					
				
			}
		else if($action == 'edit'){
			
			    $vId  		= $_REQUEST['vId'];
			    /*$customer  		= $_REQUEST['customer'];
				$vtype  		= $_REQUEST['vtype'];
				$tId  		= $_REQUEST['tID'];
				$tPwd  		= $_REQUEST['tPwd'];
				$next_service  		= $_REQUEST['next_service'];
				$purchase_date  		= $_REQUEST['purchase_date'];
				$lId  		= $_REQUEST['loginId'];*/
			
			//$vId  		='1234455';
					
					$sql="SELECT t1.*, IFNULL(vm.vehicleType, 'NA') AS vehicleType, IFNULL(vm.vehicleModel, 'NA') AS vehicleModel 
					FROM (SELECT vd.*, IFNULL(ct.name, 'NA') AS name FROM `vehicleDetails` AS vd LEFT JOIN customerTable ct 
					ON vd.customerId = ct.customerId WHERE ct.activeStatus = 'active' && vd.vehicleId='$vId') AS t1 LEFT JOIN 
					vehicleModels AS vm ON vm.vehicleTypeId= t1.vehicleTypeId";

            $sql_res=mysqli_query($con,$sql) or die('error');
				 
			
                   if(mysqli_num_rows($sql_res)>0)
                   {
					   //echo '111';
					    $r=0;
					   
					    while($fet=mysqli_fetch_assoc($sql_res)){
							
						$temp['customerName']       = $fet['name'];
					    $temp['trackerId']       = $fet['trackerId'];
					    $temp['vehicleId']       = $fet['vehicleId'];
					    $temp['vehicleType']       = $fet['vehicleType'];
					     $temp['vehicleModel']       = $fet['vehicleModel'];
					    $temp['purchaseDate']       = $fet['purchaseDate'];
					    $temp['noOfService']       = $fet['noOfServices'];
					    $temp['nextService']       = $fet['nextService']; 
					    $temp['trackerPassword']       = $fet['trackerPassword']; 
					 
							
							$data[$r]=$temp;
					     
					     $r++;
						}
					   
					  $result['status']='true';
					  $result['message']='success';
					  $result['data']=$data;
			
			  
			  
		  }
		  else{
			  $result['status']='fail';
			 $result['message']='failure_edit';
			  }
					
					}
		
				
				
				
					else{
		 	$result['status'] = 'false';
			$result['message'] = 'no data';
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
