<?php

  $corp_code 		= $_REQUEST['corp_code'];
   // $corp_code 		= 'sram';
  
   $_SESSION["corp_code"] = $corp_code;
  	include 'connect.php';
       
	header('Access-Control-Allow-Origin: *');
	header('Access-Control-Allow-Methods: POST, GET, OPTIONS');
	
	if($_SERVER['REQUEST_METHOD']=="POST" || $_SERVER['REQUEST_METHOD'] == "GET")
		{
                 
		   
		 
			$sql="SELECT ct.`customerId`, `name`, `mobileNumber`, at.city, at.currentAddress,COUNT(vd.vehicleId) AS noofvehicles FROM `customerTable` AS ct 
			 INNER JOIN addressTable AS at ON at.userId=ct.customerId LEFT JOIN vehicleDetails AS vd ON vd.customerId= ct.customerId WHERE
			  ct.role='customer' GROUP BY ct.customerId"or die('error');
			 

			$sql_res=mysqli_query($con,$sql);
			
				
			
                   if(mysqli_num_rows($sql_res)>0)
                   {
					   $r = 0;
   
					    while($fet=mysqli_fetch_assoc($sql_res)){
							
						$temp['customerName']       = $fet['name'];
					    $temp['mobile']       = $fet['mobileNumber'];
					    $temp['address']       = $fet['city'];
					    $temp['noOfVeh']       = $fet['noofvehicles'];
					    $temp['customerId']       = $fet['customerId'];
					    $temp['currentAddress']       = $fet['currentAddress'];
					    
					    
					    $data[$r]= $temp;
					    $r++;
							
						}
					   
					  $result['status']='true';
					  $result['message']='success';
					  $result['data']=$data;
			
			  
			  
		  }
		  else{
			  $result['status']='fail';
			 $result['message']='failure';
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

		  
		  
