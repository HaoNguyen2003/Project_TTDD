<?php
include "connectDB.php";
include "Content.php";
$current_url = "http://$_SERVER[HTTP_HOST]/ServerAndroid/";
$query = "SELECT * FROM content";
$data = mysqli_query($connect, $query);
$list_content = array();
while ($row = mysqli_fetch_assoc($data)){
    $content=new Content();
    $content->ID=$row["ID"];
    $content->ImageURL=$current_url.$row["ImageURL"];
    $content->linkURL=$row["linkURL"];
    array_push($list_content,$content);
}
echo json_encode($list_content);
?>