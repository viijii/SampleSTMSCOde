<?php

session_start();

     $corp_code 		= $_REQUEST['corp_code'];
         // $corp_code 		= 'sram';
     $_SESSION["corp_code"] = $corp_code ;

	include "connect.php";
     
    header('Access-Control-Allow-Origin: *');
	header('Access-Control-Allow-Methods: POST, GET, OPTIONS');
	
	 if($_SERVER['REQUEST_METHOD']=="POST" || $_SERVER['REQUEST_METHOD'] == "GET")
        {
            // $mobilenumber = $_REQUEST['mobilenumber'];
            //  $mobilenumber="7095311956";
                  // $agent_id = "A13";
                   //$status="0";
                   
                   $eId 		= $_REQUEST['empId'];
                  // $eId 		= '14333';
                  
            $sql1 = "SELECT t1.*, COUNT(cc.vehicleId) AS noofservices FROM (SELECT vehicleDetails.*,  customerTable.name as customerName,customerTable.mobileNumber as mobileNumber FROM `vehicleDetails` vehicleDetails 
            left outer JOIN customerTable customerTable on  vehicleDetails.customerId=customerTable.customerId where vehicleDetails.vehicleId = '$eId') 
            AS t1 left JOIN customerComplaint AS cc ON cc.vehicleId= t1.vehicleId GROUP BY cc.vehicleId";    
            $res = mysqli_query($con,$sql1) or die('error');
          

             if($row = mysqli_fetch_assoc($res))
                 {

               // $r = 0;
									$temp['vehicleid']=$row['vehicleId'];									
							            $temp['customerid']=$row['customerId'];
										$temp['trackerid']=$row['trackerId'];
                                        $temp['trackerpassword']=$row['trackerPassword'];
                                        $temp['vehicletype']=$row['vehicleType'];                                      
							            $temp['purchasedate']=$row['purchaseDate'];
							            $temp['registrationnumber']=$row['registrationNumber'];
							            $temp['noofservices']=$row['noofservices'];
							            $temp['createdby']=$row['createdBy'];
							               $temp['customername']=$row['customerName'];
							               $temp['mobilenumber']=$row['mobileNumber'];
             
                   $data[]=$temp;
                   
                   
                    $result['status'] = 'True';
            
                      $result['data'] = $data; 
                            
                        }
                        else{
							
							$result['status'] = 'False';
            
							}
                      
                          
     }
     else{
				$result['status'] = 'fail';
				$result['message'] = 'requested metho wrong';
            
							}
                      
    header("Content-Type:application/json");
    echo json_encode($result);
   ?>
