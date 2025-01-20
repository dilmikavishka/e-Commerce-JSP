<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet"/>
    <title>Title</title>
</head>
<body>
<jsp:include page="sidebar.jsp" />
<div class="content">
    <div class="container my-4">
        <h1 class="text-center">Category Management</h1>

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

        <!-- Customer Form -->
        <div class="card mb-4">
            <div class="card-header">Add Category</div>
            <div class="card-body">
                <form action="category-save" method="post">
                    <div class="mb-3">
                        <label for="Category_id" class="form-label">Category ID</label>
                        <input type="text" id="Category_id" name="Category_id" class="form-control" placeholder="Leave empty for new Category">
                    </div>
                    <div class="mb-3">
                        <label for="Category_name" class="form-label">Category Name</label>
                        <input type="text" id="Category_name" name="Category_name" class="form-control" required>
                    </div>
                    <div class="mb-3">
                        <label for="description" class="form-label">description</label>
                        <input type="text" id="description" name="description" class="form-control" required>
                    </div>
                    <button type="submit" class="btn btn-primary">Save</button>
                </form>
            </div>
        </div>

    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
