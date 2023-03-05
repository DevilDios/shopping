package project.shopping.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberModifyForm {
    @Pattern(regexp="^[a-z]+[a-z0-9]{1,19}$")
    private String userId;

    private String password;

    @NotBlank
    private String name;

    @NotBlank
    private String address;

    @NotBlank
    private String phone;

    public static MemberModifyForm createMemberModify(String userId, String password, String name, String address, String phone) {
        MemberModifyForm memberModifyForm = new MemberModifyForm();
        memberModifyForm.setUserId(userId);
        memberModifyForm.setPassword(password);
        memberModifyForm.setName(name);
        memberModifyForm.setAddress(address);
        memberModifyForm.setPhone(phone);
        return memberModifyForm;
    }

    public void memberModifyInit(String userId, String password, String name, String address, String phone) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.address = address;
        this.phone = phone;
    }
}
