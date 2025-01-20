package lk.ijse.ecommercecosmaticswebsite.Servlets;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.ecommercecosmaticswebsite.bo.Bo.CartItemBo;
import lk.ijse.ecommercecosmaticswebsite.bo.BoFactory;
import lk.ijse.ecommercecosmaticswebsite.bo.BoImp.CartItemBoImpl;
import lk.ijse.ecommercecosmaticswebsite.dto.CartItemDetailsDTO;


import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "OrderSaveServlet", value = "/getOrders")
public class OrderSaveServlet extends HttpServlet {

    CartItemBo cartItemBo = (CartItemBo) BoFactory.getBoFactory().getBO(BoFactory.BoType.CartItem);

    @Override
    public void init() throws ServletException {
        ServletContext servletContext = getServletContext();
        cartItemBo = new CartItemBoImpl((DataSource) servletContext.getAttribute("dataSource"));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter("userId");
        String userRole = req.getParameter("role");
        System.out.println(userRole + userId);
        if (userRole != null && !userRole.isEmpty()) {
            try {
                List<CartItemDetailsDTO> cartItemDetailsList;

                if ("admin".equals(userRole)) {

                    cartItemDetailsList = cartItemBo.getAllCartItemDetails();
                } else {
                    if (userId != null && !userId.isEmpty()) {
                        cartItemDetailsList = cartItemBo.getCartItemDetails(userId);
                    } else {
                        resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "User ID is missing.");
                        return;
                    }
                }

                req.setAttribute("cartItemDetails", cartItemDetailsList);
                req.getRequestDispatcher("order.jsp").forward(req, resp);
            } catch (Exception e) {
                e.printStackTrace();
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error retrieving cart items.");
            }
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "User role is missing.");
        }
    }
}
