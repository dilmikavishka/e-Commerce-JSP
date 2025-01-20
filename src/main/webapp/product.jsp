<%@ page import="java.util.List" %>
<%@ page import="lk.ijse.ecommercecosmaticswebsite.dto.ProductDTO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Product Cards</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Material Icons -->
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <style>
        body {
            background-image: url("111.jpg");
            /*background-color: #f4f6f9;*/
            font-family: 'Arial', sans-serif;
        }

        .product-card {
            border: none;
            border-radius: 12px;
            overflow: hidden;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            transition: transform 0.3s, box-shadow 0.3s;
            background-color: #fff;
            width: 100%;
            max-width: 350px;
        }

        .product-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);
        }

        .product-card .card-header {
            background-color: #007bff;
            color: #fff;
            padding: 10px;
            text-align: center;
            font-size: 18px;
            font-weight: bold;
        }

        .product-card .card-body {
            padding: 15px;
            text-align: center;
        }

        .product-card .card-body h5 {
            font-size: 20px;
            margin-bottom: 10px;
            color: #333;
        }

        .product-card .card-body p {
            font-size: 14px;
            color: #555;
            margin: 5px 0;
        }

        .product-card .card-footer {
            background-color: #f8f9fa;
            padding: 10px;
            text-align: center;
        }

        .product-card .action-buttons button {
            margin: 5px 5px 0;
        }

        .modal-content {
            border-radius: 8px;
        }

        .filter-container {
            margin-bottom: 20px;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .cart-modal {
            position: fixed;
            bottom: 20px;
            right: 20px;
            z-index: 1050;
            background-color: #fff;
            border: 1px solid #ddd;
            border-radius: 8px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            width: 300px;
            max-height: 400px;
            overflow-y: auto;
            padding: 15px;
        }
        .product-card img {
            width: 100%;
            height: auto;
            max-height: 200px;
            object-fit: contain;
        }

        .cart-modal ul {
            list-style: none;
            padding: 0;
        }

        .cart-modal ul li {
            padding: 5px 0;
        }
    </style>
</head>
<body>
<jsp:include page="sidebar.jsp" />
<div class="content container mt-5">
    <div class="row justify-content-center" id="productContainer">
        <script>
            const user = JSON.parse(localStorage.getItem('user'));

            const isAdmin = user.role === 'admin';
        </script>

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
            List<ProductDTO> productDTOList = (List<ProductDTO>) request.getAttribute("products");
            if (productDTOList != null && !productDTOList.isEmpty()) {
                for (ProductDTO product : productDTOList) {
        %>

        <div class="col-md-4 mb-4 d-flex justify-content-center">
            <div class="card product-card" data-category="<%= product.getCategory_id() %>">
                <div class="card-header">
                    Product ID: <%= product.getProduct_id() %>
                </div>
                <div class="card-body">
                    <img src="<%= product.getBase64Image() %>"
                         alt="<%= product.getBase64Image() %>"
                         class="img-fluid" />
                    <h5 class="card-title"><%= product.getProduct_name() %></h5>
                    <p><%= product.getDescription() %></p>
                    <p>Category: <%= product.getCategory_id() %></p>
                    <p>Price: <%= product.getPrice() %></p>
                </div>
                <div class="card-footer">
                    <div class="action-buttons">
                        <button class="btn btn-primary btn-sm" data-bs-toggle="modal" data-bs-target="#detailsModal<%= product.getProduct_id() %>">View Details</button>
                        <div class="action-buttons">
                            <button class="btn btn-success btn-sm" onclick="addToCart('<%= product.getProduct_id() %>', '<%= product.getProduct_name() %>', '<%= product.getPrice() %>')">
                                <span class="material-icons">add_shopping_cart</span> Add to Cart
                            </button>
                            <script>
                                if (isAdmin) {
                                    document.write('<button class="btn btn-warning btn-sm" data-bs-toggle="modal" data-bs-target="#updateModal<%= product.getProduct_id() %>">Update</button>');
                                    document.write('<button class="btn btn-danger btn-sm" data-bs-toggle="modal" data-bs-target="#deleteModal<%= product.getProduct_id() %>">Delete</button>');
                                }
                            </script>
                        </div>
                    </div>
                </div>
            </div>
        </div>


        <div class="modal fade" id="updateModal<%= product.getProduct_id() %>" tabindex="-1" aria-labelledby="updateModalLabel<%= product.getProduct_id() %>" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="updateModalLabel<%= product.getProduct_id() %>">Update Product</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <form action="product-list" method="post">
                        <div class="modal-body">
                            <input type="hidden" name="product_id" value="<%= product.getProduct_id() %>">
                            <div class="mb-3">
                                <label for="product_name" class="form-label">Product Name</label>
                                <input type="text" class="form-control" id="product_name" name="product_name" value="<%= product.getProduct_name() %>" required>
                            </div>
                            <div class="mb-3">
                                <label for="category_id" class="form-label">Product Name</label>
                                <input type="text" class="form-control" id="category_id" name="category_id" value="<%= product.getCategory_id() %>" required>
                            </div>
                            <div class="mb-3">
                                <label for="description" class="form-label">Description</label>
                                <input type="text" class="form-control" id="description" name="description" value="<%= product.getDescription() %>" required>
                            </div>
                            <div class="mb-3">
                                <label for="price" class="form-label">Price</label>
                                <input type="number" class="form-control" id="price" name="price" value="<%= product.getPrice() %>" required>
                            </div>
                            <div class="mb-3">
                                <label for="stock" class="form-label">Stock</label>
                                <input type="number" class="form-control" id="stock" name="stock" value="<%= product.getStock() %>" required>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                            <button type="submit" class="btn btn-primary" name="action" value="update">Save changes</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>


        <div class="modal fade" id="deleteModal<%= product.getProduct_id() %>" tabindex="-1" aria-labelledby="deleteModalLabel<%= product.getProduct_id() %>" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="deleteModalLabel<%= product.getProduct_id() %>">Delete Product</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <p>Are you sure you want to delete this product?</p>
                    </div>
                    <div class="modal-footer">
                        <form action="product-list" method="post">
                            <input type="hidden" name="product_id" value="<%= product.getProduct_id() %>">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                            <button type="submit" class="btn btn-danger" name="action" value="delete">Yes, Delete</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>


        <div class="modal fade" id="detailsModal<%= product.getProduct_id() %>" tabindex="-1" aria-labelledby="detailsModalLabel<%= product.getProduct_id() %>" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="detailsModalLabel<%= product.getProduct_id() %>">Product Details</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <p><strong>Product ID:</strong> <%= product.getProduct_id() %></p>
                        <p><strong>Name:</strong> <%= product.getProduct_name() %></p>
                        <p><strong>Description:</strong> <%= product.getDescription() %></p>
                        <p><strong>Category:</strong> <%= product.getCategory_id() %></p>
                        <p><strong>Price:</strong> <%= product.getPrice() %></p>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>

        <%
            }
        } else {
        %>
        <div class="col-12 text-center">
            <p>No products available.</p>
        </div>
        <%
            }
        %>

    </div>
</div>
<!-- Shopping Cart Modal -->
<div class="cart-modal" id="cartModal">
    <h5>Shopping Cart</h5>
    <ul id="cartItems"></ul>
    <div><strong>Total: </strong><span id="totalPrice">0</span></div>
        <form id="cartSaveForm" action="cart-save" method="POST">
<%--    <form id="cartSaveForm">--%>
        <input type="hidden" name="cartData" id="cartData">
        <button type="button" class="btn btn-primary mt-3" onclick="alertCartDataAndSave()">Save Cart</button>
    </form>
</div>

<script>
    let cart = [];

    const user_id = user.id;
    function addToCart(productId, productName, price) {
        price = parseFloat(price);

        const existingProduct = cart.find(item => item.productId === productId);
        if (existingProduct) {
            existingProduct.quantity += 1;
        } else {
            cart.push({ user_id, productId, productName, price, quantity: 1 });
        }
        updateCartModal();
    }

    function updateCartModal() {
        const cartItemsContainer = document.getElementById("cartItems");
        const totalPriceElement = document.getElementById("totalPrice");
        let total = 0;

        cartItemsContainer.innerHTML = '';
        cart.forEach(item => {
            total += item.price * item.quantity;
            const cartItem = document.createElement("li");
            cartItem.textContent = item.productName + "=" + item.price * item.quantity + " x " + item.quantity;
            cartItemsContainer.appendChild(cartItem);
        });

        totalPriceElement.textContent = total.toFixed(2);
        document.getElementById("cartData").value = JSON.stringify({ user_id, products: cart });
    }

    function alertCartDataAndSave() {
        console.log(cart);
        document.getElementById("cartSaveForm").submit();
    }


    <%--const products = [];--%>

    <%--<% if (productDTOList != null) { %>--%>
    <%--<% for (ProductDTO product : productDTOList) { %>--%>
    <%--products.push({--%>
    <%--    p_id: "<%= product.getProduct_id() %>",--%>
    <%--    p_name: "<%= product.getProduct_name() %>",--%>
    <%--    p_description: "<%= product.getDescription() %>",--%>
    <%--    p_category: "<%= product.getCategory_id() %>",--%>
    <%--    price: "<%= product.getPrice() %>",--%>
    <%--    p_image: "<%= product.getBase64Image() %>"--%>
    <%--});--%>
    <%--<% } %>--%>
    <%--<% } %>--%>

    <%--// Store in localStorage--%>
    <%--localStorage.setItem("products", JSON.stringify(products));--%>
    <%--console.log("Products stored in localStorage:", products);--%>
</script>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>