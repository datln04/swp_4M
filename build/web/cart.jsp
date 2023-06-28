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

            .card-price{
                padding: 10px;
                text-align: end;
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
        <jsp:include page="header.jsp"></jsp:include>
        <c:set var="profile" value="${sessionScope.USER}" />
        <c:set var="totalPriceCart" value="0" />
        <c:set var="idOrder" value="0"/>
        <div class="container">
            <c:if test="${sessionScope.LIST_CARR_ITEM.size() > 0}">
                <h2>Photograph Schedule</h2>
                <table>
                    <tr>                                       
                        <th>Name</th>
                        <th>Description</th>                      
                        <th>Photo Date</th>  
                        <th>Price</th>    
                    </tr>
                    <c:forEach items="${sessionScope.LIST_CARR_ITEM}" var="cart" varStatus="count">
                        <c:set var="idOrder" value="${cart.getOrderId()}" />
                        <th style="border: none;">${count.index + 1}</th>
                            <c:forEach items="${cart.list}" var="item" >
                                <c:set var="totalPriceCart" value="${totalPriceCart + item.price}" />
                            <tr>                       
                                <td>${item.name}</td>
                                <td>${item.description}</td>
                                <td>${item.orderDate}</td>
                                <td>$ ${item.price}</td>      
                            </tr>
                        </c:forEach>
                        <th style="border-bottom: none"></th>
                        <th style="border-bottom: none"></th>
                        <th style="border-bottom: none; text-align: center">
                            <button class="button" onclick="openPopup('${cart.getOrderId()}', '${cart.getItemTypeId(count.index)}')">Change</button>
                            <button class="button button-delete btn-delete-all-item" onclick="openPopupDelete('${cart.getOrderId()}', '${cart.getOrderDetailId(count.index)}')">Delete Item</button>                                                                 
                        </th>
                        <tr style="border-top: 1px solid black"></tr>
                    </c:forEach>
                </table>
                <div class="card-price"> 
                    <form action="DispatcherServlet" method="POST">
                        <input type="hidden" name="idOrder" value="${idOrder}"/>
                        <input type="hidden" name="amount" value="${totalPriceCart}"/>
                        <h3>Total: ${totalPriceCart}</h3>
                        <input  type="submit" class="btn btn-secondary w-25 mt-3" name="btAction" value="Payment"/>
                    </form>
                </div>
            </c:if>

            <!-- pop-up booking photo schedule -->
            <div id="popup" class="popup">
                <c:set var="totalPrice" value="0" />
                <div id="popupContent" class="popup-content">
                    <span class="close" onclick="closePopup()">&times;</span>
                    <h2>Booking Photography Schedule</h2>
                    <form action="DispatcherServlet" method="POST">
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

            <div id="popupDelete" class="popup">
                <div id="popupContent" class="popup-content">
                    <span class="close" onclick="closePopup()">&times;</span>

                    <h2>Warning</h2>
                    <p class="warning-message">Are you sure!!!</p>
                    <div class="form-group delete-pop-up-actions">                       
                        <button class="button button-gap" onclick="closePopup()" >Cancel</button>
                        <form action="DispatcherServlet" method="POST">
                            <input type="hidden" id="orderId" name="orderId">
                            <input type="hidden" id="orderDetailId" name="orderDetailId">
                            <input type="submit" value="DeleteItem" name="btAction" class="button button-delete" />
                        </form>
                    </div>

                </div>
            </div>

        </div>

        <jsp:include page="footer.jsp"></jsp:include>
        <script>

            function openPopup(orderId, itemId) {
                document.getElementById('txtOrderId').value = orderId;
                document.getElementById('txtItemId').value = itemId;

                console.log(orderId);
                console.log(itemId);
                console.log(document.getElementById('txtOrderId').value);
                console.log(document.getElementById('txtItemId').value);

                document.getElementById('popup').style.display = 'block';
            }

            function openPopupDelete(orderId, orderDetailId) {
                document.getElementById('orderId').value = orderId;
                document.getElementById('orderDetailId').value = orderDetailId;

                document.getElementById('popupDelete').style.display = 'block';
            }

            function closePopup() {
                document.getElementById('popup').style.display = 'none';
                document.getElementById('popupDelete').style.display = 'none';
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
                timeRangeInput.value = currentDateString;
            });

        </script>
    </body>

</html>
