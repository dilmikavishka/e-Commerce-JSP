package lk.ijse.ecommercecosmaticswebsite.Servlets;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.ecommercecosmaticswebsite.bo.Bo.UserBo;
import lk.ijse.ecommercecosmaticswebsite.bo.BoFactory;
import lk.ijse.ecommercecosmaticswebsite.bo.BoImp.UserBoImpl;
import lk.ijse.ecommercecosmaticswebsite.dto.UserDTO;


import javax.sql.DataSource;
import java.io.IOException;

@WebServlet(name = "UserServlet", urlPatterns = {"/user-save", "/customer-get"})
public class UserSaveServlet extends HttpServlet {


    UserBo user = (UserBo) BoFactory.getBoFactory().getBO(BoFactory.BoType.User);

    @Override
    public void init() throws ServletException {
        ServletContext servletContext = getServletContext();
        user = new UserBoImpl((DataSource) servletContext.getAttribute("dataSource"));
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getServletPath();

        switch (action) {
            case "/user-save":
                try {
                    saveUser(req, resp);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                break;
            default:
                resp.sendRedirect("user-save.jsp?error=Invalid action");
        }
    }




    private void saveUser(HttpServletRequest req, HttpServletResponse resp) throws IOException, ClassNotFoundException {
        String userId = user.generateNewUserId();
        String username = req.getParameter("username");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String role = req.getParameter("role");

        UserDTO userDTO = new UserDTO(userId, username, password, email, role);
        boolean isSaved = user.saveUser(userDTO);
        if (isSaved) {
            resp.sendRedirect("user-save.jsp?message=User Saved Successfully");
        } else {
            resp.sendRedirect("user-save.jsp?error=Failed to save the user");
        }
    }

}
