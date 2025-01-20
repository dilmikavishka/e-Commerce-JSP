package lk.ijse.ecommercecosmaticswebsite.Servlets;


import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.ecommercecosmaticswebsite.bo.Bo.CartBo;
import lk.ijse.ecommercecosmaticswebsite.bo.BoFactory;
import lk.ijse.ecommercecosmaticswebsite.bo.BoImp.CartBoImpl;


import javax.sql.DataSource;
import java.io.IOException;

@WebServlet(name = "CartActionsServlet", value = "/cart-actions")
public class CartActionServlet extends HttpServlet {
    CartBo cartBo = (CartBo) BoFactory.getBoFactory().getBO(BoFactory.BoType.Cart);

    @Override
    public void init() throws ServletException {
        ServletContext servletContext = getServletContext();
        cartBo = new CartBoImpl((DataSource) servletContext.getAttribute("dataSource"));

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

    private void handleDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String cartId = req.getParameter("cart_id");
        boolean isDeleted = cartBo.deleteCart(cartId);

        if (isDeleted) {

            resp.sendRedirect("order.jsp?message=Item%20deleted%20successfully");
        } else {

            resp.sendRedirect("order.jsp?error=Failed%20to%20delete%20item");
        }
    }

    private void handleUpdate(HttpServletRequest req, HttpServletResponse resp) {
    }
}
