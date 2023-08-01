<?php
$con = mysqli_connect("localhost", "root", "", "projektandroid");
$response = array();
$login = $_GET["login"];
$date = $_GET["date"];
if ($con)
{
    $sql = "select t.id, t.name, t.start_time, t.end_time, t.time, t.date, t.status, u.login from timetable t INNER JOIN users u ON t.user_id = u.id WHERE t.date = '$date' AND u.login = '$login' ORDER BY t.start_time";
    $result = mysqli_query($con, $sql);
    if($result)
    {
        header("Content-Type: JSON");
        $i=0;
        while($row = mysqli_fetch_assoc($result))
        {
            $response[$i]['id'] = $row ['id'];
            $response[$i]['name'] = $row ['name'];
            $response[$i]['start_time'] = $row ['start_time'];
            $response[$i]['end_time'] = $row ['end_time'];
            $response[$i]['time'] = $row ['time'];
            $response[$i]['date'] = $row ['date'];
            $response[$i]['status'] = $row ['status'];
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