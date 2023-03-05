package project.shopping;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import project.shopping.domain.*;

import java.time.LocalDateTime;

/**
 * DB 테스트용 데이터 초기화
 */
@RequiredArgsConstructor
@Component
public class DBInit {

   private final DbInitService dbInitService;

    @PostConstruct
    public void init() {
//        dbInitService.memberInit();
//        dbInitService.productInit();
//        dbInitService.orderProduct();
    }

    @RequiredArgsConstructor
    @Component
    @Transactional
    public static class DbInitService {

        private final EntityManager entityManager;

        public void memberInit() {
            entityManager.persist(Member.createMember("admin","123","admin","admin","admin", MemberStatus.MANAGER));
            for (int i = 1; i <= 220; i++) {
                String userId = "member"+i;
                entityManager.persist(Member.createMember(userId, "1", "mmmm", "mmmmm", "1111111", MemberStatus.COMMON));
            }
        }

        public void productInit() {
            for (int i = 1; i <= 9; i++) {
                entityManager.persist(Product.createProduct(Image.createImage("asdf.jpg",i+".jpg"),"상품"+i,1000,7));
            }
        }

//        public void orderProduct() {
//
//            Member member = Member.createMember("member1", "1", "mmmm", "mmmmm", "1111111", MemberStatus.COMMON);
//            Image image = Image.createImage("asdf.jpg", "1.jpg");
//            Order order = Order.createOrder(member, LocalDateTime.now(), OrderStatus.ORDER);
//            Product product = Product.createProduct(Image.createImage("asdf.jpg","1.jpg"),"상품1",1000,7);
//            entityManager.persist(OrderProduct.createOrderProduct(order, product, null, 3));
//        }


    }
}
