package lk.ijse.ecommercecosmaticswebsite.Servlets;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.ecommercecosmaticswebsite.bo.Bo.ProductBo;
import lk.ijse.ecommercecosmaticswebsite.bo.BoFactory;
import lk.ijse.ecommercecosmaticswebsite.bo.BoImp.ProductBoImpl;
import lk.ijse.ecommercecosmaticswebsite.dto.ProductDTO;


import javax.sql.DataSource;
import java.io.IOException;

@WebServlet(name = "ProductSaveServet", value = "/saveProduct")
public class ProductSaveServet extends HttpServlet {

    ProductBo productBo = (ProductBo) BoFactory.getBoFactory().getBO(BoFactory.BoType.Product);

    @Override
    public void init() throws ServletException {
        ServletContext servletContext = getServletContext();
        productBo = new ProductBoImpl((DataSource) servletContext.getAttribute("dataSource"));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String Product_Id = productBo.generateNewProductId();
        String ProductName = req.getParameter("product_name");
        String category = req.getParameter("category_id");
        Double price = Double.valueOf(req.getParameter("price"));
        Integer stock = Integer.valueOf(req.getParameter("stock"));
        String description = req.getParameter("description");
        String base64Image = req.getParameter("base64_image");
        System.out.println(Product_Id);
        System.out.println(ProductName);
        System.out.println(price);
        ProductDTO productDTO = new ProductDTO(Product_Id,ProductName,category,price,stock,description,base64Image);
        boolean isProductSaved = productBo.save(productDTO);
        if (isProductSaved){
            resp.sendRedirect("product-management.jsp?message=User Saved Successfully");
        } else {
            resp.sendRedirect("product-management.jsp?error=Failed to save the user");
        }
    }
}
