<?php
include "SendEmail.php";
include "checkEmail.php";
if($_SERVER["REQUEST_METHOD"] == "POST")
{
    $Header = $_POST["Header"]; 
    $Content = $_POST["Content"]; 
    $ID = $_POST["ID"];
    $Email=getEmaiOrder($ID);
    sendEmail($Email,$Header,$Content,"Email Send");
}
?>