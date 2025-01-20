package lk.ijse.ecommercecosmaticswebsite.dao.DaoImpl;


import lk.ijse.ecommercecosmaticswebsite.dao.Dao.ProductDao;
import lk.ijse.ecommercecosmaticswebsite.entity.Product;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoImpl implements ProductDao {
    private DataSource dataSource;

    public ProductDaoImpl(){

    }

    public ProductDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public boolean save(Product entity) {
        String insertQuery = "INSERT INTO products (product_id,product_name,category_id,price,stock,description,base64_image) VALUES (?,?, ?, ?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement pst = connection.prepareStatement(insertQuery)) {
                pst.setString(1, entity.getProduct_id());
                pst.setString(2, entity.getProduct_name());
                pst.setString(3, entity.getCategory_id());
                pst.setDouble(4, entity.getPrice());
                pst.setInt(5, entity.getStock());
                pst.setString(6, entity.getDescription());
                pst.setString(7, entity.getBase64Image());
                return pst.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(Product entity) {
        String updateQuery = "UPDATE products SET product_name=?,category_id=?,price=?,stock=?,description=? WHERE product_id=?";

        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement pst = connection.prepareStatement(updateQuery)) {
                pst.setString(1, entity.getProduct_name());
                pst.setString(2, entity.getCategory_id());
                pst.setDouble(3, entity.getPrice());
                pst.setInt(4, entity.getStock());
                pst.setString(5, entity.getDescription());
                pst.setString(6, entity.getProduct_id());
//                pst.setString(7, entity.getBase64Image());
                return pst.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(String id) {
        String deleteQuery = "DELETE FROM products WHERE product_id=?";

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
    public Product search(String id) {
        return null;
    }

    @Override
    public String generateNewProductId() {
        String newId = "P001";
        String getMaxIdQuery = "SELECT MAX(product_id) AS max_id FROM products";

        try (Connection connection = dataSource.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(getMaxIdQuery)) {

            if (rs.next() && rs.getString("max_id") != null) {
                int numericPart = Integer.parseInt(rs.getString("max_id").substring(1));
                newId = String.format("P%03d", numericPart + 1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return newId;
    }

    @Override
    public List<Product> getAll() {
        List<Product> products = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            String query = "SELECT * FROM products";
            try (Statement stmt = connection.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {
                while (rs.next()) {
                    Product product = new Product();
                    product.setProduct_id(rs.getString("product_id"));
                    product.setProduct_name(rs.getString("product_name"));
                    product.setCategory_id(rs.getString("category_id"));
                    product.setPrice(rs.getDouble("price"));
                    product.setStock(rs.getInt("stock"));
                    product.setDescription(rs.getString("description"));
                    product.setBase64Image(rs.getString("base64_image"));
                    products.add(product);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return products;
    }
}
