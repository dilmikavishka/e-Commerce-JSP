package lk.ijse.ecommercecosmaticswebsite.dao.DaoImpl;

import lk.ijse.ecommercecosmaticswebsite.dao.Dao.CartItemDao;
import lk.ijse.ecommercecosmaticswebsite.dto.CartItemDetailsDTO;
import lk.ijse.ecommercecosmaticswebsite.entity.CartItem;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartItemDaoImpl implements CartItemDao {
    private DataSource dataSource;

    public CartItemDaoImpl() {
    }

    public CartItemDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    @Override
    public boolean save(CartItem entity) {
        String insertQuery = "INSERT INTO cart_items (cart_item_id, cart_id, product_id, quantity) VALUES (?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement pst = connection.prepareStatement(insertQuery)) {
                pst.setString(1, entity.getCart_item_id());
                pst.setString(2, entity.getCart_id());
                pst.setString(3, entity.getProduct_id());
                pst.setInt(4, entity.getQuantity());

                int rowsAffected = pst.executeUpdate();
                connection.commit();
                return rowsAffected > 0;
            } catch (SQLException e) {
                connection.rollback(); // Rollback transaction on failure
                throw e;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error saving cart item", e);
        }
    }

    @Override
    public boolean update(CartItem entity) {
        return false;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }

    @Override
    public CartItem search(String id) {
        return null;
    }

    @Override
    public String generateCartItemId() {
        String newId = "CartItem001";
        String getMaxIdQuery = "SELECT MAX(cart_item_id) AS max_id FROM cart_items FOR UPDATE";

        try (Connection connection = dataSource.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(getMaxIdQuery)) {

            if (rs.next() && rs.getString("max_id") != null) {
                String maxId = rs.getString("max_id");
                String numericPartStr = maxId.substring(8);
                int numericPart = Integer.parseInt(numericPartStr);
                newId = String.format("CartItem%03d", numericPart + 1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error generating cart item ID", e);
        }

        return newId;
    }

    public List<CartItemDetailsDTO> getCartItemDetails(String userId) {
        List<CartItemDetailsDTO> cartItemDetailsDTOList = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            String query = "SELECT c.user_id,ci.cart_id, ci.product_id, p.product_name, ci.quantity, p.price, " +
                    "(ci.quantity * p.price) AS total_price_per_item " +
                    "FROM cart_items ci " +
                    "JOIN cart c ON ci.cart_id = c.cart_id " +
                    "JOIN products p ON ci.product_id = p.product_id " +
                    "WHERE c.user_id = ?";

            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, userId); // Set the userId parameter at index 1
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        CartItemDetailsDTO cartItemDetailsDTO = new CartItemDetailsDTO();
                        cartItemDetailsDTO.setProductId(rs.getString("product_id"));
                        cartItemDetailsDTO.setCartId(rs.getString("cart_id"));
                        cartItemDetailsDTO.setUserID(userId);
                        cartItemDetailsDTO.setProductName(rs.getString("product_name"));
                        cartItemDetailsDTO.setQuantity(rs.getInt("quantity"));
                        cartItemDetailsDTO.setPrice(rs.getDouble("price"));
                        cartItemDetailsDTO.setTotalPricePerItem(rs.getDouble("total_price_per_item"));
                        cartItemDetailsDTOList.add(cartItemDetailsDTO);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving cart item details", e);
        }
        return cartItemDetailsDTOList;
    }

    @Override
    public List<CartItemDetailsDTO> getAllCartItemDetails() {
        List<CartItemDetailsDTO> cartItemDetailsDTOList = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            
            String query = "SELECT c.user_id, ci.cart_id, ci.product_id, p.product_name, ci.quantity, p.price, " +
                    "(ci.quantity * p.price) AS total_price_per_item " +
                    "FROM cart_items ci " +
                    "JOIN cart c ON ci.cart_id = c.cart_id " +
                    "JOIN products p ON ci.product_id = p.product_id";

            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        CartItemDetailsDTO cartItemDetailsDTO = new CartItemDetailsDTO();
                        cartItemDetailsDTO.setProductId(rs.getString("product_id"));
                        cartItemDetailsDTO.setCartId(rs.getString("cart_id"));
                        cartItemDetailsDTO.setUserID(rs.getString("user_id"));
                        cartItemDetailsDTO.setProductName(rs.getString("product_name"));
                        cartItemDetailsDTO.setQuantity(rs.getInt("quantity"));
                        cartItemDetailsDTO.setPrice(rs.getDouble("price"));
                        cartItemDetailsDTO.setTotalPricePerItem(rs.getDouble("total_price_per_item"));
                        cartItemDetailsDTOList.add(cartItemDetailsDTO);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving cart item details", e);
        }
        return cartItemDetailsDTOList;

    }


}
