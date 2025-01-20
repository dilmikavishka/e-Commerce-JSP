package lk.ijse.ecommercecosmaticswebsite.bo.Bo;


import lk.ijse.ecommercecosmaticswebsite.bo.SuperBo;
import lk.ijse.ecommercecosmaticswebsite.dto.ProductDTO;

import java.util.List;

public interface ProductBo extends SuperBo {
    String generateNewProductId();

    boolean save(ProductDTO productDTO);

    List<ProductDTO> getAllProducts();

    boolean update(ProductDTO productDTO);

    boolean delete(String productId);
}
