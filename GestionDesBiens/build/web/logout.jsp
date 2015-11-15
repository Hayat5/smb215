<%@ page session="true"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
       
        

You has been logged out.



<br/><br/>
<a href="faces/index.xhtml">Click here to go to return to Home page</a>
    </body>
</html>
<% session.invalidate(); %>