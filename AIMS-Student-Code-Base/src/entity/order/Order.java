package entity.order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import dto.OrderDto;
import entity.db.AIMSDB;
import entity.invoice.Invoice;
import entity.shipping.DeliveryInfo;
import utils.Configs;

public class Order {
    private int id;
    private DeliveryInfo deliveryInfo2;
    private Invoice invoice;
    private String status;
    
    private int shippingFees;
    private List lstOrderMedia;
    private HashMap<String, String> deliveryInfo;

    public Order(){
        this.lstOrderMedia = new ArrayList<>();
    }

    public Order(List lstOrderMedia) {
        this.lstOrderMedia = lstOrderMedia;
    }
    public Order(OrderDto orderDto) {
        this.id = orderDto.getId();
        this.deliveryInfo2 = new DeliveryInfo();
        this.deliveryInfo2.setEmail(orderDto.getEmail());
        this.deliveryInfo2.setShippingAddress(orderDto.getAddress());
        this.deliveryInfo2.setPhoneNumber(orderDto.getPhone());
        this.setStatus(orderDto.getStatus());
        this.deliveryInfo2.setProvince(orderDto.getProvince());
        this.deliveryInfo2.setRushShippingInstruction(orderDto.getRushShippingInstruction());
        this.deliveryInfo2.setShippingInstruction(orderDto.getShippingInstruction());
        if (orderDto.getIsRushShipping() == 0) {
            this.getDeliveryInfo2().setRushShipping(false);
        } else {
            this.getDeliveryInfo2().setRushShipping(true);
        }
    }

    public void addOrderMedia(OrderMedia om){
        this.lstOrderMedia.add(om);
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getStatus() {
        return status;
    }

    public void removeOrderMedia(OrderMedia om){
        this.lstOrderMedia.remove(om);
    }

    public List getlstOrderMedia() {
        return this.lstOrderMedia;
    }

    public void setlstOrderMedia(List lstOrderMedia) {
        this.lstOrderMedia = lstOrderMedia;
    }

    public void setShippingFees(int shippingFees) {
        this.shippingFees = shippingFees;
    }

    public int getShippingFees() {
        return shippingFees;
    }

    public HashMap getDeliveryInfo() {
        return deliveryInfo;
    }
    public DeliveryInfo getDeliveryInfo2() {
        return deliveryInfo2;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDeliveryInfo(HashMap deliveryInfo) {
        this.deliveryInfo = deliveryInfo;
    }

    public int getAmount(){
        double amount = 0;
        for (Object object : lstOrderMedia) {
            OrderMedia om = (OrderMedia) object;
            amount += om.getPrice()* om.getQuantity();
        }
        return (int) (amount + (Configs.PERCENT_VAT/100)*amount);
    }
    public static List<Order> getAllOrders() throws SQLException {
        System.out.println("get all orders");
        List<Order> orders = new ArrayList<>();

        String sql = "SELECT * FROM `Order`";

        Connection connection = AIMSDB.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        int count = 0;
        while (resultSet.next()) {
            System.out.println("ok result");
        }

            while (resultSet.next()) {
            System.out.println(++count);

            OrderDto newOrderDto = new OrderDto(
                    resultSet.getInt("id"),
                    resultSet.getString("email"),
                    resultSet.getString("address"),
                    resultSet.getString("phone"),
                    resultSet.getInt("userID"),
                    resultSet.getInt("shipping_fee"),
                    resultSet.getString("status"),
                    resultSet.getString("rush_shipping_time"),
                    resultSet.getString("province"),
                    resultSet.getString("shipping_instruction"),
                    resultSet.getString("rush_shipping_instruction"),
                    resultSet.getInt("is_rush_shipping"));

            Order order = new Order(newOrderDto);
            orders.add(order);
        }

        return orders;
    }
    public static void updateOrderStatus(Integer id, String newState) throws SQLException {
        String sql = "UPDATE `Order` SET status = ? WHERE id = ?";
        Connection connection = AIMSDB.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, newState);
        preparedStatement.setInt(2, id);
        preparedStatement.executeUpdate();
    }

}
