<?php
include "connectDB.php";
include "Size.php";
if(isset($_GET['categoryID'])) {
    $categoryID = $_GET['categoryID'];
    $query = "
    SELECT size.Size_ID, size.Size_Name
    FROM size
    JOIN categorysize ON size.Size_ID = categorysize.Size_ID
    JOIN category ON categorysize.Category_ID = category.Category_ID
    WHERE category.Category_ID = $categoryID;";
   
    $data = mysqli_query($connect, $query);
    $list_size = array();
    while ($row = mysqli_fetch_assoc($data)) {
        $size=new Size();
        $size->SizeID=$row["Size_ID"];
        $size->SizeName=$row["Size_Name"];
        array_push($list_size,$size);
    }
    echo json_encode($list_size);
}
?>