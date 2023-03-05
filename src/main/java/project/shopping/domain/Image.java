package project.shopping.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Service;

/**
 * 이미지
 */
@Getter
@ToString
@Entity
public class Image {

    @Id
    @GeneratedValue
    @Column(name = "image_id")
    private Long id;

    private String realName;

    private String UUIDName;

    public static Image createImage(String realName, String UUIDName) {
        Image image = new Image();
        image.imageInit(realName, UUIDName);
        return image;
    }

    public void imageInit(String realName, String UUIDName) {
        this.realName = realName;
        this.UUIDName = UUIDName;
    }
}
