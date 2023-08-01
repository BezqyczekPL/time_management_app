<?php
include "DataBase.php";
$con = mysqli_connect("localhost", "root", "", "crewmate");
$uzytkownik_id=$_GET['uzytkownik_id'];
$zdjecie = $_GET['zdjecie'];;

$qry="UPDATE users SET photo = '$zdjecie' WHERE id='$uzytkownik_id'";
$pol = mysqli_query($con, $qry);

if ($pol)
{
    echo "Zmieniles zdjecie profilowe";
}
else echo "Nie udalo sie";
