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
import dto.RentalProduct;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ptd
 */
@WebServlet(name = "CategoryServlet", urlPatterns = {"/CategoryServlet"})
public class CategoryServlet extends HttpServlet {

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
        String url = HOME_PAGE;

        try {

            String value = request.getParameter("category");

            request.removeAttribute("LIST_LOCATION");
            request.removeAttribute("LIST_PRODUCT");
            request.removeAttribute("LIST_STUDIO");
            request.removeAttribute("LIST_COMBO");

            LocationDAO locationDAO = new LocationDAO();
            RentalProductDAO rentalDAO = new RentalProductDAO();
            PhotographyStudiosDAO photoDAO = new PhotographyStudiosDAO();
            DressPhotoComboDAO dressPhotoComboDAO = new DressPhotoComboDAO();

            List<Location> listLocation = locationDAO.getAllLocation();
            List<RentalProduct> listProduct = rentalDAO.getAllRentalProduct();
            List<PhotographyStudio> listStudio = photoDAO.getAllPhotographyStudio();
            List<DressPhotoCombo> listCombo = dressPhotoComboDAO.getAllDressPhotoCombo();
            if ("location".equals(value)) {
                request.setAttribute("LIST_LOCATION", listLocation);
            } else if ("product".equals(value)) {
                request.setAttribute("LIST_PRODUCT", listProduct);
            } else if ("studio".equals(value)) {
                request.setAttribute("LIST_STUDIO", listStudio);
            } else if ("combo".equals(value)) {
                request.setAttribute("LIST_COMBO", listCombo);
            } else {
                request.setAttribute("LIST_LOCATION", listLocation);
                request.setAttribute("LIST_PRODUCT", listProduct);
                request.setAttribute("LIST_STUDIO", listStudio);
                request.setAttribute("LIST_COMBO", listCombo);
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
