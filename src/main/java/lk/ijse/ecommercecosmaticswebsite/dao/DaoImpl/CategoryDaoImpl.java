package lk.ijse.ecommercecosmaticswebsite.dao.DaoImpl;


import lk.ijse.ecommercecosmaticswebsite.dao.Dao.CategoryDao;
import lk.ijse.ecommercecosmaticswebsite.entity.Category;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDaoImpl implements CategoryDao {
    private DataSource dataSource;

    public CategoryDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public CategoryDaoImpl() {

    }

    @Override
    public boolean save(Category entity) {
        String insertQuery = "INSERT INTO categories (category_id,category_name,description) VALUES (?,?,?)";
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement pst = connection.prepareStatement(insertQuery)) {
                pst.setString(1, entity.getId());
                pst.setString(2, entity.getName());
                pst.setString(3, entity.getDescription());
                return pst.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(Category entity) {
        String updateQuery = "UPDATE categories SET category_name = ?, description = ? WHERE category_id = ?";
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement pst = connection.prepareStatement(updateQuery)) {
                pst.setString(1, entity.getName());
                pst.setString(2, entity.getDescription());
                pst.setString(3, entity.getId());
                return pst.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(String id) {
        String deleteQuery = "DELETE FROM categories WHERE category_id = ?";
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
    public Category search(String id) {
        return null;
    }

    @Override
    public String generateCategoryId() {
        String newId = "C001";
        String getMaxIdQuery = "SELECT MAX(category_id) AS max_id FROM categories";

        try (Connection connection = dataSource.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(getMaxIdQuery)) {

            if (rs.next() && rs.getString("max_id") != null) {
                int numericPart = Integer.parseInt(rs.getString("max_id").substring(1));
                newId = String.format("C%03d", numericPart + 1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return newId;
    }

    @Override
    public List<Category> getAll() {
        List<Category> categoryArrayList = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            String query = "SELECT * FROM categories";
            try (Statement stmt = connection.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {
                while (rs.next()) {
                    Category category = new Category();
                    category.setId(rs.getString("category_id"));
                    category.setName(rs.getString("category_name"));
                    category.setDescription(rs.getString("description"));
                    categoryArrayList.add(category);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return categoryArrayList;
    }
}
