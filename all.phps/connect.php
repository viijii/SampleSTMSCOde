<?php


/*
$db_host = 'qptmscom.globatmysql.com';
$db_user = 'cadrac';
$db_password = 'Novi1234'; 
$db_name = 'qptmstesting';

mysql_connect($db_host, $db_user, $db_password);

$con = mysql_select_db($db_name)or die("nothing");


//$con=mysql_connect($db_host,$db_user,$db_password,$db_name) or die("none");

*/



$db_host = '192.168.0.150';
$db_user = 'sram';
$db_password = 'sram@123'; 

$db_name =$_SESSION['corp_code'];

//$db_name = 'sram';

$con=mysqli_connect($db_host, $db_user, $db_password,$db_name) or die("error");

/*if($con){
	echo "sucess";
	}
	else{
		echo "fail";
		}*/

//$con = mysqli_select_db($con,$db_name);



?>
