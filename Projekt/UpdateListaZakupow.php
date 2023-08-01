<?php
include "DataBase.php";
$con = mysqli_connect("localhost", "root", "", "projektandroid");
$id=$_GET['id'];

$produkt = $_GET['produkt'];
$liczba = $_GET['liczba'];


$qry="UPDATE products SET name = '$produkt', amount = '$liczba' WHERE id ='$id'";
mysqli_query($con, $qry);
