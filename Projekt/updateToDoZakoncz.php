<?php
include "DataBase.php";
$con = mysqli_connect("localhost", "root", "", "projektandroid");
$id=$_GET['id'];

$qry="UPDATE events SET status = '1' WHERE id='$id'";
mysqli_query($con, $qry);