package lk.ijse.ecommercecosmaticswebsite.dao.Dao;


import lk.ijse.ecommercecosmaticswebsite.dao.CrudDAO;
import lk.ijse.ecommercecosmaticswebsite.entity.User;

import java.util.List;

public interface UserDao extends CrudDAO<User> {
    String generateNewUserId();

    List<User> getAllUsers();


    User findByUsernameAndPassword(String username, String password);
}
