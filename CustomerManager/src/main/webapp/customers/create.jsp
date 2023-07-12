<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 7/10/2023
  Time: 8:50 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/css/bootstrap.min.css"
          integrity="sha512-t4GWSVZO1eC8BM339Xd7Uphw5s17a86tIZIj8qRxhnKub6WoyhnrxeCIMeAqBPgdZGlCcG2PrZjMc+Wr78+5Xg==" crossorigin="anonymous" referrerpolicy="no-referrer" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/11.7.12/sweetalert2.css" integrity="sha512-K0TEY7Pji02TnO4NY04f+IU66vsp8z3ecHoID7y0FSVRJHdlaLp/TkhS5WDL3OygMkVns4bz0/ah4j8M3GgguA=="
          crossorigin="anonymous" referrerpolicy="no-referrer" />
    <script src="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/11.7.12/sweetalert2.min.js" integrity="sha512-JbRQ4jMeFl9Iem8w6WYJDcWQYNCEHP/LpOA11LaqnbJgDV6Y8oNB9Fx5Ekc5O37SwhgnNJdmnasdwiEdvMjW2Q=="
            crossorigin="anonymous" referrerpolicy="no-referrer"></script>
</head>
<body>
<div class="container d-flex justify-content-center">
    <div class="col-6 mt-5">
        <form method="post" >
            <h4>Create</h4>
<%--           <c:if test="${requestScope.message != null}">--%>
<%--               <div class="alert alert-danger">--%>
<%--                   <label>${requestScope.message}</label>--%>
<%--               </div>--%>
<%--           </c:if>--%>

            <c:if test="${requestScope.errors != null}">
                <div class="alert alert-danger">
                    <ul>
                        <c:forEach items="${requestScope.errors}" var="e">
                            <li>${e}</li>
                        </c:forEach>
                    </ul>
                </div>
            </c:if>
            <c:if test="${requestScope.message !=null}">
                <script>
                   window.onload = ()=>{
                       Swal.fire({
                           position: 'top-end',
                           icon: 'success',
                           title: 'Thêm thành công',
                           showConfirmButton: false,
                           timer: 1500
                       })
                   }
                </script>
            </c:if>

            <div class="row mt-3 mb-3">
                <label class="col-3" for="">Name: </label>
                <div class="col-9">
                    <input type="text" class="form-control" name = "name" value="${requestScope.customer.getName()}"/>
                </div>
            </div>
            <div class="row mb-3">
                <label class="col-3" for="">Email: </label>
                <div class="col-9">
                    <input type="text" class="form-control" name="email" value="${requestScope.customer.getEmail()}"/>
                </div>
                <c:if test="${requestScope.errorEmail != null}">
                    <label style="color: red">${requestScope.errorEmail}</label>
                </c:if>
            </div>
            <div class="row mb-3">
                <label class="col-3" for="">Address: </label>
                <div class="col-9">
                    <input type="text" class="form-control" name="address" value="${requestScope.customer.getAddress()}"/>
                </div>
            </div>
            <div class="row mb-3">
                <label class="col-3" for="">Customer types: </label>
                <div class="col-9">
                    <select name="customer-type">
                            <c:forEach items="${requestScope.customerTypes}" var="ct">
                                <option value="${ct.getId()}">${ct.getName()}</option>
                            </c:forEach>
                    </select>
                </div>
            </div>
            <div class="row mb-3">
                <div class="col-9 offset-3">
                    <button class="btn btn-primary">Create</button>
                    <a href="/customers"><button type="button" class="btn btn-dark">Back</button></a>
                </div>
            </div>
        </form>
    </div>
</div>
</body>
</html>