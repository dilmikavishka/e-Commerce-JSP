package lk.ijse.ecommercecosmaticswebsite.dao.Dao;


import lk.ijse.ecommercecosmaticswebsite.dao.CrudDAO;
import lk.ijse.ecommercecosmaticswebsite.entity.Cart;

public interface CartDao extends CrudDAO<Cart> {
    String generateCartId();
}
