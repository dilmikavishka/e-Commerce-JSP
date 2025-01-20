package lk.ijse.ecommercecosmaticswebsite.bo.BoImp;


import lk.ijse.ecommercecosmaticswebsite.bo.Bo.CategoryBo;
import lk.ijse.ecommercecosmaticswebsite.dao.Dao.CategoryDao;
import lk.ijse.ecommercecosmaticswebsite.dao.DaoFactory;
import lk.ijse.ecommercecosmaticswebsite.dao.DaoImpl.CategoryDaoImpl;
import lk.ijse.ecommercecosmaticswebsite.dto.CategoryDTO;
import lk.ijse.ecommercecosmaticswebsite.entity.Category;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class CategoryBoImpl implements CategoryBo {

    CategoryDao categoryDao = (CategoryDao) DaoFactory.getDaoFactory().getDAOType(DaoFactory.DAOTYPE.Category);

    public CategoryBoImpl(DataSource dataSource) {
        this.categoryDao = new CategoryDaoImpl(dataSource);
    }

    public CategoryBoImpl() {
    }

    @Override
    public String generateCategoryId() {
        return categoryDao.generateCategoryId();
    }

    @Override
    public boolean saveCategory(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setId(categoryDTO.getId());
        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());
        return categoryDao.save(category);
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        List<Category> allCategories = categoryDao.getAll();
        List<CategoryDTO> categoryDTOList = new ArrayList<>();
        for (Category category : allCategories) {
            CategoryDTO categoryDTO = new CategoryDTO();
            categoryDTO.setId(category.getId());
            categoryDTO.setName(category.getName());
            categoryDTO.setDescription(category.getDescription());
            categoryDTOList.add(categoryDTO);
        }
        return categoryDTOList;
    }

    @Override
    public boolean updateCategory(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setId(categoryDTO.getId());
        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());
        return categoryDao.update(category);
    }

    @Override
    public boolean deleteCategory(String categoryId) {
        return categoryDao.delete(categoryId);
    }
}
