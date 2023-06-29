/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.DressPhotoComboDAO;
import dao.LocationDAO;
import dao.OrderDAO;
import dao.OrderDetailDAO;
import dao.PhotoScheduleDAO;
import dao.PhotographyStudiosDAO;
import dao.RentalProductDAO;
import dto.DressPhotoCombo;
import dto.Location;
import dto.Order;
import dto.OrderDetail;
import dto.OrderItem;
import dto.PhotoSchedule;
import dto.PhotographyStudio;
import dto.Profile;
import dto.RentalProduct;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.time.Period;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import util.Utilities;

/**
 *
 * @author ptd
 */
@WebServlet(name = "AddToCartServlet", urlPatterns = {"/AddToCartServlet"})
public class AddToCartServlet extends HttpServlet {

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

        String itemId = request.getParameter("itemId");
        String itemType = request.getParameter("itemType");
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String price = request.getParameter("price");

        String url = ERROR_PAGE;

        // create booking schedule - create order for card - order detail
        PhotoScheduleDAO photoDAO = new PhotoScheduleDAO();
        LocationDAO locationDAO = new LocationDAO();
        PhotographyStudiosDAO studioDAO = new PhotographyStudiosDAO();
        RentalProductDAO productDAO = new RentalProductDAO();
        OrderDAO orderDAO = new OrderDAO();
        DressPhotoComboDAO comboDAO = new DressPhotoComboDAO();
        OrderDetailDAO orderDetailDAO = new OrderDetailDAO();

        try {
            HttpSession session = request.getSession();
            Profile profile = (Profile) session.getAttribute("USER");
            if (session != null && profile != null) {
                String time = OffsetDateTime
                        .now(ZoneOffset.UTC)
                        .minus(Period.ofYears(1))
                        .toString(); // get orderbyProfileId
                Order orderExist = orderDAO.getOrderByProfileId(profile.getProfileId());
                int orderId = 0;
                if (orderExist != null) {
                    orderId = orderExist.getOrderId();
                } else {
                    // add order and order Detail
                    boolean orderResult = orderDAO.insertOrder(new Order(profile.getProfileId(), time, "create")); // see it

                    // get last order to add order detail
                    if (orderResult) {
                        Order order = orderDAO.getLastOrder();
                        orderId = order.getOrderId();
                    }
                }

                if (orderId > 0) {

                    // check if stock available for book
                    if ("rental_product".equals(itemType)) {
                        RentalProduct rentalProduct = productDAO.getRentalProductById(Integer.parseInt(itemId));

                        if (rentalProduct != null) {
                            int stock = rentalProduct.getStock() - 1;
                            boolean minusStock = productDAO.setStockRentalProduct(Integer.parseInt(itemId), stock);

                            if (minusStock) {
                                // todo
                                // add insert order detail
                                OrderDetail orderDetail = new OrderDetail(0, name, description, Double.parseDouble(price), time, orderId, Integer.parseInt(itemId), itemType);
                                boolean resultOrderDetail = orderDetailDAO.insertOrderDetail(orderDetail);

                                if (resultOrderDetail) {
                                    List<OrderDetail> listOrderDetailByOrder = orderDetailDAO.getOrderDetailByOrderId(orderId);

                                    List<OrderItem> listPhotoScheduleItem = new ArrayList<>();

                                    for (OrderDetail detail : listOrderDetailByOrder) {
                                        //item_id and item_type --> add schedule photo
                                        if (detail.getItemType().equals("photo_schedule")) {
                                            PhotoSchedule photoSchedule = photoDAO.getPhotoScheduleById(detail.getItemId());

                                            // get item
                                            Location location = locationDAO.getLocationById(photoSchedule.getLocationId());
                                            PhotographyStudio studio = studioDAO.getStudioById(photoSchedule.getStudioId());

                                            // init photo schedule
                                            OrderItem photoScheduleItem = new OrderItem();
                                            List<OrderDetail> listScheduleOrderDetail = new ArrayList<>();

                                            // add item into list
                                            listScheduleOrderDetail.add(new OrderDetail(detail.getOrderDetailId(), location.getName(), location.getDescription(), location.getPrice(), photoSchedule.getScheduleDate(), orderId, photoSchedule.getScheduleId(), "photo_schedule"));
                                            listScheduleOrderDetail.add(new OrderDetail(detail.getOrderDetailId(), studio.getName(), studio.getDescription(), studio.getPrice(), photoSchedule.getScheduleDate(), orderId, photoSchedule.getScheduleId(), "photo_schedule"));

                                            // add list into item photo schedule    
                                            photoScheduleItem.setList(listScheduleOrderDetail);
                                            listPhotoScheduleItem.add(photoScheduleItem);
                                        } else {
                                            OrderItem photoScheduleItem = new OrderItem();
                                            List<OrderDetail> listScheduleOrderDetail = new ArrayList<>();

                                            listScheduleOrderDetail.add(detail);
                                            // add list into item photo schedule    
                                            photoScheduleItem.setList(listScheduleOrderDetail);
                                            listPhotoScheduleItem.add(photoScheduleItem);
                                        }
                                    }
                                    url = CART_PAGE;
                                    session.setAttribute("LIST_CARR_ITEM", listPhotoScheduleItem);
                                    session.setAttribute("CART_ITEM", listPhotoScheduleItem.size());
                                }
                            }
                        }
                    } else if ("combo".equals(itemType)) {
                        DressPhotoCombo combo = comboDAO.getComboById(Integer.parseInt(itemId));

                        if (combo != null) {
                            int stock = combo.getStock() - 1;

                            boolean minusStock = comboDAO.setStockCombo(Integer.parseInt(itemId), stock);

                            if (minusStock) {
                                // add insert order detail
                                OrderDetail orderDetail = new OrderDetail(0, name, description, Double.parseDouble(price), time, orderId, Integer.parseInt(itemId), itemType);
                                boolean resultOrderDetail = orderDetailDAO.insertOrderDetail(orderDetail);

                                if (resultOrderDetail) {
                                    List<OrderDetail> listOrderDetailByOrder = orderDetailDAO.getOrderDetailByOrderId(orderId);

                                    List<OrderItem> listPhotoScheduleItem = new ArrayList<>();

                                    for (OrderDetail detail : listOrderDetailByOrder) {
                                        //item_id and item_type --> add schedule photo
                                        if (detail.getItemType().equals("photo_schedule")) {
                                            PhotoSchedule photoSchedule = photoDAO.getPhotoScheduleById(detail.getItemId());

                                            // get item
                                            Location location = locationDAO.getLocationById(photoSchedule.getLocationId());
                                            PhotographyStudio studio = studioDAO.getStudioById(photoSchedule.getStudioId());

                                            // init photo schedule
                                            OrderItem photoScheduleItem = new OrderItem();
                                            List<OrderDetail> listScheduleOrderDetail = new ArrayList<>();

                                            // add item into list
                                            listScheduleOrderDetail.add(new OrderDetail(detail.getOrderDetailId(), location.getName(), location.getDescription(), location.getPrice(), photoSchedule.getScheduleDate(), orderId, photoSchedule.getScheduleId(), "photo_schedule"));
                                            listScheduleOrderDetail.add(new OrderDetail(detail.getOrderDetailId(), studio.getName(), studio.getDescription(), studio.getPrice(), photoSchedule.getScheduleDate(), orderId, photoSchedule.getScheduleId(), "photo_schedule"));

                                            // add list into item photo schedule    
                                            photoScheduleItem.setList(listScheduleOrderDetail);
                                            listPhotoScheduleItem.add(photoScheduleItem);
                                        } else {
                                            OrderItem photoScheduleItem = new OrderItem();
                                            List<OrderDetail> listScheduleOrderDetail = new ArrayList<>();

                                            listScheduleOrderDetail.add(detail);
                                            // add list into item photo schedule    
                                            photoScheduleItem.setList(listScheduleOrderDetail);
                                            listPhotoScheduleItem.add(photoScheduleItem);
                                        }
                                    }
                                    url = CART_PAGE;
                                    session.setAttribute("LIST_CARR_ITEM", listPhotoScheduleItem);
                                    session.setAttribute("CART_ITEM", listPhotoScheduleItem.size());
                                }
                            }
                        }
                    } else {
                        // add insert order detail
                        OrderDetail orderDetail = new OrderDetail(0, name, description, Double.parseDouble(price), time, orderId, Integer.parseInt(itemId), itemType);
                        boolean resultOrderDetail = orderDetailDAO.insertOrderDetail(orderDetail);

                        if (resultOrderDetail) {
                            List<OrderDetail> listOrderDetailByOrder = orderDetailDAO.getOrderDetailByOrderId(orderId);

                            List<OrderItem> listPhotoScheduleItem = new ArrayList<>();

                            for (OrderDetail detail : listOrderDetailByOrder) {
                                //item_id and item_type --> add schedule photo
                                if (detail.getItemType().equals("photo_schedule")) {
                                    PhotoSchedule photoSchedule = photoDAO.getPhotoScheduleById(detail.getItemId());

                                    // get item
                                    Location location = locationDAO.getLocationById(photoSchedule.getLocationId());
                                    PhotographyStudio studio = studioDAO.getStudioById(photoSchedule.getStudioId());

                                    // init photo schedule
                                    OrderItem photoScheduleItem = new OrderItem();
                                    List<OrderDetail> listScheduleOrderDetail = new ArrayList<>();

                                    // add item into list
                                    listScheduleOrderDetail.add(new OrderDetail(detail.getOrderDetailId(), location.getName(), location.getDescription(), location.getPrice(), photoSchedule.getScheduleDate(), orderId, photoSchedule.getScheduleId(), "photo_schedule"));
                                    listScheduleOrderDetail.add(new OrderDetail(detail.getOrderDetailId(), studio.getName(), studio.getDescription(), studio.getPrice(), photoSchedule.getScheduleDate(), orderId, photoSchedule.getScheduleId(), "photo_schedule"));

                                    // add list into item photo schedule    
                                    photoScheduleItem.setList(listScheduleOrderDetail);
                                    listPhotoScheduleItem.add(photoScheduleItem);
                                } else {
                                    OrderItem photoScheduleItem = new OrderItem();
                                    List<OrderDetail> listScheduleOrderDetail = new ArrayList<>();

                                    listScheduleOrderDetail.add(detail);
                                    // add list into item photo schedule    
                                    photoScheduleItem.setList(listScheduleOrderDetail);
                                    listPhotoScheduleItem.add(photoScheduleItem);
                                }
                            }
                            url = CART_PAGE;
                            session.setAttribute("LIST_CARR_ITEM", listPhotoScheduleItem);
                            session.setAttribute("CART_ITEM", listPhotoScheduleItem.size());
                        }
                    }
                }

            } else {
                url = "login.jsp";
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
