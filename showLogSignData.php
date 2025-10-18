<?php

$json = file_get_contents('php://input');
$data = json_decode($json , true);

$key = $data['key'];
$email = $data['email'];
$dec_key = decryptData($key);

if($dec_key=='266636@Ar'){
    $temp = array();

    $connect = mysqli_connect('localhost' , 'skwahjts_ar_sarkar' , 'xpbGKOF0NarbrngX' , 'skwahjts_ar_sarkar_db');
    
    $query22 = "SELECT * FROM data_table WHERE email = '$email' ";
    $result22 = mysqli_query($connect , $query22);

    while($rows = mysqli_fetch_assoc($result22)){
        $namess = $rows['name'];
        $emailss = $rows['email'];
        $imagess = $rows['image'];

        $temp['name'] =$namess;
        $temp['email'] =$emailss;
        $temp['image'] ='https://arsarkar.xyz/Apps/'.$imagess;
    }
    
    echo json_encode($temp);

}


function decryptData($text){
    $decode = base64_decode($text);
    $decrypt = openssl_decrypt($decode , 'AES-128-ECB' , '7fQ@Lp2!vC9#rXen' , OPENSSL_RAW_DATA);
    return $decrypt;
}

?>