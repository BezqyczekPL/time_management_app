<?php
include "DataBase.php";
$con = mysqli_connect("localhost", "root", "", "crewmate");

$encodedImage = $_POST['EN_IMAGE'];
$profil_id = $_POST['id'];
$imageTitle = "myImage";
$imageLocation = "img/$imageTitle$profil_id.jpg";

$sqlQuery = "UPDATE users SET photo = '$imageLocation' WHERE id = '$profil_id'";

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