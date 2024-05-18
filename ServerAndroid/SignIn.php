<?php
include "connectDB.php";
include "Account.php";
$current_url = "http://$_SERVER[HTTP_HOST]/ServerAndroid/";
if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $Username = $_POST['Username'];
    $Password = $_POST['Password'];
    $Query = "SELECT * FROM `account` WHERE Account_User='$Username' 
    AND Account_Pass='$Password' AND Access=1";
    $data = mysqli_query($connect, $Query);
    $account=new Account();
    while ($row = mysqli_fetch_assoc($data)) {
        $account->AccountID=$row["Account_ID"];
        $account->Username=$row['Account_User'];
        $account->Password=$row['Account_Pass'];
        $account->Email=$row['Account_Email'];
        $account->Phonenumber=$row['Phone_Number'];
        $account->CreateAt=$row['Created_at'];
        $account->UpdateAt=$row['Updated_at'];
        $account->Address=$row['Adress'];
        $account->Access=$row['Access'];
        $account->MissionID=$row['Mission_ID'];
        $account->ImageURL=$current_url.$row['ImageURL'];
    }
    echo json_encode($account);
}
?>