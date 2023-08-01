<?php
require "Database.php";
$db = new Database();
if (isset($_POST['name']) && isset($_POST['time']) && isset($_POST['start_time']) && isset($_POST['date']) && isset($_POST['user_id']) && isset($_POST['end_time']))
{
    if ($db->dbConnect())
    {
        if ($db->dodajDoTerminarzyka("timetable", $_POST['name'], $_POST['time'], $_POST['start_time'], $_POST['date'], $_POST['user_id'], $_POST['end_time'] ))
        {
            echo "Udalo sie dodac wydarzenie do bazy danych";
        } else echo "Cos co podales jest nieprawidlowe";
    } else echo "Nie mozna polaczyc sie z baza danych";
} else echo "Wypelnij wszystkie pola";
?>

