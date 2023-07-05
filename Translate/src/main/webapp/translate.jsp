<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Document</title>
</head>
<body>
<form action="/translate" method="post">
  <input type="text" name="kw"/>
  <button type="submit">Transalte</button>
</form>
<%
  String result = "";
  if(request.getAttribute("result")!=null){
    result = request.getAttribute("result").toString();
  }
%>
<label>Ket qua: <%= result%></label>
</body>
</html>