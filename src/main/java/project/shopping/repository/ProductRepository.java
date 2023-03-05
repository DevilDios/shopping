package project.shopping.repository;


import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import project.shopping.domain.Product;

import java.util.List;

/**
 * 상품 리포지터리
 */
@RequiredArgsConstructor
@Repository
public class ProductRepository {

    private final EntityManager entityManager;

    public void save(Product product) {
        entityManager.persist(product);
    }

    public Product findOne(Long id) {
        return entityManager.find(Product.class, id);
    }

    public List<Product> findAll() {
        return entityManager.createQuery("select p from Product p", Product.class).getResultList();
    }
}
