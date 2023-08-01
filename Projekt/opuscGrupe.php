<?php
include "DataBase.php";
$con = mysqli_connect("localhost", "root", "", "crewmate");
$grupa_id=$_GET['grupa_id'];
$profil_id=$_GET['profil_id'];

$qry="UPDATE users SET group_id = NULL WHERE id ='$profil_id'";
$pol = mysqli_query($con, $qry);

if ($pol)
{
    echo "Opusciles grupe";
}