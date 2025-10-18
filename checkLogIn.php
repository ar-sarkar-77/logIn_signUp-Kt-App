<?php

$key = $_POST['key'];
$email = $_POST['email'];
$password = $_POST['password'];

$dec_key = decryptData($key);
$dec_email = decryptData($email);

if($dec_key=='266636@Ar'){
    
    $connect = mysqli_connect('localhost' , 'skwahjts_ar_sarkar' , 'xpbGKOF0NarbrngX' , 'skwahjts_ar_sarkar_db');
    
    $query22 = "SELECT * FROM data_table WHERE email = '$dec_email' AND password = '$password' ";
    $result22 = mysqli_query($connect , $query22);
    $rows = mysqli_num_rows($result22);
    
    if($rows>0){
        echo "Valid User";
    }else{
        echo "Wrong password";
    }

}else{
    echo "Key Not match";
}


function decryptData($text){
    $decode = base64_decode($text);
    $decrypt = openssl_decrypt($decode , 'AES-128-ECB' , '7fQ@Lp2!vC9#rXen' , OPENSSL_RAW_DATA);
    return $decrypt;
}

?>