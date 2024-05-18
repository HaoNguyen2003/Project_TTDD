<?php
include "connectDB.php";
include "Brand.php";
$current_url = "http://$_SERVER[HTTP_HOST]/ServerAndroid/";
$query = "SELECT * FROM brand";
$data = mysqli_query($connect, $query);

$list_brand = array();

while ($row = mysqli_fetch_assoc($data)) {
    $brand = new Brand();
    $brand->brandID=$row['Brand_ID'];
    $brand->brandName=$row['Brand_Name'];
    $brand->imageURL = $current_url.$row['Brand_Image'];
    array_push($list_brand, $brand);
}
echo json_encode($list_brand);

?>
