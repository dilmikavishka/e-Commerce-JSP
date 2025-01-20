package lk.ijse.ecommercecosmaticswebsite.bo.Bo;


import lk.ijse.ecommercecosmaticswebsite.bo.SuperBo;
import lk.ijse.ecommercecosmaticswebsite.dto.UserDTO;

import java.util.List;

public interface UserBo extends SuperBo {


    String generateNewUserId();

    boolean saveUser(UserDTO userDTO);

    List<UserDTO> getAllUsers();

    boolean updateUser(UserDTO userDTO);

    boolean deleteUser(String userId);

    UserDTO authenticateUser(String username, String password);
}
