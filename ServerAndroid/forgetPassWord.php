<?php
include "SendEmail.php";
include "checkEmail.php";
include "respose.php";
$response = new response();
if($_SERVER["REQUEST_METHOD"] == "POST")
{
    $Email = $_POST["Email"]; 
    $PassWord = $_POST["PassWord"]; 
    $PassWordMD5 = $_POST["PassWordMD5"]; 
    $Header = $_POST["Header"]; 
    $Content = $_POST["Content"]; 
    $Footer = $_POST["Footer"]; 
    if(checkMail($Email)==1){
        $check=UpdatePass($Email,$PassWordMD5);
        if($check==1)
        {
            sendEmail($Email,$Header,$Content.": ".$PassWord,$Footer);
            $response->message = "Please Check Your Mail";
        }
        else{
            $response->message = "We can't Send Mail For You";
        }
    }
    else{
        $response->message = "Server of us have issue";
    }
}
else{
    $response->message = "Failed resetPassWord";
}
echo json_encode($response);
?>