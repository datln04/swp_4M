/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.CartDAO;
import dao.LocationDAO;
import dao.OrderDAO;
import dao.OrderDetailDAO;
import dao.PhotoScheduleDAO;
import dao.PhotographyStudiosDAO;
import dto.Cart;
import dto.Location;
import dto.Order;
import dto.OrderDetail;
import dto.PhotoSchedule;
import dto.OrderItem;
import dto.PhotographyStudio;
import dto.Profile;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ptd
 */
@WebServlet(name = "BookScheduleServlet", urlPatterns = {"/BookScheduleServlet"})
public class BookScheduleServlet extends HttpServlet {

    public final String ERROR_PAGE = "error.jsp";
    public final String CART_PAGE = "cart.jsp";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String url = ERROR_PAGE;

        String locationId = request.getParameter("location");
        String studioId = request.getParameter("studio");
        String time = request.getParameter("timeRange");
        String price = request.getParameter("price");

        // create booking schedule - create order for card - order detail
        PhotoScheduleDAO photoDAO = new PhotoScheduleDAO();
        LocationDAO locationDAO = new LocationDAO();
        PhotographyStudiosDAO studioDAO = new PhotographyStudiosDAO();
        OrderDAO orderDAO = new OrderDAO();
        OrderDetailDAO orderDetailDAO = new OrderDetailDAO();
        CartDAO cardDAO = new CartDAO();

        try {
            HttpSession session = request.getSession();
            Profile profile = (Profile) session.getAttribute("USER");
            if (session != null && profile != null) {
                // add photo schedule
                boolean photoResult = photoDAO.insertPhotoSchedule(new PhotoSchedule(1, profile.getProfileId(), Integer.parseInt(locationId), Integer.parseInt(studioId), time, "pending"));

                if (photoResult) {
//                    //get last photo schedule Id
//                    int photoScheduleId = photoDAO.getLastIdOfPhotoSchedule();

                    // get orderbyProfileId
                    Order orderExist = orderDAO.getOrderByProfileId(profile.getProfileId());
                    int orderId = 0;
                    if (orderExist != null) {
                        orderId = orderExist.getOrderId();
                    } else {
                        // add order and order Detail
                        boolean orderResult = orderDAO.insertOrder(new Order(profile.getProfileId(), time, "pending"));

                        // get last order to add order detail
                        if (orderResult) {
                            Order order = orderDAO.getLastOrder();
                            orderId = order.getOrderId();
                        }
                    }

                    if (orderId > 0) {
                        Location location = locationDAO.getLocationById(Integer.parseInt(locationId));
                        PhotographyStudio studio = studioDAO.getStudioById(Integer.parseInt(studioId));

                        // save order detail by order Id
                        boolean orderDetailLocationResult = orderDetailDAO.insertOrderDetail(new OrderDetail(location.getId(), location.getName(), location.getDescription(), location.getPrice(), time, orderId, location.getId(), "location"));
                        boolean orderDetailStudioResult = orderDetailDAO.insertOrderDetail(new OrderDetail(studio.getId(), studio.getName(), studio.getDescription(), studio.getPrice(), time, orderId, studio.getId(), "studio"));

                        if (orderDetailLocationResult && orderDetailStudioResult) {
                            List<OrderItem> listPhotoScheduleItem = (List<OrderItem>) session.getAttribute("LIST_PHOTO_SCHEDULE");
                            List<OrderItem> listPhotoScheItem = new ArrayList<>();

                            if (photoResult) {
                                OrderDetail detailLocation = new OrderDetail(location.getId(), location.getName(), location.getDescription(), location.getPrice(), time);
                                OrderDetail detailStudio = new OrderDetail(studio.getId(), studio.getName(), studio.getDescription(), studio.getPrice(), time);
                                if (listPhotoScheduleItem.size() < 1 || listPhotoScheduleItem != null) {
                                    OrderItem photoScheduleItem = new OrderItem();
                                    photoScheduleItem.getList().add(detailLocation);
                                    photoScheduleItem.getList().add(detailStudio);
                                    listPhotoScheduleItem.add(photoScheduleItem);
                                    session.setAttribute("LIST_PHOTO_SCHEDULE", listPhotoScheduleItem);
                                    url = CART_PAGE;
                                } else {
                                    OrderItem photoScheItem = new OrderItem();
                                    photoScheItem.getList().add(detailLocation);
                                    photoScheItem.getList().add(detailStudio);
                                    listPhotoScheItem.add(photoScheItem);
                                    session.setAttribute("LIST_PHOTO_SCHEDULE", listPhotoScheItem);
                                    url = CART_PAGE;
                                }
                            }
                        }
                    }

                }

                // add photo schedule to cart db
                // create order 
//                boolean resultOrder = orderDAO.insertOrder(new Order(profile.getProfileId(), time, true));
//                if (resultOrder) {
//                    // add order detail
//                    Order lastOrder = orderDAO.getLastOrder();
//                    
//                    boolean insertOrderDetailLocation = detailDAO.insertOrderDetail(new OrderDetail(1,
//                            location.getName(), location.getDescription(), location.getPrice(), time, true,lastOrder.getOrderId()));
//
//                    boolean insertOrderDetailStudio = detailDAO.insertOrderDetail(new OrderDetail(1,
//                            studio.getName(), studio.getDescription(), studio.getPrice(), time, true,lastOrder.getOrderId()));
//
//                    if (photoResult && insertOrderDetailLocation && insertOrderDetailStudio) {
//                        List<OrderDetail> list = detailDAO.getTop2OrderDetail();
//                        url = CART_PAGE;
//                        session.setAttribute("LIST_PHOTO_SCHEDULE", list);
//                    }
//                }
//
//            }
            }
        } catch (NamingException ex) {
            log("BookScheduleServlet_NamingException: " + ex.getMessage());
        } catch (SQLException ex) {
            log("BookScheduleServlet_SQLException " + ex.getMessage());
        } finally {
//            RequestDispatcher dispatcher = request.getRequestDispatcher(url);
//            dispatcher.forward(request, response);
            response.sendRedirect(url);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
