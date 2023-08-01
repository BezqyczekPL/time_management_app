<?php
include "DataBase.php";
$con = mysqli_connect("localhost", "root", "", "crewmate");
$uzytkownik_id=$_GET['uzytkownik_id'];
$email = $_GET['email'];

$qry="UPDATE users SET email = '$email' WHERE id='$uzytkownik_id'";
$pol = mysqli_query($con, $qry);

if (!$pol)
{
    echo "Email jest zajety";
}
else{
    echo "Zmieniles adres email";
}