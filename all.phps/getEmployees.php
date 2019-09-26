<?php
      $corp_code 		= $_REQUEST['corp_code'];
     $corp_code        ='sram';
      $_SESSION["corp_code"] = $corp_code;    
     include "connect.php";



if($_SERVER['REQUEST_METHOD']=="POST" || $_SERVER['REQUEST_METHOD'] == "GET")
		{
			date_default_timezone_set('Asia/Kolkata'); 
			$time= date("Y-m-d H:i:s");
			
			/*$sql="SELECT e.*,IFNULL(d.userId, 'NA') AS userId ,IFNULL(d.city, 'NA') AS city,IFNULL(d.currentAddress, 'NA') 
			      AS currentAddress,IFNULL(d.permanentAddress, 'NA') AS permanentAddress 
			      FROM `employeeTable`e   left outer join addressTable d on d.userId=e.empId 
			      left  OUTER join addressTable a on e.role=a.role order by empId asc";*/
			      
			      $sql="SELECT e.*,IFNULL(d.userId, 'NA') AS userId ,IFNULL(d.city, 'NA') AS city,IFNULL(d.currentAddress, 'NA')
			       AS currentAddress,IFNULL(d.permanentAddress, 'NA') AS permanentAddress FROM `employeeTable`e left outer join 
			       addressTable d on d.userId=e.empId left OUTER join addressTable a on e.role=a.role where activeStatus='active' 
			       group by empId";
			
			$sql_res=mysqli_query($con,$sql) or die('error');
			
			$r=0;
			
			while($fet=mysqli_fetch_array($sql_res))
			{
			$temp['id']=$fet['empId'];
			$temp['username']=$fet['username'];	
			$temp['full_name']=$fet['name'];
			$temp['email']=$fet['email'];
			$temp['mobile']=$fet['mobileNumber'];
			$temp['user_type']=$fet['role'];
            $temp['city']=$fet['city'];
           
			
			
				$data[$r]=$temp;
				$r++;
				
				
				
				
				}
			
						
						$response['status'] = 'True';
						$response['message'] = 'Success';	
						$response['data'] = $data;
			
			
			}
			
			else     
		{
			$response['status']='FALSE';
			$response['message']='Request method wrong!';
		}
		

		header("Content-Type:application/json");
		echo json_encode($response);	

?>
