<?php
include "connectDB.php";
include "bar.php";
$Query="SELECT 
brand.Brand_Name, 
COALESCE(SUM(detailordes.Quanlity), 0) AS Total_Products_Ordered
FROM 
brand
JOIN 
product ON brand.Brand_ID = product.Brand_ID
JOIN 
detailordes ON product.Product_ID = detailordes.Product_ID
JOIN 
orders ON detailordes.Orders_ID = orders.Orders_ID 
         AND orders.checkOrder = 3 
         AND orders.Orders_Day >= DATE_SUB(CURDATE(), INTERVAL 7 DAY)
GROUP BY 
brand.Brand_Name;";
$data=mysqli_query($connect,$Query);
$list_item = array();
while ($row = mysqli_fetch_assoc($data)) {
    $ItemBar=new ItemBar();
    $ItemBar->ItemName=$row["Brand_Name"];
    $ItemBar->amount=$row["Total_Products_Ordered"];
    array_push($list_item,$ItemBar);
}
echo json_encode($list_item);
?>