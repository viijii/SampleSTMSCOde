<?php

session_start();

     $corp_code 		    = $_REQUEST['corp_code'];
    // $corp_code 		    = 'sram';
     $_SESSION["corp_code"] = $corp_code ;

	include "connect.php";
     
    header('Access-Control-Allow-Origin: *');
	header('Access-Control-Allow-Methods: POST, GET, OPTIONS');
	
	 if($_SERVER['REQUEST_METHOD']=="POST" || $_SERVER['REQUEST_METHOD'] == "GET")
        {
			
			$cId 		    = $_REQUEST['empId'];
			
			//$cId 		    = '121';
			
			 $sql1 = "SELECT t1.*, IFNULL(vm.vehicleType, 'NA') AS vehicleType FROM (SELECT ct.name, ct.email, ct.mobileNumber,
			  ct.aadhar, ct.username, ct.password, IFNULL(at.city, 'NA') AS city, IFNULL(at.currentAddress, 'NA') AS currentAddress,
			   IFNULL(at.permanentAddress, 'NA') AS permanentAddress, IFNULL(vd.vehicleId, 'NA') AS vehicleId, 
			   IFNULL(vd.vehicleTypeId, 'NA') AS vehicleTypeId FROM customerTable AS ct LEFT JOIN addressTable AS at ON
			    at.userId = ct.customerId LEFT JOIN vehicleDetails AS vd ON vd.customerId = ct.customerId WHERE ct.customerId='$cId') AS t1
			     LEFT JOIN vehicleModels AS vm ON vm.vehicleTypeId= t1.vehicleTypeId";    
            $res = mysqli_query($con,$sql1);
           // echo "1";
              while($row = mysqli_fetch_array($res))
                 {
					                $temp['name']=$row['name'];
									$temp['email']=$row['email'];
									$temp['mobileNumber']=$row['mobileNumber'];
									$temp['aadhar']=$row['aadhar'];
									$temp['username']=$row['username'];
									$temp['password']=$row['password'];
									$temp['city']=$row['city'];
									$temp['currentAddress']=$row['currentAddress'];
									$temp['permanentAddress']=$row['permanentAddress'];
									$temp['vehicleId']=$row['vehicleId'];
									$temp['vehicleType']=$row['vehicleType'];
									
                             $data[]=$temp;
                            
                        }
                         $result['status'] = 'True';
            
                         $result['data'] = $data; 
                        
                              
     }
    header("Content-Type:application/json");
    echo json_encode($result);
   ?>






