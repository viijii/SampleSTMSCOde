<?php

  $corp_code 		= $_REQUEST['corp_code'];
    $corp_code 		= 'sram';
  
   $_SESSION["corp_code"] = $corp_code;
  	include 'connect.php';
       
	header('Access-Control-Allow-Origin: *');
	header('Access-Control-Allow-Methods: POST, GET, OPTIONS');
	
	if($_SERVER['REQUEST_METHOD']=="POST" || $_SERVER['REQUEST_METHOD'] == "GET")
		{
                 
		   
		 
			$sql="SELECT `title`, `location`, `path` FROM `fieldActivity`"or die('error');
			 

			$sql_res=mysqli_query($con,$sql);
			
				
			
                   if(mysqli_num_rows($sql_res)>0)
                   {
					   $r = 0;
   
					    while($fet=mysqli_fetch_assoc($sql_res)){
							
						$temp['title']       = $fet['title'];
					    $temp['location']    = $fet['location'];
					    $temp['path']        = $fet['path'];
					   
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

		  
		  
