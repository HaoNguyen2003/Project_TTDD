<?php
function checkMail($email)
{
    include "connectDB.php";
    $check=0;
    $Query="SELECT count(*) as dem FROM `account` WHERE account.Account_Email='$email';";
    $Data = mysqli_query($connect, $Query);
    while($row = mysqli_fetch_assoc($Data))
    {
        $check=$row["dem"];
    }
    return $check;
}
function UpdatePass($email,$Pass)
{
    include "connectDB.php";
    $Query="UPDATE `account` SET `Account_Pass`='$Pass' WHERE `Account_Email`='$email'";
    $Data = mysqli_query($connect, $Query);
    if($Data)
    {
        return 1;
    }
    return 0;
}
function getEmailAdmin()
{
    include "connectDB.php";
    $Query="SELECT `Account_Email` FROM `account` WHERE Mission_ID=1";
    $Data = mysqli_query($connect, $Query);
    $Email="";
    while($row = mysqli_fetch_assoc($Data))
    {
        $Email=$row["Account_Email"];
    }
    return $Email;
}
function getEmaiOrder($ID)
{
    include "connectDB.php";
    $Query="SELECT account.Account_Email
    FROM orders
    JOIN account ON orders.Account_ID=account.Account_ID 
    WHERE orders.Orders_ID=$ID
    LIMIT 1;";
    $Data = mysqli_query($connect, $Query);
    $Email="";
    while($row = mysqli_fetch_assoc($Data))
    {
        $Email=$row["Account_Email"];
    }
    return $Email;
}
?>