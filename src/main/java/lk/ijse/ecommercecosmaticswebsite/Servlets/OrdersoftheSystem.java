package lk.ijse.ecommercecosmaticswebsite.Servlets;


import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.ecommercecosmaticswebsite.bo.Bo.CartBo;
import lk.ijse.ecommercecosmaticswebsite.bo.Bo.OrderBo;
import lk.ijse.ecommercecosmaticswebsite.bo.Bo.OrderDetailBo;
import lk.ijse.ecommercecosmaticswebsite.bo.BoFactory;
import lk.ijse.ecommercecosmaticswebsite.bo.BoImp.CartBoImpl;
import lk.ijse.ecommercecosmaticswebsite.bo.BoImp.OrderBoImpl;
import lk.ijse.ecommercecosmaticswebsite.bo.BoImp.OrderDetailBoImpl;
import lk.ijse.ecommercecosmaticswebsite.dto.OrderDetailsWithOrder;


import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "OrdersoftheSystem",value = "/getOrdersoftheSystem")
public class OrdersoftheSystem extends HttpServlet {

    OrderBo orderBo = (OrderBo) BoFactory.getBoFactory().getBO(BoFactory.BoType.Order);
    CartBo cartBo = (CartBo) BoFactory.getBoFactory().getBO(BoFactory.BoType.Cart);
    OrderDetailBo orderDetailBo = (OrderDetailBo) BoFactory.getBoFactory().getBO(BoFactory.BoType.OrderDetail);

    @Override
    public void init() throws ServletException {
        ServletContext servletContext = getServletContext();
        orderBo = new OrderBoImpl((DataSource) servletContext.getAttribute("dataSource"));
        cartBo = new CartBoImpl((DataSource) servletContext.getAttribute("dataSource"));
        orderDetailBo = new OrderDetailBoImpl((DataSource) servletContext.getAttribute("dataSource"));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String orderId = req.getParameter("orderId");
        String status = req.getParameter("status");

        if (orderId == null || status == null || orderId.isEmpty() || status.isEmpty()) {
            resp.sendRedirect("order-management.jsp?error=Invalid input. Please try again.");
            return;
        }
        try {
            boolean isUpdated = orderBo.updateOrder(orderId, status);
            if (isUpdated) {
                resp.sendRedirect("order-management.jsp?message=Order updated successfully.");
            } else {
                resp.sendRedirect("order-management.jsp?error=Failed to update the order. Please try again.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect("order-management.jsp?error=An error occurred while updating the order.");
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter("userId");
        String userRole = req.getParameter("role");
        System.out.println(userRole + userId);
        if (userRole != null && !userRole.isEmpty()) {
            try {
                List<OrderDetailsWithOrder> orderDetailsWithOrders;

                if ("admin".equals(userRole)) {
                    orderDetailsWithOrders = orderBo.getAllOrderDetails();
                } else {
                    if (userId != null && !userId.isEmpty()) {
                        orderDetailsWithOrders = orderBo.getOrderDetailsByUser(userId);
                    } else {
                        resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "User ID is missing.");
                        return;
                    }
                }

                System.out.println("Order details size: " + (orderDetailsWithOrders != null ? orderDetailsWithOrders.size() : "null"));
                req.setAttribute("orderDetailsWithOrders", orderDetailsWithOrders);
                req.getRequestDispatcher("order-management.jsp").forward(req, resp);
            } catch (Exception e) {
                e.printStackTrace();
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error retrieving cart items.");
            }
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "User role is missing.");
        }
    }
}
