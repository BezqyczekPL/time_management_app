<?php
$con = mysqli_connect("localhost", "root", "", "crewmate");
$response = array();
$grupa_id = $_GET["grupa_id"];
if ($con)
{
    $sql = "select * from groups WHERE id = '$grupa_id'";
    $result = mysqli_query($con, $sql);
    if($result)
    {
        header("Content-Type: JSON");
        $i=0;
        while($row = mysqli_fetch_assoc($result))
        {
            $response[$i]['id'] = $row ['id'];
            $response[$i]['about'] = $row ['about'];
            $response[$i]['name_of_group'] = $row ['name_of_group'];
            $response[$i]['picture'] = $row ['picture'];
            $response[$i]['admin_id'] = $row ['admin_id'];
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





