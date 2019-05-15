<%--
  Created by IntelliJ IDEA.
  User: ehsan
  Date: 5/3/19
  Time: 7:32 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
‌Book Name: <%= request.getParameter("bookname")%><br>
Author: <%= request.getParameter("author")%><br>
Session Requests: <%= session.getAttribute("cnt") %><br>
Application Requests: <%= application.getAttribute("cnt") %>
</body>
</html>
