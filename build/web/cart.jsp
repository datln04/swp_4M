<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Cart</title>

        <style>
            /* CSS styles go here */
            body {
                font-family: Arial, sans-serif;
                margin: 0;
                padding: 0;
            }

            h1 {
                text-align: center;
            }

            .container {
                max-width: 1200px;
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
                border-bottom: 1px solid #ccc;
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
        <jsp:include page="header.jsp"></jsp:include>
        <c:set var="profile" value="${sessionScope.USER}" />
        <c:set var="totalPrice" value="0" />
        <div class="container">
            <c:if test="${sessionScope.LIST_PHOTO_SCHEDULE.size() > 0}">
                <h2>Photograph Schedule</h2>
                <table>
                    <tr>                                       
                        <th>Name</th>
                        <th>Description</th>                      
                        <th>Photo Date</th>  
                        <th>Price</th>    
                        <th style="text-align: center">Actions</th>
                    </tr>
                    <c:forEach items="${sessionScope.LIST_PHOTO_SCHEDULE}" var="schedule">
                        <c:forEach items="${schedule.list}" var="scheduleItem" >
                            <tr>                       
                                <td>${scheduleItem.name}</td>
                                <td>${scheduleItem.description}</td>
                                <td>${scheduleItem.orderDate}</td>
                                <td>$ ${scheduleItem.price}</td>      

                                <td class="actions" style="text-align: center">
                                    <button class="button" onclick="openPopup('${scheduleItem.orderDetailId}', '${scheduleItem.name}', '${scheduleItem.description}', '${scheduleItem.price}', '${scheduleItem.orderDate}')">Change</button>                     
                                </td>
                            </tr>
                        </c:forEach>
                        <th style="border-bottom: none"></th>


                        <th style="border-bottom: none; text-align: center">
                            <button class="button button-delete btn-delete-all-item" onclick="openPopupDelete('${scheduleItem.orderDetailId}')">Delete Item</button>                                                                 
                        </th>
                    </c:forEach>
                </table>
            </c:if>
            <c:if test="${sessionScope.LIST_ORDER_DETAIL.size() > 0}">
                <h1>Cart</h1>
                <table>
                    <tr>                                       
                        <th>ID</th>
                        <th>Name</th>
                        <th>Description</th>                      
                        <th>Order Date</th>  
                        <th>Price</th>    
                        <th style="text-align: center">Actions</th>
                    </tr>

                    <c:forEach items="${sessionScope.LIST_ORDER_DETAIL}" var="orderDetail">
                        <tr>                       
                            <td>${orderDetail.orderDetailId}</td>
                            <td>${orderDetail.name}</td>
                            <td>${orderDetail.description}</td>
                            <td>${orderDetail.orderDate}</td>
                            <td>$ ${orderDetail.price}</td>                   
                            <td class="actions" style="text-align: center">
                                <button class="button button-delete " onclick="openPopupDelete('${orderDetail.orderDetailId}')">Delete Item</button>                                                                 
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </c:if>

            <div id="popup" class="popup">
                <div id="popupContent" class="popup-content">
                    <span class="close" onclick="closePopup()">&times;</span>
                    <h2>Update Location</h2>
                    <form action="DispatcherServlet" method="POST">
                        <input type="hidden" id="scheduleId">                        

                        <p for="locationName">Name:</p>
                        <input type="text" id="scheduleName"/>

                        <p for="locationDescription">Description:</p>
                        <input type="text" id="scheduleDescription"/>
                        
                        <p for="locationImage">Photo Date</p>
                        <input type="text" id="scheduleOrderDate" />

                        <p for="locationPrice">Price:</p>
                        <input type="text" id="schedulePrice"/>
                        <br/> 
                        <button class="button button-update">Save</button>
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
                            <input type="hidden" id="orderDeleteId" name="txtOrderDetailId">
                            <input type="submit" value="DeleteItem" name="btAction" class="button button-delete" />
                        </form>
                    </div>

                </div>
            </div>

        </div>

        <jsp:include page="footer.jsp"></jsp:include>
        <script>

            function openPopup(id, name, description, price, orderDate) {
                document.getElementById('scheduleId').value = id;
                document.getElementById('scheduleName').value = name;
                document.getElementById('scheduleDescription').value = description;
                document.getElementById('schedulePrice').value = price;
                document.getElementById('scheduleOrderDate').value = orderDate;

                document.getElementById('popup').style.display = 'block';
            }

            function openPopupDelete(id) {
                document.getElementById('locationDeleteId').value = id;

                document.getElementById('popupDelete').style.display = 'block';
            }

            function closePopup() {
                document.getElementById('popup').style.display = 'none';
                document.getElementById('popupDelete').style.display = 'none';
            }
        </script>
    </body>

</html>
