<%@ page import="java.util.List" %>
<%@ page import="lk.ijse.ecommercecosmaticswebsite.dto.UserDTO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Customer Management</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<jsp:include page="sidebar.jsp" />
<div class="content">
    <%
        List<UserDTO> users = (List<UserDTO>) request.getAttribute("users");
    %>
    <div class="card">
        <div class="card-header">User List</div>
        <div class="card-body">
            <table class="table table-bordered table-hover">
                <thead class="table-dark">
                <tr>
                    <th>User ID</th>
                    <th>Username</th>
                    <th>Email</th>
                    <th>Password</th>
                    <th>Role</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                <%
                    if (users != null && !users.isEmpty()) {
                        for (UserDTO user : users) {
                %>
                <tr>
                    <td><%= user.getUserId() %></td>
                    <td><%= user.getUsername() %></td>
                    <td><%= user.getEmail() %></td>
                    <td><%= user.getPassword() %></td>
                    <td><%= user.getRole() %></td>
                    <td>
                        <a href="user-edit?id=<%= user.getUserId() %>" class="btn btn-sm btn-warning">Edit</a>
                        <a href="user-delete?user_id=<%= user.getUserId() %>" class="btn btn-sm btn-danger" onclick="return confirm('Are you sure you want to delete this user?');">Delete</a>
                    </td>
                </tr>
                <%
                    }
                } else {
                %>
                <tr>
                    <td colspan="6" class="text-center">No users found</td>
                </tr>
                <%
                    }
                %>
                </tbody>
            </table>
        </div>
    </div>
</div>

<script>
    // Collect user data
    const users = [];

    <% if (users != null) { %>
    <% for (UserDTO user : users) { %>
    users.push({
        userId: "<%= user.getUserId() %>",
        username: "<%= user.getUsername() %>",
        email: "<%= user.getEmail() %>",
        password: "<%= user.getPassword() %>",
        role: "<%= user.getRole() %>"
    });
    <% } %>
    <% } %>

    // Store the user data in localStorage
    if (users.length > 0) {
        localStorage.setItem('users', JSON.stringify(users));
        console.log('User data stored in localStorage:', users);
    }
</script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
