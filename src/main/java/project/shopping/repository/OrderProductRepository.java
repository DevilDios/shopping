package project.shopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.shopping.domain.OrderProduct;

import java.util.List;

/**
 * 주문상품 리포지터리
 */
public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {
}
