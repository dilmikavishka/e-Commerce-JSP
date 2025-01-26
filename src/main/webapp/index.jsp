<%@ page import="lk.ijse.ecommercecosmaticswebsite.dto.ProductDTO" %>
<%@ page import="java.util.List" %>
<%@ page import="com.google.gson.Gson" %>
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
      background-color: #f8f9fa;
      color: #333;
      font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
      margin: 0;
      padding: 0;
    }


    .search-bar {
      position: relative;
      max-width: 300px;
      margin-right: 15px;
    }

    .search-bar input {
      width: 100%;
      padding: 5px 10px;
      border-radius: 15px;
      border: none;
      font-size: 14px;
      outline: none;
      box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    }

    .search-bar .icon {
      position: absolute;
      top: 50%;
      right: 10px;
      transform: translateY(-50%);
      font-size: 14px;
      color: #6c757d;
      pointer-events: none;
    }

    .profile-icon {
      width: 40px;
      height: 40px;
      border-radius: 50%;
      object-fit: cover;
      border: 2px solid #fff;
    }

    .carousel-inner img {
      height: 500px;
      object-fit: cover;
    }

    .carousel {
      margin-top: 20px;
      border-radius: 10px;
      overflow: hidden;
      box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
    }

    .content {
      padding: 80px 20px 20px;
    }


    .top-products {
      padding: 40px 20px;
      background-color: #ffffff;
      border-radius: 10px;
      box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    }

    .top-products h2 {
      font-size: 28px;
      color: #333;
      font-weight: bold;
      margin-bottom: 20px;
    }

    .top-products .card {
      width: 250px;
      height: 400px;
      border: none;
      border-radius: 8px;
      box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
      transition: transform 0.3s, box-shadow 0.3s;
      margin: 0 auto;
    }

    .top-products .card:hover {
      transform: translateY(-5px);
      box-shadow: 0 4px 10px rgba(0, 0, 0, 0.15);
    }

    .top-products .card img {
      width: 100%;
      height: 250px;
      object-fit: contain;
      border-top-left-radius: 8px;
      border-top-right-radius: 8px;
    }

    .top-products .card-body {
      padding: 15px;
      display: flex;
      flex-direction: column;
      justify-content: space-between;
      height: calc(100% - 150px);
    }

    .top-products .card-title {
      font-size: 18px;
      font-weight: bold;
      color: #007bff;
      margin-bottom: 10px;
    }

    .top-products .card-text {
      font-size: 14px;
      color: #555;
      margin-bottom: 5px;
      flex-grow: 1;
    }

    .suggestion-box {
      position: absolute;
      background-color: #fff;
      box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
      border-radius: 5px;
      max-height: 400px;
      overflow-y: auto;
      width: 100%;
      z-index: 1000;
      padding: 10px;
      display: none;
      flex-direction: column;
      gap: 10px;

    }

    .suggestion-card {
      display: flex;
      align-items: center;
      padding: 5px;
      border: 1px solid #ddd;
      border-radius: 8px;
      background-color: #f8f9fa;
      transition: background-color 0.3s ease;
      cursor: pointer;
      width: 150px;
    }

    .suggestion-card:hover {
      background-color: #e9ecef;
    }

    .suggestion-image {
      width: 60px;
      height: 60px;
      object-fit: contain;
      border-radius: 5px;
      margin-right: 10px;
    }

    .suggestion-name {
      font-size: 14px;
      color: #333;
      font-weight: lighter;
    }
    footer {
      font-size: 14px;
    }

    footer h5 {
      font-size: 18px;
      font-weight: bold;
    }

    .offers-section {
      background-color: #f8f9fa;
      padding: 40px 20px;
      border-radius: 10px;
      box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    }

    .offers-section .card {
      border: none;
      border-radius: 8px;
      box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
      transition: transform 0.3s, box-shadow 0.3s;
    }

    .offers-section .card:hover {
      transform: translateY(-5px);
      box-shadow: 0 4px 10px rgba(0, 0, 0, 0.15);
    }

    .offers-section .card img {
      width: 100%;
      height: 200px;
      object-fit: cover;
      border-top-left-radius: 8px;
      border-top-right-radius: 8px;
    }





  </style>
</head>
<body>
<jsp:include page="sidebar.jsp" />
<div class="container content">
  <nav class="navbar">
    <div class="container-fluid">
      <div class="d-flex align-items-center ms-auto">
        <div class="search-bar">
          <input id="searchInput" type="text" placeholder="Search..." oninput="handleSearch()" />
          <div id="suggestionBox" class="suggestion-box">
            <div id="suggestionTemplate" style="display: none;">
              <div class="suggestion-card d-flex align-items-center">
                <img class="suggestion-image" />
                <span class="suggestion-name"></span>
              </div>
            </div>
          </div>

        </div>

        <img src="images/Image-4.png" alt="Profile Icon" class="profile-icon">
      </div>
    </div>
  </nav>


  <div id="carouselExampleIndicators" class="carousel slide" data-bs-ride="carousel" data-bs-interval="2000">
    <div class="carousel-indicators">
      <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="0" class="active" aria-current="true" aria-label="Slide 1"></button>
      <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="1" aria-label="Slide 2"></button>
      <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="2" aria-label="Slide 3"></button>
    </div>
    <div class="carousel-inner">
      <div class="carousel-item active">
        <img src="images/work1.jpg" class="d-block w-100" alt="First Slide">
      </div>
      <div class="carousel-item">
        <img src="images/work2.jpg" class="d-block w-100" alt="Second Slide">
      </div>
      <div class="carousel-item">
        <img src="images/work3.jpg" class="d-block w-100" alt="Third Slide">
      </div>
    </div>
  </div>

  <div class="top-products mt-5">
    <h2 class="text-center mb-4">Top Products of the month</h2>
    <div class="row">
      <%
        List<ProductDTO> allProducts = (List<ProductDTO>) session.getAttribute("allProducts");

        if (allProducts != null && !allProducts.isEmpty()) {
          int totalProducts = allProducts.size();
          int startIndex = Math.max(0, totalProducts - 8);
          for (int i = startIndex; i < totalProducts; i++) {
            ProductDTO product = allProducts.get(i);
      %>
      <div class="col-md-3 mb-4">
        <div class="card product-item" data-id="<%= product.getProduct_id() %>" data-name="<%= product.getProduct_name().toLowerCase() %>">
          <img src="<%= product.getBase64Image() %>" class="card-img-top" alt="<%= product.getProduct_name() %>">
          <div class="card-body">
            <h5 class="card-title"><%= product.getProduct_name() %></h5>
            <p class="card-text">Price: LKR <%= product.getPrice() %></p>
          </div>
        </div>
      </div>
      <%
        }
      } else {
      %>
      <p class="text-center">No products available at the moment.</p>
      <%
        }
      %>
    </div>
  </div>


</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
<script>
  const user = {
    id: '<%= session.getAttribute("userId") %>',
    username: '<%= session.getAttribute("username") %>',
    email: '<%= session.getAttribute("email") %>',
    role: '<%= session.getAttribute("role") %>'
  };

  console.log(user);

  localStorage.setItem('user', JSON.stringify(user));

  function handleSearch() {
    const searchQuery = document.getElementById('searchInput').value.toLowerCase();
    const suggestionBox = document.getElementById('suggestionBox');
    const suggestionTemplate = document.getElementById('suggestionTemplate');
    const allProducts = JSON.parse('<%= new Gson().toJson(session.getAttribute("allProducts")) %>');


    if (!suggestionTemplate) {
      console.error("Suggestion template not found!");
      return;
    }


    suggestionBox.innerHTML = '';


    const products = document.querySelectorAll('.product-item');
    products.forEach(product => product.style.display = 'block');


    if (!searchQuery.trim()) {
      suggestionBox.style.display = 'none';
      return;
    }


    const filteredProducts = allProducts.filter(product =>
            product.product_name.toLowerCase().includes(searchQuery)
    );

    if (!filteredProducts.length) {
      suggestionBox.style.display = 'none';
      return;
    }

    filteredProducts.forEach(product => {
      const suggestionCard = suggestionTemplate.cloneNode(true);
      suggestionCard.style.display = 'flex';
      suggestionCard.removeAttribute('id');

      const suggestionImage = suggestionCard.querySelector('.suggestion-image');
      const suggestionName = suggestionCard.querySelector('.suggestion-name');
      suggestionImage.src = product.base64Image;
      suggestionImage.alt = product.product_name;
      suggestionName.textContent = product.product_name;

      suggestionCard.onclick = () => {
        document.getElementById('searchInput').value = product.product_name;
        suggestionBox.style.display = 'none';


        products.forEach(p => {
          if (p.dataset.id === product.product_id) {
            p.style.display = 'block';
            p.scrollIntoView({ behavior: 'smooth', block: 'center' });
          } else {
            p.style.display = 'none';
          }
        });
      };

      suggestionBox.appendChild(suggestionCard);
    });

    suggestionBox.style.display = 'block';
  }


  document.addEventListener('click', (e) => {
    if (!e.target.closest('.search-bar')) {
      document.getElementById('suggestionBox').style.display = 'none';
    }
  });
</script>
</body>
</html>

