<?php
include "connectDB.php";
include "Category.php";
$current_url = "http://$_SERVER[HTTP_HOST]/ServerAndroid/";
$query = "SELECT * FROM category";
$data = mysqli_query($connect, $query);
$list_category = array();
while ($row = mysqli_fetch_assoc($data)) {
    $category = new Category();
    $category->categoryID=$row['Category_ID'];
    $category->categoryName=$row['Category_Name'];
    $category->imageURL =$current_url.$row['Category_Image'];
    array_push($list_category, $category);
}
echo json_encode($list_category);
?>