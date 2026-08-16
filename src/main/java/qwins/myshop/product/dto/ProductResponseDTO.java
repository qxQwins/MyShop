package qwins.myshop.product.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.Map;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponseDTO {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private String categoryName;
    private Map<String, Object> attributes;
}
