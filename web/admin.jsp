
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
                max-width: 1550px;
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

            .card-price{
                padding: 10px;
                text-align: end;
            }


            .card .card-image img {
                height: 230px;
                width: 250px;
            }

        </style>
        <link 
            rel="stylesheet"
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-alpha1/dist/css/bootstrap.min.css"
            >
        <script src=
                "https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-alpha1/dist/js/bootstrap.bundle.min.js">
        </script>
        <script src=
                "https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js">
        </script>
    </head>
    <body>
        <c:if test="${sessionScope.USER.roleName ne 'admin'}">
            <jsp:forward page="login.jsp"></jsp:forward>
        </c:if>
        <jsp:include page="header.jsp"></jsp:include>
        <c:set var="profile" value="${sessionScope.USER}" />
        <c:set var="totalPriceCart" value="0" />
        <c:set var="idOrder" value="0"/>
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
                                    <button class="button button-delete btn-delete-all-item" onclick="openPopupDeleteAccount('${staff.profileId}')">Delete</button>  
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
                                    <button class="button button-delete btn-delete-all-item" onclick="openPopupDeleteAccount('${user.profileId}')">Delete</button>                   
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
                            <th>Order</th>
                            <th>Name</th>
                            <th>Description</th>                      
                            <th>Photo Date</th>  
                            <th>Price</th>    
                            <th>Status</th>   
                            <th style="text-align: center">Actions</th>
                        </tr>
                        <c:forEach items="${sessionScope.LIST_ORDER_ADMIN}" var="cart" >
                            <c:set var="idOrder" value="${cart.getOrderId()}" />
                            <c:set var="typeofItem" value=""/>
                            <c:set var="idofItem" value=""/>
                            <c:set var="orderDetailIdofItem" value=""/>
                            <c:forEach items="${cart.list}" var="item" varStatus="temp">
                                <c:set var="typeofItem" value="${item.itemType}"/>
                                <c:set var="totalPriceCart" value="${totalPriceCart + item.price}" />
                                <c:set var="idofItem" value="${item.itemId}"/>
                                <c:set var="orderDetailIdofItem" value="${item.orderDetailId}"/>
                                <tr>                       
                                    <td>${item.orderId}</td>
                                    <td>${item.name}</td>
                                    <td style="width: 400px">${item.description}</td>
                                    <td style="width: 150px">${item.orderDate}</td>
                                    <td>$ ${item.price}</td>
                                    <td>${item.status}</td>
                                    <c:if test="${item.itemType ne 'photo_schedule'}" >
                                        <td style="border-bottom: none; text-align: center">
                                            <button class="button" onclick="openPopupUpdate('${item.orderDetailId}', '${item.itemType}', '${item.orderId}')">Change Item</button>
                                            <button class="button button-delete btn-delete-all-item" onclick="openPopupDelete('${item.orderId}', '${item.orderDetailId}')">Delete Item</button>                                                                 
                                        </td>
                                    </c:if>

                                </tr>
                                <c:if test="${typeofItem eq 'photo_schedule' and temp.index == 1}" >
                                    <tr >
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td style="border-bottom: none; text-align: center"><button class="button" onclick="openPopup('${item.orderId}', '${item.itemId}')">Change</button>
                                            <button class="button button-delete btn-delete-all-item" onclick="openPopupDelete('${item.orderId}', '${item.orderDetailId}')">Delete Item</button>  </td>                                                              
                                    </tr>
                                </c:if>
                            </c:forEach>
                            <!--                            <th style="border-bottom: none"></th>
                                                        <th style="border-bottom: none"></th>
                                                        <th style="border-bottom: none"></th>
                                                        <th style="border-bottom: none"></th>-->

                            <tr style="border-top: 1px solid black"></tr>
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



        <!-- pop-up booking photo schedule -->
        <div id="popup" class="popup">
            <c:set var="totalPrice" value="0" />
            <div id="popupContent" class="popup-content">
                <span class="close" onclick="closePopup()">&times;</span>
                <h2>Booking Photography Schedule</h2>
                <form action="UpdateBookingScheduleAdmin" method="POST">
                    <input type="hidden" name="txtOrderId" id="txtOrderId" />
                    <input type="hidden" name="txtItemId" id="txtItemId" />
                    <label for="locationImage">Select location:</label>
                    <select name="location" class="form-select" aria-label="Default select example" onchange="updateTotalPrice(this)" required="true">

                        <c:forEach items="${sessionScope.LIST_LOCATION}" var="l">
                            <option value="${l.id}" data-price="${l.price}">${l.name}</option>
                        </c:forEach>
                    </select>
                    <br/> 
                    <label for="studioName">Select Studio:</label>
                    <select name="studio" class="form-select" aria-label="Default select example" onchange="updateTotalPrice(this)" required="true">

                        <c:forEach items="${sessionScope.LIST_STUDIO}" var="s">
                            <option value="${s.id}" data-price="${s.price}">${s.name}</option>
                        </c:forEach>
                    </select>
                    <br/> 
                    <label for="locationDescription">Select Time range</label>

                    <input type="datetime-local" name="timeRange" class="ml-5" required="true" id="timeRangeInput">
                    <br/> 
                    <br/> 
                    <label for="locationPrice">Price:</label>
                    <input type="number" name="price" class="ml-5" id="totalPrice" readonly="true" id="timeRangeInput"/>

                    <br />
                    <input type="submit" value="Update Booking Schedule" name="btAction" class="button button-update mt-3"/>
                </form>
            </div>
        </div>

        <!-- pop-up item -->
        <div id="popupUpdate" class="popup">
            <div id="popupContent" class="popup-content" style="max-width: 900px">
                <span class="close" onclick="closePopup()">&times;</span>

                <h2>Update Item</h2>

                <div class="card-container" >
                    <c:forEach items="${sessionScope.LIST_ORDER_ALL}" var="data">  
                        <div class="card" data-item-type="${data.itemType}" style="display: flex; flex-direction: row; margin: 20 0;">
                            <div class="card-image">
                                <img src="${data.image}" alt="Image">
                            </div>
                            <div class="card-content ml-4">
                                <h3>${data.name}</h3>
                                <p>${data.description}</p>
                                <p>Price For Rent: $ ${data.price}</p>
                                <p><a href="ownerContact.jsp" class="contact-link">Contact</a></p>
                                <form action="ChangeItemAdmin" method="POST">                                    
                                    <input type="hidden" name="orderId" class="order-input">                                     
                                    <input type="hidden" name="orderDetailId" class="detail-input">                                     
                                    <input type="hidden" name="itemId" value="${data.itemId}"/>
                                    <input type="hidden" name="itemType" value="${data.itemType}"/>
                                    <button class="button">Change</button>
                                </form>
                            </div>
                        </div>
                    </c:forEach>
                </div>  
            </div>
        </div>


        <div id="popupDelete" class="popup">
            <div id="popupContent" class="popup-content">
                <span class="close" onclick="closePopup()">&times;</span>

                <h2>Warning</h2>
                <p class="warning-message">Are you sure!!!</p>
                <div class="form-group delete-pop-up-actions">                       
                    <button class="button button-gap" onclick="closePopup()" >Cancel</button>
                    <form action="deleteItem" method="POST">
                        <input type="text" id="orderId" name="orderId">
                        <input type="text" id="orderDetailId" name="orderDetailId">
                        <button class="button button-delete">Delete</button>
                    </form>
                </div>

            </div>
        </div>

        <div id="popupDeleteAccount" class="popup">
            <div id="popupContent" class="popup-content">
                <span class="close" onclick="closePopup()">&times;</span>

                <h2>Warning</h2>
                <p class="warning-message">Are you sure!!!</p>
                <div class="form-group delete-pop-up-actions">                       
                    <button class="button button-gap" onclick="closePopup()" >Cancel</button>
                    <form action="DispatcherServlet" method="POST">
                        <input type="hidden" id="proId" name="txtId">
                        <input type="submit" value="Delete Account" name="btAction" class="button button-delete" />
                    </form>
                </div>

            </div>
        </div>
        <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
        <script>
                        if (${requestScope.ERROR_USER_NAME != null}) {
                            swal("Invalid UserName");
                        }
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

                        function openPopup(orderId, itemId) {
                            document.getElementById('txtOrderId').value = orderId;
                            document.getElementById('txtItemId').value = itemId;

                            document.getElementById('popup').style.display = 'block';
                        }

                        function openPopupDelete(orderId, orderDetailId) {
                            document.getElementById('orderId').value = orderId;
                            document.getElementById('orderDetailId').value = orderDetailId;

                            document.getElementById('popupDelete').style.display = 'block';
                        }

                        function openPopupDeleteAccount(profileId) {
                            document.getElementById('proId').value = profileId;
                            document.getElementById('popupDeleteAccount').style.display = 'block';
                        }

                        function closePopup() {
                            document.getElementById('popupAccount').style.display = 'none';
                            document.getElementById('popupUpdate').style.display = 'none';
                            document.getElementById('popup').style.display = 'none';
                            document.getElementById('popupDelete').style.display = 'none';
                            document.getElementById('popupDeleteAccount').style.display = 'none';
                        }

                        function updateTotalPrice(selectElement) {
                            var locationValue = document.querySelector('select[name="location"]').value;
                            var studioValue = document.querySelector('select[name="studio"]').value;

                            var locationPrice = parseInt(document.querySelector('select[name="location"] option:checked').getAttribute("data-price"));
                            var studioPrice = parseInt(document.querySelector('select[name="studio"] option:checked').getAttribute("data-price"));

                            var totalPrice = locationPrice + studioPrice;
                            document.getElementById("totalPrice").value = totalPrice;
                        }

                        // Set default price on page load
                        window.addEventListener("DOMContentLoaded", function () {
                            var locationSelect = document.querySelector('select[name="location"]');
                            var locationPrice = parseInt(locationSelect.options[locationSelect.selectedIndex].getAttribute("data-price"));

                            var studioSelect = document.querySelector('select[name="studio"]');
                            var studioPrice = parseInt(studioSelect.options[studioSelect.selectedIndex].getAttribute("data-price"));

                            var totalPrice = locationPrice + studioPrice;
                            document.getElementById("totalPrice").value = totalPrice;
                        });

                        // Set default value to current date and time
                        window.addEventListener("DOMContentLoaded", function () {
                            var timeRangeInput = document.getElementById("timeRangeInput");
                            var currentDate = new Date();
                            var currentDateString = currentDate.toISOString().slice(0, 16); // Format: "YYYY-MM-DDTHH:MM"

                            timeRangeInput.min = currentDateString;
                            timeRangeInput.value = currentDateString;
                        });

                        function openPopupUpdate(orderDetailID, itemType, orderId) {

                            document.getElementById('popupUpdate').style.display = 'block';


                            var items = document.getElementsByClassName("card");
                            for (var i = 0; i < items.length; i++) {
                                var tmp = items[i].getAttribute('data-item-type');
                                if (tmp !== itemType) {
                                    items[i].style.display = "none";
                                } else {
                                    items[i].style.display = "flex"
                                }

                            }

                            var detailInputs = document.getElementsByClassName("detail-input");
                            for (var i = 0; i < detailInputs.length; i++) {
                                detailInputs[i].value = orderDetailID;
                            }

                            var orderInputs = document.getElementsByClassName("order-input");
                            for (var i = 0; i < orderInputs.length; i++) {
                                orderInputs[i].value = orderId;
                            }
                        }
        </script>
        <jsp:include page="footer.jsp"></jsp:include>
    </body>
</html>
