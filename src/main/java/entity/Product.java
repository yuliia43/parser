package entity;

import java.util.List;

/**
 * @author Yuliia Shcherbakova ON 07.11.2019
 * @project parser
 */
public class Product {

    private String articleId;
    private String productName;
    private String brand;
    private String price;
    private String color;
    private List<Product> anotherColors;

    public Product(String articleId, String productName, String brand, String price, String color) {
        this.articleId = articleId;
        this.productName = productName;
        this.brand = brand;
        this.price = price;
        this.color = color;
        anotherColors = null;
    }

    public Product(String articleId, String productName, String price, String color) {
        this.articleId = articleId;
        this.productName = productName;
        this.price = price;
        this.color = color;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public List<Product> getAnotherColors() {
        return anotherColors;
    }

    public void setAnotherColors(List<Product> anotherColors) {
        this.anotherColors = anotherColors;
    }

    public void addAnotherColors(Product anotherColor) {
        this.anotherColors.add(anotherColor);
    }
}
