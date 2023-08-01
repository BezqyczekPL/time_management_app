<?php
include "DataBase.php";
$con = mysqli_connect("localhost", "root", "", "crewmate");
$id=$_GET['id'];
$profil_id=$_GET['profil_id'];

$qry="UPDATE groups SET admin_id = '$profil_id' WHERE id ='$id'";
mysqli_query($con, $qry);
