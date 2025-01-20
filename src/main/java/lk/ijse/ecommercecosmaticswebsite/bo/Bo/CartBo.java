package lk.ijse.ecommercecosmaticswebsite.bo.Bo;


import lk.ijse.ecommercecosmaticswebsite.bo.SuperBo;
import lk.ijse.ecommercecosmaticswebsite.dto.CartDTO;

public interface CartBo extends SuperBo {
    String generateCartId();

    boolean saveCart(CartDTO cartDTO);

    boolean deleteCart(String cartId);
}
