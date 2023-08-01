<?php
include "DataBase.php";
$con = mysqli_connect("localhost", "root", "", "crewmate");
$response = array();
$email = $_GET["email"];
$sql="SELECT group_id FROM users WHERE email = '$email'";
$result = mysqli_query($con, $sql);
if($result)
{
    header("Content-Type: JSON");
    $i=0;
    while($row = mysqli_fetch_assoc($result))
    {
        $response[$i]['group_id'] = $row ['group_id'];
        $i++;
    }
    echo json_encode($response, JSON_PRETTY_PRINT);
}

else
{
    echo "Nie połączono z bazą danych";
}