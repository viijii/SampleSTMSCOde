<?php

session_start();

     $corp_code 		    = $_REQUEST['corp_code'];
     $corp_code 		    = 'sram';
     $_SESSION["corp_code"] = $corp_code ;

	include "connect.php";
     
    header('Access-Control-Allow-Origin: *');
	header('Access-Control-Allow-Methods: POST, GET, OPTIONS');
	
	 if($_SERVER['REQUEST_METHOD']=="POST" || $_SERVER['REQUEST_METHOD'] == "GET")
        {
			
			$sql1 = "SELECT * from feedbackQuestion ";    
            $res = mysqli_query($con,$sql1);
          
              while($row = mysqli_fetch_array($res))
                 {
					                $temp['question']=$row['question'];
					                $temp['qid']=$row['qid'];

																									
                             $data[]=$temp;
                            
                        }
                         $result['status'] = 'True';
            
                         $result['data'] = $data; 
                        
                              
     }
    header("Content-Type:application/json");
    echo json_encode($result);
   ?>






