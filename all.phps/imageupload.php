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
			$customerId = $_REQUEST['customerId'];
			$title=$_REQUEST['title'];
			$description=$_REQUEST['description'];
			$vehicleId=$_REQUEST['vehicleId'];
			$imageData=$_REQUEST['imagePath'];
			
		/*	$action ='complaintRequest';
			$title='srinivasa';
			$imageData='/9j/4AAQSkZJRgABAQAAAQABAAD/2wBDAAEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEB
    AQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQH/2wBDAQEBAQEBAQEBAQEBAQEBAQEBAQEB
    AQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQH/wAARCAUAA8ADASIA
    AhEBAxEB/8QAHwAAAQQDAQEBAQAAAAAAAAAABAIDBQYABwgJCgEL/8QAhhAAAAMEBAkHBwYHBw4H
    CgEdAQIDAAQRIQUSMUEGEyJRYXGBkfAHMkKhscHRCBQjM1Lh8QkVJENTYjRjcnOCg5MWJURUZKOz
    FzU3OHR2hJKUpLS1w9MmRVV3h6K2GBlIV2WVt8TH4yc2RlZ1haWyxdQoZoaWl+TzR2inwtfnWGem
    0tXW8v/EAB4BAAMBAQEBAQEBAQAAAAAAAAACAwQBBQYHCQgK/8QAVxEAAAMEBggEAwYCBQkFBwUB
    AAECAxEhMRJBUWGBoQQycZGxwdHwBRMi4UJi8RQjM0NSggY0BxVTcqIkNURjc3SSssIIFlST0hc3
    ZHaDw+IlNkWztfL/2gAMAwEAAhEDEQA/AOkC0fRarqV0eKKczIpJgROsgip6OGmXvaDeMC8H1ax3
    FZ4otYOb5qtjE8/qVg9046WuAFGGQpzvaLCGsN26cGHFJQAEpyJqh7UdMbO1vwPy78vcfrSVGmXf
    MUkKDwjcwMDlSTo/FL6ojyUXdQ8dMZ6OxmS0hTLjWCkqJeEywGsomXzh3ldjke1rmZ3EBiCpkZaF
    CT432s6cjwqiIAYixS+zOPGfVnagbzLs/YU9Gl6OeTGAqhUzfejo7fexxiIiFcDGrRmYvb3aI6GM
    UoZxfiqA8OqZje0YkwGV7QauDiqAm8yfnp1AOaBVPOE/2K1/wsbOGSolS75A45jASAGKYucxeBtj
    bGOmLR4ETMUxjk/xQusnPZdpaPM74SulYAK60gnORTKu6mqEmFDCDzUTFf6OfHUxRyjGRxid1iyP
    VOUoZ2agqzMuoZJfo2wPCbxMC7oHIYSGKa4CKJ6+B02DBo8zmJKwp4xO3mmxndr1xYhCmaMegi7v
    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
    G/J9Hdnz+Otv0SrJBAwgbrE'; */
		
			
			 if($action == 'complaintRequest')
		    {
				
             //echo '1';
			  $insert_sql = "Insert into `customerComplaint`(`title`,`description`,`customerId`,`vehicleId`,`complaintDate`, 
			   `approveStatus`)VALUES('$title','$description','$customerId','$vehicleId','$complaintDate', 'pending')";
				$result_sql = mysqli_query($con,$insert_sql);
				
			
		
			if($result_sql){
				//echo '2';
				$getData_sql ="SELECT * FROM `customerComplaint` WHERE customerId='$customerId' ORDER BY complaintId DESC LIMIT 1";
				$getData_res = mysqli_query($con, $getData_sql);
				
				if($getData_res){
					//echo '3';
				$row=mysqli_fetch_assoc($getData_res);
				
				$complaintId = $row['complaintId'];
		
			echo $complaintId; 
				$ImagePath = "uploads/".$complaintId.".png";
				$ServerURL = "http://115.98.3.215:90/sRamapptest/".$ImagePath;
				$InsertImg = mysqli_query($con,"UPDATE `customerComplaint` SET imagePath='$ServerURL' WHERE `complaintId` ='$complaintId'");
			//	if($InsertImg){
               // echo '4';
				file_put_contents($ImagePath,base64_decode($imageData));
			//		echo 'success';
					
			//	}
					$result['status'] = 'TRUE';
					$result['message']='Sucess';
					$result['complaintId']=$complaintId;
				
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



          
