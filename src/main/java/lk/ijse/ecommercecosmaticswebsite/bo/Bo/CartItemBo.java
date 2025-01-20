package lk.ijse.ecommercecosmaticswebsite.bo.Bo;


import lk.ijse.ecommercecosmaticswebsite.bo.SuperBo;
import lk.ijse.ecommercecosmaticswebsite.dto.CartItemDTO;
import lk.ijse.ecommercecosmaticswebsite.dto.CartItemDetailsDTO;

import java.util.List;

public interface CartItemBo extends SuperBo {
    String generateCartItemId();

    boolean saveCartItems(List<CartItemDTO> cartItems);

    List<CartItemDetailsDTO> getCartItemDetails(String userId);

    List<CartItemDetailsDTO> getAllCartItemDetails();

}
