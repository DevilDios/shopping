package project.shopping.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.shopping.domain.OrderProduct;
import project.shopping.domain.OrderStatus;
import project.shopping.domain.Product;
import project.shopping.repository.OrderProductRepository;

import java.util.List;
import java.util.Optional;

/**
 * 주문상품 서비스
 */
@RequiredArgsConstructor
@Service
public class OrderProductService {

    private final OrderProductRepository orderProductRepository;

    //주문상품 저장
    @Transactional
    public OrderProduct save(OrderProduct orderProduct) {
        return orderProductRepository.save(orderProduct);
    }

    @Transactional(readOnly = true)
    //주문상품 전체 조회
    public List<OrderProduct> findAll() {
        return orderProductRepository.findAll();
    }

    //주문상품 회원id 조회
    @Transactional(readOnly = true)
    public List<OrderProduct> findByUserId(String userId) {
        return orderProductRepository.findAll().stream().filter(orderProduct -> orderProduct.getOrder().getMember().getUserId().equals(userId)).toList();
    }

    //주문상품 id 조회
    @Transactional(readOnly = true)
    public OrderProduct findById(Long id) {
        return orderProductRepository.findById(id).orElse(null);
    }

    //주문상품 상태 수정 CART, ORDER, CANCEL, COMPLETE
    @Transactional
    public void orderStatusUpdate(Long id, OrderStatus orderStatus) {
        OrderProduct orderProduct = orderProductRepository.findById(id).orElse(null);
        orderProduct.getOrder().setOrderStatus(orderStatus);
    }
}
