<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Customer Management System</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
  <style>
    body {
      background-image: url("111.jpg");
      background-size: cover;
      background-attachment: fixed;
      color: #333;
      font-family: Arial, sans-serif;
    }
    .profile-box {
      display: flex;
      align-items: center;
      margin-right: 20px;
      border: 2px solid #ffb3cc;
      border-radius: 8px;
      padding: 10px;
      background-color: #ffb3cc;
      color: #fff;
    }
    .profile-box img {
      border-radius: 50%;
      width: 40px;
      height: 40px;
      margin-right: 10px;
    }
    .profile-box span {
      font-weight: bold;
      font-size: 16px;
    }
    .content-header {
      display: flex;
      align-items: center;
      justify-content: center;
    }
    .box-section {
      display: flex;
      flex-wrap: wrap;
      gap: 20px;
      justify-content: center;
      margin-top: 20px;
    }
    .box {
      background-color: #fff;
      border: 1px solid #ddd;
      border-radius: 10px;
      padding: 20px;
      width: 300px;
      box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
      text-align: center;
      transition: transform 0.3s ease, box-shadow 0.3s ease;
    }
    .box:hover {
      transform: translateY(-5px);
      box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);
    }
    .box h4 {
      font-size: 1.5rem;
      margin-bottom: 10px;
    }
    .box p {
      font-size: 1rem;
      margin-bottom: 15px;
    }
    .badge {
      font-size: 0.9rem;
      padding: 5px 10px;
    }
    #chart-section {
      margin-top: 20px;
    }
  </style>
</head>
<body>
<jsp:include page="sidebar.jsp" />
<div class="content">
  <div class="content-header">
    <!-- Profile Box -->
    <div class="profile-box">
      <img id="profile-pic" src="https://via.placeholder.com/40" alt="Profile Picture">
      <span id="profile-name">Loading...</span>
    </div>
    <h1 class="text-center">LuxeBloom</h1>
  </div>
  <hr>


  <div class="box-section">
    <!-- Hot Deals -->
    <div class="box">
      <h4>Hot Deals</h4>
      <p>50% off on Product A</p>
      <span class="badge bg-danger">Limited Time</span>
    </div>
    <div class="box">
      <h4>Hot Deals</h4>
      <p>Buy 1 Get 1 Free</p>
      <span class="badge bg-warning">Hurry!</span>
    </div>
    <div class="box">
      <h4>Hot Deals</h4>
      <p>Free Shipping on Orders Above $50</p>
      <span class="badge bg-primary">New</span>
    </div>

    <!-- Hot Sales -->
    <div class="box">
      <h4>Hot Sales</h4>
      <p>Product A</p>
      <span class="badge bg-danger">$29.99</span>
    </div>
    <div class="box">
      <h4>Hot Sales</h4>
      <p>Product B</p>
      <span class="badge bg-danger">$49.99</span>
    </div>
    <div class="box">
      <h4>Hot Sales</h4>
      <p>Product C</p>
      <span class="badge bg-danger">$19.99</span>
    </div>

    <!-- Recents -->
    <div class="box">
      <h4>Recents</h4>
      <p><strong>Order Placed:</strong> Order #12345</p>
      <p>Jan 10, 2025</p>
    </div>
    <div class="box">
      <h4>Recents</h4>
      <p><strong>Customer Signed Up:</strong></p>
      <p>john_doe@example.com</p>
    </div>
    <div class="box">
      <h4>Recents</h4>
      <p><strong>New Review:</strong> "Great product!"</p>
      <p>Product A</p>
    </div>
  </div>

  <!-- Analytics Section -->
  <div id="chart-section" class="text-center">
    <h3>Analytics</h3>
    <canvas id="customerChart" width="400" height="200"></canvas>
  </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script>

  const user = {
    id: '<%= session.getAttribute("userId") %>',
    username: '<%= session.getAttribute("username") %>',
    email: '<%= session.getAttribute("email") %>',
    role: '<%= session.getAttribute("role") %>'
  };

  console.log(user)

  localStorage.setItem('user', JSON.stringify(user));


  const profileName = document.getElementById('profile-name');
  if (profileName && user.username) {
    profileName.textContent = user.username;
  }


  const ctx = document.getElementById('customerChart').getContext('2d');
  const customerChart = new Chart(ctx, {
    type: 'bar',
    data: {
      labels: ['Customer 1', 'Customer 2', 'Customer 3', 'Customer 4'],
      datasets: [{
        label: 'Number of Orders',
        data: [12, 19, 3, 5],
        backgroundColor: [
          'rgba(255, 99, 132, 0.2)',
          'rgba(54, 162, 235, 0.2)',
          'rgba(255, 206, 86, 0.2)',
          'rgba(75, 192, 192, 0.2)'
        ],
        borderColor: [
          'rgba(255, 99, 132, 1)',
          'rgba(54, 162, 235, 1)',
          'rgba(255, 206, 86, 1)',
          'rgba(75, 192, 192, 1)'
        ],
        borderWidth: 1
      }]
    },
    options: {
      responsive: true,
      plugins: {
        legend: {
          position: 'top',
        },
        title: {
          display: true,
          text: 'Customer Analytics'
        }
      }
    }
  });
</script>
</body>
</html>
