package project.shopping.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import project.shopping.domain.Order;

import javax.swing.text.html.parser.Entity;
import java.util.List;

/**
 * 주문정보 리포지터리
 */
@RequiredArgsConstructor
@Repository
public class OrderRepository {

    private final EntityManager entityManager;

    public void save(Order order) {
        entityManager.persist(order);
    }

    public List<Order> findAll() {
        return entityManager.createQuery("select o from Order o", Order.class).getResultList();
    }
}
