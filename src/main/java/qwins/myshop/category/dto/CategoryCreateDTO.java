package qwins.myshop.category.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import qwins.myshop.attribute.AttributeRule;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryCreateDTO {
    @NotBlank(message = "Category name cannot be empty")
    private String name;
    private List<AttributeRule> allowedAttributes;
}
