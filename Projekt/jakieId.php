<?php
include "DataBase.php";
$con = mysqli_connect("localhost", "root", "", "projektandroid");
$response = array();
$login = $_GET["login"];
$sql="SELECT id FROM users WHERE login = '$login'";
$result = mysqli_query($con, $sql);
if($result)
{
    header("Content-Type: JSON");
    $i=0;
    while($row = mysqli_fetch_assoc($result))
    {
        $response[$i]['id'] = $row ['id'];
        $i++;
    }
    echo json_encode($response, JSON_PRETTY_PRINT);
}

else
{
    echo "Nie połączono z bazą danych";
}