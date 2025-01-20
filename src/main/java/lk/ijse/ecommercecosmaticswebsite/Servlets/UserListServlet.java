package lk.ijse.ecommercecosmaticswebsite.Servlets;

import jakarta.servlet.RequestDispatcher;
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
import java.util.List;

@WebServlet(name = "UserListServlet", value = "/user-table")
public class UserListServlet extends HttpServlet {

    UserBo user = (UserBo) BoFactory.getBoFactory().getBO(BoFactory.BoType.User);

    @Override
    public void init() throws ServletException {
        ServletContext servletContext = getServletContext();
        user = new UserBoImpl((DataSource) servletContext.getAttribute("dataSource"));
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp){
        try {
            List<UserDTO> userList = user.getAllUsers();
            System.out.println("Fetched Users: " + userList);
            req.setAttribute("users", userList);
            RequestDispatcher rd = req.getRequestDispatcher("user-table.jsp");
            rd.forward(req, resp);

        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
        List<UserDTO> userList = user.getAllUsers();
        System.out.println("Fetched Users: " + userList);
    }


}
