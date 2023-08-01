<?php
require "Database.php";
$db = new Database();
if (isset($_POST['name_of_group']))
{
    if ($db->dbConnect())
    {
        if ($db->dodajGrupe("groups", $_POST['name_of_group']))
        {
            echo "Utworzyles grupe! Mozesz teraz nia zarzadzac. Aby dodac znajomych, podaj im nazwe grupy";
        } else echo "Cos co podales jest nieprawidlowe";
    } else echo "Nie mozna polaczyc sie z baza danych";
} else echo "Wypelnij wszystkie pola";
?>