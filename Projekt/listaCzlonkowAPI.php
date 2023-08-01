<?php
$con = mysqli_connect("localhost", "root", "", "crewmate");
$response = array();
$grupa_id = $_GET["grupa_id"];
if ($con)
{
    $sql = "select * from users WHERE group_id = '$grupa_id'";
    $result = mysqli_query($con, $sql);
    if($result)
    {
        header("Content-Type: JSON");
        $i=0;
        while($row = mysqli_fetch_assoc($result))
        {
            $response[$i]['id'] = $row ['id'];
            $response[$i]['name'] = $row ['name'];
            $response[$i]['surname'] = $row ['surname'];
            $response[$i]['group_id'] = $row ['group_id'];

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