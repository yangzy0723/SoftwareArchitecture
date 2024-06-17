package data.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Product implements Serializable {
    private String id;
    private String name;
    private double price;
    private String image;

    public Product(String id, String name, double price, String image) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
    }

    @Override
    public String toString() {
        return "Product{" + "id='" + id + '\'' + ", name='" + name + '\'' + ", price=" + price + ", image='" + image + '\'' + '}';
    }
}
