package lk.ijse.ecommercecosmaticswebsite.dao.DaoImpl;


import lk.ijse.ecommercecosmaticswebsite.dao.Dao.UserDao;
import lk.ijse.ecommercecosmaticswebsite.entity.User;
import org.mindrot.jbcrypt.BCrypt;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {
    private DataSource dataSource;

    public UserDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public UserDaoImpl() {
    }

    @Override
    public boolean save(User entity) {
        String insertQuery = "INSERT INTO users (user_id, username, email, password, role) VALUES (?,?,?,?,?)";
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement pst = connection.prepareStatement(insertQuery)) {
                pst.setString(1, entity.getUserId());
                pst.setString(2, entity.getUsername());
                pst.setString(3, entity.getEmail());

                String hashedPassword = BCrypt.hashpw(entity.getPassword(), BCrypt.gensalt());
                pst.setString(4, hashedPassword);

                pst.setString(5, entity.getRole());
                return pst.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(User entity) {
        String updateQuery = "UPDATE users SET username = ?, password = ?, email = ?, role = ? WHERE user_id = ?";
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement pst = connection.prepareStatement(updateQuery)) {
                pst.setString(1, entity.getUsername());
                String hashedPassword = BCrypt.hashpw(entity.getPassword(), BCrypt.gensalt());
                pst.setString(2, hashedPassword);

                pst.setString(3, entity.getEmail());
                pst.setString(4, entity.getRole());
                pst.setString(5, entity.getUserId());
                return pst.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(String id) {
        String deleteQuery = "DELETE FROM users WHERE user_id = ?";
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
    public User search(String id) {
        return null; // Implement search logic if necessary
    }

    @Override
    public String generateNewUserId() {
        String newId = "U001";
        String getMaxIdQuery = "SELECT MAX(user_id) AS max_id FROM users";

        try (Connection connection = dataSource.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(getMaxIdQuery)) {

            if (rs.next() && rs.getString("max_id") != null) {
                int numericPart = Integer.parseInt(rs.getString("max_id").substring(1));
                newId = String.format("U%03d", numericPart + 1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return newId;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userArrayList = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            String query = "SELECT * FROM users";
            try (Statement stmt = connection.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {
                while (rs.next()) {
                    User user = new User();
                    user.setUserId(rs.getString("user_id"));
                    user.setUsername(rs.getString("username"));
                    user.setEmail(rs.getString("email"));
                    user.setPassword(rs.getString("password"));
                    user.setRole(rs.getString("role"));
                    userArrayList.add(user);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userArrayList;
    }

    @Override
    public User findByUsernameAndPassword(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, username);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    String storedPassword = rs.getString("password");
                    // Validate password with bcrypt
                    if (BCrypt.checkpw(password, storedPassword)) {
                        User user = new User();
                        user.setUserId(rs.getString("user_id"));
                        user.setUsername(rs.getString("username"));
                        user.setPassword(rs.getString("password"));
                        user.setEmail(rs.getString("email"));
                        user.setRole(rs.getString("role"));
                        return user;
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
