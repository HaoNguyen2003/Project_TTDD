<?php
include "connectDB.php";
include "respose.php";
include "proccess.php";
$Query="SELECT * FROM `product` WHERE 1";
$Data=mysqli_query($connect,$Query);
if ($_SERVER['REQUEST_METHOD'] === 'POST' && isset($_FILES['file'])) {
   $target_dir = "IMGDATAAPP/PRODUCT/";  
   $ID=$_GET["ID"];
   $Quanlity=CountImageProduct($ID);
   $target_file_name;
   $Query="";
   if($Quanlity==0)
   {
      $target_file_name = $target_dir ."Image".$ID.".png"; 
      $stringPath= "tinghow/".$target_file_name;
      $Query="UPDATE `product` SET `Image_link`='$stringPath' WHERE `Product_ID`='$ID'"; 
   }
   else
   {
      $index=$Quanlity-1;
      $target_file_name = $target_dir ."Image".$ID."_".$index.".png";
      $stringPath= "tinghow/".$target_file_name;
      $Query="UPDATE product 
      SET Image_list = CONCAT(Image_list, '$stringPath') 
      WHERE Product_ID = $ID;"; 
   }
   if(move_uploaded_file($_FILES["file"]["tmp_name"], $target_file_name)){ 
   $Data=mysqli_query($connect,$Query);
}
}
?>  
?>