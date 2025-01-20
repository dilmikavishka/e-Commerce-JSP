package lk.ijse.ecommercecosmaticswebsite.dto;

public class CartItemDTO {
    private String cart_item_id;
    private String cart_id;
    private String product_id;
    private int quantity;

    public CartItemDTO(){

    }

    public CartItemDTO(String cart_item_id, String cart_id, String product_id, int quantity) {
        this.cart_item_id = cart_item_id;
        this.cart_id = cart_id;
        this.product_id = product_id;
        this.quantity = quantity;
    }

    public String getCart_item_id() {
        return cart_item_id;
    }

    public void setCart_item_id(String cart_item_id) {
        this.cart_item_id = cart_item_id;
    }

    public String getCart_id() {
        return cart_id;
    }

    public void setCart_id(String cart_id) {
        this.cart_id = cart_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "CartItemDTO{" +
                "cart_item_id='" + cart_item_id + '\'' +
                ", cart_id='" + cart_id + '\'' +
                ", product_id='" + product_id + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
