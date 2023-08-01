<?php
require "Database.php";
$db = new Database();
if (isset($_POST['email']) && isset($_POST['password']))
{
    if ($db->dbConnect())
    {
        if ($db->logIn("users", $_POST['email'], $_POST['password']))
        {
            echo "Udalo sie zalogowac";
        } else echo "Email lub haslo sa nieprawidlowe";
    } else echo "Nie mozna polaczyc sie z baza danych";
} else echo "Wypelnij wszystkie pola";
?>