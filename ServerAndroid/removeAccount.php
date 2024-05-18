<?php
include "connectDB.php";
include "Account.php";
$current_url = "http://$_SERVER[HTTP_HOST]/ServerAndroid/";
if(isset($_GET['ID'])){
    $categoryID = $_GET['ID'];
    $query = "DELETE FROM `account` WHERE Account_ID=$categoryID";
    $data = mysqli_query($connect, $query);
    $response = array("message" => "Account created successfully");
    if ($data) {
        $response["message"] = "Remove successfully";
    } else {
        $response["message"] = "Remove Fail";
    }
} else {
    $response["message"] = "Invalid request";
}
echo json_encode($response);
?>