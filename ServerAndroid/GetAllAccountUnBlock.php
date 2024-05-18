<?php
include "connectDB.php";
include "Account.php";
$current_url = "http://$_SERVER[HTTP_HOST]/ServerAndroid/";
$query = "SELECT * FROM `account` WHERE Access=1";
$data = mysqli_query($connect, $query);
$list_Account = array();
while ($row = mysqli_fetch_assoc($data)) {
    $account=new Account();
    $account->AccountID=$row['Account_ID'];
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
    array_push($list_Account, $account);
}
echo json_encode($list_Account);
?>