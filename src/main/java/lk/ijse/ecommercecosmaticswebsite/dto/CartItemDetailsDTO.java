package lk.ijse.ecommercecosmaticswebsite.dto;

public class CartItemDetailsDTO {
    private String productId;
    private String cartId;
    private String userID;
    private String productName;
    private int quantity;
    private double price;
    private double totalPricePerItem;


    public CartItemDetailsDTO(){

    }

    public CartItemDetailsDTO(String productId, String cartId, String userID, String productName, int quantity, double price, double totalPricePerItem) {
        this.productId = productId;
        this.cartId = cartId;
        this.userID = userID;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.totalPricePerItem = totalPricePerItem;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
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
        return "CartItemDetailsDTO{" +
                "productId='" + productId + '\'' +
                ", cartId='" + cartId + '\'' +
                ", userID='" + userID + '\'' +
                ", productName='" + productName + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", totalPricePerItem=" + totalPricePerItem +
                '}';
    }
}
