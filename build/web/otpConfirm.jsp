<%-- 
    Document   : otpConfirm
    Created on : Jun 30, 2023, 11:50:35 AM
    Author     : ptd
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Confirm OTP Page</title>
    </head>
    <style>
        .form-container {
            max-width: 400px;
            margin: 0 auto;
            padding: 20px;
            background-color: #f2f2f2;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            margin-bottom: 20px;
            margin-top:20px;
        }

        .form-container label {
            display: block;
            margin-bottom: 10px;
            font-weight: bold;
        }

        .form-container input[type="text"] {
            width: 100%;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
            box-sizing: border-box;
            font-size: 14px;
            margin-bottom: 20px;
        }

        .form-container button[type="submit"] {
            background-color: #4caf50;
            color: #fff;
            border: none;
            padding: 10px 20px;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
        }

        .form-container button[type="submit"]:hover {
            background-color: #45a049;
        }
    </style>

    <body>
        <jsp:include page="header.jsp"></jsp:include>
            <div class="form-container">
                <form action="changePassword" method="post">
                    <label for="otp">Enter OTP:</label>
                    <input type="text" id="otp" name="otp">
                    <button type="submit">Submit</button>
                </form>
            </div>


        <jsp:include page="footer.jsp"></jsp:include>
    </body>
</html>
