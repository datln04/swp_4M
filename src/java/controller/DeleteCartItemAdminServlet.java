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
import dto.RentalProduct;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import util.Contant;
import util.PaginationHelper;

/**
 *
 * @author ptd
 */
public class DeleteCartItemAdminServlet extends HttpServlet {

    public final String ERROR_PAGE = "error.jsp";
    public final String ADMIN_PAGE = "admin.jsp";

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
        String orderId = request.getParameter("orderId");
        String orderDetailId = request.getParameter("orderDetailId");
        String url = ERROR_PAGE;

        OrderDAO orderDAO = new OrderDAO();
        OrderDetailDAO orderDetailDAO = new OrderDetailDAO();
        PhotoScheduleDAO scheduleDAO = new PhotoScheduleDAO();
        LocationDAO locationDAO = new LocationDAO();
        PhotographyStudiosDAO studioDAO = new PhotographyStudiosDAO();
        RentalProductDAO productDAO = new RentalProductDAO();
        DressPhotoComboDAO comboDAO = new DressPhotoComboDAO();

        HttpSession session = request.getSession();

        try {
            if (session != null) {

                // get order Id and orderDetailRemove 
                Order existOrder = orderDAO.getOrderAdminById(Integer.parseInt(orderId));

                if (existOrder != null) {
                    // from orderId -> get order detail available 
                    List<OrderDetail> listOrderDetail = orderDetailDAO.getOrderDetailByOrderId(existOrder.getOrderId());

                    // filter orderDetailRemove include in list available 
                    for (OrderDetail orderDetail : listOrderDetail) {
                        if (orderDetail.getOrderDetailId() == Integer.parseInt(orderDetailId)) {

                            if ("rental_product".equals(orderDetail.getItemType())) {
                                RentalProduct product = productDAO.getRentalProductById(orderDetail.getItemId());
                                if (product != null) {
                                    int stock = product.getStock() + 1;
                                    boolean resultProduct = productDAO.setStockRentalProduct(orderDetail.getItemId(), stock);

                                    if (resultProduct) {

                                        // remove order detail
                                        boolean result = orderDetailDAO.deleteOrderDetail(orderDetail.getOrderDetailId());

                                        if (result) {
                                            url = ADMIN_PAGE;
                                        }
                                    }
                                }
                            } else if ("combo".equals(orderDetail.getItemType())) {
                                DressPhotoCombo combo = comboDAO.getComboByIdDelete(orderDetail.getItemId());
                                if (combo != null) {
                                    int stock = combo.getStock() + 1;
                                    boolean resultCombo = comboDAO.setStockCombo(orderDetail.getItemId(), stock);

                                    if (resultCombo) {

                                        // remove order detail
                                        boolean result = orderDetailDAO.deleteOrderDetail(orderDetail.getOrderDetailId());

                                        if (result) {
                                            url = ADMIN_PAGE;
                                        }
                                    }
                                }

                            } else {

                                // remove order detail
                                boolean result = orderDetailDAO.deleteOrderDetail(orderDetail.getOrderDetailId());

                                if (result) {
                                    url = ADMIN_PAGE;
                                }
                            }

                        }
                    }

                    // manage order
                    List<Order> listOrder = orderDAO.getAllOrder();
                    List<OrderItem> listPhotoScheduleItem = new ArrayList<>();

                    if (listOrder.size() > 0) {
                        for (Order order : listOrder) {
                            List<OrderDetail> listOrderDetail1 = orderDetailDAO.getOrderDetailByOrderId(order.getOrderId());

                            for (OrderDetail detail : listOrderDetail1) {
                                //item_id and item_type --> add schedule photo
                                if (detail.getItemType().equals("photo_schedule")) {
                                    PhotoSchedule photoSchedule = scheduleDAO.getPhotoScheduleById(detail.getItemId());

                                    // get item
                                    Location location = locationDAO.getLocationById(photoSchedule.getLocationId());
                                    PhotographyStudio studio = studioDAO.getStudioById(photoSchedule.getStudioId());

                                    // init photo schedule
                                    OrderItem photoScheduleItem = new OrderItem();
                                    List<OrderDetail> listScheduleOrderDetail = new ArrayList<>();

                                    // add item into list
                                    OrderDetail detailLocation = new OrderDetail(detail.getOrderDetailId(), location.getName(), location.getDescription(), location.getPrice(), photoSchedule.getScheduleDate(), order.getOrderId(), photoSchedule.getScheduleId(), "photo_schedule");
                                    detailLocation.setStatus(order.getStatus());
                                    listScheduleOrderDetail.add(detailLocation);

                                    OrderDetail detailStudio = new OrderDetail(detail.getOrderDetailId(), studio.getName(), studio.getDescription(), studio.getPrice(), photoSchedule.getScheduleDate(), order.getOrderId(), photoSchedule.getScheduleId(), "photo_schedule");
                                    detailStudio.setStatus(order.getStatus());
                                    listScheduleOrderDetail.add(detailStudio);

                                    // add list into item photo schedule    
                                    photoScheduleItem.setList(listScheduleOrderDetail);
                                    listPhotoScheduleItem.add(photoScheduleItem);
                                } else {
                                    OrderItem photoScheduleItem = new OrderItem();
                                    List<OrderDetail> listScheduleOrderDetail = new ArrayList<>();
                                    detail.setStatus(order.getStatus());
                                    listScheduleOrderDetail.add(detail);
                                    // add list into item photo schedule    
                                    photoScheduleItem.setList(listScheduleOrderDetail);
                                    listPhotoScheduleItem.add(photoScheduleItem);
                                }
                            }

                        }
                        session.setAttribute("LIST_ORDER_ADMIN", listPhotoScheduleItem);
                    }

                }

            }
        } catch (NamingException ex) {
            log("DeleteCartItemServlet_NamingException: " + ex.getMessage());
        } catch (SQLException ex) {
            log("DeleteCartItemServlet_SQLException " + ex.getMessage());
        } finally {
            RequestDispatcher dispatcher = request.getRequestDispatcher(url);
            dispatcher.forward(request, response);
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
