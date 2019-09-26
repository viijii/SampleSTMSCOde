<?php

  $corp_code 		= $_REQUEST['corp_code'];
 // $corp_code 		= 'sram';
   $_SESSION["corp_code"] = $corp_code;
   
  	include 'connect.php';
       
	header('Access-Control-Allow-Origin: *');
	header('Access-Control-Allow-Methods: POST, GET, OPTIONS');
	
	if($_SERVER['REQUEST_METHOD']=="POST" || $_SERVER['REQUEST_METHOD'] == "GET")
		{
                  
		   $action     = $_REQUEST['action'];
		   
		  //$action='get';
		  
		   
		   
		    if($action =='get')
		  {
			$sql="SELECT vd.*,ct.*,vm.vehicleType FROM  `vehicleDetails` AS vd join vehicleModels vm on vm.vehicleTypeId=vd.vehicleTypeId
			 INNER JOIN customerTable ct ON vd.customerId = ct.customerId WHERE ct.activeStatus = 'active'";
                  
                  
      

            $sql_res=mysqli_query($con,$sql);
				 
			
                   if(mysqli_num_rows($sql_res)>0)
                   {
					   
					    $r=0;
					   
					    while($fet=mysqli_fetch_assoc($sql_res)){
							
						$temp['customerName']       = $fet['name'];
					    $temp['mobile']       = $fet['mobileNumber'];
					    $temp['vehicleId']       = $fet['vehicleId'];
					    $temp['vehicleType']       = $fet['vehicleType'];
					    $temp['purchaseDate']       = $fet['purchaseDate'];
					    $temp['noOfService']       = $fet['noOfServices'];
					    $temp['nextServiceDue']       = $fet['nextServiceDue']; 
					 
							
							$data[$r]=$temp;
					     
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
	  
  }
	  else     
		{
			$result['status']='FALSE';
			$result['message']='Request method wrong!';
		}
     header("Content-Type:application/json"); 
     echo json_encode($result);
    ?>

		  
		  
