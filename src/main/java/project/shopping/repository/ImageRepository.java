package project.shopping.repository;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import project.shopping.domain.Image;

/**
 * 이미지 리포지터리
 */
@Repository
public class ImageRepository {

    EntityManager entityManager;

    public void save(Image image) {
        entityManager.persist(image);
    }

    public Image findOne(Long id) {
        return entityManager.find(Image.class, id);
    }
}
