package project.shopping.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

/**
 * 주문된 상품
 */
@ToString
@Entity
@Getter
@Setter
public class OrderProduct {

    @Id
    @GeneratedValue
    @Column(name = "order_product_id")
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, fetch = LAZY)
    @JoinColumn(name = "orders_id")
    private Order order;

    @OneToOne(cascade = CascadeType.ALL, fetch = LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    private int quantity;

    private int orderPrice;

    public static OrderProduct createOrderProduct(Order order, Product product, int quantity) {
        OrderProduct orderProduct = new OrderProduct();
        orderProduct.init(order, product, quantity);
        return orderProduct;
    }

    public void init(Order order, Product product, int quantity) {
        this.order = order;
        this.product = product;
        this.quantity = quantity;
        this.orderPrice = product.getPrice() * quantity;
    }
}
