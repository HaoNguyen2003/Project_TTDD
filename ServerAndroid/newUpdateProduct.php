<?php
include "connectDB.php";
include "respose.php";
header('Content-Type: application/json');
if ($_SERVER["REQUEST_METHOD"] == "POST" && isset($_GET['ID'])) {
    $productID = $_GET['ID'];
    $brandID = $_POST['BrandID'];
    $categoryID = $_POST['CategoryID'];
    $productName = $_POST['ProductName'];
    $productTitle = $_POST['ProductTitle'];
    $price = $_POST['Price'];
    $priceIn = $_POST['PriceIn'];
    $description = $_POST['Description'];
    $discount = $_POST['Discount'];

    $query = "UPDATE `product` SET `Category_ID`=?, `Brand_ID`=?, `Product_Name`=?,
              `Title`=?, `Price`=?, `Price_In`=?, `Description`=?, `Discount`=?,
              `Updated_at`=CURRENT_DATE WHERE `Product_ID`=?";
    $statement = $connect->prepare($query);
    $statement->bind_param("iisssdssi", $categoryID, $brandID, $productName,
                           $productTitle, $price, $priceIn, $description,
                           $discount, $productID);
    
    $result = $statement->execute();
    $response = new response();
    if ($result) {
        $response->message = "Product updated successfully";
    } else {
        $response->message = "Failed to update product";
    }
    echo json_encode($response);
}
?>
