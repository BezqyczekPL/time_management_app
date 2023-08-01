<?php
include "DataBase.php";
$con = mysqli_connect("localhost", "root", "", "crewmate");
$response = array();
$nazwa_grupy = $_GET["nazwa_grupy"];
$sql="SELECT id FROM groups WHERE name_of_group = '$nazwa_grupy'";
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