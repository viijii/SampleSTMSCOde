<?php


     $corp_code 		= $_REQUEST['corp_code'];
        // $corp_code 		= 'sram';
     $_SESSION['corp_code'] = $corp_code ;

	include "connect.php";
     
    header('Access-Control-Allow-Origin: *');
	header('Access-Control-Allow-Methods: POST, GET, OPTIONS');
	
	 if($_SERVER['REQUEST_METHOD']=="POST" || $_SERVER['REQUEST_METHOD'] == "GET")
        {
			date_default_timezone_set('Asia/Kolkata'); 
			$complaintDate= date("Y-m-d H:i:s");
			
			$imageData= $_REQUEST['imagePath'];
			$action = $_REQUEST['action'];
			$title= $_REQUEST['title'];
			$description = $_REQUEST['description'];
			$customerId= $_REQUEST['customerId'];
			$vehicleId = $_REQUEST['vehicleId'];
			
			
			$empId=$_REQUEST['empId'];
			$complaintId=$_REQUEST['complaintId'];
			$startTime=$_REQUEST['strDate'];
			$instructions=$_REQUEST['instructions'];
			$spinner=$_REQUEST['spinner'];
			$days=$_REQUEST['days'];
			
			
			

				//$action = 'complaintRequest';
	     	

	
		
		    if($action == 'complaintRequest')
		    {
				

			  $insert_sql = "Insert into `customerComplaint`(`title`,`description`,`customerId`,`vehicleId`,`complaintDate`, 
			   `approveStatus`)VALUES('$title','$description','$customerId','$vehicleId','$complaintDate', 'pending')";
				$result_sql = mysqli_query($con,$insert_sql) or die('error');
				
			
		
			if($result_sql){
				
				$getData_sql ="SELECT * FROM `customerComplaint` WHERE customerId='$customerId' ORDER BY complaintId DESC LIMIT 1";
				$getData_res = mysqli_query($con, $getData_sql);
				
				if($getData_res){
				$row=mysqli_fetch_assoc($getData_res);
				
				$complaintId = $row['complaintId'];
				
				$ImagePath = "uploads/".$complaintId.".png";
				$ServerURL = "http://192.168.0.21:90/sRamapptest/".$ImagePath;
				$InsertImg = mysqli_query($con,"UPDATE `customerComplaint` SET imagePath='$ServerURL' WHERE `complaintId` ='$complaintId'");
				if($InsertImg){
 
					file_put_contents($ImagePath,base64_decode($imageData));
					
					
					$result['status'] = 'TRUE';
					$result['message']='Sucess';
					$result['complaintId']=$complaintId;
					
					
				}else{
					$result['status'] = 'False';
				$result['message']='fail to upload';
				}
			}
			else{
				$result['status'] = 'False';
				$result['message']='no data';
				}
				
		}
			else{
				$result['status'] = 'False';
				$result['message']='not inserted';
			
		}
	}
		
		else if($action == 'AssignTask'){
			
			$cenvertedTime = date('Y-m-d H:i:s', strtotime($complaintDate. ' +'.$days.'days'));
			
			//echo $cenvertedTime;

			   $insert_sql1 = "Insert into `taskDetails`(`taskTitle`,`complaintId`,`assignedTo`,`assignedBy`,`assignedtime`,`targetTime`,`instruments`,`status`) 
				 VALUES ('$title','$complaintId','$spinner','$empId','$complaintDate','$cenvertedTime','$instructions','pending')" ;
				$result_sql1 = mysqli_query($con, $insert_sql1);
				if($result_sql1){
			
				$result['status'] = 'TRUE';
				$result['message']='Sucess';
			
			}
			
			else{
				
				$result['status'] = 'False';
				$result['message']='Fail';
				
				}
			}
			
			else if($action == 'approve'){

			   $insert_sql1 = "UPDATE `customerComplaint` set approveStatus='approved' where complaintId='$complaintId'" ;
				$result_sql1 = mysqli_query($con, $insert_sql1);
				
			if($result_sql1){
				$result['status'] = 'TRUE';
				$result['message']='Sucess';
			
			}
			
			else{
				
				$result['status'] = 'false';
				$result['message']='Fail';
				
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


          
