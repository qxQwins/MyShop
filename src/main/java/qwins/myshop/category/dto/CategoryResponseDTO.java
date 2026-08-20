package qwins.myshop.category.dto;

import lombok.*;
import qwins.myshop.attribute.AttributeRule;
import qwins.myshop.category.Category;

import java.util.List;
public record CategoryResponseDTO (
    Long id,
    String name,
    List<AttributeRule> attributes
){
    public CategoryResponseDTO(Category category){
        this(
                category.getId(),
                category.getName(),
                category.getAllowedAttributes()
        );
    }
}
