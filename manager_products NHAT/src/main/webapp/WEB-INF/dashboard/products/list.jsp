<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="utf-8">
        <title>Basic Tables | Zircos - Responsive Bootstrap 4 Admin Dashboard</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta content="Responsive bootstrap 4 admin template" name="description">
        <meta content="Coderthemes" name="author">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <jsp:include page="/WEB-INF/dashboard/layout/head_css.jsp"></jsp:include>

    </head>
    <body data-layout="horizontal">

        <!-- Begin page -->
        <div id="wrapper">

            <!-- Navigation Bar-->
            <jsp:include page="/WEB-INF/dashboard/layout/topnav.jsp"></jsp:include>
                <!-- End Navigation Bar-->

            <!-- ============================================================== -->
            <!-- Start Page Content here -->
            <!-- ============================================================== -->

            <div class="content-page">
                <div class="content">

                    <!-- Start Content-->
                    <div class="container-fluid">

                        <!-- start page title -->
                        <div class="row">
                            <div class="col-12">
                                <div class="page-title-box">
                                    <div class="page-title-right">
                                        <ol class="breadcrumb m-0">
                                            <li class="breadcrumb-item"><a href="javascript: void(0);">Zircos</a></li>
                                            <li class="breadcrumb-item"><a href="javascript: void(0);">Tables</a></li>
                                            <li class="breadcrumb-item active">Basic Tables</li>
                                        </ol>
                                    </div>
                                    <h4 class="page-title">Basic Tables</h4>
                                </div>
                            </div>
                        </div>
                        <!-- end page title -->

                        <div class="row">
                            <c:if test="${sessionScope.messageEdit !=null}">
                                <script>
                                    window.onload = ()=>{
                                        Swal.fire({
                                            position: 'top-end',
                                            icon: 'success',
                                            title: 'Sửa thành công',
                                            showConfirmButton: false,
                                            timer: 1500
                                        })
                                    }
                                </script>
                                <%--        <c:set scope="session" var="messageEdit" value="null"></c:set>--%>
                                <% session.setAttribute("messageEdit", null);%>
                            </c:if>

                            <c:if test="${sessionScope.messageDelete !=null}">
                                <script>
                                    window.onload = ()=>{
                                        Swal.fire({
                                            position: 'top-end',
                                            icon: 'success',
                                            title: 'Xóa thành công',
                                            showConfirmButton: false,
                                            timer: 1500
                                        })
                                    }
                                </script>
                                <%--        <c:set scope="session" var="messageEdit" value="null"></c:set>--%>
                                <% session.setAttribute("messageDelete", null);%>
                            </c:if>
                            <table class="table table-striped m-0">

                                <thead>
                                <tr>
                                    <th>Name</th>
                                    <th>Description</th>
                                    <th>Price</th>
                                    <th>CreateAt</th>
                                    <th>Category</th>
                                    <th>Size</th>
                                    <th>UpdateAt</th>
                                    <th>Action</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${requestScope.products}" var="p">
                                    <tr>
                                        <td>${p.getName()}</td>
                                        <td>${p.getDescription()}</td>
                                        <td>${p.getPrice()}</td>
                                        <td>${p.getCreateAt()}</td>
                                        <td>${p.getCategory().getName()}</td>
                                        <td>${p.getSize().getName()}</td>
                                        <td><fmt:formatDate value="${p.getUpdateAtTypeUtil()}" pattern="yyyy-MM-dd" /></td>

                                        <td>
                                            <a href="/products?action=edit&id=${p.getId()}"><i class="fas fa-edit"></i></a>
                                            <a href="javascript:void(0)" onclick="handleDeleteClick(${p.getId()}, '${p.getName()}')"><i class="fas fa-trash-alt"></i></a>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                            <!-- end col -->
                        </div>
                        <!-- end row -->

                    </div>
                    <!-- end container-fluid -->

                </div>
                <!-- end content -->

                

                <!-- Footer Start -->
                <jsp:include page="/WEB-INF/dashboard/layout/footer.jsp"></jsp:include>
                <!-- end Footer -->

            </div>

            <!-- ============================================================== -->
            <!-- End Page content -->
            <!-- ============================================================== -->

        </div>
        <!-- END wrapper -->

        <jsp:include page="/WEB-INF/dashboard/layout/right_bar.jsp"></jsp:include>

        <jsp:include page="/WEB-INF/dashboard/layout/js_footer.jsp"></jsp:include>

    </body>

</html>