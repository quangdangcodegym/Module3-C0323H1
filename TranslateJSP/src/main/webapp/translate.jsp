<%@ page import="java.util.Set" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Document</title>
</head>
<body>
<form action="/translate" method="post">

  <input type="text" name="kw" value="${requestScope.kw}"/>
  <button type="submit">Transalte</button>
</form>

<label>Ket qua: ${requestScope.result}</label>
<div>
  <ul>
    <c:set var = "i" scope = "session" value = "${0}"/>
    <c:forEach items="${requestScope.relativeKeys}" var="key">
      <c:choose>
        <c:when test = "${i%2 == 0}">
          <li style="color: red">${key}</li>
        </c:when>
        <c:otherwise>
          <li>${key}</li>
        </c:otherwise>
      </c:choose>
      <c:set var="i" scope="session" value="${i = i+1}"></c:set>

    </c:forEach>
  </ul>
</div>
</body>
</html>