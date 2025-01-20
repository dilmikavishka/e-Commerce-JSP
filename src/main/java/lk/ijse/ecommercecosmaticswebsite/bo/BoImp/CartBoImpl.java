package lk.ijse.ecommercecosmaticswebsite.bo.BoImp;


import lk.ijse.ecommercecosmaticswebsite.bo.Bo.CartBo;
import lk.ijse.ecommercecosmaticswebsite.dao.Dao.CartDao;
import lk.ijse.ecommercecosmaticswebsite.dao.DaoFactory;
import lk.ijse.ecommercecosmaticswebsite.dao.DaoImpl.CartDaoImpl;
import lk.ijse.ecommercecosmaticswebsite.dto.CartDTO;
import lk.ijse.ecommercecosmaticswebsite.entity.Cart;

import javax.sql.DataSource;

public class CartBoImpl implements CartBo {
    CartDao cartDao = (CartDao) DaoFactory.getDaoFactory().getDAOType(DaoFactory.DAOTYPE.Cart);
    private CartDTO cartDTO;

    public CartBoImpl() {
    }

    public CartBoImpl(DataSource dataSource) {
        this.cartDao = new CartDaoImpl(dataSource);
    }

    @Override
    public String generateCartId() {
        return cartDao.generateCartId();
    }

    @Override
    public boolean saveCart(CartDTO cartDTO) {
        this.cartDTO = cartDTO;
        Cart cart = new Cart();
        cart.setCart_id(cartDTO.getCart_id());
        cart.setUser_id(cartDTO.getUser_id());
        cart.setTotalPrice(cartDTO.getTotalPrice());
        return cartDao.save(cart);
    }

    @Override
    public boolean deleteCart(String cartId) {
        return cartDao.delete(cartId);
    }
}
