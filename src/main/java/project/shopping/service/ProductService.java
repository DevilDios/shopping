package project.shopping.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.shopping.domain.Image;
import project.shopping.domain.Product;
import project.shopping.repository.ImageRepository;
import project.shopping.repository.ProductRepository;

import java.util.List;

//상품 서비스
@RequiredArgsConstructor
@Service
public class ProductService {

    private final ImageRepository imageRepository;
    private final ProductRepository productRepository;

    //상품 등록
    @Transactional
    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    //상품 전체 조회
    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    //상품 id 조회
    @Transactional(readOnly = true)
    public Product findOne(Long id) {
        return productRepository.findOne(id);
    }
}
