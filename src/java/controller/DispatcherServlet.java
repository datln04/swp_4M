/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.DressPhotoComboDAO;
import dao.LocationDAO;
import dao.PhotographyStudiosDAO;
import dao.RentalProductDAO;
import dto.DressPhotoCombo;
import dto.Location;
import dto.OrderDetail;
import dto.PhotographyStudio;
import dto.Profile;
import dto.RentalProduct;
import java.io.IOException;
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

/**
 *
 * @author ptd
 */
public class DispatcherServlet extends HttpServlet {

    public final String LOGIN_SERVLET = "LoginServlet";
    public final String LOGOUT_SERVLET = "LogoutServlet";
    public final String SEARCH_SERVLET = "SearchServlet";
    public final String FILTER_SERVLET = "FilterServlet";
    public final String UPDATE_PROFILE_SERVLET = "UpdateProfileServlet";
    public final String DELETE_PROFILE_SERVLET = "DeleteProfileServlet";
    public final String DELETE_ACCOUNT_SERVLET = "DeleteAccountServlet";
    public final String CATEGORY_SERVLET = "CategoryServlet";
    public final String BOOK_SCHEDULE = "BookScheduleServlet";
    public final String DELETE_CART_ITEM = "DeleteCardItemServlet";
    public final String EDIT_ACCOUNT_SERVLET = "EditAccountServlet";
    public final String UPDATE_ORDER_ADMIN_SERVLET = "UpdateOrderAdminServlet";
    public final String DELETE_ORDER_SERVLET = "DeleteOrderServlet";
    public final String COMFIRM_SCHEDULE_SERVLET = "ConfirmScheduleServlet";

    public final String UPDATE_LOCATION_SERVLET = "UpdateLocationServlet";
    public final String DELETE_LOCATION_SERVLET = "DeleteLocationServlet";

    public final String HOME_PAGE = "home.jsp";

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

        String action = request.getParameter("btAction");
        String url = HOME_PAGE;

        HttpSession session = request.getSession();
        Profile profile = (Profile) session.getAttribute("USER");
        LocationDAO locationDAO = new LocationDAO();
        RentalProductDAO rentalDAO = new RentalProductDAO();
        PhotographyStudiosDAO photoDAO = new PhotographyStudiosDAO();
        DressPhotoComboDAO dressPhotoComboDAO = new DressPhotoComboDAO();

        try {
            if (action == null || "Logout".equals(action) || "Home".equals(action)) {

                List<Location> listLocation = locationDAO.getAllLocation();
                List<RentalProduct> listProduct = rentalDAO.getAllRentalProduct();
                List<PhotographyStudio> listStudio = photoDAO.getAllPhotographyStudio();
                List<DressPhotoCombo> listCombo = dressPhotoComboDAO.getAllDressPhotoCombo();

                List<OrderDetail> listOrder = new ArrayList<>();

                // add location into list order
                for (Location location : listLocation) {
                    OrderDetail orderDetail = new OrderDetail();
                    orderDetail.setName(location.getName());
                    orderDetail.setDescription(location.getDescription());
                    orderDetail.setPrice(location.getPrice());
                    orderDetail.setOrderDate(""); // Set the order date as per your requirements
                    orderDetail.setActive(location.isActive());
                    orderDetail.setOrderId(0); // Assuming orderId is not available for now
                    orderDetail.setItemId(location.getId()); // Map locationId to itemId
                    orderDetail.setItemType("location"); // Set the itemType as "location"
                    orderDetail.setImage(location.getImage());

                    listOrder.add(orderDetail);
                }

                // product
                for (PhotographyStudio studio : listStudio) {
                    OrderDetail orderDetail = new OrderDetail();
                    orderDetail.setName(studio.getName());
                    orderDetail.setDescription(studio.getDescription());
                    orderDetail.setPrice(studio.getPrice());
                    orderDetail.setOrderDate(""); // Set the order date as per your requirements
                    orderDetail.setActive(studio.isActive());
                    orderDetail.setOrderId(0); // Assuming orderId is not available for now
                    orderDetail.setItemId(studio.getId()); // Map locationId to itemId
                    orderDetail.setItemType("studio"); // Set the itemType as "location"
                    orderDetail.setImage(studio.getImage());

                    listOrder.add(orderDetail);
                }

                // studio               
                for (RentalProduct product : listProduct) {
                    OrderDetail orderDetail = new OrderDetail();
                    orderDetail.setName(product.getName());
                    orderDetail.setDescription(product.getDescription());
                    orderDetail.setPrice(product.getPrice());
                    orderDetail.setOrderDate(""); // Set the order date as per your requirements
                    orderDetail.setActive(product.isActive());
                    orderDetail.setOrderId(0); // Assuming orderId is not available for now
                    orderDetail.setItemId(product.getId()); // Map locationId to itemId
                    orderDetail.setItemType("rental_product"); // Set the itemType as "location"
                    orderDetail.setImage(product.getImage());
                    listOrder.add(orderDetail);
                }

                // combo               
                for (DressPhotoCombo combo : listCombo) {
                    OrderDetail orderDetail = new OrderDetail();
                    orderDetail.setName(combo.getComboName());
                    orderDetail.setDescription(combo.getComboDescription());
                    orderDetail.setPrice(combo.getPrice());
                    orderDetail.setOrderDate(""); // Set the order date as per your requirements
                    orderDetail.setActive(combo.isActive());
                    orderDetail.setOrderId(0); // Assuming orderId is not available for now
                    orderDetail.setItemId(combo.getId()); // Map locationId to itemId
                    orderDetail.setItemType("combo"); // Set the itemType as "location"
                    orderDetail.setImage(combo.getImage());

                    listOrder.add(orderDetail);
                }

                // Set the number of entities per page
                int entitiesPerPage = 6;

                // Calculate the total pages for all lists combined
                int totalEntities = listOrder.size();
                int totalPages = (int) Math.ceil((double) totalEntities / entitiesPerPage);

                // Retrieve the current page number from the request parameters
                int currentPage = getCurrentPage(request, "page", totalPages);

                // Retrieve the sublist of entities based on the current page number
//                List<Location> locationPage = getPageEntities(listLocation, currentPage, entitiesPerPage);
//                List<RentalProduct> productPage = getPageEntities(listProduct, currentPage, entitiesPerPage);
//                List<DressPhotoCombo> dessertPage = getPageEntities(listCombo, currentPage, entitiesPerPage);
//                List<PhotographyStudio> studioPage = getPageEntities(listStudio, currentPage, entitiesPerPage);
//
//                request.setAttribute("LIST_LOCATION", locationPage);
//                request.setAttribute("LIST_PRODUCT", productPage);
//                request.setAttribute("LIST_COMBO", dessertPage);
//                request.setAttribute("LIST_STUDIO", studioPage);

                List<OrderDetail> listOrderDetailPage = getPageEntities(listOrder, currentPage, entitiesPerPage);
                request.setAttribute("LIST_ORDER_PAGING", listOrderDetailPage);

                request.setAttribute("totalPages", totalPages);
                request.setAttribute("currentPage", currentPage);

            }
            if ("Login".equals(action)) {
                url = LOGIN_SERVLET;
            } else if ("Logout".equals(action)) {
                url = LOGOUT_SERVLET;
            } else if ("Search".equals(action)) {
                url = SEARCH_SERVLET;
            } else if ("UpdateLocation".equals(action)) {
                url = UPDATE_LOCATION_SERVLET;
            } else if ("DeleteLocation".equals(action)) {
                url = DELETE_LOCATION_SERVLET;
            } else if ("Filter".equals(action)) {
                url = FILTER_SERVLET;
            } else if ("Save Profile".equals(action)) {
                url = UPDATE_PROFILE_SERVLET;
            } else if ("Delete Profile".equals(action)) {
                url = DELETE_PROFILE_SERVLET;
            } else if ("Delete Account".equals(action)) {
                url = DELETE_ACCOUNT_SERVLET;
            } else if ("CategoryFilter".equals(action)) {
                url = CATEGORY_SERVLET;
            } else if ("Book Schedule".equals(action)) {
                url = BOOK_SCHEDULE;
            } else if ("DeleteItem".equals(action)) {
                url = DELETE_CART_ITEM;
            } else if ("EditAccount".equals(action)) {
                url = EDIT_ACCOUNT_SERVLET;
            } else if ("Update Order".equals(action)) {
                url = UPDATE_ORDER_ADMIN_SERVLET;
            } else if ("Delete Order".equals(action)) {
                url = DELETE_ORDER_SERVLET;
            } else if ("Confirm Schedule".equals(action)) {
                url = COMFIRM_SCHEDULE_SERVLET;
            }
        } catch (NamingException ex) {
            log("DispatcherServlet_NamingException: " + ex.getMessage());
        } catch (SQLException ex) {
            log("DispatcherServlet_SQLException " + ex.getMessage());
        } finally {
            RequestDispatcher dispatcher = request.getRequestDispatcher(url);
            dispatcher.forward(request, response);
        }
    }

    private int getCurrentPage(HttpServletRequest request, String paramName, int totalPages) {
        int currentPage = 1; // Default to the first page
        String pageParam = request.getParameter(paramName);
        if (pageParam != null && !pageParam.isEmpty()) {
            currentPage = Integer.parseInt(pageParam);
            if (currentPage < 1) {
                currentPage = 1;
            } else if (currentPage > totalPages) {
                currentPage = totalPages;
            }
        }
        return currentPage;
    }

    private <T> List<T> getPageEntities(List<T> entityList, int currentPage, int entitiesPerPage) {
        int startIndex = (currentPage - 1) * entitiesPerPage;
        int endIndex = Math.min(startIndex + entitiesPerPage, entityList.size());
        return entityList.subList(startIndex, endIndex);
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
