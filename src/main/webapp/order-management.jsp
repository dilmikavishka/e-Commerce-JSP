<%@ page import="java.util.List" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="lk.ijse.ecommercecosmaticswebsite.dto.OrderDetailsWithOrder" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Order Details</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f8f9fa;
        }
        .card {
            border: none;
            border-radius: 10px;
            box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
            transition: transform 0.3s ease, box-shadow 0.3s ease;
        }
        .card:hover {
            transform: scale(1.02);
            box-shadow: 0px 8px 12px rgba(0, 0, 0, 0.2);
        }
        .card-header {
            background-color: #007bff;
            color: white;
            border-radius: 10px 10px 0 0;
        }
        .card-body {
            background-color: #ffffff;
        }
        .btn-primary {
            background-color: #007bff;
            border: none;
        }
        .btn-primary:hover {
            background-color: #0056b3;
        }
        .table {
            margin: 0;
            background-color: #f9f9f9;
        }
        .admin-section {
            display: none;
        }
    </style>
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

            List<OrderDetailsWithOrder> orderDetailsWithOrderList = (List<OrderDetailsWithOrder>) request.getAttribute("orderDetailsWithOrders");
            if (orderDetailsWithOrderList != null && !orderDetailsWithOrderList.isEmpty()) {
                HashMap<String, OrderDetailsWithOrder> uniqueOrders = new HashMap<>();
                for (OrderDetailsWithOrder orderDetails : orderDetailsWithOrderList) {
                    uniqueOrders.put(orderDetails.getOrderId(), orderDetails);
                }

                for (OrderDetailsWithOrder uniqueOrder : uniqueOrders.values()) {
        %>
        <div class="card mb-4" style="width: 100%;">
            <div class="card-header d-flex justify-content-between align-items-center">
                <h5 class="mb-0">Order ID: <%= uniqueOrder.getOrderId() %></h5>
                <button class="btn btn-primary btn-sm" type="button" data-bs-toggle="collapse" data-bs-target="#orderDetailsCollapse<%= uniqueOrder.getOrderId() %>" aria-expanded="false" aria-controls="orderDetailsCollapse<%= uniqueOrder.getOrderId() %>">
                    Toggle Details
                </button>
            </div>
            <div class="card-body collapse" id="orderDetailsCollapse<%= uniqueOrder.getOrderId() %>">
                <p class="card-text">
                    <strong>User ID:</strong> <%= uniqueOrder.getUserId() %><br>
                    <strong>Order Date:</strong> <%= uniqueOrder.getOrderDate() %><br>
                    <strong>Status:</strong>
                    <span
                            class="badge"
                            style="padding: 5px 10px;
                                <% if ("Pending".equals(uniqueOrder.getStatus())) { %>
                                    background-color: red; color: black; border: 2px solid #de4d18;
                                <% } else if ("accepted".equals(uniqueOrder.getStatus())) { %>
                                    background-color: lightgreen; color: black; border: 2px solid darkgreen;
                                <% } else if ("shipped".equals(uniqueOrder.getStatus())) { %>
                                    background-color: orange; color: black; border: 2px solid darkorange;
                                    <% } %>">
                                <%= uniqueOrder.getStatus() %>
                    </span>
                    <br>
                    <strong>Total Price:</strong> LKR <%= uniqueOrder.getTotalPrice() %>

                </p>
                <div class="admin-section">
                    <h6>Update Status</h6>
                    <form action="getOrdersoftheSystem" method="POST">
                        <input type="hidden" name="orderId" value="<%= uniqueOrder.getOrderId() %>">
                        <select class="form-select mb-3" name="status" required>
                            <option value="accepted" <%= "accepted".equals(uniqueOrder.getStatus()) ? "selected" : "" %>>Accepted</option>
                            <option value="shipped" <%= "shipped".equals(uniqueOrder.getStatus()) ? "selected" : "" %>>Shipped</option>
                        </select>
                        <button type="submit" class="btn btn-primary">Update Status</button>
                    </form>
                </div>

                <div class="mt-3">
                    <h6>Order Items:</h6>
                    <table class="table table-bordered">
                        <thead>
                        <tr>
                            <th>Product ID</th>
                            <th>Quantity</th>
                            <th>Price</th>
                            <th>Total Price Per Item</th>
                        </tr>
                        </thead>
                        <tbody>
                        <%
                            for (OrderDetailsWithOrder orderDetails : orderDetailsWithOrderList) {
                                if (orderDetails.getOrderId().equals(uniqueOrder.getOrderId())) {
                        %>
                        <tr>
                            <td><%= orderDetails.getProductId() %></td>
                            <td><%= orderDetails.getQuantity() %></td>
                            <td>LKR <%= orderDetails.getPrice() %></td>
                            <td>LKR <%= orderDetails.getTotalPricePerItem() %></td>
                        </tr>
                        <%
                                }
                            }
                        %>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <%
            }
        } else {
        %>
        <p>No order details found.</p>
        <%
            }
        %>
    </div>
</div>

<script>
    const user = JSON.parse(localStorage.getItem('user'));
    const isAdmin = user && user.role === 'admin';

    if (isAdmin) {
        const adminSections = document.querySelectorAll('.admin-section');
        adminSections.forEach(section => {
            section.style.display = 'block';
        });
    }
</script>

<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.min.js"></script>
</body>
</html>
