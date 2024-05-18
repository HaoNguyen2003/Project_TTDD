<?php
include "respose.php";
$response=new response();
$response->message = "Product updated successfully";
echo json_encode($response);
?>
