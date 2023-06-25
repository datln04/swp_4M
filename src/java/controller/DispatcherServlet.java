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
import dto.PhotographyStudio;
import dto.Profile;
import dto.RentalProduct;
import java.io.IOException;
import java.sql.SQLException;
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

                if (listLocation.size() > 0 && listProduct.size() > 0 && listStudio.size() > 0) {
                    request.setAttribute("LIST_LOCATION", listLocation);
                    request.setAttribute("LIST_PRODUCT", listProduct);
                    request.setAttribute("LIST_STUDIO", listStudio);
                    request.setAttribute("LIST_COMBO", listCombo);
                }
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
