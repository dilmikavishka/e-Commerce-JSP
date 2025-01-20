package lk.ijse.ecommercecosmaticswebsite.dao.DaoImpl;


import lk.ijse.ecommercecosmaticswebsite.dao.Dao.CartDao;
import lk.ijse.ecommercecosmaticswebsite.entity.Cart;

import javax.sql.DataSource;
import java.sql.*;

public class CartDaoImpl implements CartDao {
    private DataSource dataSource;

    public CartDaoImpl() {
    }

    public CartDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public boolean save(Cart entity) {
        String insertQuery = "INSERT INTO cart (cart_id,user_id,total_Price) VALUES (?,?,?)";
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement pst = connection.prepareStatement(insertQuery)) {
                pst.setString(1, entity.getCart_id());
                pst.setString(2, entity.getUser_id());
                pst.setString(3, String.valueOf(entity.getTotalPrice()));
                return pst.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(Cart entity) {
        return false;
    }

    @Override
    public boolean delete(String id) {
        String deleteQuery = "DELETE FROM cart WHERE cart_id = ?";
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement pst = connection.prepareStatement(deleteQuery)) {
                pst.setString(1, id);
                return pst.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Cart search(String id) {
        return null;
    }

    @Override
    public String generateCartId() {
        String newId = "CART001";
        String getMaxIdQuery = "SELECT MAX(cart_id) AS max_id FROM cart";

        try (Connection connection = dataSource.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(getMaxIdQuery)) {
            if (rs.next() && rs.getString("max_id") != null) {
                String maxId = rs.getString("max_id");
                String numericPartStr = maxId.substring(4);
                int numericPart = Integer.parseInt(numericPartStr);
                newId = String.format("CART%03d", numericPart + 1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error generating cart ID", e);
        }

        return newId;

    }



}
