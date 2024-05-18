<?php
include "connectDB.php";
include "Order.php";
$current_url = "http://$_SERVER[HTTP_HOST]/ServerAndroid/";
$query = "SELECT `Orders_ID`, `Account_ID`, `Customer_Name`, `Customer_Address`, `Customer_Number`, `Orders_Day`, `Total`,`checkOrder`,`URLImage` FROM `orders` WHERE checkOrder=2";
$data = mysqli_query($connect, $query);
$list_order = array();
while ($row = mysqli_fetch_assoc($data)) {
    $order = new Order();
    $order->OrderID=$row['Orders_ID'];
    $order->AccountID=$row['Account_ID'];
    $order->CustomerName=$row['Customer_Name'];
    $order->Address=$row['Customer_Address'];
    $order->PhoneNumber=$row['Customer_Number'];
    $order->totalPrice=$row['Total'];
    $order->CreateDay=$row['Orders_Day'];
    $order->checkOrder=$row['checkOrder'];
    $order->URLImage=$current_url.$row['URLImage'];
    array_push($list_order, $order);
}
echo json_encode($list_order);
?>