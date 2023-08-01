<?php
include "DataBase.php";
$con = mysqli_connect("localhost", "root", "", "crewmate");
$uzytkownik_id=$_GET['uzytkownik_id'];
$nick = $_GET['nick'];;

$qry="UPDATE users SET nick = '$nick' WHERE id='$uzytkownik_id'";
$pol = mysqli_query($con, $qry);

if ($pol)
{
    echo "Zmieniles nick";
}
