package dto;

import javax.management.relation.Role;
import java.io.Serializable;
import java.util.Date;

public class Order implements Serializable {

    private int orderId;
    private int profileId;

    private String orderDate;
    private String status;

    public Order() {
    }

    public Order(int orderId, int userId, String orderDate, String status) {
        this.orderId = orderId;
        this.profileId = profileId;
        this.orderDate = orderDate;
        this.status = status;
    }

    public Order(int profileId, String orderDate, String status) {

        this.profileId = profileId;
        this.orderDate = orderDate;
        this.status = status;
    }

    public int getProfileId() {
        return profileId;
    }

    public void setProfileId(int profileId) {
        this.profileId = profileId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

}
