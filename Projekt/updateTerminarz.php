<?php
include "DataBase.php";
$con = mysqli_connect("localhost", "root", "", "projektandroid");
$id=$_GET['id'];

$name = $_GET['name'];
$time = $_GET['time'];
$start_time = $_GET['start_time'];
$end_time = $_GET['end_time'];


$qry="UPDATE timetable SET name = '$name', time = '$time', start_time = '$start_time', end_time = '$end_time' WHERE id ='$id'";
mysqli_query($con, $qry);