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
			
			$eId 		= $_REQUEST['empId'];
			
			 $sql1 = "SELECT e.empId as empId,e.name as name,e.email as email,e.mobileNumber as mobilenumber,
			  a.city as city,e.aadhar as aadhar,e.joiningDate as joiningDate,e.username as username,e.password as password,
			   a.currentAddress as currentaddress,a.permanentAddress as permanentaddress
			    FROM `employeeTable`e LEFT OUTER JOIN addressTable a on e.empId=a.userId where e.empId='$eId'";    
            $res = mysqli_query($con,$sql1);
            
              if($row = mysqli_fetch_assoc($res))
                 {
					 
									$temp['empid']=$row['empId'];
									$temp['name']=$row['name'];
									$temp['email']=$row['email'];
									$temp['mobilenumber']=$row['mobilenumber'];
									$temp['city']=$row['city'];
									$temp['aadhar']=$row['aadhar'];
									$temp['joiningdate']=$row['joiningDate'];
									$temp['username']=$row['username'];
									$temp['password']=$row['password'];
									$temp['currentaddress']=$row['currentaddress'];
									$temp['permanentaddress']=$row['permanentaddress'];
                   $data[]=$temp;
                            
                        }
                         $result['status'] = 'True';
            
                           $result['data'] = $data; 
                        
                              
     }
    header("Content-Type:application/json");
    echo json_encode($result);
   ?>


          
