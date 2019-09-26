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
					$updateDescription  = $_REQUEST['offers'];
					$roles 	    	    = $_REQUEST['s'];
					$role 	    	    = $_REQUEST['role'];
				
					
				/*	$action  	    	= 'getting';
					$role 	    	    = 'employees';*/ 
					
			if($action =='updates'){

				$sql_ins ="INSERT INTO `updates`(`updateDescription`,`roles`,`updatedTime`) VALUES ('$updateDescription','$roles','$time')";
										
				$res_ins = mysqli_query($con,$sql_ins) or die('error');
			
				if($res_ins){
			
			      $result['status']='TRUE';
			      $result['message']='Sucess updated';
		        }
		         else{
			         $result['status']='False';
			         $result['message']='fail';
			
			      }
		   }
		    else{
			
			  $result['status']='fail';
			  $result['message']='role not inserted';
			
		   }
		   
		  if($action =='getting'){
			  
			  if($role=='admin'){
				  $sql="select * from `updates` order by updatedTime desc limit 1 ";
				  }else if($role=='employees'){
					   $sql="select * from `updates` where roles='$role'|| roles='all' order by updatedTime desc limit 1  ";
					  }else{
						  $sql="select * from `updates` where roles='$role'|| roles='all' order by updatedTime desc limit 1 ";
						  }
			 $sql_res=mysqli_query($con,$sql) or die('error');
			
			$r=0;
			
			while($fet=mysqli_fetch_array($sql_res))
			{
			$temp['updateDescription']=$fet['updateDescription'];
			$temp['roles']=$fet['roles'];	
			$temp['updatedTime']=$fet['updatedTime']; 
					$data[$r]=$temp;
				    $r++;
                        
			    }
			            $result['status'] = 'True';
						$result['message'] = 'Success';
						$result['data'] = $data;
			  
			  }else{
				  $result['status']='fail';
			      $result['message']='not getting';
				  }
	      }else{
				  $result['status']='fail';
			      $result['message']='server not getting';
			
				  }
	
	header("Content-Type:application/json");
	echo json_encode($result);		
?>			
