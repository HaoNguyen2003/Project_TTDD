<?php 
include "connectDB.php";
include "Product.php";
include "proccess.php";
$current_url = "http://$_SERVER[HTTP_HOST]/ServerAndroid/";
$query = "SELECT * FROM product";
$data = mysqli_query($connect, $query);
$list_product = array();
while ($row = mysqli_fetch_assoc($data)) {
    $product=new Product();
    $product->ProductID=$row["Product_ID"];
    $product->BrandID=$row["Brand_ID"];
    $product->CategoryID=$row["Category_ID"];
    $product->ProductName=$row["Product_Name"];
    $product->ProductTitle=$row["Title"];
    $product->Price=$row["Price"];
    $product->PriceIn=$row["Price_In"];
    $product->Description=$row["Description"];
    $product->Discount=$row["Discount"];
    $product->CreateAt=$row["Created_at"];
    $product->UpdateAt=$row["Updated_at"];
    $listURL =$row["Image_link"].$row["Image_list"];
    $product->listURL=HandleTheURLArray($listURL);
    $product->Viewer=$row["Viewer"];
    $product->Active=$row["Active"];
    array_push($list_product,$product);
}
echo json_encode($list_product);
?>