<?php
include "connectDB.php";
include "Account.php";
if(isset($_GET['ID'])){
    $categoryID = $_GET['ID'];
    $query = "UPDATE `account` SET `Access`=0 WHERE Account_ID=$categoryID";
    $data = mysqli_query($connect, $query);
    $response = array("message" => "Account created successfully");
    if ($data) {
        $response["message"] = "block successfully";
    } else {
        $response["message"] = "block Fail";
    }
} else {
    $response["message"] = "Invalid request";
}
echo json_encode($response);
?>