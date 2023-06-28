<%-- 
    Document   : register
    Created on : Jun 19, 2023, 9:32:39 PM
    Author     : ptd
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Register Page</title>
    </head>
    <body>
        <jsp:include page="header.jsp"></jsp:include>
            <div class="container">
                <div class="row justify-content-center">
                    <div class="col-md-6">
                        <h2 class="text-center">Register</h2>
                        <form action="DispatcherServlet" method="POST">
                            <div class="form-group">
                                <label for="txtUserName">User Name</label>
                                <input type="text" id="txtUserName" name="txtUserName" class="form-control" value="${param.txtUserName}" />
                        </div>
                        <div class="form-group">
                                <label for="txtPassword">Password</label>
                                <input type="password" id="txtUserName" name="txtPassword" class="form-control" value="${param.txtPassword}" />
                        </div>
                        <div class="form-group">
                            <label for="txtFirstName">First Name</label>
                            <input type="text" id="txtFirstName" name="txtFirstName" class="form-control" value="${param.txtFirstName}" />
                        </div>
                        <div class="form-group">
                            <label for="txtLastName">Last Name</label>
                            <input type="text" id="txtLastName" name="txtLastName" class="form-control" value="${param.txtLastName}" />
                        </div>
                        <div class="form-group">
                            <label for="txtEmail">Email</label>
                            <input type="text" id="txtEmail" name="txtEmail" class="form-control" value="${param.txtEmail}" />
                        </div>
                        <div class="form-group">
                            <label for="txtPhone">Phone</label>
                            <input type="text" id="txtPhone" name="txtPhone" class="form-control" value="${param.txtPhone}" />
                        </div>
                        <div class="form-group">
                            <label for="txtAddress">Address</label>
                            <input type="text" id="txtAddress" name="txtAddress" class="form-control" value="${param.txtAddress}" />
                        </div>
                        <div class="text-center mt-2 mb-3">
                            <button type="submit" name="btAction" value="Register" class="btn btn-secondary">Add Account</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <jsp:include page="footer.jsp"></jsp:include>
    </body>
</html>
