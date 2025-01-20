package lk.ijse.ecommercecosmaticswebsite.bo.BoImp;


import lk.ijse.ecommercecosmaticswebsite.bo.Bo.ProductBo;
import lk.ijse.ecommercecosmaticswebsite.dao.Dao.ProductDao;
import lk.ijse.ecommercecosmaticswebsite.dao.DaoFactory;
import lk.ijse.ecommercecosmaticswebsite.dao.DaoImpl.ProductDaoImpl;
import lk.ijse.ecommercecosmaticswebsite.dto.ProductDTO;
import lk.ijse.ecommercecosmaticswebsite.entity.Product;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class ProductBoImpl implements ProductBo {
    ProductDao productDao = (ProductDao) DaoFactory.getDaoFactory().getDAOType(DaoFactory.DAOTYPE.Product);



    public ProductBoImpl() {
    }

    public ProductBoImpl(DataSource dataSource) {
        this.productDao = new ProductDaoImpl(dataSource);
    }

    @Override
    public String generateNewProductId() {
      return productDao.generateNewProductId();
    }

    @Override
    public boolean save(ProductDTO productDTO) {
        Product product = new Product();
        product.setProduct_id(productDTO.getProduct_id());
        product.setProduct_name(productDTO.getProduct_name());
        product.setCategory_id(productDTO.getCategory_id());
        product.setPrice(productDTO.getPrice());
        product.setStock(productDTO.getStock());
        product.setDescription(productDTO.getDescription());
        product.setBase64Image(productDTO.getBase64Image());
        return productDao.save(product);
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        List<Product> allProducts = productDao.getAll();
        List<ProductDTO> productDTOS = new ArrayList<>();
        for (Product product : allProducts) {
            ProductDTO productDTO = new ProductDTO();
            productDTO.setProduct_id(product.getProduct_id());
            productDTO.setProduct_name(product.getProduct_name());
            productDTO.setCategory_id(product.getCategory_id());
            productDTO.setPrice(product.getPrice());
            productDTO.setStock(product.getStock());
            productDTO.setDescription(product.getDescription());
            productDTO.setBase64Image(product.getBase64Image());
            productDTOS.add(productDTO);
        }
        return productDTOS;
     }

    @Override
    public boolean update(ProductDTO productDTO) {
        Product product = new Product();
        product.setProduct_id(productDTO.getProduct_id());
        product.setProduct_name(productDTO.getProduct_name());
        product.setCategory_id(productDTO.getCategory_id());
        product.setPrice(productDTO.getPrice());
        product.setStock(productDTO.getStock());
        product.setDescription(productDTO.getDescription());
//        product.setBase64Image(productDTO.getBase64Image());
        return productDao.update(product);
    }

    @Override
    public boolean delete(String productId) {
        return productDao.delete(productId);
    }
}
