<?php
include "DataBase.php";
$con = mysqli_connect("localhost", "root", "", "crewmate");

$encodedImage = $_POST['EN_IMAGE'];
$grupa_id = $_POST['id'];
$imageTitle = "GroupImage";
$imageLocation = "img/$imageTitle$grupa_id.jpg";

$sqlQuery = "UPDATE groups SET picture = '$imageLocation' WHERE id = '$grupa_id'";

if(mysqli_query($con, $sqlQuery)){

    file_put_contents($imageLocation, base64_decode($encodedImage));

    $result["status"] = TRUE;
    $result["remarks"] = "Image Uploaded Successfully";

}else{

    $result["status"] = FALSE;
    $result["remarks"] = "Image Uploading Failed";

}

mysqli_close($con);

print(json_encode($result));

?>