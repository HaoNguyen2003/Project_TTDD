<?php
include "connectDB.php";
if ($_SERVER["REQUEST_METHOD"] == "PUT" && isset($_GET['ProductID'])) {
    $id = $_GET['ProductID'];
    $Query="UPDATE `product` SET `Viewer`=Viewer+1 WHERE Product_ID=$id";
    $Data = mysqli_query($connect, $Query);
    if ($Data) {
        echo json_encode(array("message" => "User information updated successfully"));
    } else {
        echo json_encode(array("message" => "Failed to update user information"));
    }
}
 else {
    echo json_encode(array("message" => "Incomplete data"));
}
?>