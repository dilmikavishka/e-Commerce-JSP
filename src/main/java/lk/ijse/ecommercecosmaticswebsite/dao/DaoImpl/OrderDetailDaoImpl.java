package lk.ijse.ecommercecosmaticswebsite.dao.DaoImpl;


import lk.ijse.ecommercecosmaticswebsite.dao.Dao.OrderDetailDao;
import lk.ijse.ecommercecosmaticswebsite.entity.OrderDetail;

import javax.sql.DataSource;
import java.sql.*;

public class OrderDetailDaoImpl implements OrderDetailDao {
    private DataSource dataSource;

    public OrderDetailDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public OrderDetailDaoImpl(){}

    @Override
    public boolean save(OrderDetail entity) {
        String insertQuery = "INSERT INTO order_details (order_detail_id, order_id, product_id, quantity, price) VALUES (?, ?, ?, ?, ?)";
        String updateStockQuery = "UPDATE products SET stock = stock - ? WHERE product_id = ?";

        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement pst = connection.prepareStatement(insertQuery)) {
                pst.setString(1, entity.getOrderDetailId());
                pst.setString(2, entity.getOrderId());
                pst.setString(3, entity.getProductId());
                pst.setString(4, String.valueOf(entity.getQuantity()));
                pst.setString(5, String.valueOf(entity.getPrice()));

                int rowsAffected = pst.executeUpdate();


                if (rowsAffected > 0) {
                    try (PreparedStatement pstStock = connection.prepareStatement(updateStockQuery)) {
                        pstStock.setInt(1, entity.getQuantity());
                        pstStock.setString(2, entity.getProductId());
                        int stockRowsAffected = pstStock.executeUpdate();

                        if (stockRowsAffected > 0) {
                            connection.commit();
                            return true;
                        } else {
                            connection.rollback();
                            return false;
                        }
                    }
                } else {
                    connection.rollback();
                    return false;
                }
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error saving Order Details item", e);
        }
    }


    @Override
    public boolean update(OrderDetail entity) {
        return false;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }

    @Override
    public OrderDetail search(String id) {
        return null;
    }

    @Override
    public String generateNewOrderDetailId() {
        String newId = "OrderD001"; // Default starting value
        String getMaxIdQuery = "SELECT MAX(order_detail_id) AS max_id FROM order_details FOR UPDATE";

        try (Connection connection = dataSource.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(getMaxIdQuery)) {

            if (rs.next()) {
                String maxId = rs.getString("max_id");

                if (maxId != null) {
                    if (maxId.startsWith("OrderD")) {
                        String numericPartStr = maxId.substring(6);
                        try {
                            int numericPart = Integer.parseInt(numericPartStr);
                            newId = String.format("OrderD%03d", numericPart + 1);
                        } catch (NumberFormatException e) {
                            throw new SQLException("Invalid numeric part in max order_detail_id: " + maxId);
                        }
                    } else {
                        throw new SQLException("Unexpected format for order_detail_id: " + maxId);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error generating new order_detail_id", e);
        }

        return newId;
    }

}
