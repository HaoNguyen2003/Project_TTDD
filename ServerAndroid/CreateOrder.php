<?php
include 'connectDB.php';
include 'proccess.php';
$response = array();
if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $order = json_decode(file_get_contents('php://input'), true);
    if (!empty($order)) {
        $accountID = $order['AccountID'];
        $customerName = $order['CustomerName'];
        $phoneNumber = $order['PhoneNumber'];
        $address = $order['Address'];
        $totalPrice = $order['totalPrice'];
        $URLImage=HandleTheURL($order['URLImage'],"http://$_SERVER[HTTP_HOST]/ServerAndroid/");
        $carts = $order['carts'];
        $sql = "INSERT INTO `orders`(`Account_ID`, `Customer_Name`, `Customer_Address`, `Customer_Number`, `Total`,`URLImage`) 
                VALUES ('$accountID', '$customerName', '$address', '$phoneNumber', '$totalPrice','$URLImage')";
        $result = mysqli_query($connect, $sql); 
        $order_id = mysqli_insert_id($connect);
        if ($result) {
            foreach ($carts as $cart) {
                $productID = $cart['productID'];
                $size = $cart['size'];
                $price_out = $cart['price_out'];
                $amount=$cart['amount'];
                $totalPrice=$cart['totalPrice'];
                $img= HandleTheURL($cart['img'],"http://$_SERVER[HTTP_HOST]/ServerAndroid/");
                $sql = "INSERT INTO `detailordes`(`Orders_ID`, `Product_ID`, `Quanlity`, `Prices`,`Total_Money`,`Size_Name`,`ImageURL`) 
                        VALUES ('$order_id', '$productID', '$amount', '$price_out','$totalPrice','$size','$img')";
                mysqli_query($connect, $sql);
            }
            $response["message"] = "Order created successfully";
        } else {
            $response["message"] = "Failed to create order";
        }
    } else {
        $response["message"] = "Invalid request";
    }
} else {
    $response["message"] = "Invalid request";
}
echo json_encode($response);
?>
