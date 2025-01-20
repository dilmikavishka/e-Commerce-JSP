package lk.ijse.ecommercecosmaticswebsite.bo.Bo;


import lk.ijse.ecommercecosmaticswebsite.bo.SuperBo;
import lk.ijse.ecommercecosmaticswebsite.dto.CategoryDTO;

import java.util.List;

public interface CategoryBo extends SuperBo {
    String generateCategoryId();

    boolean saveCategory(CategoryDTO categoryDTO);

    List<CategoryDTO> getAllCategories();

    boolean updateCategory(CategoryDTO categoryDTO);

    boolean deleteCategory(String categoryId);
}
