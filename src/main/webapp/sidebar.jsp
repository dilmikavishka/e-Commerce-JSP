<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="CSS/styles.css">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <script>
        function toggleSidebar() {
            var sidebar = document.querySelector('.sidebar');
            var toggleBtn = document.querySelector('.toggle-btn span');
            sidebar.classList.toggle('expanded');
            if (sidebar.classList.contains('expanded')) {
                toggleBtn.textContent = 'arrow_back';
            } else {
                toggleBtn.textContent = 'arrow_forward';
            }
        }

        window.onload = function () {
            const user = JSON.parse(localStorage.getItem('user'));
            if (user && user.role) {
                if (user.role === 'admin') {
                    document.getElementById('add-customer').style.display = 'block';
                    document.getElementById('home').style.display = 'block';
                    document.getElementById('view-customer').style.display = 'block';
                    document.getElementById('action-customer').style.display = 'block';
                    document.getElementById('order-management').style.display = 'block';
                    document.getElementById('category-management').style.display = 'block';
                    document.getElementById('category-table').style.display = 'block';
                    document.getElementById('product-management').style.display = 'block';
                    document.getElementById('product').style.display = 'block';
                    document.getElementById('order').style.display = 'block';
                } else {
                    document.getElementById('order-management').style.display = 'block';
                    document.getElementById('home').style.display = 'block';
                    document.getElementById('product').style.display = 'block';
                    document.getElementById('order').style.display = 'block';
                    document.getElementById('view-customer').style.display = 'none';
                    document.getElementById('action-customer').style.display = 'none';
                    document.getElementById('add-customer').style.display = 'none';
                    document.getElementById('category-management').style.display = 'none';
                    document.getElementById('category-table').style.display = 'none';
                    document.getElementById('product-management').style.display = 'none';
                }
                document.getElementById('order').href = 'getOrders?userId=' + user.id + '&role=' + user.role;
                document.getElementById('order-management').href = 'getOrdersoftheSystem?userId=' + user.id + '&role=' + user.role;
            } else {
                console.error('User data not found or role is missing in localStorage.');
            }
        };

    </script>
</head>
<body>
<div class="sidebar">
    <a href="javascript:void(0);" onclick="toggleSidebar()" class="toggle-btn">
        <span class="material-icons">arrow_forward</span>
    </a>

    <h2 style="display: none;">Menu</h2>

    <!-- Admin-specific sections -->
    <a href="index.jsp" id="home" style="display: none;">
        <span class="material-icons" style="color: #CF1936">home</span>
        <span class="text">Home</span>
    </a>

    <a href="user-save.jsp" id="add-customer" style="display: none;">
        <span class="material-icons" style="color: #CF1936">person_add</span>
        <span class="text">Add Customer</span>
    </a>

    <a href="user-table" id="view-customer" style="display: none;">
        <span class="material-icons" style="color: #CF1936">group</span>
        <span class="text">View Customers</span>
    </a>

    <a href="user-actions.jsp" id="action-customer" style="display: none;">
        <span class="material-icons" style="color: #CF1936">build_circle</span>
        <span class="text">Manage Customers</span>
    </a>

    <a href="getOrdersoftheSystem" id="order-management" style="display: none;">
        <span class="material-icons" style="color: #CF1936">receipt_long</span>
        <span class="text">Order Management</span>
    </a>

    <a href="category-management.jsp" id="category-management" style="display: none;">
        <span class="material-icons" style="color: #CF1936">category</span>
        <span class="text">Category Management</span>
    </a>

    <a href="category-table" id="category-table" style="display: none;">
        <span class="material-icons" style="color: #CF1936">table_chart</span>
        <span class="text">Category Table</span>
    </a>

    <a href="product-management.jsp" id="product-management" style="display: none;">
        <span class="material-icons" style="color: #CF1936">inventory_2</span>
        <span class="text">Product Management</span>
    </a>

    <!-- Common sections for all users -->
    <a href="product-list" id="product" style="display: none;">
        <span class="material-icons" style="color: #CF1936">shopping_bag</span>
        <span class="text">Products</span>
    </a>

    <a href="getOrders" id="order" style="display: none;">
        <span class="material-icons" style="color: #CF1936">credit_card</span>
        <span class="text">Orders</span>
    </a>
</div>
</body>
</html>
