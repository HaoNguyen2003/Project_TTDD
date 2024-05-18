<?php
include "connectDB.php";
include "DetailOrder.php";
$current_url = "http://$_SERVER[HTTP_HOST]/ServerAndroid/";
if(isset($_GET['OrderID'])) {
    $OrderID=$_GET['OrderID'];
    $Query="SELECT D.DetailOrder_ID,D.Product_ID,P.Product_Name,P.Title,D.Quanlity,D.Prices,D.Total_Money,D.Size_Name,D.ImageURL
    FROM detailordes AS D
    JOIN product AS P ON D.Product_ID=P.Product_ID WHERE D.Orders_ID=$OrderID";
    $data=mysqli_query($connect,$Query);
    $list_detail=array();
    while($row = mysqli_fetch_assoc($data))
    {
        $detail=new detailOrder();
        $detail->OrderID=$OrderID;
        $detail->id=$row['DetailOrder_ID'];
        $detail->productID=$row['Product_ID'];
        $detail->amount=$row['Quanlity'];
        $detail->price_out=$row['Prices'];
        $detail->totalPrice=$row['Total_Money'];
        $detail->productName=$row['Product_Name'];
        $detail->ProductTitle=$row['Title'];
        $detail->size=$row['Size_Name'];
        $detail->img=$current_url.$row['ImageURL'];
        array_push($list_detail,$detail);
    }
    echo json_encode($list_detail);
}
?>