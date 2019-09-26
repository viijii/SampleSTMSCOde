<?php
  	session_start();
$corp_code 		= $_REQUEST['corp_code'];

  //$corp_code 		= 'sram';


$_SESSION["corp_code"] = $corp_code;
  	
  	include 'connect.php';
       
	header('Access-Control-Allow-Origin: *');
	header('Access-Control-Allow-Methods: POST, GET, OPTIONS');
	
	if($_SERVER['REQUEST_METHOD']=="POST" || $_SERVER['REQUEST_METHOD'] == "GET")
		{
                  
		  $action     = $_REQUEST['action'];
		  $cId        =$_REQUEST['customerId'];
		  $vtype=$_REQUEST['vehType'];
		  
		 /*$action     = 'model';
		$vtype='tractor';*/
	  
		  if($action =='desig')
		  {
                  
           $sql="SELECT `designation` FROM `columnValues` where `designation`!='NA'";
           
           $sql_res=mysqli_query($con,$sql);
           
              if(mysqli_num_rows($sql_res)>0){
				  $r=0;
				  
				while($fet=mysqli_fetch_array($sql_res)){
					
				$temp['designation']=$fet['designation'];
				
				$data[$r] = $temp;
							
				$r++;
					
					}
					        $result['status'] = 'True';
						    $result['message'] = 'Success';	
						    $result['data'] = $data;
					
					
				  }	
		  }
		  
		  if($action =='team')
		  {
                  
           $sql="SELECT `team` FROM `columnValues`";
           
           $sql_res=mysqli_query($con,$sql);
           
              if(mysqli_num_rows($sql_res)>0){
				  $r=0;
				  
				while($fet=mysqli_fetch_array($sql_res)){
					
				$temp['empTeam']=$fet['team'];
				
				$data[$r] = $temp;
							
				$r++;
					
					}
					        $result['status'] = 'True';
						    $result['message'] = 'Success';	
						    $result['data'] = $data;
					
					
				  }	
		  }
		   if($action =='customer')
		  {
                  
           $sql="SELECT `name` FROM `customerTable`";
           
           $sql_res=mysqli_query($con,$sql);
           
              if(mysqli_num_rows($sql_res)>0){
				  $r=0;
				  
				while($fet=mysqli_fetch_array($sql_res)){
					
				$temp['cus']=$fet['name'];
				
				$data[$r] = $temp;
							
				$r++;
					
					}
					        $result['status'] = 'True';
						    $result['message'] = 'Success';	
						    $result['data'] = $data;
					
				  }	
				  else{
					  $result['status'] = 'false';
						    $result['message'] = 'no data';	
					  }
		  }
		   if($action =='vehicle')
		  {
                  
           $sql="SELECT DISTINCT vehicleType FROM `vehicleModels`";
           
           $sql_res=mysqli_query($con,$sql);
           
              if(mysqli_num_rows($sql_res)>0){
				  $r=0;
				  
				while($fet=mysqli_fetch_array($sql_res)){
					
				$temp['veh']=$fet['vehicleType'];
				
				$data[$r] = $temp;
							
				$r++;
					
					}
					        $result['status'] = 'True';
						    $result['message'] = 'Success';	
						    $result['data'] = $data;
					
				  }	
				  else{
					  $result['status'] = 'false';
						    $result['message'] = 'no data';	
					  }
		  }
		  if($action =='vehicleSpinner')
		  {
                  
           $sql="SELECT t1. *, IFNULL(vm.vehicletype, 'NA') AS vehicleType, IFNULL(vm.vehicleModel, 'NA') AS vehicleModel
            FROM (SELECT * FROM `vehicleDetails` where customerId='$cId') AS t1 LEFT JOIN vehicleModels AS vm ON
             vm.vehicleTypeId= t1.vehicleTypeId";
           
           $sql_res=mysqli_query($con,$sql);
           
              if(mysqli_num_rows($sql_res)>0){
				  $r=0;
				  
				while($fet=mysqli_fetch_array($sql_res)){
					
				$temp['vehicleType']=$fet['vehicleType'];
				$temp['vehicleId']=$fet['vehicleId'];
				
				$data[$r] = $temp;
							
				$r++;
					
					}
					        $result['status'] = 'True';
						    $result['message'] = 'Success';	
						    $result['data'] = $data;
					
				  }	
				  else{
					        $result['status'] = 'false';
						    $result['message'] = 'no data';	
					  }
		  }
		  
		  if($action =='empSpinner')
		  {
                  
           $sql="SELECT * FROM `employeeTable` where role <> 'admin' ";
           
           $sql_res=mysqli_query($con,$sql);
           
              if(mysqli_num_rows($sql_res)>0){
				  $r=0;
				  
				while($fet=mysqli_fetch_array($sql_res)){
					
				$temp['empId']=$fet['empId'];
				$temp['name']=$fet['name'];
				
				$data[$r] = $temp;
							
				$r++;
					
					}
					        $result['status'] = 'True';
						    $result['message'] = 'Success';	
						    $result['data'] = $data;
					
				  }	
				  else{
					        $result['status'] = 'false';
						    $result['message'] = 'no data';	
					  }
		  }
		  
		  
		  
		   if($action =='model')
		  {
                  
           $sql="SELECT * FROM `vehicleModels` where vehicleType='$vtype' ";
           
           $sql_res=mysqli_query($con,$sql);
           
              if(mysqli_num_rows($sql_res)>0){
				  $r=0;
				  
				while($fet=mysqli_fetch_array($sql_res)){
					
				$temp['vehModel']=$fet['vehicleModel'];
				
				$data[$r] = $temp;
							
				$r++;
					
					}
					        $result['status'] = 'True';
						    $result['message'] = 'Success';	
						    $result['data'] = $data;
					
				  }	
				  else{
					  $result['status'] = 'false';
						    $result['message'] = 'no data';	
					  }
		  }
		
				  
			
	  }else     
		{
			$result['status']='FALSE';
			$result['message']='Request method wrong!';
		}
     header("Content-Type:application/json"); 
     echo json_encode($result);
    ?>
