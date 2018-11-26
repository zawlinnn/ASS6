<?php
    header("Content-Type: text/javascript; charset=utf-8");
    define('HOST', 'localhost');
    define('USER', 'root');
    define('PASS', '');
    define('DB', 'lab_connect_mysql');

    if($_SERVER['REQUEST_METHOD']=='GET'){
        $con = mysqli_connect(HOST,USER,PASS,DB) or die ('Unable to connect');

        mysqli_set_charset($con,'UTF-8');

        $sql = "SELECT * FROM student";

        $query = mysqli_query($con,$sql);

        $result = array();

        while($row = mysqli_fetch_array($query)){
            array_push($result,array("std_id" => $row['std_id'],
                                    "std_name" => $row['std_name'],
                                    "std_tel" => $row['std_tel'],
                                    "std_email" => $row['std_email']));
        }
        print json_encode(array('result' => $result));

        mysqli_close($con);
    }
?>