package lk.ijse.ecommercecosmaticswebsite.entity;

public class Product {
    private String product_id;
    private String product_name;
    private String category_id;
    private Double price;
    private Integer Stock;
    private String description;
    private String base64Image;

    public Product(){}

    public Product(String product_id, String product_name, String category_id, Double price, Integer stock, String description, String base64Image) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.category_id = category_id;
        this.price = price;
        Stock = stock;
        this.description = description;
        this.base64Image = base64Image;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getStock() {
        return Stock;
    }

    public void setStock(Integer stock) {
        Stock = stock;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBase64Image() {
        return base64Image;
    }

    public void setBase64Image(String base64Image) {
        this.base64Image = base64Image;
    }

    @Override
    public String toString() {
        return "Product{" +
                "product_id='" + product_id + '\'' +
                ", product_name='" + product_name + '\'' +
                ", category_id='" + category_id + '\'' +
                ", price=" + price +
                ", Stock=" + Stock +
                ", description='" + description + '\'' +
                ", base64Image='" + base64Image + '\'' +
                '}';
    }
}
