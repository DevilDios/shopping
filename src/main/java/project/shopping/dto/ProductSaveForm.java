package project.shopping.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter @Setter
public class ProductSaveForm {

    @NotBlank
    private String name;

    @NotNull
    private int price;

    @NotNull
    private int stock;
}
