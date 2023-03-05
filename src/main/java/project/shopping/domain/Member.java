package project.shopping.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 회원
 */
@ToString
@Getter
@Setter
@Entity
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(name = "userid")
    private String userId;

    private String password;

    private String name;

    private String address;

    private String phone;

    @Enumerated(EnumType.STRING)
    private MemberStatus memberStatus;

    public static Member createMember(String userId, String password, String name, String address, String phone, MemberStatus memberStatus) {
        Member member = new Member();
        member.setUserId(userId);
        member.setPassword(password);
        member.setName(name);
        member.setAddress(address);
        member.setPhone(phone);
        member.setMemberStatus(memberStatus);
        return member;
    }

    public void memberInit(String userId, String password, String name, String address, String phone, MemberStatus memberStatus) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.memberStatus = memberStatus;
    }

    public void memberModifyInit(String password, String name, String address, String phone) {
        if (!password.isEmpty()) {
            this.password = password;
        }
        this.name = name;
        this.address = address;
        this.phone = phone;
    }
}
