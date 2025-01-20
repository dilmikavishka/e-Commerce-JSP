package lk.ijse.ecommercecosmaticswebsite.dao.DaoImpl;


import lk.ijse.ecommercecosmaticswebsite.dao.Dao.OrderDao;
import lk.ijse.ecommercecosmaticswebsite.dto.OrderDetailsWithOrder;
import lk.ijse.ecommercecosmaticswebsite.entity.Order;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDaoImpl implements OrderDao {
    private DataSource dataSource;

    public OrderDaoImpl (){}

    public OrderDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public boolean save(Order entity) {
        String insertQuery = "INSERT INTO  orders (order_id,user_id,total_price,status) VALUES (?,?,?,?)";
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement pst = connection.prepareStatement(insertQuery)) {
                pst.setString(1, entity.getOrderId());
                pst.setString(2, entity.getUserId());
                pst.setString(3, String.valueOf(entity.getTotalPrice()));
                pst.setString(4, entity.getStatus());
                return pst.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(Order entity) {
        return false;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }

    @Override
    public Order search(String id) {
        return null;
    }

    @Override
    public String generateNewOrderId() {
        String newId = "Order001";
        String getMaxIdQuery = "SELECT MAX(order_id) AS max_id FROM orders FOR UPDATE";

        try (Connection connection = dataSource.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(getMaxIdQuery)) {

            if (rs.next()) {
                String maxId = rs.getString("max_id");

                if (maxId != null && maxId.startsWith("Order")) {
                    String numericPartStr = maxId.substring(5);
                    try {
                        int numericPart = Integer.parseInt(numericPartStr);
                        newId = String.format("Order%03d", numericPart + 1);
                    } catch (NumberFormatException e) {
                        throw new SQLException("Invalid numeric part in max order_id: " + maxId);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error generating order ID", e);
        }

        return newId;
    }


    public List<OrderDetailsWithOrder> getOrderDetailsByUser(String userId) {
        List<OrderDetailsWithOrder> orderDetailsDTOList = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            String query = "SELECT o.order_id, o.user_id, o.order_date, o.total_price, o.status, " +
                    "od.order_detail_id, od.product_id, od.quantity, od.price, " +
                    "(od.quantity * od.price) AS total_price_per_item " +
                    "FROM orders o " +
                    "JOIN order_details od ON o.order_id = od.order_id " +
                    "WHERE o.user_id = ?";

            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, userId);
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        OrderDetailsWithOrder orderDetailsDTO = new OrderDetailsWithOrder();
                        orderDetailsDTO.setOrderId(rs.getString("order_id"));
                        orderDetailsDTO.setUserId(rs.getString("user_id"));
                        orderDetailsDTO.setOrderDate(rs.getString("order_date"));
                        orderDetailsDTO.setTotalPrice(rs.getDouble("total_price"));
                        orderDetailsDTO.setStatus(rs.getString("status"));
                        orderDetailsDTO.setOrderDetailId(rs.getString("order_detail_id"));
                        orderDetailsDTO.setProductId(rs.getString("product_id"));
                        orderDetailsDTO.setQuantity(rs.getInt("quantity"));
                        orderDetailsDTO.setPrice(rs.getDouble("price"));
                        orderDetailsDTO.setTotalPricePerItem(rs.getDouble("total_price_per_item"));
                        orderDetailsDTOList.add(orderDetailsDTO);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving order details for user " + userId, e);
        }
        return orderDetailsDTOList;
    }


    public List<OrderDetailsWithOrder> getAllOrderDetails() {
        List<OrderDetailsWithOrder> orderDetailsDTOList = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {

            String query = "SELECT o.order_id, o.user_id, o.order_date, o.total_price, o.status, " +
                    "od.order_detail_id, od.product_id, od.quantity, od.price, " +
                    "(od.quantity * od.price) AS total_price_per_item " +
                    "FROM orders o " +
                    "JOIN order_details od ON o.order_id = od.order_id";

            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        OrderDetailsWithOrder orderDetailsDTO = new OrderDetailsWithOrder();
                        orderDetailsDTO.setOrderId(rs.getString("order_id"));
                        orderDetailsDTO.setUserId(rs.getString("user_id"));
                        orderDetailsDTO.setOrderDate(rs.getString("order_date"));
                        orderDetailsDTO.setTotalPrice(rs.getDouble("total_price"));
                        orderDetailsDTO.setStatus(rs.getString("status"));
                        orderDetailsDTO.setOrderDetailId(rs.getString("order_detail_id"));
                        orderDetailsDTO.setProductId(rs.getString("product_id"));
                        orderDetailsDTO.setQuantity(rs.getInt("quantity"));
                        orderDetailsDTO.setPrice(rs.getDouble("price"));
                        orderDetailsDTO.setTotalPricePerItem(rs.getDouble("total_price_per_item"));
                        orderDetailsDTOList.add(orderDetailsDTO);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving all order details", e);
        }
        return orderDetailsDTOList;
    }

    @Override
    public boolean updateOrder(String orderId, String status) {
        String updateQuery = "UPDATE orders SET status = ? WHERE order_id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(updateQuery)) {
            pstmt.setString(1, status);
            pstmt.setString(2, orderId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error updating order status for Order ID: " + orderId, e);
        }
    }



}
