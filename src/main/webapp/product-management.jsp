<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add Product</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<jsp:include page="sidebar.jsp" />
<div class="content">
    <div class="container my-4">
        <h1 class="text-center">Product Management</h1>

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


        <div class="card mb-4">
            <div class="card-header">Add Product</div>
            <div class="card-body">
                <form action="saveProduct" method="post">
                    <div class="mb-3">
                        <label for="product_name" class="form-label">Product Name</label>
                        <input type="text" class="form-control" id="product_name" name="product_name" required>
                    </div>
                    <div class="mb-3">
                        <label for="category_id" class="form-label">Category</label>
                        <select class="form-select" id="category_id" name="category_id" required>
                            <option value="">Select Category</option>
                        </select>
                    </div>
                    <div class="mb-3">
                        <label for="price" class="form-label">Price</label>
                        <input type="number" class="form-control" id="price" name="price" step="0.01" required>
                    </div>
                    <div class="mb-3">
                        <label for="stock" class="form-label">Stock Quantity</label>
                        <input type="number" class="form-control" id="stock" name="stock" required>
                    </div>
                    <div class="mb-3">
                        <label for="description" class="form-label">Description</label>
                        <textarea class="form-control" id="description" name="description" rows="3"></textarea>
                    </div>
                    <div class="mb-3">
                        <label for="image" class="form-label">Product Image</label>
                        <input type="file" class="form-control" id="image" accept="image/*">
                        <input type="hidden" id="base64_image" name="base64_image">
                    </div>
                    <button type="submit" class="btn btn-primary" id="submit_button">Save Product</button>
                </form>

            </div>
        </div>
    </div>
</div>

<script>
    const categories = JSON.parse(localStorage.getItem('categories')) || [];
    const categorySelect = document.getElementById('category_id');

    if (categories.length > 0) {
        categories.forEach(category => {
            const option = document.createElement('option');
            option.value = category.Id;
            option.textContent = category.name;
            categorySelect.appendChild(option);
        });
    } else {
        console.log("No categories found in localStorage.");
    }



    //image ek convert krn ek
    document.getElementById('image').addEventListener('change', function(event) {
        const file = event.target.files[0];
        if (file) {
            const reader = new FileReader();
            reader.onload = function(e) {
                document.getElementById('base64_image').value = e.target.result;
            };
            reader.readAsDataURL(file);
        }
    });

    document.querySelector('form').addEventListener('submit', function(event) {
        const base64Image = document.getElementById('base64_image').value;
        if (!base64Image) {
            event.preventDefault();
            alert('Please select an image to upload.');
        }
    });



</script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
