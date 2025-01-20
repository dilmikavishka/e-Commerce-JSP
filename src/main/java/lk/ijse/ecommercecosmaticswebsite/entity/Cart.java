package lk.ijse.ecommercecosmaticswebsite.entity;

public class Cart {
    private String cart_id;
    private String user_id;
    private Double totalPrice;

    public Cart() {
    }

    public Cart(String cart_id, String user_id, Double totalPrice) {
        this.cart_id = cart_id;
        this.user_id = user_id;
        this.totalPrice = totalPrice;
    }

    public String getCart_id() {
        return cart_id;
    }

    public void setCart_id(String cart_id) {
        this.cart_id = cart_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "cart_id='" + cart_id + '\'' +
                ", user_id='" + user_id + '\'' +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
