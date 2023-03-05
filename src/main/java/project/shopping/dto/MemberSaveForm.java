package project.shopping.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

@ToString
@Getter @Setter
public class MemberSaveForm {

    @Pattern(regexp="^[a-z]+[a-z0-9]{1,19}$")
    private String userId;

    @Pattern(regexp="^[a-z]+[a-z0-9]{3,16}$")
    private String password;

    @NotBlank
    private String name;

    @NotBlank
    private String address;

    @NotBlank
    private String phone;

    public static MemberSaveForm memberSaveInit(String userId, String password, String name, String address, String phone) {

        MemberSaveForm memberSaveForm = new MemberSaveForm();
        memberSaveForm.userId = userId;
        memberSaveForm.password = password;
        memberSaveForm.name = name;
        memberSaveForm.address = address;
        memberSaveForm.phone = phone;

        return memberSaveForm;
    }
}
