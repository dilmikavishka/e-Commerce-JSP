package lk.ijse.ecommercecosmaticswebsite.bo.BoImp;


import lk.ijse.ecommercecosmaticswebsite.bo.Bo.UserBo;
import lk.ijse.ecommercecosmaticswebsite.dao.Dao.UserDao;
import lk.ijse.ecommercecosmaticswebsite.dao.DaoFactory;
import lk.ijse.ecommercecosmaticswebsite.dao.DaoImpl.UserDaoImpl;
import lk.ijse.ecommercecosmaticswebsite.dto.UserDTO;
import lk.ijse.ecommercecosmaticswebsite.entity.User;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class UserBoImpl implements UserBo {
    UserDao USER_DAO = (UserDao) DaoFactory.getDaoFactory().getDAOType(DaoFactory.DAOTYPE.User);

    public UserBoImpl(DataSource dataSource) {
        this.USER_DAO = new UserDaoImpl(dataSource);
    }

    public UserBoImpl() {
    }

    @Override
    public String generateNewUserId() {
        return USER_DAO.generateNewUserId();
    }

    @Override
    public boolean saveUser(UserDTO userDTO) {
        User user = new User();
        user.setUserId(userDTO.getUserId());
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setEmail(userDTO.getEmail());
        user.setRole(userDTO.getRole());
        return USER_DAO.save(user);

    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> allUsers = USER_DAO.getAllUsers();
        List<UserDTO> userDTOS = new ArrayList<>();
        for (User user : allUsers) {
            UserDTO userDTO = new UserDTO();
            userDTO.setUserId(user.getUserId());
            userDTO.setUsername(user.getUsername());
            userDTO.setEmail(user.getEmail());
            userDTO.setPassword(user.getPassword());
            userDTO.setRole(user.getRole());
            userDTOS.add(userDTO);
        }
        return userDTOS;
    }

    @Override
    public boolean updateUser(UserDTO userDTO) {
        User user = new User();
        user.setUserId(userDTO.getUserId());
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setEmail(userDTO.getEmail());
        user.setRole(userDTO.getRole());
        return USER_DAO.update(user);
    }

    @Override
    public boolean deleteUser(String userId) {
        return USER_DAO.delete(userId);
    }

    @Override
    public UserDTO authenticateUser(String username, String password) {
        User user = USER_DAO.findByUsernameAndPassword(username, password);
        if (user != null) {
            UserDTO userDTO = new UserDTO();
            userDTO.setUserId(user.getUserId());
            userDTO.setUsername(user.getUsername());
            userDTO.setEmail(user.getEmail());
            userDTO.setPassword(user.getPassword());
            userDTO.setRole(user.getRole());
            return userDTO;
        }
        return null; // Return null if authentication fails
    }

}
