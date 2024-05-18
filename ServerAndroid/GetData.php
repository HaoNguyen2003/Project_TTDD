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
    $category->imageURL =$row['Category_Image'];
    echo "INSERT INTO table_name (column1, column2, column3, ...)
    VALUES (value1, value2, value3, ...);"
}
?>