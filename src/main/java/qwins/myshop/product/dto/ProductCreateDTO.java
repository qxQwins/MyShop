package qwins.myshop.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import qwins.myshop.category.Category;

import java.math.BigDecimal;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductCreateDTO {

    private String name;

    private String description;

    private BigDecimal price;

    private Long categoryId;

    private Map<String, Object> attributes;


}
