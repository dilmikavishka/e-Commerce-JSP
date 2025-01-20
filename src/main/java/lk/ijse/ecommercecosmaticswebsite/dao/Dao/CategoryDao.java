package lk.ijse.ecommercecosmaticswebsite.dao.Dao;


import lk.ijse.ecommercecosmaticswebsite.dao.CrudDAO;
import lk.ijse.ecommercecosmaticswebsite.entity.Category;

import java.util.List;

public interface CategoryDao extends CrudDAO<Category> {
    String generateCategoryId();

    List<Category> getAll();
}
