package qwins.myshop.category.dto;

import lombok.*;
import qwins.myshop.attribute.AttributeRule;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryCreateDTO {
    private String name;
    private List<AttributeRule> allowedAttributes;
}
