<!DOCTYPE html>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User Management</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<jsp:include page="sidebar.jsp" />
<div class="content">
    <div class="container my-4">
        <h1 class="text-center">User Management</h1>

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

        <div class="container my-4">
            <!-- User Form -->
            <form action="user-action" method="post" id="userForm">
                <input type="hidden" name="action" id="action" value="update">

                <div class="mb-3">
                    <label for="userSelect" class="form-label">Select User</label>
                    <select id="userSelect" class="form-select">
                        <option value="">Select a User</option>
                    </select>
                </div>

                <div class="mb-3">
                    <label for="id" class="form-label">User ID</label>
                    <input type="text" id="id" name="user_id" class="form-control" required readonly>
                </div>
                <div class="mb-3">
                    <label for="username" class="form-label">Username</label>
                    <input type="text" id="username" name="username" class="form-control">
                </div>
                <div class="mb-3">
                    <label for="email" class="form-label">Email</label>
                    <input type="email" id="email" name="email" class="form-control">
                </div>
                <div class="mb-3">
                    <label for="password" class="form-label">Password</label>
                    <input type="password" id="password" name="password" class="form-control">
                </div>

                <div class="mb-3">
                    <label for="role" class="form-label">Role</label>
                    <input type="text" id="role" name="role" class="form-control">
                </div>

                <button type="submit" class="btn btn-primary" onclick="setAction('update')">Update</button>
                <button type="submit" class="btn btn-danger" onclick="setAction('delete')">Delete</button>
            </form>
        </div>
    </div>
</div>

<script>

    const users = JSON.parse(localStorage.getItem('users')) || [];
    const userSelect = document.getElementById('userSelect');

    users.forEach(user => {
        const option = document.createElement('option');
        option.value = user.userId;
        option.textContent = user.userId;
        userSelect.appendChild(option);
    });

    userSelect.addEventListener('change', () => {
        const selectedId = userSelect.value;
        const selectedUser = users.find(u => u.userId === selectedId);

        if (selectedUser) {
            document.getElementById('id').value = selectedUser.userId;
            document.getElementById('username').value = selectedUser.username;
            document.getElementById('email').value = selectedUser.email;
            document.getElementById('password').value = selectedUser.password;
            document.getElementById('role').value = selectedUser.role;
        } else {
            document.getElementById('id').value = '';
            document.getElementById('username').value = '';
            document.getElementById('email').value = '';
            document.getElementById('password').value = '';
            document.getElementById('role').value = '';
        }
    });

    function setAction(action) {
        document.getElementById('action').value = action;
    }
</script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
