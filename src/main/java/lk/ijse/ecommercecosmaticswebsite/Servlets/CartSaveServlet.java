package lk.ijse.ecommercecosmaticswebsite.Servlets;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.ecommercecosmaticswebsite.bo.Bo.CartBo;
import lk.ijse.ecommercecosmaticswebsite.bo.Bo.CartItemBo;
import lk.ijse.ecommercecosmaticswebsite.bo.BoFactory;
import lk.ijse.ecommercecosmaticswebsite.bo.BoImp.CartBoImpl;
import lk.ijse.ecommercecosmaticswebsite.bo.BoImp.CartItemBoImpl;
import lk.ijse.ecommercecosmaticswebsite.dto.CartDTO;
import lk.ijse.ecommercecosmaticswebsite.dto.CartItemDTO;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "CartSaveServlet", value = "/cart-save")
public class CartSaveServlet extends HttpServlet {

    CartBo cartBo = (CartBo) BoFactory.getBoFactory().getBO(BoFactory.BoType.Cart);
    CartItemBo cartItemBo = (CartItemBo) BoFactory.getBoFactory().getBO(BoFactory.BoType.CartItem);

    @Override
    public void init() throws ServletException {
        ServletContext servletContext = getServletContext();
        cartBo = new CartBoImpl((DataSource) servletContext.getAttribute("dataSource"));
        cartItemBo = new CartItemBoImpl((DataSource) servletContext.getAttribute("dataSource"));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String cartData = req.getParameter("cartData");
        Gson gson = new Gson();
        JsonObject cartJson = gson.fromJson(cartData, JsonObject.class);
        System.out.println(cartJson);

        String userId = cartJson.get("user_id").isJsonNull() ? null : cartJson.get("user_id").getAsString();
        String cartId = cartBo.generateCartId();

        JsonArray products = cartJson.getAsJsonArray("products");
        List<CartItemDTO> cartItems = new ArrayList<>();
        double totalPrice = 0.0;

        for (int i = 0; i < products.size(); i++) {
            JsonObject product = products.get(i).getAsJsonObject();
            String productId = product.get("productId").getAsString();
            int quantity = product.get("quantity").getAsInt();
            double price = product.get("price").getAsDouble();
            String cartItemId = "";

            totalPrice += price * quantity;

            CartItemDTO cartItemDTO = new CartItemDTO(cartItemId, cartId, productId, quantity);
            cartItems.add(cartItemDTO);

        }

        CartDTO cartDTO = new CartDTO(cartId, userId, totalPrice);

        boolean cartSaved = cartBo.saveCart(cartDTO);

        if (!cartSaved) {
            resp.sendRedirect("product.jsp?error=Failed to save the cart");
            return;
        }

        boolean allItemsSaved = cartItemBo.saveCartItems(cartItems);

        if (allItemsSaved) {
            resp.sendRedirect("product.jsp?message=Your cart was saved successfully. Total Price: " + totalPrice);
        } else {
            resp.sendRedirect("product.jsp?error=Failed to save some or all cart items.");
        }
    }




}
