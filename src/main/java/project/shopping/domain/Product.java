package project.shopping.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 상품
 */
@ToString
@Getter
@Entity
public class Product {

    @Id @GeneratedValue
    @Column(name = "product_id")
    private Long Id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id")
    private Image image;

    private String name;

    private int price;

    private int stock;

    public static Product createProduct(Image image, String name, int price, int stock) {
        Product product = new Product();
        product.productInit(image, name, price, stock);
        return product;
    }

    private void productInit(Image image, String name, int price, int stock) {
        this.image = image;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }
}
