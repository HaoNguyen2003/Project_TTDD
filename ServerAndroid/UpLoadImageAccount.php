<?php 
include "connectDB.php";
include "respose.php";
$response = new response();
if ($_SERVER['REQUEST_METHOD'] === 'POST' && isset($_FILES['file'])) {
   $target_dir = "IMGDATAAPP/AVATAR/";  
   $ID=$_GET["ID"];
   $target_file_name = $target_dir .$ID.".png"; 
   if(move_uploaded_file($_FILES["file"]["tmp_name"], $target_file_name)){ 
   $Query="UPDATE `account` SET `ImageURL`='$target_file_name' WHERE `Account_ID`='$ID'"; 
   $Data=mysqli_query($connect,$Query);
   if($Data)
   {
      $response->message = "Upload Avatar Success"; 
   }
   else{
      $response->message = "Connect DataBase Fail";
   }
   }
   else   
   {  
      $response->message = "Upload Avatar Fail";
   }  
}
   echo json_encode($response); 
?>  