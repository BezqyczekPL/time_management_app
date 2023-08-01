<?php
require "Database.php";
$db = new Database();
if (isset($_POST['title']) && isset($_POST['deadline_day']) && isset($_POST['deadline_time']) && isset($_POST['user_id']))
{
    if ($db->dbConnect())
    {
        if ($db->dodajDoTerminarza("events", $_POST['title'], $_POST['deadline_day'], $_POST['deadline_time'], $_POST['user_id']))
        {
            echo "Udalo sie dodac wydarzenie do bazy danych";
        } else echo "Cos co podales jest nieprawidlowe";
    } else echo "Nie mozna polaczyc sie z baza danych";
} else echo "Wypelnij wszystkie pola";
?>

