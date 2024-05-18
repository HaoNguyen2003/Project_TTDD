<?php
include "connectDB.php";
include "Account.php";
if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $Username = $_POST['Username'];
    $Password = $_POST['Password'];
    $Email = $_POST['Email'];
    $Query = "INSERT INTO 
        `account` (`Account_ID`, `Account_User`, `Account_Pass`, `Account_Email`, `Phone_Number`, `Created_at`, `Updated_at`, `Adress`, `Mission_ID`, `Access`)
        VALUES (NULL, '$Username', '$Password', '$Email', '0123456789', current_timestamp(), current_timestamp(), 'VietNam', '0', b'1')";

    $checkQuery="SELECT * FROM `account` WHERE Account_Email='$Email' OR Account_User='$Username'";
    $check_data=mysqli_query($connect,$checkQuery);
    if(mysqli_num_rows($check_data) > 0)
    {
        $response["message"] = "Email or Username already exists";
        echo json_encode($response);
        return;
    }
    $data = mysqli_query($connect, $Query);
    $response = array("message" => "Account created successfully");
    if ($data) {
        $response["message"] = "Account created successfully";
    } else {
        $response["message"] = "Account created Fail";
    }
} else {
    $response["message"] = "Invalid request";
}
echo json_encode($response);
?>
