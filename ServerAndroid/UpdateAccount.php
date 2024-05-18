<?php
include "connectDB.php";
if ($_SERVER["REQUEST_METHOD"] == "PUT" && isset($_GET['id'])) {
    $data = json_decode(file_get_contents("php://input"), true);
    if (isset($data['Username']) && isset($data['Email']) && isset($data['Password']) && isset($data['Phonenumber']) && isset($data['Address'])) {
        $id = $_GET['id'];
        $username = $data['Username'];
        $email = $data['Email'];
        $password = $data['Password'];
        $phonenumber = $data['Phonenumber'];
        $address = $data['Address'];
        $query = "UPDATE `account` SET `Account_User`='$username', `Account_Email`='$email', `Account_Pass`='$password', `Phone_Number`='$phonenumber', `Adress`='$address' WHERE `Account_ID`=$id";
        $result = mysqli_query($connect, $query);
        if ($result) {
            echo json_encode(array("message" => "User information updated successfully"));
        } else {
            echo json_encode(array("message" => "Failed to update user information"));
        }
    } else {
        echo json_encode(array("message" => "Incomplete data"));
    }
} else {
    echo json_encode(array("message" => "Invalid request method or missing 'id' parameter"));
}
?>
