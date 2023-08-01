<?php
include "DataBase.php";
$con = mysqli_connect("localhost", "root", "", "crewmate");
$grupa_id=$_GET['grupa_id'];
$opis = $_GET['opis'];;

$qry="UPDATE groups SET about = '$opis' WHERE id='$grupa_id'";
$pol = mysqli_query($con, $qry);

if ($pol)
{
    echo "Zmieniles opis grupy";
}

