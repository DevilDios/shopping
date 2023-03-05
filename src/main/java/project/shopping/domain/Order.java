package project.shopping.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * 주문 정보
 */
@ToString
@Getter @Setter
@Table(name = "orders")
@Entity
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "orders_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    public static Order createOrder(Member member, LocalDateTime orderDate, OrderStatus orderStatus) {
        Order order = new Order();
        order.init(member, orderDate, orderStatus);
        return order;
    }

    private void init(Member member, LocalDateTime orderDate, OrderStatus orderStatus) {
        this.member = member;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
    }
}
