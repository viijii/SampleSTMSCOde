<?php
     $corp_code 		= $_REQUEST['corp_code'];
     //$corp_code 		= 'sram';
     $_SESSION["corp_code"] = $corp_code ;

	include "connect.php";
     
    header('Access-Control-Allow-Origin: *');
	header('Access-Control-Allow-Methods: POST, GET, OPTIONS');
	
	 if($_SERVER['REQUEST_METHOD']=="POST" || $_SERVER['REQUEST_METHOD'] == "GET")
        {
			date_default_timezone_set('Asia/Kolkata'); 
			$complaintDate= date("Y-m-d H:i:s");
			
		    $action = $_REQUEST['action'];
		    $title=$_REQUEST['title'];
			$location=$_REQUEST['location'];
		    $imageData=$_REQUEST['imagePath'];
			
			
			/*$action = 'fieldActivity';
			$title='srinivasa';
			$location='bangolore'; 
			$imageData='http://115.98.3.215:90/sRamapptest/uploads/2.png';*/ 
			
			 if($action == 'fieldActivity')
		    {
				
             //echo '1';
			  $insert_sql = "Insert into `fieldActivity`(`title`,`location`)VALUES('$title','$location')";
				$result_sql = mysqli_query($con,$insert_sql);
				
			
		
			if($result_sql){
				//echo '2';
				$getData_sql ="SELECT * FROM `fieldActivity` ORDER BY id DESC LIMIT 1";
				$getData_res = mysqli_query($con, $getData_sql);
				
				if($getData_res){
					//echo '3';
				$row=mysqli_fetch_assoc($getData_res);
				
				$id = $row['id'];
		
		//	echo $id; 
				$ImagePath = "uploads/".$id.".png";
				$ServerURL = "http://115.98.3.215:90/sRamapptest/".$ImagePath;
				$InsertImg = mysqli_query($con,"UPDATE `fieldActivity` SET path='$ServerURL' WHERE `id` ='$id'");
			//	if($InsertImg){
               // echo '4';
				file_put_contents($ImagePath,base64_decode($imageData));
			//		echo 'success';
					
			//	}
					$result['status'] = 'TRUE';
					$result['message']='Sucess';
					$result['id']=$id;
				
			/*}else{
				
				$result['status'] = 'False';
				$result['message']='fail to upload';
				}*/
		}else{
			//echo '6';
			$result['status'] = 'False';
				$result['message']='no data';
			}	
		
  }
  else{
	//  echo '7';
				$result['status'] = 'False';
				$result['message']='not inserted';
			
		}
	}
}
	else{
		//echo '8';
				$result['status'] = 'false';
				$result['message']='request method wrong';
		}

  
 header("Content-Type:application/json");
    echo json_encode($result);
   ?>



          
