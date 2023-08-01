<?php
require "Database.php";
$db = new Database();
if (isset($_POST['name']) && isset($_POST['amount']) && isset($_POST['status']) && isset($_POST['user_id']))
{
    if ($db->dbConnect())
    {
        if ($db->dodajDoListy("products", $_POST['name'], $_POST['amount'], $_POST['status'], $_POST['user_id'] ))
        {
            echo "Udalo sie dodac produkt do bazy danych";
        } else echo "Produkt lub liczba sa nieprawidlowe";
    } else echo "Nie mozna polaczyc sie z baza danych";
} else echo "Wypelnij wszystkie pola";
?>

