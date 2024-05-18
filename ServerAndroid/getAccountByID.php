<?php
include "connectDB.php";
include "Account.php";
if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $AccountID = $_POST['AccountID'];
    $Query = "SELECT * FROM `account` WHERE Account_ID=$AccountID";
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
        $account->ImageURL=$row['ImageURL'];
    }
    echo json_encode($account);
}
?>