/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.PhotoSchedule;
import dto.PhotographyStudio;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import util.ConnectionConfig;

/**
 *
 * @author ptd
 */
public class PhotoScheduleDAO implements Serializable {

    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    public boolean insertPhotoSchedule(PhotoSchedule ps) throws NamingException, SQLException {
        boolean rs = false;
        try {
            conn = ConnectionConfig.getConnection();
            if (conn != null) {
                String sql = "insert dbo.photo_schedules(user_id,location_id,studio_id,schedule_date,status)\n"
                        + "values (?, ?, ?, ?, 'pending')";

                pst = conn.prepareStatement(sql);
                pst.setInt(1, ps.getUserId());
                pst.setInt(2, ps.getLocationId());
                pst.setInt(3, ps.getStudioId());
                pst.setString(4, ps.getScheduleDate());

                int result = pst.executeUpdate();

                if (result > 0) {
                    rs = true;
                }

            }
        } finally {
            if (pst != null) {
                pst.close();
            }
            if (conn != null) {
                conn.close();
            }
        }

        return rs;
    }

    public List<PhotoSchedule> getPhotoScheduleByUserId(int userId) throws NamingException, SQLException {
        List<PhotoSchedule> list = new ArrayList();
        try {
            conn = ConnectionConfig.getConnection();
            if (conn != null) {
                String sql = "select schedule_id, user_id, location_id, studio_id,schedule_date,status\n"
                        + "from photo_schedules\n"
                        + "where user_id = ? and status = 'confirm'";

                pst = conn.prepareStatement(sql);
                pst.setInt(1, userId);

                rs = pst.executeQuery();

                while (rs.next()) {
                    int scheduleId = rs.getInt("schedule_id");
                    int userID = rs.getInt("user_id");
                    int locationId = rs.getInt("location_id");
                    int studioId = rs.getInt("studio_id");
                    String scheduleDate = rs.getString("schedule_date");
                    String status = rs.getString("status");

                    list.add(new PhotoSchedule(scheduleId, userID, locationId, studioId, scheduleDate, status));
                }

            }
        } finally {
            if (pst != null) {
                pst.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return list;
    }

    public List<PhotoSchedule> getPhotoScheduleByStudioId(int studioId) throws NamingException, SQLException {
        List<PhotoSchedule> list = new ArrayList();
        try {
            conn = ConnectionConfig.getConnection();
            if (conn != null) {
                String sql = "select schedule_id,user_id,location_id,schedule_date,status\n"
                        + "from photo_schedules\n"
                        + "where studio_id = ? and status = 'confirm'";

                pst = conn.prepareStatement(sql);
                pst.setInt(1, studioId);

                rs = pst.executeQuery();

                while (rs.next()) {
                    int scheduleId = rs.getInt("schedule_id");
                    int userID = rs.getInt("user_id");
                    int locationId = rs.getInt("location_id");
                    String scheduleDate = rs.getString("schedule_date");
                    String status = rs.getString("status");

                    list.add(new PhotoSchedule(scheduleId, userID, locationId, studioId, scheduleDate, status));
                }

            }
        } finally {
            if (pst != null) {
                pst.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return list;
    }

    public int getLastIdOfPhotoSchedule() throws NamingException, SQLException {

        try {
            conn = ConnectionConfig.getConnection();
            if (conn != null) {
                String sql = "select top 1 schedule_id\n"
                        + "from photo_schedules\n"
                        + "order by schedule_id desc";

                pst = conn.prepareStatement(sql);

                rs = pst.executeQuery();

                if (rs.next()) {
                    int scheduleId = rs.getInt("schedule_id");

                    return scheduleId;
                }

            }
        } finally {
            if (pst != null) {
                pst.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return 0;

    }
    

    public boolean confirmScheduleByScheduleId(int scheduleId) throws NamingException, SQLException {
        try {
            conn = ConnectionConfig.getConnection();
            if (conn != null) {
                String sql = "update photo_schedules\n"
                        + "set status = 'complete'\n"
                        + "where schedule_id = ?";

                pst = conn.prepareStatement(sql);
                pst.setInt(1, scheduleId);

                int result = pst.executeUpdate();

                if (result > 0) {
                    return true;
                }

            }
        } finally {
            if (pst != null) {
                pst.close();
            }
            if (conn != null) {
                conn.close();
            }
        }

        return false;
    }
}
