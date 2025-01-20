package lk.ijse.ecommercecosmaticswebsite.dao.Dao;

import lk.ijse.ecommercecosmaticswebsite.dao.CrudDAO;
import lk.ijse.ecommercecosmaticswebsite.dto.CartItemDetailsDTO;
import lk.ijse.ecommercecosmaticswebsite.entity.CartItem;

import java.util.List;

public interface CartItemDao extends CrudDAO<CartItem> {
    String generateCartItemId();

    List<CartItemDetailsDTO> getCartItemDetails(String userId);

    List<CartItemDetailsDTO> getAllCartItemDetails();

}
