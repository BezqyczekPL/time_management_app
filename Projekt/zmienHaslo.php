<?php
include "DataBase.php";
$con = mysqli_connect("localhost", "root", "", "crewmate");
$uzytkownik_id=$_GET['uzytkownik_id'];
$haslo = $_GET['haslo'];;
$haslo = mysqli_real_escape_string($con, $haslo);
$haslo = password_hash($haslo, PASSWORD_DEFAULT);

$qry="UPDATE users SET password = '$haslo' WHERE id='$uzytkownik_id'";
$pol = mysqli_query($con, $qry);

if ($pol)
{
    echo "Zmieniles haslo";
}
