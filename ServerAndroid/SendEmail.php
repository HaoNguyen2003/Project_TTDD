<?php
use PHPMailer\PHPMailer\PHPMailer;
use PHPMailer\PHPMailer\Exception;

require 'PHPMailer/src/Exception.php';
require 'PHPMailer/src/PHPMailer.php';
require 'PHPMailer/src/SMTP.php';
function sendEmail($to, $subject, $body,$Footer) {
    $mail = new PHPMailer(true);
    try {
        $mail->isSMTP();                                            
        $mail->Host       = 'smtp.gmail.com';                    
        $mail->SMTPAuth   = true;                                  
        $mail->Username   = 'email';                     
        $mail->Password   = 'key';                             
        $mail->SMTPSecure = 'ssl';          
        $mail->Port       = 465; 

        $mail->setFrom('tinghow1234@gmail.com');
        $mail->addAddress($to);

        $mail->isHTML(true);
        $mail->Subject = $subject;
        $mail->Body    = $body;
        $mail->AltBody = $Footer;

        $mail->send();
        return 1;
    } catch (Exception $e) {
        return 0;
    }
}
?>
