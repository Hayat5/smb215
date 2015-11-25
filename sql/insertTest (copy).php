<?php
include 'config.php';
$deviceId = $_REQUEST['deviceId'];
$onlineuserid = $_REQUEST['onlineUserId'];



mysql_query("insert into testTable (id,name) values('$deviceId','$onlineuserid')",$con);
mysql_close($con);
?> 
