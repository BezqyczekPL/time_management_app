<?php
$con = mysqli_connect("localhost", "root", "", "projektandroid");
$response = array();
$login = $_GET["login"];
if ($con)
{
    $sql = "select p.id, p.name, p.amount, p.status, p.user_id, u.login from products p INNER JOIN users u ON p.user_id = u.id WHERE status = 0 AND u.login = '$login'";
    $result = mysqli_query($con, $sql);
    if($result)
    {
        header("Content-Type: JSON");
        $i=0;
        while($row = mysqli_fetch_assoc($result))
        {
            $response[$i]['id'] = $row ['id'];
            $response[$i]['name'] = $row ['name'];
            $response[$i]['amount'] = $row ['amount'];
            $response[$i]['status'] = $row ['status'];
            $response[$i]['user_id'] = $row ['user_id'];
            $response[$i]['login'] = $row ['login'];

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
