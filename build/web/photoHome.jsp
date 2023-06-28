<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Product Management</title>
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



        </style>

    </head>
    <body>
        <jsp:include page="header.jsp"></jsp:include>
        <c:set var="profile" value="${sessionScope.USER}" />
        <div class="container-parent">
            <div class="tab">
                <button class="tablinks active" onclick="openTab(event, 'Tab1')">Location</button>
                <button class="tablinks" onclick="openTab(event, 'Tab2')">Schedule Confirm</button>


            </div>


            <div id="Tab1" class="tabcontent" style="display: block;">
                <h3>Location For Rent Management</h3>

                <table>
                    <tr>
                        <th>Image</th>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Description</th>
                        <th>Price</th>       
                        <th>Actions</th>
                    </tr>

                    <c:forEach items="${sessionScope.LOCATIONS}" var="location">
                        <tr>
                            <td><img src="${location.image}" alt="location image"></td>
                            <td>${location.id}</td>
                            <td>${location.name}</td>
                            <td>${location.description}</td>
                            <td>$ ${location.price}</td>                   
                            <td class="actions" style="width: fit-content">
                                <button class="button" onclick="openPopup('${location.image}', ${location.id}, '${location.name}', '${location.description}', ${location.price})">Update Location</button>                     
                                <button class="button button-delete" onclick="openPopupDelete('${location.id}')">Delete Location</button>                                                                 
                            </td>
                        </tr>
                    </c:forEach>


                </table>
            </div>
            <div id="Tab2" class="tabcontent" >
                <h3>Booking Schedule Confirmation</h3>
                <table>
                    <tr>                                       
                        <th>Name</th>
                        <th>Description</th>                      
                        <th>Photo Date</th>  
                        <th>Price</th>    
                        <th style="text-align: center">Actions</th>
                    </tr>
                    <c:forEach items="${sessionScope.LIST_PHOTO_SCHEDULE_STAFF}" var="schedule" varStatus="count">
                        <th style="border: none;">${count.index + 1}</th>
                        <c:forEach items="${schedule.list}" var="scheduleItem" >
                            <tr>                       
                                <td>${scheduleItem.name}</td>
                                <td>${scheduleItem.description}</td>
                                <td>${scheduleItem.orderDate}</td>
                                <td>$ ${scheduleItem.price}</td>      
                            </tr>
                        </c:forEach>
                        <th style="border-bottom: none"></th>
                        <th style="border-bottom: none"></th>
                        <th style="border-bottom: none"></th>
                        <th style="border-bottom: none"></th>

                        <th style="border-bottom: none; text-align: center">
                            <form action="DispatcherServlet" method="POST">
                                <input type="hidden" name="orderId" value="${schedule.orderId}"/>
                                <input type="hidden" name="scheduleId" value="${schedule.orderDetailId}"/>
                                <input type="submit" class="button btn-primary btn-delete-all-item" value="Confirm Schedule" name="btAction"/>  
                            </form>
                                                                                           
                        </th>
                        <tr style="border-top: 1px solid black;"></tr>
                    </c:forEach>
                </table>
            </div>

            <div id="popup" class="popup">
                <div id="popupContent" class="popup-content">
                    <span class="close" onclick="closePopup()">&times;</span>
                    <h2>Update Location</h2>
                    <form action="DispatcherServlet" method="POST">
                        <input type="hidden" id="locationId" name="txtLocationId">

                        <p for="locationImage">Image Link:</p>
                        <input type="text" id="locationImage" name="txtLocationImage"/>

                        <p for="locationName">Name:</p>
                        <input type="text" id="locationName" name="txtLocationName"/>

                        <p for="locationDescription">Description:</p>
                        <input type="text" id="locationDescription" name="txtLocationDescription"/>

                        <p for="locationPrice">Price:</p>
                        <input type="text" id="locationPrice" name="txtLocationPrice"/>
                        <br/> 
                        <input type="submit" value="UpdateLocation" name="btAction" class="button button-update"/>
                    </form>
                </div>
            </div>

            <!-- Popup for photo schedule -->
            <div id="popupSchedule" class="popup">
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
                            <input type="hidden" id="locationDeleteId" name="txtDeleteId">
                            <input type="submit" value="DeleteLocation" name="btAction" class="button button-delete" />
                        </form>
                    </div>
                </div>
            </div>

        </div>

        <jsp:include page="footer.jsp"></jsp:include>
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


            function openPopup(image, id, name, description, price) {
                document.getElementById('locationId').value = id;
                document.getElementById('locationName').value = name;
                document.getElementById('locationDescription').value = description;
                document.getElementById('locationPrice').value = price;
                document.getElementById('locationImage').value = image;

                document.getElementById('popup').style.display = 'block';
            }
            
            function openPopupSchedule(id, name, description, price, orderDate) {
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
                document.getElementById('popupSchedule').style.display = 'none';
                document.getElementById('popupDelete').style.display = 'none';
            }
        </script>
    </body>

</html>
