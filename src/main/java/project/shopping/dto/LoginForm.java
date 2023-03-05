package project.shopping.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class LoginForm {

    @Pattern(regexp="^[a-z]+[a-z0-9]{1,19}$")
    private String userId;

    @NotBlank
    private String password;
}
