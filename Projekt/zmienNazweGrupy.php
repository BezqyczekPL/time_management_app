<?php
include "DataBase.php";
$con = mysqli_connect("localhost", "root", "", "crewmate");
$grupa_id=$_GET['grupa_id'];
$nazwa = $_GET['nazwa'];

$qry="UPDATE groups SET name_of_group = '$nazwa' WHERE id='$grupa_id'";
$pol = mysqli_query($con, $qry);

if ($pol)
{
    echo "Zmieniles nazwe grupy";
}
else echo "Nazwa grupa jest juz zajeta";
