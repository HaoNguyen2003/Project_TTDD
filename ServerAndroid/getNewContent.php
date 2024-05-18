<?php
include "connectDB.php";
include "Content.php";
$current_url = "http://$_SERVER[HTTP_HOST]/ServerAndroid/";
$query = "SELECT * FROM content";
$data = mysqli_query($connect, $query);
$content=new Content();
while ($row = mysqli_fetch_assoc($data)){
    $content->ID=$row["ID"];
    $content->ImageURL=$current_url.$row["ImageURL"];
    $content->linkURL=$row["linkURL"];
}
echo json_encode($content);
?>