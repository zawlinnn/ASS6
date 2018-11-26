<?php
    header("Content-Type: text/javascript; charset=utf-8");
    define('HOST', 'localhost');
    define('USER', 'root');
    define('PASS', '');
    define('DB', 'lab_connect_mysql');

    $con = mysqli_connect(HOST,USER,PASS,DB) or die ('Unable to connect');

    $std_id     = $_POST['std_id'];
    $std_name   = $_POST['std_name'];
    $std_tel    = $_POST['std_tel'];
    $std_email  = $_POST['std_email'];

    if(isset($_POST)){
            $sql = "INSERT INTO student(std_id,std_name,std_tel,std_email) VALUES ('".$std_id."','".$std_name."','".$std_tel."','".$std_email."')";
            mysqli_query($con,$sql);
    }
    mysqli_close($con);
?>