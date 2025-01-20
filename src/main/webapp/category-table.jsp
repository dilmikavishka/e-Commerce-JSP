<%@ page import="java.util.List" %>
<%@ page import="lk.ijse.ecommercecosmaticswebsite.dto.CategoryDTO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet"/>
    <title>Category Management</title>
</head>
<body>
<jsp:include page="sidebar.jsp" />
<div class="content">
    <h1 class="text-center">Category Table</h1>


    <%
        String message = request.getParameter("message");
        String error = request.getParameter("error");
        if (message != null) {
    %>
    <div class="alert alert-success alert-dismissible fade show" role="alert">
        <%= message %>
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>
    <script>
        setTimeout(function() {
            window.location.reload();
        }, 2000);
    </script>
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
        List<CategoryDTO> categories = (List<CategoryDTO>) request.getAttribute("categories");
    %>
    <div class="card">
        <div class="card-header">Category List</div>
        <div class="card-body">
            <table class="table table-bordered table-hover">
                <thead class="table-dark">
                <tr>
                    <th>Id</th>
                    <th>Name</th>
                    <th>Description</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                <%
                    if (categories != null) {
                        for (CategoryDTO categoryDTO : categories) {
                %>
                <tr>
                    <td><%= categoryDTO.getId() %></td>
                    <td><%= categoryDTO.getName() %></td>
                    <td><%= categoryDTO.getDescription() %></td>

                    <td>
                        <!-- Edit Button -->
                        <button class="btn btn-sm btn-warning" data-bs-toggle="modal" data-bs-target="#updateModal<%= categoryDTO.getId() %>">Edit</button>

                        <!-- Delete Button -->
                        <button class="btn btn-sm btn-danger" data-bs-toggle="modal" data-bs-target="#deleteModal<%= categoryDTO.getId() %>">Delete</button>
                    </td>
                </tr>
                <%
                    }
                } else {
                %>
                <tr>
                    <td colspan="4" class="text-center">No categories found</td>
                </tr>
                <%
                    }
                %>
                </tbody>
            </table>
        </div>
    </div>
</div>


<%
    if (categories != null) {
        for (CategoryDTO categoryDTO : categories) {
%>
<div class="modal fade" id="updateModal<%= categoryDTO.getId() %>" tabindex="-1" aria-labelledby="updateModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="updateModalLabel">Update Category</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <form action="category-table" method="post">
                <div class="modal-body">
                    <input type="hidden" name="category_id" value="<%= categoryDTO.getId() %>">
                    <div class="mb-3">
                        <label for="category_name" class="form-label">Category Name</label>
                        <input type="text" class="form-control" id="category_name" name="category_name" value="<%= categoryDTO.getName() %>" required>
                    </div>
                    <div class="mb-3">
                        <label for="description" class="form-label">Description</label>
                        <input type="text" class="form-control" id="description" name="description" value="<%= categoryDTO.getDescription() %>" required>
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


<div class="modal fade" id="deleteModal<%= categoryDTO.getId() %>" tabindex="-1" aria-labelledby="deleteModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="deleteModalLabel">Delete Category</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <p>Are you sure you want to delete this category?</p>
            </div>
            <div class="modal-footer">
                <form action="category-table" method="post">
                    <input type="hidden" name="category_id" value="<%= categoryDTO.getId() %>">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                    <button type="submit" class="btn btn-danger" name="action" value="delete">Yes, Delete</button>
                </form>
            </div>
        </div>
    </div>
</div>
<%
        }
    }
%>

<script>
    const categories = [];

    <% if (categories != null) { %>
    <% for (CategoryDTO categoryDTO : categories) { %>
    categories.push({
        Id: "<%= categoryDTO.getId() %>",
        name: "<%= categoryDTO.getName() %>",
        description: "<%= categoryDTO.getDescription() %>",

    });
    <% } %>
    <% } %>


    if (categories.length > 0) {
        localStorage.setItem('categories', JSON.stringify(categories));
        console.log('categories data stored in localStorage:', categories);
    }
</script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
