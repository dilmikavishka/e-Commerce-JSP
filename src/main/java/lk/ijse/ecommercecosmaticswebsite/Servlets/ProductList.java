package lk.ijse.ecommercecosmaticswebsite.Servlets;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.ecommercecosmaticswebsite.bo.Bo.CategoryBo;
import lk.ijse.ecommercecosmaticswebsite.bo.Bo.ProductBo;
import lk.ijse.ecommercecosmaticswebsite.bo.BoFactory;
import lk.ijse.ecommercecosmaticswebsite.bo.BoImp.CategoryBoImpl;
import lk.ijse.ecommercecosmaticswebsite.bo.BoImp.ProductBoImpl;
import lk.ijse.ecommercecosmaticswebsite.dto.CategoryDTO;
import lk.ijse.ecommercecosmaticswebsite.dto.ProductDTO;


import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ProductList", value = "/product-list")
public class ProductList extends HttpServlet {

    ProductBo productBo = (ProductBo) BoFactory.getBoFactory().getBO(BoFactory.BoType.Product);
    CategoryBo categoryBo = (CategoryBo) BoFactory.getBoFactory().getBO(BoFactory.BoType.Category);

    @Override
    public void init() throws ServletException {
        ServletContext servletContext = getServletContext();
        productBo = new ProductBoImpl((DataSource) servletContext.getAttribute("dataSource"));
        categoryBo = new CategoryBoImpl((DataSource) servletContext.getAttribute("dataSource"));

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<ProductDTO> allProducts = productBo.getAllProducts();
            List<CategoryDTO>categoryDTOS = categoryBo.getAllCategories();
            req.setAttribute("products", allProducts);
            req.setAttribute("categories", categoryDTOS);
            req.getRequestDispatcher("product.jsp").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("update".equals(action)) {
            handleUpdate(req, resp);
        } else if ("delete".equals(action)) {
            handleDelete(req, resp);
        }
    }

    private void handleDelete(HttpServletRequest req, HttpServletResponse resp) {
        String Product_Id = req.getParameter("product_id");
        boolean isProductDeleted = productBo.delete(Product_Id);
        if (isProductDeleted) {
            try {
                resp.sendRedirect("product.jsp?message=Category deleted successfully");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            try {
                resp.sendRedirect("product.jsp?error=Category delete failed");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleUpdate(HttpServletRequest req, HttpServletResponse resp) {
        String Product_Id = req.getParameter("product_id");
        String ProductName = req.getParameter("product_name");
        String category = req.getParameter("category_id");
        Double price = Double.valueOf(req.getParameter("price"));
        Integer stock = Integer.valueOf(req.getParameter("stock"));
        String description = req.getParameter("description");
        String base64Image = req.getParameter("base64_image");

        ProductDTO productDTO = new ProductDTO(Product_Id, ProductName, category, price, stock, description, base64Image);
        boolean isProductUpdated = productBo.update(productDTO);
        if (isProductUpdated) {
            try {
                resp.sendRedirect("product.jsp?message=Category updated successfully");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            try {
                resp.sendRedirect("product.jsp?error=Category update failed");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
