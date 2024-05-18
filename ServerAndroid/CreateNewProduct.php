<?php
include "connectDB.php";
include "respose.php";

if ($_SERVER['REQUEST_METHOD'] === 'POST'){
    $data = json_decode(file_get_contents("php://input"), true);
    

    $BrandID = mysqli_real_escape_string($connect, $data['BrandID']);
    $CategoryID = mysqli_real_escape_string($connect, $data['CategoryID']);
    $ProductName = mysqli_real_escape_string($connect, $data['ProductName']);
    $ProductTitle = mysqli_real_escape_string($connect, $data['ProductTitle']);
    $Price = mysqli_real_escape_string($connect, $data['Price']);
    $PriceIn = mysqli_real_escape_string($connect, $data['PriceIn']);
    $Description = mysqli_real_escape_string($connect, $data['Description']);
    $Discount = mysqli_real_escape_string($connect, $data['Discount']);
    

    $Query = "INSERT INTO `product`(`Category_ID`, `Brand_ID`, `Product_Name`, `Title`, `Price`, `Price_In`, `Description`, `Discount`) 
    VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    

    $stmt = mysqli_prepare($connect, $Query);
    

    mysqli_stmt_bind_param($stmt, "iissssss", $CategoryID, $BrandID, $ProductName, $ProductTitle, $Price, $PriceIn, $Description, $Discount);
    

    $result = mysqli_stmt_execute($stmt);
    

    $response = new response();
    
    if($result) {
        $id = mysqli_insert_id($connect);
        $response->message = "$id";
    } else {
        $response->message = "0";
    }
    
    echo json_encode($response);

    mysqli_stmt_close($stmt);
}
?>
