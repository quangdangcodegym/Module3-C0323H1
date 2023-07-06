<%@ page import="java.util.Set" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Document</title>
</head>
<body>
<form action="/translate" method="post">

  <input type="text" name="kw" value="<%= request.getAttribute("kw") !=null ? request.getAttribute("kw") : ""%>"/>
  <button type="submit">Transalte</button>
</form>

<%

  String result = "";
  if(request.getAttribute("result")!=null){
    result = request.getAttribute("result").toString();
  }
%>
<label>Ket qua: <%= result%></label>
<div>
  <ul>
    <%
      if(request.getAttribute("relativeKeys")!=null){
        Set<String> relativeKeys = (Set<String>) request.getAttribute("relativeKeys");
        for(String item : relativeKeys){
          out.println("<li>"+ item + "</li>");
        }
      }

    %>
  </ul>
</div>
</body>
</html>