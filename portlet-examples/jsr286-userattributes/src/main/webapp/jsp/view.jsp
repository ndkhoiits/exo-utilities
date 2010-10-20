<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
Hello user
   <ul>
      <li>Your first name : <%=request.getAttribute("firstname") %> </li>
      <li>Your lasname name : <%=request.getAttribute("lastname") %> </li>
      <li>Your gender : <%=request.getAttribute("gender") %> </li>
   </ul>
</body>
</html>