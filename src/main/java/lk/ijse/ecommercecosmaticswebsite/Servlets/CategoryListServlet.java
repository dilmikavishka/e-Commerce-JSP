package lk.ijse.ecommercecosmaticswebsite.Servlets;

import jakarta.servlet.RequestDispatcher;
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
import java.util.List;

@WebServlet(name = "CategoryListServlet", urlPatterns = "/category-table")
public class CategoryListServlet extends HttpServlet {
    CategoryBo categoryBo = (CategoryBo) BoFactory.getBoFactory().getBO(BoFactory.BoType.Category);

    @Override
    public void init() throws ServletException {
        ServletContext servletContext = getServletContext();
        categoryBo = new CategoryBoImpl((DataSource) servletContext.getAttribute("dataSource"));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<CategoryDTO> categoryList = categoryBo.getAllCategories();
            System.out.println("Fetched Categories: " + categoryList);
            req.setAttribute("categories", categoryList);
            RequestDispatcher rd = req.getRequestDispatcher("category-table.jsp");
            rd.forward(req, resp);
        } catch (ServletException | IOException e) {
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
        String categoryId = req.getParameter("category_id");
        boolean isDeleted = categoryBo.deleteCategory(categoryId);
        if (isDeleted){
            try {
                resp.sendRedirect("category-table.jsp?message=Category deleted successfully");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void handleUpdate(HttpServletRequest req, HttpServletResponse resp) {
        String categoryId = req.getParameter("category_id");
        String categoryName = req.getParameter("category_name");
        String description = req.getParameter("description");
        CategoryDTO categoryDTO = new CategoryDTO(categoryId, categoryName, description);
        boolean isUpdated = categoryBo.updateCategory(categoryDTO);
        if (isUpdated){
            try {
                resp.sendRedirect("category-table.jsp?message=Category updated successfully");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
