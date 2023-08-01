<?php
$con = mysqli_connect("localhost", "root", "", "projektandroid");
$login=$_GET['login'];
$response = array();
if ($con)
{
    $sql = "select e.id, e.title, e.status, e.now, e.deadline_day, e.deadline_time, u.login from events e INNER JOIN users u ON e.user_id = u.id WHERE u.login = '$login' AND status = 0";
    $result = mysqli_query($con, $sql);
    if($result)
    {
        header("Content-Type: JSON");
        $i=0;
        while($row = mysqli_fetch_assoc($result))
        {
            $response[$i]['id'] = $row ['id'];
            $response[$i]['title'] = $row ['title'];
            $response[$i]['deadline_day'] = $row ['deadline_day'];
            $response[$i]['deadline_time'] = $row ['deadline_time'];
            $response[$i]['status'] = $row['status'];
            $response[$i]['now'] = $row['now'];
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