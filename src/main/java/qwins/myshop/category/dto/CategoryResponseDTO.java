package qwins.myshop.category.dto;

import lombok.*;
import qwins.myshop.attribute.AttributeRule;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryResponseDTO {
    private Long id;
    private String name;
    private List<AttributeRule> attributes;
}
