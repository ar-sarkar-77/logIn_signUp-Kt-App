<?php

$names = $_POST['name'];
$emails = $_POST['email'];
$passworda = $_POST['password'];
$imagea = $_POST['image'];
$keys = $_POST['key'];

$namea = decryptData($names);
$emaila = decryptData($emails);
$key = decryptData($keys);

if ($key == '266636@Ar'){
    
    $connect = mysqli_connect('localhost' , 'skwahjts_ar_sarkar' , 'xpbGKOF0NarbrngX' , 'skwahjts_ar_sarkar_db');
    
    $query22 = "SELECT * FROM data_table WHERE email LIKE '$emaila' ";
    $result22 = mysqli_query($connect , $query22);
    $rows = mysqli_num_rows($result22);

    if($rows<=0){

    $decodedImage = base64_decode($imagea);

    $fileName = time() . '_' . rand(1000, 100000) . '.jpg';
    $imagePath = "images/" . $fileName;
    
        if( file_put_contents( $imagePath, $decodedImage) ) {

    $query = "INSERT INTO data_table (name , email , password , image) VALUES ('$namea' , '$emaila' , '$passworda' , '$imagePath') ";
    $result = mysqli_query($connect , $query);
    
         if($result){
            echo "Upload Success";
         }else{
            echo "Upload Failed";
            }
        }else{
            echo "Failed";
        }
    }else{
        echo "Email already exists";
    }
    
    mysqli_close($connect);
    
}else{
    echo "Key not Match";
}

function decryptData($text){
    $decode = base64_decode($text);
    $decrypt = openssl_decrypt($decode , 'AES-128-ECB' , '7fQ@Lp2!vC9#rXen' , OPENSSL_RAW_DATA);
    return $decrypt;
}

?>