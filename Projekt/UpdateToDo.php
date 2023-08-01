<?php
include "DataBase.php";
$con = mysqli_connect("localhost", "root", "", "projektandroid");
$id=$_GET['id'];

$title = $_GET['title'];
$deadline_time = $_GET['deadline_time'];
$deadline_day = $_GET['deadline_day'];


$qry="UPDATE events SET title = '$title', deadline_time = '$deadline_time', deadline_day = '$deadline_day' WHERE id ='$id'";
mysqli_query($con, $qry);
