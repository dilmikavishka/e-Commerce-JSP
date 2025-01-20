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
import lk.ijse.ecommercecosmaticswebsite.dto.OrderDTO;
import lk.ijse.ecommercecosmaticswebsite.dto.OrderDetailDTO;


import javax.sql.DataSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "OrderCompletionServlet", value = "/order-Actions")
public class OrderCompletionServlet extends HttpServlet {

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
        String action = req.getParameter("action");
        if ("save".equals(action)) {
            saveOrder(req, resp);
        } else if ("update".equals(action)) {
            updateOrder(req, resp);

        } else {
            resp.sendRedirect("error.jsp");
        }
    }

    private void updateOrder(HttpServletRequest req, HttpServletResponse resp) {

    }

    private void saveOrder(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String orderId = orderBo.generateNewOrderId();
        String cartId = req.getParameter("cart_id");
        String userId = req.getParameter("user_id");
        if (userId != null) {
            userId = userId.replaceAll("[\\[\\]]", "");
        }
        String totalPrice = req.getParameter("total_price");
        String status = "Pending";
        String products = req.getParameter("products");

        System.out.println("Order Saved: ");
        System.out.println("Order ID: " + orderId);
        System.out.println("Cart ID: " + cartId);
        System.out.println("User ID: " + userId);
        System.out.println("Total Price: " + totalPrice);
        System.out.println("Status: " + status);
        System.out.println("Products: " + products);

        String[] productItems = products.split(";");
        List<OrderDetailDTO> orderDetails = new ArrayList<>();

        for (String productItem : productItems) {
            String[] productDetails = productItem.split(",");
            String productId = productDetails[0];
            int quantity = Integer.parseInt(productDetails[1]);
            double price = Double.parseDouble(productDetails[2]);

            OrderDetailDTO orderDetail = new OrderDetailDTO();
            orderDetail.setOrderDetailId("");
            orderDetail.setOrderId(orderId);
            orderDetail.setProductId(productId);
            orderDetail.setQuantity(quantity);
            orderDetail.setPrice(price);

            orderDetails.add(orderDetail);
        }


        OrderDTO order = new OrderDTO();
        order.setOrderId(orderId);
        order.setUserId(userId);
        order.setTotalPrice(Double.parseDouble(totalPrice));
        order.setStatus(status);
        boolean orderSaved = orderBo.saveOrder(order);
        boolean orderDetailsSaved = orderDetailBo.saveOrderDetails(orderDetails);

        if (orderSaved && orderDetailsSaved) {
            cartBo.deleteCart(cartId);
            resp.sendRedirect("order.jsp?message=Order Saved Successfully Please go to your order management for to see the status of order");
        } else {
            resp.sendRedirect("order.jsp?message=Error saving order");
        }
    }


}
