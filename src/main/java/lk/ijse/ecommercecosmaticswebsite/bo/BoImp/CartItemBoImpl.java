package lk.ijse.ecommercecosmaticswebsite.bo.BoImp;


import lk.ijse.ecommercecosmaticswebsite.bo.Bo.CartItemBo;
import lk.ijse.ecommercecosmaticswebsite.dao.Dao.CartItemDao;
import lk.ijse.ecommercecosmaticswebsite.dao.DaoFactory;
import lk.ijse.ecommercecosmaticswebsite.dao.DaoImpl.CartItemDaoImpl;
import lk.ijse.ecommercecosmaticswebsite.dto.CartItemDTO;
import lk.ijse.ecommercecosmaticswebsite.dto.CartItemDetailsDTO;
import lk.ijse.ecommercecosmaticswebsite.entity.CartItem;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class CartItemBoImpl implements CartItemBo {

    CartItemDao cartItemDao = (CartItemDao) DaoFactory.getDaoFactory().getDAOType(DaoFactory.DAOTYPE.CartItem);

    public CartItemBoImpl(DataSource dataSource) {
       this.cartItemDao = new CartItemDaoImpl(dataSource);
    }

    public CartItemBoImpl () {

    }

    @Override
    public String generateCartItemId() {
        return cartItemDao.generateCartItemId();
    }



    @Override
    public boolean saveCartItems(List<CartItemDTO> cartItemDTOs) {
        boolean allSaved = true;

        try {
            for (CartItemDTO cartItemDTO : cartItemDTOs) {
                String newCartItemId = generateCartItemId();
                cartItemDTO.setCart_item_id(newCartItemId);

                CartItem cartItem = new CartItem(
                        cartItemDTO.getCart_item_id(),
                        cartItemDTO.getCart_id(),
                        cartItemDTO.getProduct_id(),
                        cartItemDTO.getQuantity()
                );

                boolean saved = cartItemDao.save(cartItem);
                if (!saved) {
                    allSaved = false;
                    break;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error saving cart items", e);
        }

        return allSaved;
    }

    @Override
    public List<CartItemDetailsDTO> getCartItemDetails(String userId) {
        List<CartItemDetailsDTO> cartItemDetailsDTOList = cartItemDao.getCartItemDetails(userId);
        List<CartItemDetailsDTO> cartItemDetailsDTOs = new ArrayList<>();
        for (CartItemDetailsDTO cartItemDetailsDTO : cartItemDetailsDTOList) {
            CartItemDetailsDTO dto = new CartItemDetailsDTO();
            dto.setProductId(cartItemDetailsDTO.getProductId());
            dto.setCartId(cartItemDetailsDTO.getCartId());
            dto.setUserID(userId);
            dto.setProductName(cartItemDetailsDTO.getProductName());
            dto.setQuantity(cartItemDetailsDTO.getQuantity());
            dto.setPrice(cartItemDetailsDTO.getPrice());
            dto.setTotalPricePerItem(cartItemDetailsDTO.getTotalPricePerItem());
            cartItemDetailsDTOs.add(dto);
        }
        return cartItemDetailsDTOs;
    }

    @Override
    public List<CartItemDetailsDTO> getAllCartItemDetails() {
        List<CartItemDetailsDTO> cartItemDetailsDTOList = cartItemDao.getAllCartItemDetails();
        List<CartItemDetailsDTO> cartItemDetailsDTOs = new ArrayList<>();
        for (CartItemDetailsDTO cartItemDetailsDTO : cartItemDetailsDTOList) {
            CartItemDetailsDTO dto = new CartItemDetailsDTO();
            dto.setProductId(cartItemDetailsDTO.getProductId());
            dto.setCartId(cartItemDetailsDTO.getCartId());
            dto.setUserID(cartItemDetailsDTO.getUserID());
            dto.setProductName(cartItemDetailsDTO.getProductName());
            dto.setQuantity(cartItemDetailsDTO.getQuantity());
            dto.setPrice(cartItemDetailsDTO.getPrice());
            dto.setTotalPricePerItem(cartItemDetailsDTO.getTotalPricePerItem());
            cartItemDetailsDTOs.add(dto);
        }
        return cartItemDetailsDTOs;
    }


}
