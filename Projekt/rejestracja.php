<?php
require "Database.php";
$db = new Database();
if (isset($_POST['email']) && isset($_POST['password']) && isset($_POST['login']) && isset($_POST['name']) && isset($_POST['surname']) && isset($_POST['date_of_birth']) )
{
    if ($db->dbConnect())
    {
        if ($db->signUp("users",  $_POST['email'], $_POST['login'], $_POST['password'], $_POST['name'], $_POST['surname'], $_POST['date_of_birth']))
            {
                echo "Udalo sie zarejestrowac!";
            }
        else if ($db->checkEmail($_POST['email']) || $db->checkLogin($_POST['login']))
                echo "Email lub login są zajete";
        else echo "Nie udalo sie zarejestrowac";
        } else echo "Nie udalo sie polaczyc z baza danych";
    } else echo "Wszystkie pola są wymagane";
?>