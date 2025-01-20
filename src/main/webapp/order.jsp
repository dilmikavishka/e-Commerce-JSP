<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.stream.Collectors" %>
<%@ page import="java.util.Set" %>
<%@ page import="lk.ijse.ecommercecosmaticswebsite.dto.CartItemDetailsDTO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Order Details</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<jsp:include page="sidebar.jsp" />
<div class="content container mt-5">
    <div class="row justify-content-center">
        <%
            String message = request.getParameter("message");
            String error = request.getParameter("error");
            if (message != null) {
        %>
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            <%= message %>
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
        <%
        } else if (error != null) {
        %>
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            <%= error %>
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
        <%
            }
        %>

        <%
            List<CartItemDetailsDTO> cartItemDetailsDTOList = (List<CartItemDetailsDTO>) request.getAttribute("cartItemDetails");
            if (cartItemDetailsDTOList != null && !cartItemDetailsDTOList.isEmpty()) {
                Map<String, List<CartItemDetailsDTO>> cartGroupedById = cartItemDetailsDTOList.stream()
                        .collect(Collectors.groupingBy(CartItemDetailsDTO::getCartId));

                for (Map.Entry<String, List<CartItemDetailsDTO>> entry : cartGroupedById.entrySet()) {
                    String cartId = entry.getKey();
                    List<CartItemDetailsDTO> items = entry.getValue();
                    double totalPrice = items.stream().mapToDouble(CartItemDetailsDTO::getTotalPricePerItem).sum();
                    Set<String> userIds = items.stream().map(CartItemDetailsDTO::getUserID).collect(Collectors.toSet());
        %>
        <div class="card w-75 mb-4">
            <div class="card-header">
                <h4>Order Summary for Cart ID: <%= cartId %></h4>
            </div>
            <div class="card-body">
                <h5 class="card-title">Your Cart Items</h5>
                <table class="table table-striped">
                    <thead>
                    <tr>
                        <th>Product Name</th>
                        <th>Quantity</th>
                        <th>Price</th>
                        <th>Total</th>
                    </tr>
                    </thead>
                    <tbody>
                    <%
                        for (CartItemDetailsDTO dto : items) {
                    %>
                    <tr>
                        <td><%= dto.getProductName() %></td>
                        <td><%= dto.getQuantity() %></td>
                        <td><%= dto.getPrice() %></td>
                        <td><%= dto.getTotalPricePerItem() %></td>
                    </tr>
                    <%
                        }
                    %>
                    </tbody>
                </table>

                <div class="modal-footer">

                    <button type="button" class="btn btn-primary btn-lg" data-bs-toggle="modal" data-bs-target="#placeOrderModal<%= cartId %>">
                        Place Order
                    </button>
                </div>

                <form action="cart-actions" method="POST" onsubmit="return confirm('Are you sure you want to delete all items from this cart?');">
                    <input type="hidden" name="action" value="delete" />
                    <input type="hidden" name="cart_id" value="<%= cartId %>" />
                    <button type="submit" class="btn btn-danger btn-lg">Delete All Items</button>
                </form>
            </div>
            <div class="card-footer text-muted">
                Total Price: <%= totalPrice %>
            </div>


            <div class="modal fade" id="placeOrderModal<%= cartId %>" tabindex="-1" aria-labelledby="placeOrderModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <form action="order-Actions" method="POST">
                            <div class="modal-header">
                                <h5 class="modal-title" id="placeOrderModalLabel">Place Order</h5>
                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                            </div>
                            <div class="modal-body">
                                <div class="mb-3">
                                    <label for="userId" class="form-label">User ID</label>
                                    <input type="text" id="userId" name="user_id" value="<%= userIds %>" class="form-control" readonly />
                                </div>

                                <div class="mb-3">
                                    <label for="cartId" class="form-label">Cart ID</label>
                                    <input type="text" id="cartId" name="cart_id" value="<%= cartId %>" class="form-control" readonly />
                                </div>

                                <div class="mb-3">
                                    <label for="totalPrice" class="form-label">Total Price</label>
                                    <input type="text" id="totalPrice" name="total_price" value="<%= totalPrice %>" class="form-control" readonly />
                                </div>

                                <label for="status" class="form-label">Status</label>
                                <select class="form-select" id="status" name="status" required>
                                    <option selected value="Pending">Pending</option>
                                    <option value="Accepted">Accepted</option>
                                    <option value="Shipped">Shipped</option>
                                </select>

                                <div class="order-items mt-3">
                                    <h5>Order Items</h5>
                                    <table class="table">
                                        <thead>
                                        <tr>
                                            <th>Product Name</th>
                                            <th>Quantity</th>
                                            <th>Price</th>
                                            <th>Total</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <%
                                            for (CartItemDetailsDTO dto : items) {
                                        %>
                                        <tr>
                                            <td><%= dto.getProductName() %></td>
                                            <td><%= dto.getQuantity() %></td>
                                            <td><%= dto.getPrice() %></td>
                                            <td><%= dto.getTotalPricePerItem() %></td>
                                        </tr>
                                        <% } %>
                                        </tbody>
                                    </table>
                                </div>

                                <input type="hidden" name="products" value='<%= items.stream()
                                    .map(item -> item.getProductId() + "," + item.getQuantity() + "," + item.getPrice())
                                    .collect(Collectors.joining(";")) %>' />
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                                <button type="submit" class="btn btn-primary" name="action" value="update" id="updateButton" style="display:none;">Update</button>
                                <button type="submit" class="btn btn-primary" name="action" value="save">Confirm Order</button>

                            </div>
                        </form>
                    </div>
                </div>
            </div>

        </div>
        <%
            }
        } else {
        %>
        <div class="text-center">No items in your carts.</div>
        <%
            }
        %>
    </div>
</div>
<script>
    const user = JSON.parse(localStorage.getItem('user'));
    const userId = user.id;
    const userRole = user.role;

    if (userRole !== 'admin') {
        const statusSelect = document.querySelector('#status');
        statusSelect.disabled = true;
        const updateButton = document.querySelector('#updateButton');
        updateButton.style.display = 'none';
    } else {

        const updateButton = document.querySelector('#updateButton');
        updateButton.style.display = 'inline-block';
    }
</script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
