<?php

session_start();

      $corp_code 		= $_REQUEST['corp_code'];
    // $corp_code        ='sram';
      $_SESSION["corp_code"] = $corp_code; 
         
     include "connect.php";

if($_SERVER['REQUEST_METHOD']=="POST" || $_SERVER['REQUEST_METHOD'] == "GET")
		{
		
			date_default_timezone_set('Asia/Kolkata'); 
			
			$feedbacktime= date("Y-m-d H:i:s");					
		    $action=$_REQUEST['action'];
		    $taskId=$_REQUEST['taskid'];
			$rating= $_REQUEST['rating'];
		    $comments= $_REQUEST['comments'];
		    
		    $option1=$_REQUEST['option1'];
		    $option2=$_REQUEST['option2'];
		    $option3=$_REQUEST['option3'];
		    $id1=$_REQUEST['id1'];
		    $id2=$_REQUEST['id2'];
		    $id3=$_REQUEST['id3'];
		    
		    
		$qid= array($option1,$option2,$option3); 
		$round = count($qid); 
		$qid1= array($id1,$id2,$id3); 
		
			if($action == 'feedback'){
			
			   $sql = "Insert into `feedbackTable`(`taskId`,`description`,`rating`,`feedbackDate`) 
				 VALUES ('$taskId','$comments','$rating','$feedbacktime')" ;
				$res = mysqli_query($con,$sql);
				
				$sql1="select taskId from  feedbackTable where taskId='$taskId'";
			    $res1 = mysqli_query($con,$sql1);
			    $fet=mysqli_fetch_assoc($res1);
			    $taskId=$fet['taskId'];
				
				if($res){
											
				$result['status'] = 'TRUE';
				$result['message']='Sucess';
			
			}
			
			else{
				
				$result['status'] = 'False';
				$result['message']='Fail';
				
				}
			} 
			
		 if($action == 'feedback'){
			
				
                 for($n = 0; $n < $round; $n++){  
					  $sql = "Insert into `feedbackOption`(`feedbackId`,`option`,`qid`) 
				 VALUES ('$taskId','$qid[$n]','$qid1[$n]')" ;
				$res = mysqli_query($con,$sql);
				if($res){
											
				$result['status'] = 'TRUE';
				$result['message']='Sucess';
			}
		}
		
	}  
				
		else if($action == 'gettingfeedback'){
				
				$sql_get = "SELECT ft.*, IFNULL(qid, 'NA') AS qid, IFNULL(option, 'NA') 
				           AS option FROM `feedbackTable` AS ft INNER JOIN feedbackOption 
				           AS fo ON ft.taskId= fo.feedbackId where taskId='$taskId'";    
            $res = mysqli_query($con,$sql_get);
          
              while($row = mysqli_fetch_array($res))
                 {
					                $temp['taskId']=$row['taskId'];
					                $temp['qid']=$row['qid'];
					                $temp['option']=$row['option'];
					                $temp['rating']=$row['rating'];
					                $temp['feedbackDate']=$row['feedbackDate'];
					                $temp['feedbackId']=$row['feedbackId'];
					                $temp['description']=$row['description'];
					                   

																									
                             $data[]=$temp;
                            
                        }
                         $result['status'] = 'True';
                         $result['message']='success';
                         $result['data'] = $data; 
				}
				
				
				
			}
			else{
				
				$result['status'] = 'False2';
				$result['message']='Fail2';
			}
			
    header("Content-Type:application/json");
    echo json_encode($result);
   ?>


          
	
