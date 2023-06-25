/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.LocationDAO;
import dao.PhotoScheduleDAO;
import dao.PhotographyStudiosDAO;
import dao.studioStaffDAO;
import dto.Location;
import dto.OrderDetail;
import dto.OrderItem;
import dto.PhotoSchedule;
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
@WebServlet(name = "ConfirmScheduleServlet", urlPatterns = {"/ConfirmScheduleServlet"})
public class ConfirmScheduleServlet extends HttpServlet {

    public final String ERROR_PAGE = "error.jsp";
    public final String PHOTO_HOME_PAGE = "photoHome.jsp";

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

        String scheduleId = request.getParameter("scheduleId");
        String url = ERROR_PAGE;

        PhotoScheduleDAO scheduleDAO = new PhotoScheduleDAO();
        LocationDAO locationDAO = new LocationDAO();
        PhotographyStudiosDAO studioDAO = new PhotographyStudiosDAO();
        studioStaffDAO studioStaffDAO = new studioStaffDAO();
        HttpSession session = request.getSession();

        try {
            if (!scheduleId.isEmpty() && session != null) {
                Profile profile = (Profile) session.getAttribute("USER");
                boolean result = scheduleDAO.confirmScheduleByScheduleId(Integer.parseInt(scheduleId));
                if (result) {
                    // get studio of staff
                    int studioId = studioStaffDAO.getStudioIdByProfileId(profile.getProfileId());

                    //get photo schedule by studio
                    List<PhotoSchedule> photoScheduleList = scheduleDAO.getPhotoScheduleByStudioId(studioId);
                    List<OrderItem> listPhotoScheItem = new ArrayList<>();
                    // get booking photo schedule
                    for (PhotoSchedule photoSchedule : photoScheduleList) {
                        Location location = locationDAO.getLocationById(photoSchedule.getLocationId());
                        PhotographyStudio studio = studioDAO.getStudioById(photoSchedule.getStudioId());

                        OrderDetail detailLocation = new OrderDetail(photoSchedule.getScheduleId(), location.getName(), location.getDescription(), location.getPrice(), photoSchedule.getScheduleDate(), location.getId(), "location");
                        OrderDetail detailStudio = new OrderDetail(photoSchedule.getScheduleId(), studio.getName(), studio.getDescription(), studio.getPrice(), photoSchedule.getScheduleDate(), studio.getId(), "studio");

                        OrderItem photoScheItem = new OrderItem();
                        photoScheItem.getList().add(detailLocation);
                        photoScheItem.getList().add(detailStudio);
                        listPhotoScheItem.add(photoScheItem);
                        session.setAttribute("LIST_PHOTO_SCHEDULE_STAFF", listPhotoScheItem);

                    }

                    // get location for manage
                    List<Location> listLocation = locationDAO.getAllLocation();
                    session.setAttribute("LOCATIONS", listLocation);
                    
                    session.setAttribute("LIST_PHOTO_SCHEDULE_STAFF", listPhotoScheItem);
                    
                    url = PHOTO_HOME_PAGE;
                }
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
