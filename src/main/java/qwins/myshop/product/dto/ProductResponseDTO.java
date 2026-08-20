package qwins.myshop.product.dto;

import lombok.*;
import qwins.myshop.product.Product;

import java.math.BigDecimal;
import java.util.Map;

public record ProductResponseDTO(
        Long id,
        String name,
        String description,
        BigDecimal price,
        String categoryName,
        Map<String, Object> attributes
) {
    public ProductResponseDTO(Product product) {
        this(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getCategory().getName(),
                product.getAttributes()
        );
    }
}
