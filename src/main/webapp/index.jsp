<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
    <h1><%= "Hello World!" %></h1>
    <br/>
    <a href="hello-servlet">Hello Servlet</a>

    <form method="get" action="hello.do">
        用户名：<input name="userName">
        <input type="submit" value="提交">
    </form>
</body>
</html>