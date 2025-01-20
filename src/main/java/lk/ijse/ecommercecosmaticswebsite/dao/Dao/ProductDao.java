package lk.ijse.ecommercecosmaticswebsite.dao.Dao;



import lk.ijse.ecommercecosmaticswebsite.dao.CrudDAO;
import lk.ijse.ecommercecosmaticswebsite.entity.Product;

import java.util.List;

public interface ProductDao extends CrudDAO<Product> {
    String generateNewProductId();

    List<Product> getAll();
}
