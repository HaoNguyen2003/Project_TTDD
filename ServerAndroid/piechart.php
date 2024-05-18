<?php
include "connectDB.php";
include "bar.php";
$Query="SELECT 
category.Category_Name, 
COALESCE(SUM(detailordes.Quanlity), 0) AS Total_Products_Ordered
FROM 
category
LEFT JOIN 
product ON category.Category_ID = product.Category_ID
JOIN 
detailordes ON product.Product_ID = detailordes.Product_ID
JOIN 
orders ON detailordes.Orders_ID = orders.Orders_ID AND orders.checkOrder = 3
WHERE 
(orders.Orders_Day >= DATE_SUB(CURDATE(), INTERVAL 7 DAY) OR orders.Orders_Day IS NULL)
GROUP BY 
category.Category_Name;";
$data=mysqli_query($connect,$Query);
$list_item = array();
while ($row = mysqli_fetch_assoc($data)) {
    $ItemBar=new ItemBar();
    $ItemBar->ItemName=$row["Category_Name"];
    $ItemBar->amount=$row["Total_Products_Ordered"];
    array_push($list_item,$ItemBar);
}
echo json_encode($list_item);
?>