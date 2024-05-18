<?php
function HandleTheURLArray($chuoi) {
    $mang = explode("tinghow", $chuoi);
    $mang_cuoi = array();
    foreach ($mang as $gia_tri) {
        if (!empty($gia_tri)) {
            $mang_cuoi[] = "http://$_SERVER[HTTP_HOST]/ServerAndroid/" . $gia_tri;
        }
    }
    return $mang_cuoi;
}
function HandleTheURL($chuoi,$fill) {
    $mang = explode("$fill", $chuoi);
    $kq=$mang[1];
    return $kq;
}
function CountImageProduct($ID)
{
    include "connectDB.php";
    $Query = "SELECT CONCAT(Image_link, Image_list) AS concatenated_images 
              FROM product 
              WHERE Product_ID = '$ID';";
    $Data = mysqli_query($connect, $Query);
    $Count = 0;
    while ($row = mysqli_fetch_assoc($Data))
    {
        $array = HandleTheURLArray($row['concatenated_images']);
        $Count = count($array);
    }
    return $Count;
}
?>
