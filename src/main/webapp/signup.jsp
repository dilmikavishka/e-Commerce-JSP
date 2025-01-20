<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sign Up</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-image: url("8595104.jpg");
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
            color: #fff;
            font-family: 'Arial', sans-serif;
        }

        .signup-container {
            background-color: rgba(255, 255, 255, 0.15);
            backdrop-filter: blur(10px);
            border-radius: 15px;
            padding: 2rem;
            width: 100%;
            max-width: 400px;
            box-shadow: 0 8px 15px rgba(0, 0, 0, 0.3);
        }

        .signup-container h1 {
            text-align: center;
            margin-bottom: 1.5rem;
            font-size: 2rem;
            color: #fff;
        }

        .form-label {
            font-weight: bold;
        }

        .btn-primary {
            background-color: #4e54c8;
            border-color: #4e54c8;
        }

        .btn-primary:hover {
            background-color: #5c63d8;
            border-color: #5c63d8;
        }

        .alert {
            margin-top: 1rem;
            text-align: center;
        }
    </style>
</head>
<body>

<div class="signup-container">
    <h1>Create an Account</h1>
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
    <form action="signup" method="post" id="signupForm">
        <div class="mb-3">
            <label for="user_id" class="form-label">User ID (for Update)</label>
            <input type="text" id="user_id" name="user_id" class="form-control" placeholder="Leave empty for new user">
        </div>
        <div class="mb-3">
            <label for="username" class="form-label">Username</label>
            <input type="text" id="username" name="username" class="form-control" required>
        </div>
        <div class="mb-3">
            <label for="email" class="form-label">Email</label>
            <input type="email" id="email" name="email" class="form-control" required>
        </div>
        <div class="mb-3">
            <label for="password" class="form-label">Password</label>
            <input type="password" id="password" name="password" class="form-control" required oninput="validatePassword()">
            <small id="passwordHelp" class="form-text text-muted">
                Password must be at least 8 characters long.
            </small>
        </div>
        <div class="mb-3">
            <label for="role" class="form-label">Role</label>
            <select id="role" name="role" class="form-control" required>
                <option value="admin">admin</option>
                <option value="customer">customer</option>
                <option selected value="user">user</option>
            </select>
        </div>
        <button type="submit" class="btn btn-primary">Save</button>
    </form>
    <div class="text-center mt-3">
        <p>Already have an account? <a href="login.jsp" class="text-white">Login here</a></p>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>

<script>
    function validatePassword() {
        var role = document.getElementById("role").value;
        var password = document.getElementById("password").value;
        var passwordHelp = document.getElementById("passwordHelp");

        if (role === "admin") {
            var regex = /^(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*])[A-Za-z\d!@#$%^&*]{8,}$/;
            if (!regex.test(password)) {
                // passwordHelp.innerHTML = "Password must be at least 8 characters long, include at least one uppercase letter, one number, and one special character.";
                passwordHelp.innerHTML = "Password must be for Admin Format";
            } else {
                passwordHelp.innerHTML = "Password is valid.";
            }
        } else {
            passwordHelp.innerHTML = "Password must be at least 8 characters long.";
        }
    }
</script>

</body>
</html>
