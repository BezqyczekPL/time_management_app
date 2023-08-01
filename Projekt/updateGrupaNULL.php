<?php
include "DataBase.php";
$con = mysqli_connect("localhost", "root", "", "crewmate");
$profil_id=$_GET['profil_id'];

$qry="UPDATE users SET group_id = NULL WHERE id ='$profil_id'";
mysqli_query($con, $qry);
