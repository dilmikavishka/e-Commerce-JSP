package lk.ijse.ecommercecosmaticswebsite.Servlets;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.ecommercecosmaticswebsite.bo.Bo.CategoryBo;
import lk.ijse.ecommercecosmaticswebsite.bo.BoFactory;
import lk.ijse.ecommercecosmaticswebsite.bo.BoImp.CategoryBoImpl;
import lk.ijse.ecommercecosmaticswebsite.dto.CategoryDTO;

import javax.sql.DataSource;
import java.io.IOException;

@WebServlet(name = "CategorySaveServlet", urlPatterns = "/category-save")
public class CategorySaveServlet extends HttpServlet {
    CategoryBo categoryBo = (CategoryBo) BoFactory.getBoFactory().getBO(BoFactory.BoType.Category);

    @Override
    public void init() throws ServletException {
        ServletContext servletContext = getServletContext();
        categoryBo = new CategoryBoImpl((DataSource) servletContext.getAttribute("dataSource"));
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getServletPath();

        switch (action) {
            case "/category-save":
                saveCategory(req, resp);
                break;
            default:
                resp.sendRedirect("category-management.jsp?error=Invalid action");

        }
    }

    private void saveCategory(HttpServletRequest req, HttpServletResponse resp) {
        String categoryId =  categoryBo.generateCategoryId();
        String categoryName = req.getParameter("Category_name");
        String description = req.getParameter("description");

        CategoryDTO categoryDTO = new CategoryDTO(categoryId, categoryName, description);
        boolean isCategorySaved = categoryBo.saveCategory(categoryDTO);
        if (isCategorySaved) {
            try {
                resp.sendRedirect("category-management.jsp?message=Category Saved Successfully");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                resp.sendRedirect("category-management.jsp?error=Failed to save the category");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
