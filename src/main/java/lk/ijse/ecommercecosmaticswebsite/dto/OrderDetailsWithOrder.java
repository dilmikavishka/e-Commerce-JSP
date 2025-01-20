package lk.ijse.ecommercecosmaticswebsite.dto;


public class OrderDetailsWithOrder {
    private String orderId;
    private String userId;
    private String orderDate;
    private double totalPrice;
    private String status;
    private String orderDetailId;
    private String productId;
    private int quantity;
    private double price;
    private double totalPricePerItem;

    public OrderDetailsWithOrder(){

    }

    public OrderDetailsWithOrder(String orderId, String userId, String orderDate, double totalPrice, String status, String orderDetailId, String productId, int quantity, double price, double totalPricePerItem) {
        this.orderId = orderId;
        this.userId = userId;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
        this.status = status;
        this.orderDetailId = orderDetailId;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
        this.totalPricePerItem = totalPricePerItem;
    }


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(String orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getTotalPricePerItem() {
        return totalPricePerItem;
    }

    public void setTotalPricePerItem(double totalPricePerItem) {
        this.totalPricePerItem = totalPricePerItem;
    }

    @Override
    public String toString() {
        return "OrderDetailsWithOrder{" +
                "orderId='" + orderId + '\'' +
                ", userId='" + userId + '\'' +
                ", orderDate='" + orderDate + '\'' +
                ", totalPrice=" + totalPrice +
                ", status='" + status + '\'' +
                ", orderDetailId='" + orderDetailId + '\'' +
                ", productId='" + productId + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", totalPricePerItem=" + totalPricePerItem +
                '}';
    }
}


