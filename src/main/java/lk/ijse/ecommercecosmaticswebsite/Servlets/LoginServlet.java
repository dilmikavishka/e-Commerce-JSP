package lk.ijse.ecommercecosmaticswebsite.Servlets;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lk.ijse.ecommercecosmaticswebsite.bo.Bo.UserBo;
import lk.ijse.ecommercecosmaticswebsite.bo.BoFactory;
import lk.ijse.ecommercecosmaticswebsite.bo.BoImp.UserBoImpl;
import lk.ijse.ecommercecosmaticswebsite.dto.UserDTO;


import javax.sql.DataSource;
import java.io.IOException;

@WebServlet(name = "LoginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

    UserBo userbo = (UserBo) BoFactory.getBoFactory().getBO(BoFactory.BoType.User);

    @Override
    public void init() throws ServletException {
        ServletContext servletContext = getServletContext();
        userbo = new UserBoImpl((DataSource) servletContext.getAttribute("dataSource"));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        try {
            UserDTO user = userbo.authenticateUser(username, password);

            if (user != null) {
                HttpSession session = req.getSession();
                session.setAttribute("loggedInUser", user);
                session.setAttribute("userId", user.getUserId());
                session.setAttribute("username", user.getUsername());
                session.setAttribute("email", user.getEmail());
                session.setAttribute("role", user.getRole());

                resp.sendRedirect("index.jsp");
            } else {
                resp.sendRedirect("login.jsp?error=Invalid Username or Password");
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect("login.jsp?error=Something went wrong. Please try again.");
        }
    }
}
