/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.OrderDAO;
import dao.OrderDetailDAO;
import dto.Order;
import dto.OrderDetail;
import dto.Profile;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
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
@WebServlet(name = "ConfirmProductServlet", urlPatterns = {"/ConfirmProductServlet"})
public class ConfirmProductServlet extends HttpServlet {

    public final String ERROR_PAGE = "error.jsp";
    public final String PRODUCT_HOME_PAGE = "rentalPage.jsp";
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

        String url = ERROR_PAGE;
        String orderId = request.getParameter("orderId");
        String orderDetailId = request.getParameter("orderDetailId");

        OrderDAO dao = new OrderDAO();
        OrderDetailDAO orderDetailDAO = new OrderDetailDAO();
        HttpSession session = request.getSession();
        Profile profile = (Profile) session.getAttribute("USER");

        try {
            if (!orderId.isEmpty() && session != null) {
                boolean updateDetail = orderDetailDAO.deleteOrderDetailById(Integer.parseInt(orderDetailId));

                if (updateDetail) {
                    List<OrderDetail> listOrderLeft = orderDetailDAO.getOrderDetailByOrderId(Integer.parseInt(orderId));

                    if (listOrderLeft.size() > 0) {
                        List<Order> listOrder = "admin".equals(profile.getRoleName()) ? dao.getAllOrder() : dao.getAllOrderStaff();

                        if (listOrder.size() > 0) {
                            List<OrderDetail> listProduct = new ArrayList<>();

                            for (Order order : listOrder) {
                                List<OrderDetail> listOrderDetail = orderDetailDAO.getOrderDetailByOrderId(order.getOrderId());

                                for (OrderDetail detail : listOrderDetail) {
                                    //item_id and item_type --> add schedule photo
                                    if (!detail.getItemType().equals("photo_schedule-location") && !detail.getItemType().equals("photo_schedule-studio")) {
                                        detail.setStatus(order.getStatus());
                                        listProduct.add(detail);
                                    }
                                }
                            }

                            session.setAttribute("LIST_CART_PRODUCT_ADMIN", listProduct);
                        }

                    } else {
                        boolean result = dao.setStatusOrderById(Integer.parseInt(orderId), "confirm");
                        if (result) {
                            List<Order> details = "admin".equals(profile.getRoleName()) ? dao.getAllOrder() : dao.getAllOrderStaff();

                            if (details.size() > 0) {
                                List<OrderDetail> listProduct = new ArrayList<>();
                                

                                for (Order order : details) {
                                    List<OrderDetail> listOrderDetail2 = orderDetailDAO.getOrderDetailByOrderId(order.getOrderId());
                                  
                                    for (OrderDetail detail : listOrderDetail2) {
                                        //item_id and item_type --> add schedule photo
                                        if (!detail.getItemType().equals("photo_schedule-location") && !detail.getItemType().equals("photo_schedule-studio")) {
                                            detail.setStatus(order.getStatus());
                                            listProduct.add(detail);
                                        }
                                    }
                                }

                                session.setAttribute("LIST_CART_PRODUCT_ADMIN", listProduct);

                            }
                        }
                    }
                }
                url = profile.getRoleName().equals("admin") ? ADMIN_PAGE : PRODUCT_HOME_PAGE;
            }
        } catch (NamingException ex) {
            log("LoginServlet_NamingException: " + ex.getMessage());
        } catch (SQLException ex) {
            log("LoginServlet_SQLException " + ex.getMessage());
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
