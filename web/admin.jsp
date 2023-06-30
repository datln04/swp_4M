
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin Page</title>
        <style>
            /* Tabbed interface styles */
            body {
                font-family: Arial, sans-serif;
                margin: 0;
                padding: 0;
            }

            h1 {
                text-align: center;
            }

            .container-parent {
                max-width: 1440px;
                margin: 20px auto;
                padding: 20px;
                background-color: #f4f4f4;
                border: 1px solid #ccc;
            }

            table {
                width: 100%;
                border-collapse: collapse;
            }

            th, td {
                padding: 8px;
                text-align: left;
                /*border-bottom: 1px solid #ccc;*/
            }

            th {
                background-color: #f2f2f2;
                font-weight: bold;
            }

            .actions {
                margin-top: 10px;
            }

            .actions button {
                margin-right: 10px;
            }
            img {
                height: 100px;
            }

            .actions{
                width: 250px;
            }

            .button {
                display: inline-block;
                padding: 10px 10px;
                background-color: lightblue;
                color: #fff;
                font-size: 16px;
                text-decoration: none;
                border: none;
                border-radius: 4px;
                cursor: pointer;
            }

            .button-update{
                margin-top: 20px;

            }

            .button:hover {
                background-color: #ccc;
            }

            .button:focus {
                outline: none;
            }

            .button:active {
                background-color: #3e8e41;
                transform: translateY(1px);
            }         

            .button-delete{
                background-color: lightcoral;
            }


            .tab {
                overflow: hidden;
            }

            .tab button {
                background-color: #f2f2f2;
                float: left;
                border: none;
                outline: none;
                cursor: pointer;
                padding: 10px 20px;
                transition: 0.3s;
            }

            .tab button:hover {
                background-color: #ddd;
            }

            .tab button.active {
                background-color: #ccc;
            }

            /* Content section styles */
            .tabcontent {
                display: none;
                padding: 20px;

                border-top: none;
            }

            .popup {
                display: none;
                position: fixed;
                z-index: 1;
                left: 0;
                top: 0;
                width: 100%;
                height: 100%;
                overflow: auto;
                background-color: rgba(0, 0, 0, 0.4);
            }

            .popup-content {
                display: block;
                background-color: #fefefe;
                margin: 15% auto;
                padding: 20px;
                border: 1px solid #888;
                width: 80%;
                max-width: 600px;
            }

            .close {
                color: #aaa;
                float: right;
                font-size: 28px;
                font-weight: bold;
                cursor: pointer;
            }

            .close:hover,
            .close:focus {
                color: black;
                text-decoration: none;
                cursor: pointer;
            }

            input[type=text]{
                width: 100%;
                padding: 1%;
            }

            .warning-message {
                font-weight: bold;
                color: red;
                text-align: center;
                margin-bottom: 20px;
            }

            .delete-pop-up-actions{
                display: flex;
                justify-content: center;
            }

            .button-gap{
                margin-right: 20px;
            }

            .btn-delete-all-item{
                margin: 10px 0 0 0;
            }
        </style>
    </head>
    <body>
        <c:if test="${sessionScope.USER.roleName ne 'admin'}">
            <jsp:forward page="login.jsp"></jsp:forward>
        </c:if>
        <jsp:include page="header.jsp"></jsp:include>
            <div class="container-parent">
                <div class="tab">
                    <button class="tablinks active" onclick="openTab(event, 'Tab1')">Staff</button>
                    <button class="tablinks" onclick="openTab(event, 'Tab2')">User</button>
                    <button class="tablinks" onclick="openTab(event, 'Tab3')">Cart</button>
                    <button class="tablinks" onclick="openTab(event, 'Tab4')">Add Account</button>

                </div>

                <div id="Tab1" class="tabcontent" style="display: block;">

                <c:if test="${sessionScope.LIST_STAFF.size() > 0}">
                    <h3>Staff</h3>
                    <table>
                        <tr>                                       
                            <th>User Name</th>
                            <th>First Name</th>
                            <th>Last Name</th>                      
                            <th>Email</th>  
                            <th>Phone Number</th>  
                            <th>Address</th>           
                            <th>Role Name</th>           
                            <th style="text-align: center">Actions</th>

                        </tr>
                        <c:forEach items="${sessionScope.LIST_STAFF}" var="staff">
                            <tr>                       
                                <td>${staff.userName}</td>
                                <td>${staff.firstName}</td>
                                <td>${staff.lastName}</td>
                                <td>${staff.email}</td>
                                <td>${staff.phoneNumber}</td>      
                                <td>${staff.address}</td>      
                                <td>${staff.roleName}</td>      

                                <td class="actions" style="text-align: center">
                                    <button class="button" onclick="openPopupAccount('${staff.profileId}', '${staff.userName}',
                                                    '${staff.firstName}', '${staff.lastName}', '${staff.email}',
                                                    '${staff.phoneNumber}', '${staff.address}', '${staff.userId}', '${staff.roleName}')">
                                        Update
                                    </button>   
                                    <button class="button button-delete btn-delete-all-item" onclick="openPopupDelete('${staff.profileId}')">Delete</button>  
                                </td>
                            </tr>
                        </c:forEach>
                    </table>

                </c:if>
            </div>

            <div id="Tab2" class="tabcontent">
                <c:if test="${sessionScope.LIST_USER.size() > 0}">
                    <h3>User</h3>
                    <table>
                        <tr>                                       
                            <th>User Name</th>
                            <th>First Name</th>
                            <th>Last Name</th>                      
                            <th>Email</th>  
                            <th>Phone Number</th>  
                            <th>Address</th>           
                            <th>Role Name</th>           
                            <th style="text-align: center">Actions</th>

                        </tr>
                        <c:forEach items="${sessionScope.LIST_USER}" var="user">
                            <tr>                       
                                <td>${user.userName}</td>
                                <td>${user.firstName}</td>
                                <td>${user.lastName}</td>
                                <td>${user.email}</td>
                                <td>${user.phoneNumber}</td>      
                                <td>${user.address}</td>      
                                <td>${user.roleName}</td>      

                                <td class="actions" style="text-align: center">
                                    <button class="button" onclick="openPopupAccount('${user.profileId}', '${user.userName}',
                                                    '${user.firstName}', '${user.lastName}', '${user.email}',
                                                    '${user.phoneNumber}', '${user.address}', '${user.userId}', '${user.roleName}')">
                                        Update
                                    </button>   
                                    <button class="button button-delete btn-delete-all-item" onclick="openPopupDelete('${user.profileId}')">Delete</button>                   
                                </td>
                            </tr>
                        </c:forEach>
                    </table>

                </c:if>
            </div>

            <div id="Tab3" class="tabcontent">
                <c:if test="${sessionScope.LIST_ORDER_ADMIN.size() > 0}">
                    <h3>Cart</h3>
                    <table>
                        <tr>                                       

                            <th>Name</th>
                            <th>Description</th>                      
                            <th>Photo Date</th>  
                            <th>Price</th>
                            <th>Status</th>
                            <th style="text-align: center">Actions</th>
                        </tr>
                        <c:forEach items="${sessionScope.LIST_ORDER_ADMIN}" var="order" varStatus="count">
                            <th style="border: none;">${count.index + 1}</th>
                                <c:forEach items="${order.list}" var="orderItem" >
                                <tr>                       
                                    <td>${orderItem.name}</td>
                                    <td>${orderItem.description}</td>
                                    <td>${orderItem.orderDate}</td>
                                    <td>${orderItem.price}</td>      
                                    <td>${orderItem.status}</td>
                                    <c:if test="${orderItem.status ne 'confirm'}">
                                        <td class="actions" style="text-align: center">
                                            <button class="button" onclick="openPopup('${orderItem.orderDetailId}', '${orderItem.name}', '${orderItem.description}', '${orderItem.price}', '${orderItem.orderDate}')">Change</button>                     
                                        </td>
                                    </c:if>
                                </tr>
                            </c:forEach>
                            <th style="border-bottom: none"></th>
                            <th style="border-bottom: none"></th>
                            <th style="border-bottom: none"></th>
                            <th style="border-bottom: none"></th>


                            <th style="border-bottom: none; text-align: center">
                                <button class="button button-delete btn-delete-all-item" onclick="openPopupDeleteOrder('${order.getOrderId()}')">Delete Item</button>                                                                 
                            </th>
                            <tr style="border-top: 1px solid black;"></tr>
                        </c:forEach>
                    </table>

                </c:if>
            </div>

            <div id="Tab4" class="tabcontent">
                <h3>Staff</h3>
                <table>
                    <tr>                                       
                        <th>User Name</th>
                        <th>First Name</th>
                        <th>Last Name</th>                      
                        <th>Email</th>  
                        <th>Phone Number</th>  
                        <th>Address</th>           
                        <th>Role Name</th>           
                        <th style="text-align: center">Actions</th>

                    </tr>
                    <tbody>
                    <form action="DispatcherServlet" method="POST"> 
                        <tr>                       
                            <td>
                                <input type="text" name="txtUserName" value="${param.txtUserName}"/>
                            </td>
                            <td>
                                <input type="text" name="txtFistName" value="${param.txtFistName}"/>
                            </td>
                            <td>
                                <input type="text" name="txtLastName" value="${param.txtLastName}"/>
                            </td>
                            <td>
                                <input type="text" name="txtEmail" value="${param.txtEmail}"/>
                            </td>
                            <td>
                                <input type="text" name="txtPhone" value="${param.txtPhone}"/>
                            </td>      
                            <td>
                                <input type="text" name="txtAddress" value="${param.txtAddress}"/>
                            </td>      
                            <td>
                                <select name="roleId">
                                    <c:forEach items="${sessionScope.LIST_STAFF_ROLE}" var="r">
                                        <option value="${r.roleId}">${r.roleName}</option>
                                    </c:forEach>
                                </select>
                            </td>      

                            <td class="actions" style="text-align: center">
                                <input type="submit" name="btAction" value="Add Account" class="btn btn-secondary"/>
                            </td>
                        </tr>
                    </form>
                    </tbody>
                </table>
            </div>
        </div>


        <div id="popupAccount" class="popup">
            <div id="popupContent" class="popup-content">
                <span class="close" onclick="closePopup()">&times;</span>
                <h2>Update Account</h2>
                <form action="DispatcherServlet" method="POST">
                    <input type="hidden" id="accountId" name="accountId">                        
                    <input type="hidden" id="isUser" name="isUser">                        

                    <p for="userName">User Name:</p>
                    <input type="text" id="userName" name="userName" required="true"/>

                    <p for="firstName">First Name:</p>
                    <input type="text" id="firstName" name="firstName" required="true"/>

                    <p for="lastName">Last Name:</p>
                    <input type="text" id="lastName" name="lastName" required="true"/>

                    <p for="email">Email:</p>
                    <input type="text" id="email" name="email" required="true"/>

                    <p for="phone">Phone: </p>
                    <input type="text" id="phoneNumber" name="phoneNumber" required="true"/>

                    <p for="address">Address: </p>
                    <input type="text" id="address" name="address" required="true"/>

                    <p for="address" id="roleNameText">Role Name </p>
                    <select name="roleId" id="userId">
                        <c:forEach items="${sessionScope.LIST_STAFF_ROLE}" var="r">
                            <option value="${r.roleId}">${r.roleName}</option>
                        </c:forEach>
                    </select>
                    <br/> 
                    <input type="submit" value="EditAccount" name="btAction" class="button button-update"/>
                </form>
            </div>
        </div>

        <div id="popup" class="popup">
            <div id="popupContent" class="popup-content">
                <span class="close" onclick="closePopup()">&times;</span>
                <h2>Update Card</h2>
                <form action="DispatcherServlet" method="POST">
                    <input type="hidden" id="orderDetailId" name="orderDetailId">

                    <p for="locationName">Name:</p>
                    <input type="text" id="orderDetailName" name="orderDetailName" required="true"/>

                    <p for="locationDescription">Description:</p>
                    <input type="text" id="orderDetailDescription" name="orderDetailDescription" required="true"/>

                    <p for="locationPrice">Price:</p>
                    <input type="text" id="orderDetailPrice" name="orderDetailPrice" required="true"/>

                    <p for="dateTime">Date Order: </p>
                    <input type="datetime-local" name="timeRange" required="true" id="orderDetailOrderDate">
                    <br/> 
                    <input type="submit" value="Update Order" name="btAction" class="button button-update"/>
                </form>
            </div>
        </div>

        <div id="popupDelete" class="popup">
            <div id="popupContent" class="popup-content">
                <span class="close" onclick="closePopup()">&times;</span>

                <h2>Warning</h2>
                <p class="warning-message">Are you sure!!!</p>
                <div class="form-group delete-pop-up-actions">                       
                    <button class="button button-gap" onclick="closePopup()" >Cancel</button>
                    <form action="DispatcherServlet" method="POST">
                        <input type="hidden" id="accountDeleteId" name="txtId">
                        <input type="submit" value="Delete Account" name="btAction" class="button button-delete" />
                    </form>
                </div>

            </div>
        </div>

        <div id="popupDeleteOrder" class="popup">
            <div id="popupContent" class="popup-content">
                <span class="close" onclick="closePopup()">&times;</span>

                <h2>Warning</h2>
                <p class="warning-message">Are you sure!!!</p>
                <div class="form-group delete-pop-up-actions">                       
                    <button class="button button-gap" onclick="closePopup()" >Cancel</button>
                    <form action="DispatcherServlet" method="POST">
                        <input type="hidden" id="orderId" name="orderId">
                        <input type="submit" value="Delete Order" name="btAction" class="button button-delete" />
                    </form>
                </div>

            </div>
        </div>

        <script>
            function openTab(event, tabName) {
                var i, tabcontent, tablinks;
                tabcontent = document.getElementsByClassName("tabcontent");
                for (i = 0; i < tabcontent.length; i++) {
                    tabcontent[i].style.display = "none";
                }
                tablinks = document.getElementsByClassName("tablinks");
                for (i = 0; i < tablinks.length; i++) {
                    tablinks[i].className = tablinks[i].className.replace(" active", "");
                }
                document.getElementById(tabName).style.display = "block";
                event.currentTarget.className += " active";
            }

            function openPopupAccount(id, userName, firstName, lastName, email, phone, address, userId, roleName) {
                document.getElementById('accountId').value = id;
                document.getElementById('userName').value = userName;
                document.getElementById('firstName').value = firstName;
                document.getElementById('lastName').value = lastName;
                document.getElementById('email').value = email;
                document.getElementById('phoneNumber').value = phone;
                document.getElementById('address').value = address;

                console.log('accountId ', id)

                if (roleName === 'user') {
                    document.getElementById('userId').style.display = 'none';
                    document.getElementById('isUser').value = 'true';
                    document.getElementById('roleNameText').style.display = 'none';
                } else {
                    document.getElementById('userId').style.display = 'block';
                    document.getElementById('roleNameText').style.display = 'block';
                    document.getElementById('isUser').value = '';
                    document.getElementById('userId').value = userId;
                }


                document.getElementById('popupAccount').style.display = 'block';
            }

            function openPopup(id, name, description, price, orderDate) {
                document.getElementById('orderDetailId').value = id;
                document.getElementById('orderDetailName').value = name;
                document.getElementById('orderDetailDescription').value = description;
                document.getElementById('orderDetailPrice').value = price;
                document.getElementById('orderDetailOrderDate').value = orderDate;

                document.getElementById('popup').style.display = 'block';
            }

            function openPopupDelete(id) {
                document.getElementById('accountDeleteId').value = id;
                document.getElementById('popupDelete').style.display = 'block';
            }

            function openPopupDeleteOrder(id) {
                document.getElementById('orderId').value = id;
                document.getElementById('popupDeleteOrder').style.display = 'block';
            }

            function closePopup() {
                document.getElementById('popupAccount').style.display = 'none';
                document.getElementById('popup').style.display = 'none';
                document.getElementById('popupDelete').style.display = 'none';
                document.getElementById('popupDeleteOrder').style.display = 'none';
            }
        </script>
        <jsp:include page="footer.jsp"></jsp:include>
    </body>
</html>
