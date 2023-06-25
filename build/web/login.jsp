<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <title>Login Page</title>
  <style>
    .container {
      width: 300px;
      margin: 0 auto;
      padding: 20px;
      border: 1px solid #ccc;
      border-radius: 5px;
      text-align: center;
    }

    h2 {
      margin-top: 0;
    }

    .form-group {
      margin-bottom: 15px;
    }

    label {
      display: block;
    }

    input[type="text"],
    input[type="password"] {
      width: 80%;
      padding: 10px;
      border: 1px solid #ccc;
      border-radius: 5px;
    }

    .btn {
      display: inline-block;
      padding: 10px 20px;
      background-color: #4CAF50;
      color: white;
      border: none;
      border-radius: 5px;
      cursor: pointer;
    }

  </style>
</head>
<body>
  <div class="container">
    <h2>Login</h2>
    <form action="DispatcherServlet" method="POST" >
      <div class="form-group">
        <label for="username">Username:</label>
        <input type="text" id="username" name="userName" required>
      </div>
      <div class="form-group">
        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required>
      </div>
        <input type="submit" value="Login" class="btn" name="btAction">
    </form>
  </div>
</body>
</html>
