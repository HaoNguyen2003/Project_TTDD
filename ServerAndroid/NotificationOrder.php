<?php
include "SendEmail.php";
include "checkEmail.php";
if($_SERVER["REQUEST_METHOD"] == "POST")
{
    $Header = $_POST["Header"]; 
    $Content = $_POST["Content"]; 
    $Name = $_POST["Name"];
    $Email=getEmailAdmin();
    sendEmail($Email,$Header,$Content.$Name,"Email Send");
}
?>