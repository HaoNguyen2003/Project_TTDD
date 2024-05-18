<?php
include "connectDB.php";
if(isset($_GET['OrderID'])) {
    $OrderID=$_GET['OrderID'];
    $Query="UPDATE `orders` SET checkOrder=3 WHERE Orders_ID=$OrderID";
    $Data = mysqli_query($connect, $Query);
    if ($Data) {
        echo json_encode(array("message" => "complete Delivered"));
    } else {
        echo json_encode(array("message" => "Failed Delivered"));
    }
}
 else {
    echo json_encode(array("message" => "Incomplete Delivered"));
}
?>