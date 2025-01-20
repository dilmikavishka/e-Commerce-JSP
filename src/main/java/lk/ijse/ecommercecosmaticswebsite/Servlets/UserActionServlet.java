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

@WebServlet(name = "UserActionServlet", urlPatterns = {"/user-action"})
public class UserActionServlet extends HttpServlet {

    UserBo user = (UserBo) BoFactory.getBoFactory().getBO(BoFactory.BoType.User);

    @Override
    public void init() throws ServletException {
        ServletContext servletContext = getServletContext();
        user = new UserBoImpl((DataSource) servletContext.getAttribute("dataSource"));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        String userId = req.getParameter("user_id");

        if (userId == null || userId.isEmpty()) {
            resp.sendRedirect("user-actions.jsp?error=User ID is required");
            return;
        }

        if ("update".equals(action)) {
            handleUpdate(req, resp, userId);
        } else if ("delete".equals(action)) {
            handleDelete(req, resp, userId);
        } else {
            resp.sendRedirect("user-actions.jsp?error=Invalid action");
        }
    }

    private void handleDelete(HttpServletRequest req, HttpServletResponse resp, String userId) {
        boolean isDeleted = user.deleteUser(userId);
        if (isDeleted) {
            try {
                resp.sendRedirect("user-actions.jsp?message=User deleted successfully");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                resp.sendRedirect("user-actions.jsp?error=User not found");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleUpdate(HttpServletRequest req, HttpServletResponse resp, String userId) {
        String user_Id = userId;
        String username = req.getParameter("username");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String role = req.getParameter("role");
        UserDTO userDTO = new UserDTO(user_Id, username, password, email, role);
        System.out.println(userDTO);
        boolean isUpdated = user.updateUser(userDTO);
        if (isUpdated) {
            try {
                resp.sendRedirect("user-actions.jsp?message=User updated successfully");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                resp.sendRedirect("user-actions.jsp?error=User not found");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
