/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.OrderDetail;
import dto.PhotoSchedule;
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
public class OrderDetailDAO implements Serializable {

    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    public boolean insertOrderDetail(OrderDetail orderDetail) throws NamingException, SQLException {
        boolean rs = false;
        try {
            conn = ConnectionConfig.getConnection();
            if (conn != null) {
                String sql = "insert dbo.order_detail(name,description,price,order_date,order_id,item_id,item_type,is_active)\n"
                        + "values (?,?,?, ?, ?, ?, ?,1)";

                pst = conn.prepareStatement(sql);
                pst.setString(1, orderDetail.getName());
                pst.setString(2, orderDetail.getDescription());
                pst.setFloat(3, (float) orderDetail.getPrice());
                pst.setString(4, orderDetail.getOrderDate());
                pst.setInt(5, orderDetail.getOrderId());
                pst.setInt(6, orderDetail.getItemId());
                pst.setString(7, orderDetail.getItemType());

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

    public List<OrderDetail> getTop2OrderDetail() throws NamingException, SQLException {
        List<OrderDetail> list = new ArrayList<>();
        try {
            conn = ConnectionConfig.getConnection();
            if (conn != null) {
                String sql = "select top 2 order_detail_id,order_date,name,description,price,is_active,order_id, item_id, item_type\n"
                        + "from order_detail\n"
                        + "where is_active = 1\n"
                        + "order by order_detail_id desc ";

                pst = conn.prepareStatement(sql);

                rs = pst.executeQuery();

                while (rs.next()) {
                    int orderDetalId = rs.getInt("order_detail_id");
                    int orderId = rs.getInt("order_id");
                    int itemId = rs.getInt("item_id");

                    String orderDate = rs.getString("order_date");
                    String name = rs.getString("name");
                    String description = rs.getString("description");
                    String itemType = rs.getString("item_type");
                    double price = rs.getFloat("price");
                    boolean active = rs.getBoolean("is_active");

                    list.add(new OrderDetail(orderDetalId, name, description, price, orderDate, active, orderId, itemId, itemType));
                }

            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pst != null) {
                pst.close();
            }
            if (conn != null) {
                conn.close();
            }
        }

        return list;
    }

    public OrderDetail getOrderDetailById(int orderDetailId) throws NamingException, SQLException {

        try {
            conn = ConnectionConfig.getConnection();
            if (conn != null) {
                String sql = "select order_detail_id,order_date,name,description,price,is_active,order_id,item_id, item_type\n"
                        + "from order_detail\n"
                        + "where is_active = 1 and order_detail_id = ?";

                pst = conn.prepareStatement(sql);
                pst.setInt(1, orderDetailId);

                rs = pst.executeQuery();

                if (rs.next()) {
                    int orderDetalId = rs.getInt("order_detail_id");
                    int orderId = rs.getInt("order_id");

                    String orderDate = rs.getString("order_date");
                    String name = rs.getString("name");
                    String description = rs.getString("description");
                    double price = rs.getFloat("price");
                    boolean active = rs.getBoolean("is_active");

                    String itemType = rs.getString("item_type");
                    int itemId = rs.getInt("item_id");

                    return new OrderDetail(orderDetalId, name, description, price, orderDate, active, orderId, itemId, itemType);
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
        return null;
    }

    public boolean deleteOrderDetail(int orderDetailId) throws NamingException, SQLException {

        try {
            conn = ConnectionConfig.getConnection();
            if (conn != null) {
                String sql = "delete order_detail\n"
                        + "where order_detail_id = ?";
                pst = conn.prepareStatement(sql);
                pst.setInt(1, orderDetailId);
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

    public List<OrderDetail> getOrderDetailByOrderId(int orderId) throws NamingException, SQLException {
        List<OrderDetail> list = new ArrayList();
        try {
            conn = ConnectionConfig.getConnection();
            if (conn != null) {
                String sql = "select order_detail_id,order_id,name,description,price,order_date,is_active, item_id, item_type\n"
                        + "from order_detail\n"
                        + "where is_active = 1 and order_id = ?";

                pst = conn.prepareStatement(sql);
                pst.setInt(1, orderId);

                rs = pst.executeQuery();

                while (rs.next()) {
                    int orderDetalId = rs.getInt("order_detail_id");
                    int orderID = rs.getInt("order_id");

                    String orderDate = rs.getString("order_date");
                    String name = rs.getString("name");
                    String description = rs.getString("description");
                    double price = rs.getFloat("price");
                    boolean active = rs.getBoolean("is_active");

                    String itemType = rs.getString("item_type");
                    int itemId = rs.getInt("item_id");

                    list.add(new OrderDetail(orderDetalId, name, description, price, orderDate, active, orderID, itemId, itemType));
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

    public boolean updateOrderDetailById(OrderDetail orderDetail) throws NamingException, SQLException {
        try {
            conn = ConnectionConfig.getConnection();
            if (conn != null) {
                String sql = "update order_detail\n"
                        + "set name = ?, description = ?, price =?, order_date = ?\n"
                        + "where order_detail_id = ?";

                pst = conn.prepareStatement(sql);
                pst.setString(1, orderDetail.getName());
                pst.setString(2, orderDetail.getDescription());
                pst.setFloat(3, (float) orderDetail.getPrice());
                pst.setString(4, orderDetail.getOrderDate());
                pst.setInt(5, orderDetail.getOrderDetailId());

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

    public boolean deleteOrderDetailById(int orderDetailId) throws NamingException, SQLException {
        try {
            conn = ConnectionConfig.getConnection();
            if (conn != null) {
                String sql = "update order_detail\n"
                        + "set is_active = 0\n"
                        + "where order_detail_id = ?";

                pst = conn.prepareStatement(sql);
                pst.setInt(1, orderDetailId);

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

    public List<OrderDetail> getOrderDetailByItemType(String itemType) throws NamingException, SQLException {
        List<OrderDetail> list = new ArrayList();
        try {
            conn = ConnectionConfig.getConnection();
            if (conn != null) {
                String sql = "select order_detail_id, od.order_id, item_id, item_type, name, description, price, od.order_date, is_active\n"
                        + "from order_detail od left join orders o on od.order_id = o.order_id\n"
                        + "where is_active = 1 and o.status = 'pending' and item_type = ?";

                pst = conn.prepareStatement(sql);
                pst.setString(1, itemType);

                rs = pst.executeQuery();

                while (rs.next()) {
                    int orderDetalId = rs.getInt("order_detail_id");
                    int orderID = rs.getInt("order_id");

                    String orderDate = rs.getString("order_date");
                    String name = rs.getString("name");
                    String description = rs.getString("description");
                    double price = rs.getFloat("price");
                    boolean active = rs.getBoolean("is_active");

                    int itemId = rs.getInt("item_id");

                    list.add(new OrderDetail(orderDetalId, name, description, price, orderDate, active, orderID, itemId, itemType));
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
    
    public boolean changeOrderDetail(OrderDetail orderDetail) throws NamingException, SQLException {
        try {
            conn = ConnectionConfig.getConnection();
            if (conn != null) {
                String sql = "update order_detail\n"
                        + "set name = ?, description = ?, price =?, order_date = ?, item_id = ?, item_type = ?\n"
                        + "where order_detail_id = ?";

                pst = conn.prepareStatement(sql);
                pst.setString(1, orderDetail.getName());
                pst.setString(2, orderDetail.getDescription());
                pst.setFloat(3, (float) orderDetail.getPrice());
                pst.setString(4, orderDetail.getOrderDate());
                pst.setInt(5, orderDetail.getItemId());
                pst.setString(6, orderDetail.getItemType());
                pst.setInt(7, orderDetail.getOrderDetailId());

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
