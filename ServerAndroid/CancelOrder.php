<?php
include "connectDB.php";
if(isset($_GET['OrderID'])) {
    $OrderID=$_GET['OrderID'];
    $Query="UPDATE `orders` SET checkOrder=2 WHERE Orders_ID=$OrderID";
    $Data = mysqli_query($connect, $Query);
    if ($Data) {
        echo json_encode(array("message" => "complete Cancel"));
    } else {
        echo json_encode(array("message" => "Failed Cancel"));
    }
}
 else {
    echo json_encode(array("message" => "Incomplete Cancel"));
}
?>