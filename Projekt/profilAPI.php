<?php
$con = mysqli_connect("localhost", "root", "", "crewmate");
$response = array();
$uzytkownik_id = $_GET["id"];
if ($con)
{
    $sql = "select * from users WHERE id = '$uzytkownik_id'";
    $result = mysqli_query($con, $sql);
    if($result)
    {
        header("Content-Type: JSON");
        $i=0;
        while($row = mysqli_fetch_assoc($result))
        {
            $response[$i]['id'] = $row ['id'];
            $response[$i]['nick'] = $row ['nick'];
            $response[$i]['photo'] = $row ['photo'];
            $i++;
        }
        echo json_encode($response, JSON_PRETTY_PRINT);
    }
}
else
{
    echo "Nie połączono z bazą danych";
}

?>