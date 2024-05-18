<?php
include "connectDB.php";
if(isset($_GET['ID'])){
    $productID = $_GET['ID'];
    $query = "DELETE FROM `product` WHERE Product_ID=$productID";
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